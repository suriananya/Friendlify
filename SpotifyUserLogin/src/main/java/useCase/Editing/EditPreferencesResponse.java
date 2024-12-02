package useCase.Editing;

import java.util.List;

/**
 * Represents the response of editing user preferences in a system.
 * It contains the status of the operation, any error messages,
 * and the updated lists of genres and artists if applicable.
 */
public class EditPreferencesResponse {
    /**
     * Indicates whether the operation was successful.
     */
    private final boolean success;

    /**
     * The error message describing why the operation failed, if applicable.
     * Null or empty if the operation was successful.
     */
    private final String errorMessage;

    /**
     * A list of genres updated during the operation.
     */
    private final List<String> updatedGenres;

    /**
     * A list of artists updated during the operation.
     */
    private final List<String> updatedArtists;

    /**
     * Constructs an instance of {@code EditPreferencesResponse}.
     *
     * @param success       Indicates whether the operation was successful.
     * @param errorMessage  The error message if the operation failed; null or empty if successful.
     * @param updatedGenres A list of updated genres; null or empty if none were updated.
     * @param updatedArtists A list of updated artists; null or empty if none were updated.
     */
    public EditPreferencesResponse(boolean success, String errorMessage, List<String> updatedGenres, List<String> updatedArtists) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.updatedGenres = updatedGenres;
        this.updatedArtists = updatedArtists;
    }

    /**
     * Checks if the operation was successful.
     *
     * @return {@code true} if the operation was successful; {@code false} otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Retrieves the error message if the operation failed.
     *
     * @return The error message, or null if the operation was successful.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Retrieves the updated list of genres.
     *
     * @return A list of updated genres; null or empty if none were updated.
     */
    public List<String> getUpdatedGenres() {
        return updatedGenres;
    }

    /**
     * Retrieves the updated list of artists.
     *
     * @return A list of updated artists; null or empty if none were updated.
     */
    public List<String> getUpdatedArtists() {
        return updatedArtists;
    }

    /**
     * Returns a message describing the operation result.
     *
     * @return A success message if the operation was successful,
     *         otherwise the error message.
     */
    public String getMessage() {
        return success ? "Preferences updated successfully." : errorMessage;
    }
}
