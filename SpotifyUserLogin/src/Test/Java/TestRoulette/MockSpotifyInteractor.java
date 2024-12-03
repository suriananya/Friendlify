package TestRoulette;

import org.json.JSONArray;
import org.json.JSONObject;

public class MockSpotifyInteractor {
    public JSONObject getUserPlaylists(String userId, int limit, int offset) {
        JSONObject response = new JSONObject();

        switch (userId) {
            case "friend1":
                response.put("items", new JSONArray().put(
                        new JSONObject().put("id", "playlist1")));
                break;
            case "friend2":
                response.put("items", new JSONArray().put(
                        new JSONObject().put("id", "playlist2")));
                break;
            case "friend3":
                response.put("items", new JSONArray()); // No playlists
                break;
            case "friend4":
                response.put("items", new JSONArray().put(
                        new JSONObject().put("id", "playlist3"))); // Playlist exists but no songs
                break;
        }
        return response;
    }
}
