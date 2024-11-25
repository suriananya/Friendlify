package Use_Case.Edit_Preferences;

import api.SpotifyInteractor;
import entities.UserProfile;

import java.util.List;

public class TestUserProfile {
    public static void main(String[] args) {
        // Mock Spotify Interactor
        SpotifyInteractor mockInteractor = new MockSpotifyInteractor();
        UserProfile userProfile = new UserProfile(mockInteractor);

        // Test initial state
        System.out.println("Initial State Test:");
        printUserProfile(userProfile);

        // Update genres
        System.out.println("\nUpdate Genres Test:");
        userProfile.updatePreferredGenres(List.of("Jazz", "Rock"));
        printUserProfile(userProfile);

        // Update artists
        System.out.println("\nUpdate Artists Test:");
        userProfile.updatePreferredArtists(List.of("Artist1", "Artist3"));
        printUserProfile(userProfile);
    }

    private static void printUserProfile(UserProfile userProfile) {
        System.out.println("Username: " + userProfile.getUsername());
        System.out.println("Preferred Genres: " + (userProfile.getPreferredGenres() != null ? String.join(", ", userProfile.getPreferredGenres()) : "None"));
        System.out.println("Preferred Artists: " + (userProfile.getPreferredArtists() != null ? String.join(", ", userProfile.getPreferredArtists()) : "None"));
    }
}

