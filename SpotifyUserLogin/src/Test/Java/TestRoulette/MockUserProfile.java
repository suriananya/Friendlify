package TestRoulette;

import api.SpotifyInteractor;
import entities.users.UserProfile;
import java.util.Arrays;
import java.util.List;

public class MockUserProfile extends UserProfile {

    public MockUserProfile(SpotifyInteractor interactor) {
        super(interactor);
    }

    @Override
    public List<String> getFriendsList(String type) {
        return Arrays.asList("friend1", "friend2", "friend3", "friend4");
    }
}
