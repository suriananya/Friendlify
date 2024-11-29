package entities.users;

import api.SpotifyInteractor;
import org.json.JSONObject;

import java.util.List;

public class AbstractUserProfile {
    private final SpotifyInteractor interactor;

    private String username;
    private String userID;  // Add a userID field

    private List<String> preferredGenres;
    private List<String> preferredArtists;

    public AbstractUserProfile(String userId, List<String> genres, List<String> artists) {
        this.interactor = new SpotifyInteractor();
        this.username = userId;
        this.userID = userId;  // Set user ID
        this.preferredGenres = genres;
        this.preferredArtists = artists;
    }

    public AbstractUserProfile(SpotifyInteractor interactor) {
        this.interactor = interactor;
        this.initUserInfo();
        this.initMusicPreference();
    }

    private void initMusicPreference() {
    }

    private void initUserInfo() {
        String fetchedUsername = "Unknown User";
        String fetchedUserID = "Unknown ID";  // Initialize userID

        // Fetch user profile
        try {
            JSONObject userProfileJson = interactor.getCurrentUserProfile();
            System.out.printf("User Profile Response: %s%n", userProfileJson); // Debugging line
            fetchedUsername = userProfileJson.optString("display_name",
                    userProfileJson.optString("id", "Unknown User"));
            fetchedUserID = userProfileJson.optString("id", "Unknown ID"); // Fetch user ID from profile
        } catch (Exception e) {
            System.err.printf("Error fetching user profile data: %s%n", e.getMessage());
        }

        this.username = fetchedUsername;
        this.userID = fetchedUserID; // Set user ID
    }
}
