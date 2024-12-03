package TestRoulette;

import api.SpotifyInteractor;
import org.json.JSONArray;
import org.json.JSONObject;

public class MockSpotifyInteractor extends SpotifyInteractor {

    @Override
    public JSONObject getCurrentUserProfile() {
        JSONObject mockUserProfile = new JSONObject();
        mockUserProfile.put("id", "mockUserId");
        mockUserProfile.put("displayName", "Mock User");
        return mockUserProfile;
    }

    @Override
    public JSONObject getCurrentUserPlaylists(int limit, int offset) {
        JSONObject mockResponse = new JSONObject();
        JSONArray playlists = new JSONArray();

        // Mock playlists with owner details
        playlists.put(new JSONObject()
                .put("id", "playlist1")
                .put("name", "My Playlist 1")
                .put("owner", new JSONObject().put("id", "mockUserId")));
        playlists.put(new JSONObject()
                .put("id", "playlist2")
                .put("name", "My Playlist 2")
                .put("owner", new JSONObject().put("id", "mockUserId")));

        mockResponse.put("items", playlists);
        return mockResponse;
    }

    @Override
    public JSONObject getUserPlaylists(String userId, int limit, int offset) {
        JSONObject response = new JSONObject();
        JSONArray playlists = new JSONArray();

        switch (userId) {
            case "friend1":
                playlists.put(new JSONObject()
                        .put("id", "playlist1")
                        .put("name", "Friend 1's Playlist")
                        .put("owner", new JSONObject().put("id", "friend1")));
                break;
            case "friend2":
                playlists.put(new JSONObject()
                        .put("id", "playlist2")
                        .put("name", "Friend 2's Playlist")
                        .put("owner", new JSONObject().put("id", "friend2")));
                break;
            case "friend3": // No playlists
                break;
            case "friend4": // Empty playlist
                playlists.put(new JSONObject()
                        .put("id", "playlist3")
                        .put("name", "Empty Playlist")
                        .put("owner", new JSONObject().put("id", "friend4")));
                break;
        }

        response.put("items", playlists);
        return response;
    }

    @Override
    public JSONObject getPlaylistItems(String playlistId, int limit, int offset) {
        JSONObject response = new JSONObject();
        JSONArray tracks = new JSONArray();

        switch (playlistId) {
            case "playlist1":
                tracks.put(new JSONObject().put("track", new JSONObject()
                        .put("name", "Song 1")
                        .put("artists", new JSONArray().put(new JSONObject().put("name", "Artist 1")))));
                tracks.put(new JSONObject().put("track", new JSONObject()
                        .put("name", "Song 2")
                        .put("artists", new JSONArray().put(new JSONObject().put("name", "Artist 2")))));
                break;
            case "playlist2":
                tracks.put(new JSONObject().put("track", new JSONObject()
                        .put("name", "Song 3")
                        .put("artists", new JSONArray().put(new JSONObject().put("name", "Artist 3")))));
                break;
            case "playlist3": // No tracks
                break;
        }

        response.put("items", tracks);
        return response;
    }
}
