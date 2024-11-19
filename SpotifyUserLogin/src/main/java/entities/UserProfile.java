package entities;
import api.SpotifyInteractor;
import org.json.JSONObject;

import java.util.List;

public class UserProfile {
    private final String username;

    private final List<Playlist> playlist;

    private final List<String> preferredGenres;
    private final List<String> preferredArtists;

    private final SpotifyInteractor interactor = new SpotifyInteractor();

    public UserProfile(String username, List<Playlist> playlists, List<String> preferredGenres,
                       List<String> preferredArtists) {
        // Initialize user information
        JSONObject userInfo = interactor.getCurrentUserProfile();
        this.username = userInfo.getString("displayName");

        // Initialize playlist information
        JSONObject playlistInfo = interactor.getCurrentUserPlaylists(1, 0);
        String playlistId = playlistInfo.getJSONArray("items").getJSONObject(0).getString("id");
        this.playlist = playlists;

        this.preferredGenres = preferredGenres;
        this.preferredArtists = preferredArtists;
    }
    public String getUsername() {
        return username;
    }

    public List<Playlist> getPlaylist() {
        return playlist;
    }

    public List<String> getPreferredGenres() {
        return preferredGenres;
    }

    public List<String> getPreferredArtists() {
        return preferredArtists;
    }
}


