package java.entities;
import java.util.List;

public class Genre {
    private final List<String> genres;

    public Genre(List<String> genres) {
        this.genres = genres;
    }
    public List<String> getGenres() {
        return genres;
    }
}

