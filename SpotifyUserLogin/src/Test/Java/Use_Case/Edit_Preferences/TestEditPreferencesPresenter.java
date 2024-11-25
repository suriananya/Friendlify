package Use_Case.Edit_Preferences;

import Use_case.Editing.EditPreferencesResponse;
import interface_adapter.editpreferences.EditPreferencesPresenter;
import interface_adapter.editpreferences.EditPreferencesState;

import java.util.List;

public class TestEditPreferencesPresenter {
    public static void main(String[] args) {
        // Create a Presenter instance
        EditPreferencesPresenter presenter = new EditPreferencesPresenter();

        // Test with a success response
        System.out.println("Present Success Response Test:");
        EditPreferencesResponse successResponse = new EditPreferencesResponse(
                true,
                "Preferences updated successfully.",
                List.of("Rock", "Pop"),
                List.of("Artist1", "Artist2")
        );
        presenter.present(successResponse);
        printState(presenter.getState());

        // Test with a failure response
        System.out.println("\nPresent Failure Response Test:");
        EditPreferencesResponse failureResponse = new EditPreferencesResponse(
                false,
                "Failed to update preferences.",
                null,
                null
        );
        presenter.present(failureResponse);
        printState(presenter.getState());
    }

    private static void printState(EditPreferencesState state) {
        if (state != null) {
            System.out.println("Success: " + state.isSuccess());
            System.out.println("Message: " + state.getMessage());
            System.out.println("Genres: " + (state.getGenres() != null ? String.join(", ", state.getGenres()) : "None"));
            System.out.println("Artists: " + (state.getArtists() != null ? String.join(", ", state.getArtists()) : "None"));
        } else {
            System.out.println("State is null.");
        }
    }
}

