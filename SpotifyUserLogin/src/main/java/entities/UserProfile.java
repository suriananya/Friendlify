package entities;

import api.SpotifyInteractor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfile {
    private final String username;
    private List<String> preferredGenres;
    private List<String> preferredArtists;

    public UserProfile(SpotifyInteractor interactor) {
        String fetchedUsername = "Unknown User";
        List<String> genres = new ArrayList<>();
        List<String> artists = new ArrayList<>();
        Map<String, Integer> genreCounts = new HashMap<>();
        Map<String, Integer> artistCounts = new HashMap<>();

        try {
            // Fetch user profile
            JSONObject userProfileJson = interactor.getCurrentUserProfile();
            System.out.println("User Profile Response: " + userProfileJson); // Debugging line
            fetchedUsername = userProfileJson.optString("display_name", userProfileJson.optString("id", "Unknown User"));

            // Fetch playlists and determine genres/artists (as before)
            JSONObject playlistsJson = interactor.getCurrentUserPlaylists(5, 0);
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

        } catch (Exception e) {
            System.err.println("Error fetching user profile data: " + e.getMessage());
            genres.add("Unknown");
            artists.add("Unknown");
        }

        this.username = fetchedUsername;
        this.preferredGenres = genres;
        this.preferredArtists = artists;
    }


    public String getUsername() {
        return username;
    }

    public List<String> getPreferredGenres() {
        return preferredGenres;
    }

    public List<String> getPreferredArtists() {
        return preferredArtists;
    }

    public void updatePreferredGenres(List<String> newGenres) {
        if (newGenres != null && !newGenres.isEmpty()) {
            this.preferredGenres = new ArrayList<>(newGenres);
        } else {
            System.err.println("Cannot update preferred genres with null or empty list.");
        }
    }

    public void updatePreferredArtists(List<String> newArtists) {
        if (newArtists != null && !newArtists.isEmpty()) {
            this.preferredArtists = new ArrayList<>(newArtists);
        } else {
            System.err.println("Cannot update preferred artists with null or empty list.");
        }
    }
}
