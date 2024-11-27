import api.SpotifyInteractor;
import org.json.JSONArray;
import org.json.JSONObject;

public class TestSpotifyInteractor {
    public static void main(String[] args) {
        SpotifyInteractor interactor = new SpotifyInteractor();
        interactor.login();

        System.out.println("Get Artist test:");
        System.out.println(interactor.getArtist("6RQ9kYbHisp1UUbnfwHNeU").getString("name"));

        System.out.println("Get Playlist items test:");
        JSONArray playlistTracks = interactor.getPlaylistItems("3NTWh52BO994lvgv3EJLyn", 1, 0).getJSONArray("items");
        JSONObject firstTrack = playlistTracks.getJSONObject(0).getJSONObject("track");
        System.out.println(firstTrack.getString("name"));

        System.out.println("Get Current User Playlists test:");
        JSONArray currentUserPlaylists = interactor.getCurrentUserPlaylists(1, 0).getJSONArray("items");
        JSONObject currentUserPlaylist = currentUserPlaylists.getJSONObject(0);
        System.out.println(currentUserPlaylist.getString("name"));

        System.out.println("Get User Playlist test:");
        JSONArray userPlaylists = interactor.getUserPlaylists("jjunevo", 1, 0).getJSONArray("items");
        JSONObject userPlaylist = userPlaylists.getJSONObject(0);
        System.out.println(userPlaylist.getString("name"));

        System.out.println("Get Current User Profile test:");
        JSONObject currentUserProfile = interactor.getCurrentUserProfile();
        System.out.println(currentUserProfile.getString("displayName"));

        System.out.println("Get User Profile test:");
        JSONObject userProfile = interactor.getUserProfile("jjunevo");
        System.out.println(userProfile.getString("displayName"));
    }
}
