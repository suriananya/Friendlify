package TestRoulette;

import api.SpotifyInteractor;
import org.json.JSONArray;
import org.json.JSONObject;

public class MockSpotifyInteractor extends SpotifyInteractor {

    @Override
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

    @Override
    public JSONObject getPlaylistItems(String playlistId, int limit, int offset) {
        JSONObject response = new JSONObject();

        switch (playlistId) {

            // friend1's playlist has two songs for song randomization
            case "playlist1":
                response.put("items", new JSONArray().put(
                        new JSONObject().put("track", new JSONObject()
                                .put("name", "Song 1")
                                .put("artists", new JSONArray().put(
                                        new JSONObject().put("name", "Artist 1"))))).put(
                        new JSONObject().put("track", new JSONObject()
                                .put("name", "Song 2")
                                .put("artists", new JSONArray().put(
                                        new JSONObject().put("name", "Artist 2"))))));
                break;

            // friend2's playlist has a single song
            case "playlist2":
                response.put("items", new JSONArray().put(
                        new JSONObject().put("track", new JSONObject()
                                .put("name", "Song 2")
                                .put("artists", new JSONArray().put(
                                        new JSONObject().put("name", "Artist 3"))))));
                break;

            // friend4's playlist has no song's in the playlist
            case "playlist3":
                response.put("items", new JSONArray()); // Playlist exists but no songs
                break;
        }
        return response;
    }
}
