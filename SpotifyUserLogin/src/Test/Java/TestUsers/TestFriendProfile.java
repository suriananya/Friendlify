package TestUsers;

import api.SpotifyInteractor;
import entities.users.UserProfile;

public class TestFriendProfile {
    public static void main(String[] args) {
        SpotifyInteractor interactor = new SpotifyInteractor();
        interactor.login();
        UserProfile newUser = new UserProfile(interactor);
        System.out.println(newUser.getFriendsList());
    }
}
