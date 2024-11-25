package interface_adapter.editpreferences;

import Use_case.Editing.EditPreferencesResponse;
import Use_case.Editing.EditPreferencesUseCase;

import java.util.List;

public class EditPreferencesController {
    private final EditPreferencesUseCase useCase;
    private final EditPreferencesPresenter presenter;

    public EditPreferencesController(EditPreferencesUseCase useCase, EditPreferencesPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    /**
     * Updates preferences dynamically by fetching data from Spotify.
     */
    public void updatePreferencesDynamically() {
        try {
            // Execute dynamic preference fetching
            EditPreferencesResponse response = useCase.execute();
            presenter.present(response);
        } catch (Exception e) {
            // Handle exceptions gracefully and notify presenter
            presenter.present(new EditPreferencesResponse(
                    false,
                    "Failed to fetch preferences dynamically: " + e.getMessage(),
                    null,
                    null
            ));
        }

    }

    public void updatePreferencesManually(List<String> newGenres, List<String> newArtists) {
        try {
            EditPreferencesResponse response = useCase.execute(newGenres, newArtists);
            presenter.present(response);
        } catch (Exception e) {
            presenter.present(new EditPreferencesResponse(
                    false,
                    "Failed to update preferences manually: " + e.getMessage(),
                    null,
                    null
            ));
        }
    }


    /**
     * Retrieves the current state of the preferences from the presenter.
     *
     * @return Current state of preferences.
     */
    public EditPreferencesState getState() {
        return presenter.getState();
    }
}
