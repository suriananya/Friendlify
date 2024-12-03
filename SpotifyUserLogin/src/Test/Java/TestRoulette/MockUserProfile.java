package TestRoulette;

import api.SpotifyInteractor;
import entities.users.UserProfile;
import java.util.Arrays;
import java.util.List;

public class MockUserProfile extends UserProfile {
    private List<String> mockFriends;

    public MockUserProfile(SpotifyInteractor interactor) {
        super(interactor);
    }

    public void setMockFriends(List<String> mockFriends) {
        this.mockFriends = mockFriends;
    }

    @Override
    public List<String> getFriendsList(String type) {
        return mockFriends != null ? mockFriends : Arrays.asList("friend1", "friend2", "friend3", "friend4");
    }
}
