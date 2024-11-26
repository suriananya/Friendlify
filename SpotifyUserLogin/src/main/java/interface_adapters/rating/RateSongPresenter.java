package interface_adapters.rating;

public class RateSongPresenter {
    public static String prepareSuccessMessage() {
        return "Your rating has been submitted successfully!";
    }

    public String prepareErrorMessage() {
        return "An error occurred while submitting your rating.";
    }
}


