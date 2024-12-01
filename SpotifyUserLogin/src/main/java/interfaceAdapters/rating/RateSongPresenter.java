package interfaceAdapters.rating;

/**
 * Handles the preparation of success and error messages for the song rating process.
 */
public class RateSongPresenter {

    /**
     * Prepares a success message indicating that the rating was submitted successfully.
     *
     * @return a success message string
     */
    public static String prepareSuccessMessage() {
        return "Your rating has been submitted successfully!";
    }

    /**
     * Prepares an error message indicating that an issue occurred during the rating submission.
     *
     * @return an error message string
     */
    public String prepareErrorMessage() {
        return "An error occurred while submitting your rating.";
    }
}


