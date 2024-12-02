package interfaceAdapters.editpreferences;

import java.util.List;

/**
 * Represents the state of the edit preferences operation as formatted for the user interface.
 * Contains the result of the operation, a message, and the updated preferences.
 */
public class EditPreferencesState {
    /**
     * Indicates whether the operation was successful.
     */
    private final boolean success;

    /**
     * A message describing the result of the operation.
     * For successful operations, this is a success message.
     * For failed operations, this is an error message.
     */
    private final String message;

    /**
     * A list of genres reflecting the updated preferences.
     */
    private final List<String> genres;

    /**
     * A list of artists reflecting the updated preferences.
     */
    private final List<String> artists;

    /**
     * Constructs an instance of {@code EditPreferencesState}.
     *
     * @param success Indicates whether the operation was successful.
     * @param message A message describing the result of the operation.
     * @param genres  The updated list of genres; null or empty if none were updated.
     * @param artists The updated list of artists; null or empty if none were updated.
     */
    public EditPreferencesState(boolean success, String message, List<String> genres, List<String> artists) {
        this.success = success;
        this.message = message;
        this.genres = genres;
        this.artists = artists;
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
     * Retrieves the message describing the operation result.
     *
     * @return A success or error message based on the operation's outcome.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the updated list of genres.
     *
     * @return A list of genres reflecting the updated preferences;
     *         null or empty if none were updated.
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * Retrieves the updated list of artists.
     *
     * @return A list of artists reflecting the updated preferences;
     *         null or empty if none were updated.
     */
    public List<String> getArtists() {
        return artists;
    }
}
