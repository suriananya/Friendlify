package entities.users;

import api.SpotifyInteractor;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendProfile extends AbstractUserProfile{
    public FriendProfile(String userId, List<String> genres, List<String> artists) {
        super(userId, genres, artists);
    }

    public FriendProfile(SpotifyInteractor interactor, String id) {
        super(interactor, id);
    }

    @Override
    Map<String, String> initUserInfo() {
        String fetchedUsername;

        // Fetch user profile
        JSONObject userProfileJson = this.getUserProfileJSON();
        System.out.printf("User Profile Response: %s%n", userProfileJson); // Debugging line
        fetchedUsername = userProfileJson.optString("displayName", userProfileJson.optString("id", "Unknown User"));

        Map<String, String> temp = new HashMap<>();
        temp.put("username", fetchedUsername);
        return temp;
    }

    @Override
    JSONObject getUserPlaylistsJSON(int limit, int offset) {
        return interactor.getUserPlaylists(userID, limit, offset);
    }

    @Override
    JSONObject getUserProfileJSON() {
        return interactor.getUserProfile(userID);
    }
}
