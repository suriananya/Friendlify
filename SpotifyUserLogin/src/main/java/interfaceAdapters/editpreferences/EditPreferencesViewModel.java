package interfaceAdapters.editpreferences;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * Represents the ViewModel for editing preferences, adhering to the MVVM architecture.
 * It provides observable properties for genres, artists, and update status to notify
 * the view of any changes.
 */
public class EditPreferencesViewModel {
    /**
     * A list of genres reflecting the user's preferences.
     */
    private List<String> genres;

    /**
     * A list of artists reflecting the user's preferences.
     */
    private List<String> artists;

    /**
     * A string representing the status of the last update operation.
     */
    private String updateStatus;

    /**
     * A support object to manage property change listeners and notify observers of property changes.
     */
    private final PropertyChangeSupport support;

    /**
     * Constructs an instance of {@code EditPreferencesViewModel}.
     * Initializes the {@link PropertyChangeSupport} for managing listeners.
     */
    public EditPreferencesViewModel() {
        support = new PropertyChangeSupport(this);
    }

    /**
     * Retrieves the list of genres.
     *
     * @return The current list of genres.
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * Updates the list of genres and notifies listeners of the change.
     *
     * @param genres The new list of genres.
     */
    public void setGenres(List<String> genres) {
        List<String> oldGenres = this.genres;
        this.genres = genres;
        support.firePropertyChange("genres", oldGenres, genres);
    }

    /**
     * Retrieves the list of artists.
     *
     * @return The current list of artists.
     */
    public List<String> getArtists() {
        return artists;
    }

    /**
     * Updates the list of artists and notifies listeners of the change.
     *
     * @param artists The new list of artists.
     */
    public void setArtists(List<String> artists) {
        List<String> oldArtists = this.artists;
        this.artists = artists;
        support.firePropertyChange("artists", oldArtists, artists);
    }
}
