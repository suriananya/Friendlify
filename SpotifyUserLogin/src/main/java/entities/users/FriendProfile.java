package entities.users;

import api.SpotifyInteractor;
import org.json.JSONObject;

import java.util.List;

public class FriendProfile extends AbstractUserProfile{
    public FriendProfile(String userId, List<String> genres, List<String> artists) {
        super(userId, genres, artists);
    }

    public FriendProfile(SpotifyInteractor interactor, String id) {
        super(interactor);
        this.userID = id;
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
