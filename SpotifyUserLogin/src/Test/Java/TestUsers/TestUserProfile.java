package TestUsers;

import api.SpotifyInteractor;
import entities.users.UserProfile;

public class TestUserProfile {
    private static final SpotifyInteractor interactor = new SpotifyInteractor();
    private static UserProfile newUser;

    public static void GetFriendsList() {
        System.out.printf("Testing get friends list:%s%n", newUser.getFriendsList());
    }

    public static void GetFriendsListIds() {
        System.out.printf("Testing get friends list ids:%s%n", newUser.getFriendsListIds());
    }

    public static void GetFriendsListNames() {
        System.out.printf("Testing get friends list names:%s%n", newUser.getFriendsListNames());
    }

    public static void GetUserId() {
        System.out.printf("Testing get user id:%s%n", newUser.getUserId());
    }

    public static void GetUsername() {
        System.out.printf("Testing get username:%s%n", newUser.getUsername());
    }

    public static void GetPreferredArtists() {
        System.out.printf("Testing get preferred artists:%s%n", newUser.getPreferredArtists());
    }

    public static void GetPreferredGenres() {
        System.out.printf("Testing get preferred genres:%s%n", newUser.getPreferredGenres());
    }

    public static void FriendsListTests() {
        GetFriendsList();
        GetFriendsListIds();
        GetFriendsListNames();
    }

    public static void UserInfoTests() {
        GetUserId();
        GetUsername();
    }

    public static void UserPreferencesTests() {
        GetPreferredArtists();
        GetPreferredGenres();
    }

    public static void RunAllTests() {
        FriendsListTests();
        UserInfoTests();
        UserPreferencesTests();
    }

    public static void main(String[] args) {
        interactor.login();
        newUser = new UserProfile(interactor);
        RunAllTests();
    }
}
