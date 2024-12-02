package useCase.FriendsList;

import api.SpotifyInteractor;
import entities.users.FriendProfile;
import entities.users.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class FriendsListUseCase {
    final SpotifyInteractor interactor;

    final UserProfile userProfile;
    final List<FriendProfile> friendProfileList = new ArrayList<>();

    public FriendsListUseCase(SpotifyInteractor interactor, UserProfile userProfile) {
        this.interactor = interactor;
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
