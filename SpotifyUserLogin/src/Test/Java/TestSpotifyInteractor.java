import api.SpotifyInteractor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSpotifyInteractor {
    private static final SpotifyInteractor interactor = new SpotifyInteractor();

    @BeforeClass
    public static void init() {
        interactor.login();
    }

    @Test
    public void getArtist() {
        System.out.println(interactor.getArtist("6RQ9kYbHisp1UUbnfwHNeU").getString("name"));
    }

    @Test
    public void getPlaylistItems() {
        JSONArray playlistTracks = interactor
                .getPlaylistItems("3NTWh52BO994lvgv3EJLyn", 1, 0)
                .getJSONArray("items");

        JSONObject firstTrack = playlistTracks.getJSONObject(0).getJSONObject("track");

        System.out.println(firstTrack.getString("name"));
    }

    @Test
    public void getCurrentUserPlaylist() {
        JSONArray currentUserPlaylists = interactor
                .getCurrentUserPlaylists(1, 0)
                .getJSONArray("items");

        JSONObject currentUserPlaylist = currentUserPlaylists.getJSONObject(0);

        System.out.println(currentUserPlaylist.getString("name"));
    }

    @Test
    public void getUserPlaylists() {
        JSONArray userPlaylists = interactor
                .getUserPlaylists("jjunevo", 1, 0)
                .getJSONArray("items");

        JSONObject userPlaylist = userPlaylists.getJSONObject(0);

        System.out.println(userPlaylist.getString("name"));
    }

    @Test
    public void getCurrentUserProfile() {
        JSONObject currentUserProfile = interactor
                .getCurrentUserProfile();

        System.out.println(currentUserProfile.getString("displayName"));
    }

    @Test
    public void getUserProfile() {
        JSONObject userProfile = interactor
                .getUserProfile("jjunevo");

        System.out.println(userProfile.getString("displayName"));
    }
}
