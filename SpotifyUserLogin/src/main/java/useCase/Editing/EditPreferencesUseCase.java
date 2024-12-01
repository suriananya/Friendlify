package useCase.Editing;

import api.SpotifyInteractor;
import entities.users.UserProfile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Use case for editing user preferences dynamically or manually based on their Spotify data.
 * Provides functionality to fetch user playlists, analyze top genres and artists, and update preferences.
 */
public class EditPreferencesUseCase {
    private final SpotifyInteractor interactor;
    private final UserProfile userProfile;

    /**
     * Constructs an EditPreferencesUseCase with a SpotifyInteractor and a UserProfile.
     *
     * @param interactor   the SpotifyInteractor instance for accessing Spotify API
     * @param userProfile  the user profile to be updated with preferences
     * @throws IllegalArgumentException if userProfile is null
     * @throws NullPointerException if interactor is null
     */
    public EditPreferencesUseCase(SpotifyInteractor interactor, UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("UserProfile cannot be null");
        }
        this.interactor = Objects.requireNonNull(interactor, "SpotifyInteractor cannot be null");
        this.userProfile = userProfile;
    }

    /**
     * Dynamically updates user preferences based on their Spotify playlists.
     *
     * @return an EditPreferencesResponse containing the outcome of the update operation
     */
    public EditPreferencesResponse execute() {
        try {
            Map<String, Integer> genreCounts = new HashMap<>();
            Map<String, Integer> artistCounts = new HashMap<>();

            JSONArray playlists = fetchPlaylists();
            if (playlists == null) {
                return new EditPreferencesResponse(false, "No playlists found.", null, null);
            }

            processPlaylists(playlists, genreCounts, artistCounts);

            String topGenre = getTopEntry(genreCounts, "Unknown");
            String topArtist = getTopEntry(artistCounts, "Unknown");

            updateUserProfile(topGenre, topArtist);

            return new EditPreferencesResponse(true, "Preferences updated dynamically.",
                    List.of(topGenre), List.of(topArtist));
        } catch (Exception e) {
            return new EditPreferencesResponse(false, "Error updating preferences dynamically: " + e.getMessage(), null, null);
        }
    }

    /**
     * Manually updates user preferences with provided genres and artists.
     *
     * @param newGenres a list of new genres to update the user profile with
     * @param newArtists a list of new artists to update the user profile with
     * @return an EditPreferencesResponse containing the outcome of the update operation
     */
    public EditPreferencesResponse execute(List<String> newGenres, List<String> newArtists) {
        if (isEmpty(newGenres) && isEmpty(newArtists)) {
            return new EditPreferencesResponse(false, "Genres and artists cannot both be empty.", null, null);
        }

        try {
            updateUserProfile(newGenres, newArtists);
            return new EditPreferencesResponse(true, "Preferences updated manually.", newGenres, newArtists);
        } catch (Exception e) {
            return new EditPreferencesResponse(false, "Error updating preferences manually: " + e.getMessage(), null, null);
        }
    }

    /**
     * Fetches playlists of the current user from Spotify.
     *
     * @return a JSONArray of playlists or null if no playlists are found
     */
    private JSONArray fetchPlaylists() {
        JSONObject playlistsJson = interactor.getCurrentUserPlaylists(5, 0);
        return (playlistsJson != null && playlistsJson.has("items")) ? playlistsJson.getJSONArray("items") : null;
    }

    /**
     * Processes playlists to update genre and artist counts.
     *
     * @param playlists a JSONArray of playlists to process
     * @param genreCounts a map to store genre counts
     * @param artistCounts a map to store artist counts
     */
    private void processPlaylists(JSONArray playlists, Map<String, Integer> genreCounts, Map<String, Integer> artistCounts) {
        for (int i = 0; i < playlists.length(); i++) {
            String playlistId = playlists.getJSONObject(i).getString("id");
            JSONArray tracks = fetchTracks(playlistId);
            if (tracks != null) {
                processTracks(tracks, genreCounts, artistCounts);
            }
        }
    }

    /**
     * Fetches tracks from a specific playlist.
     *
     * @param playlistId the ID of the playlist to fetch tracks from
     * @return a JSONArray of tracks or null if no tracks are found
     */
    private JSONArray fetchTracks(String playlistId) {
        JSONObject playlistItemsJson = interactor.getPlaylistItems(playlistId, 10, 0);
        return (playlistItemsJson != null && playlistItemsJson.has("items")) ? playlistItemsJson.getJSONArray("items") : null;
    }

    /**
     * Processes tracks to update genre and artist counts.
     *
     * @param tracks a JSONArray of tracks to process
     * @param genreCounts a map to store genre counts
     * @param artistCounts a map to store artist counts
     */
    private void processTracks(JSONArray tracks, Map<String, Integer> genreCounts, Map<String, Integer> artistCounts) {
        for (int j = 0; j < tracks.length(); j++) {
            JSONObject track = tracks.getJSONObject(j).getJSONObject("track");
            updateArtistCounts(track, artistCounts, genreCounts);
        }
    }

    /**
     * Updates artist and genre counts based on a track's metadata.
     *
     * @param track a JSONObject representing a track
     * @param artistCounts a map to store artist counts
     * @param genreCounts a map to store genre counts
     */
    private void updateArtistCounts(JSONObject track, Map<String, Integer> artistCounts, Map<String, Integer> genreCounts) {
        JSONArray artists = track.getJSONArray("artists");
        for (int k = 0; k < artists.length(); k++) {
            String artistId = artists.getJSONObject(k).getString("id");
            String artistName = artists.getJSONObject(k).getString("name");
            artistCounts.merge(artistName, 1, Integer::sum);

            JSONObject artistData = interactor.getArtist(artistId);
            if (artistData != null && artistData.has("genres")) {
                JSONArray genres = artistData.getJSONArray("genres");
                for (int g = 0; g < genres.length(); g++) {
                    genreCounts.merge(genres.getString(g), 1, Integer::sum);
                }
            }
        }
    }

    /**
     * Gets the top entry from a map based on its value.
     *
     * @param map a map of strings to integers
     * @param defaultValue the default value to return if the map is empty
     * @return the key of the highest-value entry or the default value if no entries exist
     */
    private String getTopEntry(Map<String, Integer> map, String defaultValue) {
        return map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(defaultValue);
    }

    /**
     * Updates the user profile with the specified top genre and artist.
     *
     * @param topGenre the top genre to update
     * @param topArtist the top artist to update
     */
    private void updateUserProfile(String topGenre, String topArtist) {
        userProfile.updatePreferredGenres(List.of(topGenre));
        userProfile.updatePreferredArtists(List.of(topArtist));
    }

    /**
     * Updates the user profile with the provided genres and artists.
     *
     * @param genres a list of genres to update
     * @param artists a list of artists to update
     */
    private void updateUserProfile(List<String> genres, List<String> artists) {
        if (!isEmpty(genres)) {
            userProfile.updatePreferredGenres(genres);
        }
        if (!isEmpty(artists)) {
            userProfile.updatePreferredArtists(artists);
        }
    }

    /**
     * Checks if a list is empty or null.
     *
     * @param list the list to check
     * @return true if the list is null or empty, false otherwise
     */
    private boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}
