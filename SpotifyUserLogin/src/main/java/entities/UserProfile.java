package entities;
import java.util.List;

public class UserProfile {
    private final String username;
    private final List<Playlist> playlists;
    private final List<String> preferredGenres;
    private final List<String> preferredArtists;

    public UserProfile(String username, List<Playlist> playlists, List<String> preferredGenres,
                       List<String> preferredArtists) {
        this.username = username;
        this.playlists = playlists;
        this.preferredGenres = preferredGenres;
        this.preferredArtists = preferredArtists;
    }
    public String getUsername() {
        return username;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public List<String> getPreferredGenres() {
        return preferredGenres;
    }

    public List<String> getPreferredArtists() {
        return preferredArtists;
    }
}


