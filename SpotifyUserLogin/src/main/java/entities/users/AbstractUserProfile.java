package entities.users;

import api.AbstractSpotifyInteractor;
import api.SpotifyInteractor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * An abstract class representing an arbitrary user profile.
 * Will be used for the current user, and for friend profiles.
 */
public abstract class AbstractUserProfile {
    final SpotifyInteractor interactor;

    final String username;
    final String userID;  // Add a userID field

    final List<String> preferredGenres;
    final List<String> preferredArtists;

    /**
     * Simpler constructor for easier mocking and testing.
     * @param userId the user ID.
     * @param genres the user's list of preferred genres.
     * @param artists the user's list of preferred artists.
     */
    public AbstractUserProfile(String userId, List<String> genres, List<String> artists) {
        this.interactor = new SpotifyInteractor();
        this.username = userId;
        this.userID = userId;  // Set user ID
        this.preferredGenres = genres;
        this.preferredArtists = artists;
    }

    /**
     * Pulls user data from the Spotify API to initialize their profile.
     * @param interactor the SpotifyInteractor associated with this session.
     */
    public AbstractUserProfile(SpotifyInteractor interactor) {
        this.interactor = interactor;

        Map<String, String> tempUserInfo = new HashMap<>();
        Map<String, List<String>> tempPreferences = new HashMap<>();

        try {
            tempUserInfo = initUserInfo();
            tempPreferences = initMusicPreference();

        } catch (Exception e) {
            System.err.printf("Error fetching user profile data: %s%n", e.getMessage());

            tempUserInfo.put("username", "Unknown");
            tempUserInfo.put("userID", "Unknown");
            tempPreferences.put("genres", new ArrayList<>(Collections.singletonList("Unknown")));
            tempPreferences.put("artists", new ArrayList<>(Collections.singletonList("Unknown")));
        }

        this.username = tempUserInfo.get("username");
        this.userID = tempUserInfo.get("userID");
        this.preferredGenres = tempPreferences.get("genres");
        this.preferredArtists = tempPreferences.get("artists");
    }

    /**
     * Initializes the user's preferred artists and genres.
     */
    Map<String, List<String>> initMusicPreference() {
        List<String> genres = new ArrayList<>();
        List<String> artists = new ArrayList<>();
        Map<String, Integer> genreCounts = new HashMap<>();
        Map<String, Integer> artistCounts = new HashMap<>();

        // Fetch playlists and determine genres/artists (as before)
        JSONObject playlistsJson = this.getUserPlaylistsJSON(5, 0);
        if (playlistsJson != null && playlistsJson.has("items")) {
            JSONArray playlists = playlistsJson.getJSONArray("items");
            for (int i = 0; i < playlists.length(); i++) {
                JSONObject playlist = playlists.getJSONObject(i);
                String playlistId = playlist.getString("id");

                JSONObject playlistItemsJson = interactor.getPlaylistItems(playlistId, 10, 0);
                if (playlistItemsJson != null && playlistItemsJson.has("items")) {
                    JSONArray items = playlistItemsJson.getJSONArray("items");
                    for (int j = 0; j < items.length(); j++) {
                        JSONObject track = items.getJSONObject(j).getJSONObject("track");
                        JSONArray trackArtists = track.getJSONArray("artists");

                        for (int k = 0; k < trackArtists.length(); k++) {
                            String artistId = trackArtists.getJSONObject(k).getString("id");
                            String artistName = trackArtists.getJSONObject(k).getString("name");
                            artistCounts.put(artistName, artistCounts.getOrDefault(artistName, 0) + 1);

                            // Fetch artist genres
                            JSONObject artistData = interactor.getArtist(artistId);
                            if (artistData != null && artistData.has("genres")) {
                                JSONArray artistGenres = artistData.getJSONArray("genres");
                                for (int g = 0; g < artistGenres.length(); g++) {
                                    String genre = artistGenres.getString(g);
                                    genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
                                }
                            }
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

        genres.add(topGenre);
        artists.add(topArtist);

        Map<String, List<String>> temp = new HashMap<>();
        temp.put("genres", genres);
        temp.put("artists", artists);
        return temp;
    }

    /**
     * Initializes the user's username and ID.
     */
    Map<String, String> initUserInfo() {
        String fetchedUsername;
        String fetchedUserID;  // Initialize userID

        // Fetch user profile
        JSONObject userProfileJson = this.getUserProfileJSON();
        System.out.printf("User Profile Response: %s%n", userProfileJson); // Debugging line
        fetchedUsername = userProfileJson.optString("display_name",
                userProfileJson.optString("id", "Unknown User"));
        fetchedUserID = userProfileJson.optString("id", "Unknown ID"); // Fetch user ID from profile

        Map<String, String> temp = new HashMap<>();
        temp.put("username", fetchedUsername);
        temp.put("userID", fetchedUserID);
        return temp;
    }

    /**
     * An abstract method intended to swap between getting current user and a chosen user's
     * playlists depending on the user.
     * @see AbstractSpotifyInteractor
     * @param limit the maximum number of items to return.
     * @param offset the index of the first item to return.
     * @return the response from the Spotify API. Empty JSON if there is an error.
     */
    abstract JSONObject getUserPlaylistsJSON(int limit, int offset);

    /**
     * An abstract method intended to swap between getting current user and a chosen user's
     * profile depending on the user.
     * @return the response from the Spotify API. Empty JSON if there is an error.
     */
    abstract JSONObject getUserProfileJSON();

    // *** Beyond this point, there should only be default getter and setter methods ***

    public List<String> getPreferredArtists() {
        return preferredArtists;
    }

    public List<String> getPreferredGenres() {
        return preferredGenres;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }
}
