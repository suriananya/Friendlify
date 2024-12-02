package TestUsers;

import api.SpotifyInteractor;
import entities.users.FriendProfile;
import entities.users.UserProfile;

public class TestFriendProfile {
    public static void main(String[] args) {
        SpotifyInteractor interactor = new SpotifyInteractor();
        interactor.login();
        UserProfile userProfile = new UserProfile(interactor);
        FriendProfile friendProfile = new FriendProfile(interactor, userProfile.getFriendsListIds().getFirst());

        System.out.printf("%s %s%n", friendProfile.getUsername(), friendProfile.getUserId());
        System.out.printf("%s %s%n", friendProfile.getPreferredArtists(), friendProfile.getPreferredGenres());
    }
}
