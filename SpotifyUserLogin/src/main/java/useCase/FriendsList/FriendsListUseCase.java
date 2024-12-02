package useCase.FriendsList;

import api.SpotifyInteractor;
import entities.users.FriendProfile;
import entities.users.UserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Use case interactor for the friends list.
 */
public class FriendsListUseCase {
    private final UserProfile userProfile;
    private final List<FriendProfile> friendProfileList = new ArrayList<>();

    public FriendsListUseCase(SpotifyInteractor interactor, UserProfile userProfile) {
        this.userProfile = userProfile;

        for (String friendId : userProfile.getFriendsListIds()) {
            friendProfileList.add(new FriendProfile(interactor, friendId));
        }
    }

    public List<String> getFriendsListNames() {
        return userProfile.getFriendsListNames();
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public List<FriendProfile> getFriendProfileList() {
        return friendProfileList;
    }
}
