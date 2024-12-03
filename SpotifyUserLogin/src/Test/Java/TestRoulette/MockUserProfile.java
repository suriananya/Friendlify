package TestRoulette;

import api.SpotifyInteractor;
import entities.users.UserProfile;

import java.util.List;

public class MockUserProfile extends UserProfile {
    private List<String> mockFriends;

    public MockUserProfile(SpotifyInteractor interactor) {
        super(interactor);
        this.mockFriends = List.of("friend1", "friend2", "friend3", "friend4");
    }

    public void setMockFriends(List<String> mockFriends) {
        this.mockFriends = mockFriends;
    }

    @Override
    public List<String> getFriendsList(String type) {
        return List.of("friend1", "friend2", "friend3", "friend4");
    }
}
