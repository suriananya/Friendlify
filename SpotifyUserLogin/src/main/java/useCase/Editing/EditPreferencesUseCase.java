package useCase.Editing;

import api.SpotifyInteractor;
import entities.users.UserProfile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditPreferencesUseCase {
    private final SpotifyInteractor interactor;
    private final UserProfile userProfile;

    public EditPreferencesUseCase(SpotifyInteractor interactor, UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("UserProfile cannot be null");
        }
        this.interactor = interactor;
        this.userProfile = userProfile;
    }

    // Dynamic update method
    public EditPreferencesResponse execute() {
        try {
            Map<String, Integer> genreCounts = new HashMap<>();
            Map<String, Integer> artistCounts = new HashMap<>();

            // Fetch user's playlists
            JSONObject playlistsJson = interactor.getCurrentUserPlaylists(5, 0);
            if (playlistsJson == null || !playlistsJson.has("items")) {
                return new EditPreferencesResponse(false, "No playlists found.", null, null);
            }

            JSONArray playlists = playlistsJson.getJSONArray("items");
            for (int i = 0; i < playlists.length(); i++) {
                JSONObject playlist = playlists.getJSONObject(i);
                String playlistId = playlist.getString("id");

                // Fetch tracks from the playlist
                JSONObject playlistItemsJson = interactor.getPlaylistItems(playlistId, 10, 0);
                if (playlistItemsJson == null || !playlistItemsJson.has("items")) continue;

                JSONArray items = playlistItemsJson.getJSONArray("items");
                for (int j = 0; j < items.length(); j++) {
                    JSONObject track = items.getJSONObject(j).getJSONObject("track");
                    JSONArray artists = track.getJSONArray("artists");

                    // Count artist occurrences
                    for (int k = 0; k < artists.length(); k++) {
                        String artistId = artists.getJSONObject(k).getString("id");
                        String artistName = artists.getJSONObject(k).getString("name");
                        artistCounts.put(artistName, artistCounts.getOrDefault(artistName, 0) + 1);

                        // Fetch genres for artist
                        JSONObject artistData = interactor.getArtist(artistId);
                        if (artistData != null && artistData.has("genres")) {
                            JSONArray genres = artistData.getJSONArray("genres");
                            for (int g = 0; g < genres.length(); g++) {
                                String genre = genres.getString(g);
                                genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
                            }
                        }
                    }
                }
            }

            // Determine top genre and artist
            String topGenre = genreCounts.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("Unknown");

            String topArtist = artistCounts.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("Unknown");

            // Update user profile with the new preferences
            userProfile.updatePreferredGenres(List.of(topGenre));
            userProfile.updatePreferredArtists(List.of(topArtist));

            return new EditPreferencesResponse(true, "Preferences updated dynamically.", List.of(topGenre), List.of(topArtist));
        } catch (Exception e) {
            return new EditPreferencesResponse(false, "Error updating preferences dynamically: " + e.getMessage(), null, null);
        }
    }

    // Manual update method
    public EditPreferencesResponse execute(List<String> newGenres, List<String> newArtists) {
        try {
            if ((newGenres == null || newGenres.isEmpty()) && (newArtists == null || newArtists.isEmpty())) {
                return new EditPreferencesResponse(false, "Genres and artists cannot both be empty.", null, null);
            }

            // Update user profile with provided genres and artists
            if (newGenres != null && !newGenres.isEmpty()) {
                userProfile.updatePreferredGenres(newGenres);
            }
            if (newArtists != null && !newArtists.isEmpty()) {
                userProfile.updatePreferredArtists(newArtists);
            }

            return new EditPreferencesResponse(true, "Preferences updated manually.", newGenres, newArtists);
        } catch (Exception e) {
            return new EditPreferencesResponse(false, "Error updating preferences manually: " + e.getMessage(), null, null);
        }
    }
}
