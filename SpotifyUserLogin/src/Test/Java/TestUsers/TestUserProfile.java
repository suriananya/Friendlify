package TestUsers;

import api.SpotifyInteractor;
import entities.users.UserProfile;

public class TestUserProfile {
    public static void main(String[] args) {
        SpotifyInteractor interactor = new SpotifyInteractor();
        interactor.login();
        UserProfile newUser = new UserProfile(interactor);
        System.out.println(newUser.getFriendsList());
        System.out.println(newUser.getFriendsListIds());
        System.out.println(newUser.getFriendsListNames());
        System.out.println(newUser.getUserId());
        System.out.println(newUser.getUsername());
        System.out.println(newUser.getPreferredArtists());
        System.out.println(newUser.getPreferredGenres());
    }
}
