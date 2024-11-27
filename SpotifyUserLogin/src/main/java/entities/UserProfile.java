package entities;

import api.SpotifyInteractor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.io.*;

public class UserProfile {
    private final SpotifyInteractor interactor;

    private final String username;
    private final String userID;  // Add a userID field

    private final JSONArray friendsList = new JSONArray();
    private JSONArray playlists = new JSONArray();

    private List<String> preferredGenres;
    private List<String> preferredArtists;

    public UserProfile(SpotifyInteractor interactor) {
        this.interactor = interactor;

        String fetchedUsername = "Unknown User";
        String fetchedUserID = "Unknown ID";

        List<String> genres = new ArrayList<>();
        List<String> artists = new ArrayList<>();

        Map<String, Integer> genreCounts = new HashMap<>();
        Map<String, Integer> artistCounts = new HashMap<>();

        try {
            JSONObject userProfileJson = this.fetchUserProfile();
            fetchedUsername = this.fetchUsername(userProfileJson);
            fetchedUserID = this.fetchId(userProfileJson);

            // Fetch playlists and determine genres/artists (as before)
            JSONObject playlistsJson = interactor.getCurrentUserPlaylists(5, 0);
            if (playlistsJson != null && playlistsJson.has("items")) {
                JSONArray playlists = playlistsJson.getJSONArray("items");
                this.playlists = playlistsJson.getJSONArray("items");
                for (int i = 0; i < playlists.length(); i++) {
                    JSONObject playlist = playlists.getJSONObject(i);
                    String playlistId = playlist.getString("id");

                    JSONObject playlistOwner = playlist.getJSONObject("owner");
                    if (!Objects.equals(playlistOwner.getString("id"), getUserId())) {
                        addToFriendsList(playlistOwner.getString("id"), playlistOwner.getString("display_name"));
                    }

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

        } catch (Exception e) {
            System.err.println("Error fetching user profile data: " + e.getMessage());
            genres.add("Unknown");
            artists.add("Unknown");
        }

        this.username = fetchedUsername;
        this.userID = fetchedUserID;
        this.preferredGenres = genres;
        this.preferredArtists = artists;
    }

    public UserProfile(String userId, List<String> genres, List<String> artists) {
        this.interactor = new SpotifyInteractor();
        this.username = userId;
        this.userID = userId;  // Set user ID
        this.preferredGenres = genres;
        this.preferredArtists = artists;
    }

    private void initializeUserTaste(Map<String, Integer> genreCounts, Map<String, Integer> artistCounts) {
        // Fetch playlists and determine genres/artists (as before)
        JSONObject playlistsJson = this.interactor.getCurrentUserPlaylists(5, 0);

        if (playlistsJson == null || !playlistsJson.has("items")) {
            return;
        }

        this.playlists = playlistsJson.getJSONArray("items");

        inspectPlaylists(genreCounts, artistCounts);
    }

    private void inspectPlaylists(Map<String, Integer> genreCounts, Map<String, Integer> artistCounts) {
        for (int i = 0; i < playlists.length(); i++) {
            JSONObject playlist = playlists.getJSONObject(i);
            String playlistId = playlist.getString("id");

            JSONObject playlistOwner = playlist.getJSONObject("owner");
            if (!Objects.equals(playlistOwner.getString("id"), getUserId())) {
                addToFriendsList(playlistOwner.getString("id"), playlistOwner.getString("display_name"));
            }

            JSONObject playlistItemsJson = interactor.getPlaylistItems(playlistId, 10, 0);
            determinePreference(playlistItemsJson, genreCounts, artistCounts);
        }
    }

    private void determinePreference(JSONObject playlistItemsJson, Map<String, Integer> genreCounts, Map<String, Integer> artistCounts) {
        if (playlistItemsJson == null || playlistItemsJson.has("items")) {
            return;
        }

        JSONArray items = playlistItemsJson.getJSONArray("items");
        determinePreferenceHelper(items, genreCounts, artistCounts);
    }

    private void determinePreferenceHelper(JSONArray items, Map<String, Integer> genreCounts, Map<String, Integer> artistCounts) {
        for (int j = 0; j < items.length(); j++) {
            JSONObject track = items.getJSONObject(j).getJSONObject("track");
            JSONArray trackArtists = track.getJSONArray("artists");

            analyzeArtists(trackArtists, genreCounts, artistCounts);
        }
    }

    private void analyzeArtists(JSONArray trackArtists, Map<String, Integer> genreCounts, Map<String, Integer> artistCounts) {
        for (int k = 0; k < trackArtists.length(); k++) {
            String artistId = trackArtists.getJSONObject(k).getString("id");
            String artistName = trackArtists.getJSONObject(k).getString("name");
            artistCounts.put(artistName, artistCounts.getOrDefault(artistName, 0) + 1);

            extractArtistGenres(artistId, genreCounts);
        }
    }

    private void extractArtistGenres(String artistId, Map<String, Integer> genreCounts) {
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

    private JSONObject fetchUserProfile() {
        JSONObject userProfileJson = this.interactor.getCurrentUserProfile();
        System.out.println("User Profile Response: " + userProfileJson); // Debugging line
        return userProfileJson;
    }

    private String fetchUsername(JSONObject userProfileJson) {
        String defaultName = userProfileJson.optString("id", "Unknown User");
        return userProfileJson.optString("display_name", defaultName);
    }

    private String fetchId(JSONObject userProfileJson) {
        return userProfileJson.optString("id", "Unknown ID");
    }

    private void addToFriendsList(String id, String displayName) {
        JSONObject friendsJson = new JSONObject().put("id", id).put("display_name", displayName);
        this.friendsList.put(friendsJson);
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userID;  // Return user ID
    }

    public List<String> getPreferredGenres() {
        return preferredGenres;
    }

    public List<String> getPreferredArtists() {
        return preferredArtists;
    }

    public JSONArray getFriendsList() {
        return friendsList;
    }

    /**
     * Sets the preferred genres of the user.
     * @param newGenres The list of genres to set.
     */
    public void setPreferredGenres(List<String> newGenres) {
        if (newGenres != null && !newGenres.isEmpty()) {
            this.preferredGenres = new ArrayList<>(newGenres);
        } else {
            System.err.println("Cannot set preferred genres with null or empty list.");
        }
    }

    /**
     * Sets the preferred artists of the user.
     * @param newArtists The list of artists to set.
     */
    public void setPreferredArtists(List<String> newArtists) {
        if (newArtists != null && !newArtists.isEmpty()) {
            this.preferredArtists = new ArrayList<>(newArtists);
        } else {
            System.err.println("Cannot set preferred artists with null or empty list.");
        }
    }

    /**
     * Updates the list of preferred genres by adding new genres.
     * @param newGenres The list of genres to add.
     */
    public void updatePreferredGenres(List<String> newGenres) {
        if (newGenres != null && !newGenres.isEmpty()) {
            this.preferredGenres = new ArrayList<>(newGenres);
        } else {
            System.err.println("Cannot update preferred genres with null or empty list.");
        }
    }

    /**
     * Updates the list of preferred artists by adding new artists.
     * @param newArtists The list of artists to add.
     */
    public void updatePreferredArtists(List<String> newArtists) {
        if (newArtists != null && !newArtists.isEmpty()) {
            this.preferredArtists = new ArrayList<>(newArtists);
        } else {
            System.err.println("Cannot update preferred artists with null or empty list.");
        }
    }

public class UserIDManager {
    private static final String USER_DATA_FILE = System.getProperty("user.home") + File.separator + "user_data.txt";

    /**
     * Retrieves the user's ID. If no ID exists, a new one is generated, stored, and returned.
     *
     * @return The user's unique ID.
     */
    public static String getUserID() {
        try {
            // Check if the user data file exists
            File file = new File(USER_DATA_FILE);
            if (file.exists()) {
                // Read the file and return the stored user ID
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String userId = reader.readLine();
                reader.close();
                if (userId != null && !userId.isEmpty()) {
                    return userId.trim();
                }
            }

            // If the file doesn't exist or is empty, generate a new user ID
            String newUserId = generateUserID();
            saveUserID(newUserId);
            return newUserId;

        } catch (IOException e) {
            System.err.println("Error reading or creating user data file: " + e.getMessage());
            throw new RuntimeException("Unable to retrieve user ID.", e);
        }
    }

    /**
     * Saves the user's ID to the file for future retrieval.
     *
     * @param userId The user ID to save.
     */
    private static void saveUserID(String userId) {
        try {
            // Write the user ID to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE));
            writer.write(userId);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving user ID: " + e.getMessage());
            throw new RuntimeException("Unable to save user ID.", e);
        }
    }

    /**
     * Generates a new unique user ID.
     * This is a placeholder implementation and can be replaced with a more sophisticated generator if needed.
     *
     * @return A new unique user ID.
     */
    private static String generateUserID() {
        return "user-" + System.currentTimeMillis();
    }
}
}