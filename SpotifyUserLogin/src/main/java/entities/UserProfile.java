package entities;

import api.SpotifyInteractor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private final String username;
    private final List<Playlist> playlists;
    private List<String> preferredGenres;
    private List<String> preferredArtists;

    public UserProfile(SpotifyInteractor interactor) {
        interactor.login();
        JSONObject userInfo = interactor.getCurrentUserProfile();
        this.username = userInfo.getString("displayName");

        this.playlists = fetchPlaylists(interactor);
        this.preferredGenres = new ArrayList<>();
        this.preferredArtists = new ArrayList<>();
    }

    private List<Playlist> fetchPlaylists(SpotifyInteractor interactor) {
        List<Playlist> playlists = new ArrayList<>();
        JSONObject playlistsResponse = interactor.getCurrentUserPlaylists(50, 0);
        if (playlistsResponse != null && playlistsResponse.has("items")) {
            JSONArray items = playlistsResponse.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject playlistData = items.getJSONObject(i);
                String id = playlistData.getString("id");
                String name = playlistData.getString("name");
                playlists.add(new Playlist(id, name, new ArrayList<>())); // Songs can be populated later
            }
        }
        return playlists;
    }

    public String getUsername() {
        return username;
    }

    public List<Playlist> getPlaylist() {
        return playlists;
    }

    public List<String> getPreferredGenres() {
        return preferredGenres;
    }

    public void updatePreferredGenres(List<String> genres) {
        this.preferredGenres = genres;
    }

    public List<String> getPreferredArtists() {
        return preferredArtists;
    }

    public void updatePreferredArtists(List<String> artists) {
        this.preferredArtists = artists;
    }
}
