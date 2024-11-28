package TestApi;

import api.SpotifyInteractor;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.Assert.assertEquals;

public class TestSpotifyInteractor {
    private static final SpotifyInteractor interactor = new SpotifyInteractor();

    public static void getArtist() {
        String artistName = interactor.getArtist("6RQ9kYbHisp1UUbnfwHNeU").getString("name");
        assertEquals("Feint", artistName);
    }

    public static void getPlaylistItems() {
        JSONArray playlistTracks = interactor
                .getPlaylistItems("3NTWh52BO994lvgv3EJLyn", 1, 0)
                .getJSONArray("items");

        JSONObject firstTrack = playlistTracks.getJSONObject(0).getJSONObject("track");
        assertEquals("Time", firstTrack.getString("name"));
    }

    public static void getCurrentUserPlaylist() {
        JSONArray currentUserPlaylists = interactor
                .getCurrentUserPlaylists(1, 0)
                .getJSONArray("items");

        JSONObject currentUserPlaylist = currentUserPlaylists.getJSONObject(0);

        System.out.print("Check if this is the name of your one of your playlists: ");
        System.out.println(currentUserPlaylist.getString("name"));
    }

    public static void getUserPlaylists() {
        JSONArray userPlaylists = interactor
                .getUserPlaylists("jjunevo", 1, 0)
                .getJSONArray("items");

        JSONObject userPlaylist = userPlaylists.getJSONObject(0);
        assertEquals("sep13th", userPlaylist.getString("name"));
    }

    public static void getCurrentUserProfile() {
        JSONObject currentUserProfile = interactor
                .getCurrentUserProfile();
        System.out.print("Check if this is your Spotify username: ");
        System.out.println(currentUserProfile.getString("displayName"));
    }

    public static void getUserProfile() {
        JSONObject userProfile = interactor
                .getUserProfile("jjunevo");

        assertEquals("juney", userProfile.getString("displayName"));
    }

    public static void main(String[] args) {
        interactor.login();

        getArtist();
        getPlaylistItems();

        getCurrentUserPlaylist();
        getUserPlaylists();

        getCurrentUserProfile();
        getUserProfile();
    }
}
