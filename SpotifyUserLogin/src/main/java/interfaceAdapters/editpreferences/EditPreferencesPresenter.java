package interfaceAdapters.editpreferences;

import useCase.Editing.EditPreferencesResponse;

/**
 * Acts as a presenter in the application's architecture,
 * converting the {@link EditPreferencesResponse} from the use case layer
 * into a format suitable for the user interface layer.
 */
public class EditPreferencesPresenter {
    /**
     * Holds the current state of the presenter,
     * representing the result of the last operation.
     */
    private EditPreferencesState state;

    /**
     * Converts the given {@link EditPreferencesResponse} into an {@link EditPreferencesState}.
     * This method updates the presenter's state based on the response from the use case.
     *
     * @param response The response from the use case containing operation results.
     */
    public void present(EditPreferencesResponse response) {
        this.state = new EditPreferencesState(
                response.isSuccess(),
                response.getMessage(),
                response.getUpdatedGenres(),
                response.getUpdatedArtists()
        );
    }

    /**
     * Retrieves the current state of the presenter.
     *
     * @return The {@link EditPreferencesState} representing the most recent operation result.
     */
    public EditPreferencesState getState() {
        return state;
    }
}
