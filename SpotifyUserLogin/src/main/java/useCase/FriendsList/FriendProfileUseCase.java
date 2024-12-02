package useCase.FriendsList;

import entities.users.FriendProfile;

import java.util.List;

public class FriendProfileUseCase {
    final FriendProfile friendProfile;

    public FriendProfileUseCase(FriendProfile friendProfile) {
        this.friendProfile = friendProfile;
    }

    public FriendProfile getFriendProfile() {
        return friendProfile;
    }

    public List<String> getPreferredGenres() {
        return friendProfile.getPreferredGenres();
    }

    public List<String> getPreferredArtists() {
        return friendProfile.getPreferredArtists();
    }

    public String getUsername() {
        return friendProfile.getUsername();
    }
}
