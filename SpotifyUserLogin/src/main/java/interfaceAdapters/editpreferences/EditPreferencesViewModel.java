package interfaceAdapters.editpreferences;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class EditPreferencesViewModel {
    private List<String> genres;
    private List<String> artists;
    private String updateStatus;
    private final PropertyChangeSupport support;

    public EditPreferencesViewModel() {
        support = new PropertyChangeSupport(this);
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        List<String> oldGenres = this.genres;
        this.genres = genres;
        support.firePropertyChange("genres", oldGenres, genres);
    }

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        List<String> oldArtists = this.artists;
        this.artists = artists;
        support.firePropertyChange("artists", oldArtists, artists);
    }

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        String oldStatus = this.updateStatus;
        this.updateStatus = updateStatus;
        support.firePropertyChange("updateStatus", oldStatus, updateStatus);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}


