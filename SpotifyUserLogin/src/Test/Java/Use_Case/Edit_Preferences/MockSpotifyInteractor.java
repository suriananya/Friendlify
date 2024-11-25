package Use_Case.Edit_Preferences;

import api.SpotifyInteractor;
import org.json.JSONArray;
import org.json.JSONObject;

public class MockSpotifyInteractor extends SpotifyInteractor {
    @Override
    public JSONObject getCurrentUserProfile() {
        return new JSONObject("{\"display_name\": \"Mock User\", \"id\": \"mock_user\"}");
    }

    @Override
    public JSONObject getCurrentUserPlaylists(int limit, int offset) {
        return new JSONObject("{\"items\": [{\"id\": \"playlist1\", \"name\": \"Mock Playlist\"}]}");
    }

    @Override
    public JSONObject getPlaylistItems(String playlistId, int limit, int offset) {
        return new JSONObject("{\"items\": [{\"track\": {\"name\": \"Mock Track\", \"artists\": [{\"id\": \"artist1\", \"name\": \"Mock Artist\"}]}}]}");
    }

    @Override
    public JSONObject getArtist(String artistId) {
        return new JSONObject("{\"genres\": [\"Mock Genre\"], \"name\": \"Mock Artist\"}");
    }
}
