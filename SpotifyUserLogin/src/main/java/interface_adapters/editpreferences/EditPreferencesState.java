package interface_adapters.editpreferences;

import java.util.List;

public class EditPreferencesState {
    private final boolean success;
    private final String message;
    private final List<String> genres;
    private final List<String> artists;

    public EditPreferencesState(boolean success, String message, List<String> genres, List<String> artists) {
        this.success = success;
        this.message = message;
        this.genres = genres;
        this.artists = artists;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getArtists() {
        return artists;
    }
}
