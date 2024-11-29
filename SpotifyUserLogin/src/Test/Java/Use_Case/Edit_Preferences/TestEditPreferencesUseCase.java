package Use_Case.Edit_Preferences;

import useCase.Editing.EditPreferencesResponse;
import useCase.Editing.EditPreferencesUseCase;
import entities.users.UserProfile;
import api.SpotifyInteractor;

import java.util.List;

public class TestEditPreferencesUseCase {
    public static void main(String[] args) {
        // Mock Spotify Interactor
        SpotifyInteractor mockInteractor = new MockSpotifyInteractor();
        UserProfile userProfile = new UserProfile(mockInteractor);
        EditPreferencesUseCase useCase = new EditPreferencesUseCase(mockInteractor, userProfile);

        // Test dynamic execution
        System.out.println("Dynamic Execution Test:");
        EditPreferencesResponse dynamicResponse = useCase.execute();
        printResponse(dynamicResponse);

        // Test manual execution
        System.out.println("\nManual Execution Test:");
        EditPreferencesResponse manualResponse = useCase.execute(
                List.of("Jazz", "Classical"),
                List.of("Artist1", "Artist2")
        );
        printResponse(manualResponse);

        // Test manual execution with empty input
        System.out.println("\nManual Execution Test with Empty Input:");
        EditPreferencesResponse emptyInputResponse = useCase.execute(null, null);
        printResponse(emptyInputResponse);
    }

    private static void printResponse(EditPreferencesResponse response) {
        if (response != null) {
            System.out.println("Success: " + response.isSuccess());
            System.out.println("Message: " + response.getMessage());
            System.out.println("Updated Genres: " + (response.getUpdatedGenres() != null ? String.join(", ", response.getUpdatedGenres()) : "None"));
            System.out.println("Updated Artists: " + (response.getUpdatedArtists() != null ? String.join(", ", response.getUpdatedArtists()) : "None"));
        } else {
            System.out.println("Response is null.");
        }
    }
}



