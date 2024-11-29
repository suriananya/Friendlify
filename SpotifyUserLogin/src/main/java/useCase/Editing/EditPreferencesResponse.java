package useCase.Editing;

import java.util.List;

public class EditPreferencesResponse {
    private final boolean success;
    private final String errorMessage;
    private final List<String> updatedGenres;
    private final List<String> updatedArtists;

    public EditPreferencesResponse(boolean success, String errorMessage, List<String> updatedGenres, List<String> updatedArtists) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.updatedGenres = updatedGenres;
        this.updatedArtists = updatedArtists;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<String> getUpdatedGenres() {
        return updatedGenres;
    }

    public List<String> getUpdatedArtists() {
        return updatedArtists;
    }

    /**
     * Returns a message based on the success status.
     * @return Success message if successful, otherwise the error message.
     */
    public String getMessage() {
        return success ? "Preferences updated successfully." : errorMessage;
    }
}
