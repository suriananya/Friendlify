package entities.users;

import api.SpotifyInteractor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public abstract class AbstractUserProfile {
    private final SpotifyInteractor interactor;

    private String username;
    private String userID;  // Add a userID field

    private List<String> preferredGenres;
    private List<String> preferredArtists;

    public AbstractUserProfile(String userId, List<String> genres, List<String> artists) {
        this.interactor = new SpotifyInteractor();
        this.username = userId;
        this.userID = userId;  // Set user ID
        this.preferredGenres = genres;
        this.preferredArtists = artists;
    }

    public AbstractUserProfile(SpotifyInteractor interactor) {
        this.interactor = interactor;
        try {
            this.initUserInfo();
            this.initMusicPreference();
        } catch (Exception e) {
            System.err.println("Error fetching user profile data: " + e.getMessage());
            this.preferredGenres = new ArrayList<>(Collections.singleton("Unknown"));
            this.preferredArtists = new ArrayList<>(Collections.singleton("Unknown"));
        }
    }

    private void initMusicPreference() {
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
    }

    private void initUserInfo() {
        String fetchedUsername = "Unknown User";
        String fetchedUserID = "Unknown ID";  // Initialize userID

        // Fetch user profile
        JSONObject userProfileJson = this.getUserProfileJSON();
        System.out.printf("User Profile Response: %s%n", userProfileJson); // Debugging line
        fetchedUsername = userProfileJson.optString("display_name",
                userProfileJson.optString("id", "Unknown User"));
        fetchedUserID = userProfileJson.optString("id", "Unknown ID"); // Fetch user ID from profile

        this.username = fetchedUsername;
        this.userID = fetchedUserID; // Set user ID
    }

    abstract JSONObject getUserPlaylistsJSON(int limit, int offset);

    abstract JSONObject getUserProfileJSON();
}
