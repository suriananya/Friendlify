package interfaceAdapters.editpreferences;

import useCase.Editing.EditPreferencesResponse;

public class EditPreferencesPresenter {
    private EditPreferencesState state;

    public void present(EditPreferencesResponse response) {
        this.state = new EditPreferencesState(
                response.isSuccess(),
                response.getMessage(),
                response.getUpdatedGenres(),
                response.getUpdatedArtists()
        );
    }

    public EditPreferencesState getState() {
        return state;
    }
}
