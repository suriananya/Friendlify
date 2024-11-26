package entities;

import java.util.List;
import java.util.ArrayList;

public class Song {
    private final String title;
    private final String artist;
    private final List<String> genres;
    private final List<Rating> ratings = new ArrayList<>();

    public Song(String title, String artist, List<String> genres) {
        this.title = title;
        this.artist = artist;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public List<String> getGenres() {
        return genres != null ? genres : List.of();
    }

    public void addRating(Rating rating) {
        this.ratings.add(rating);
    }

    public List<Rating> getRatings() {
        return new ArrayList<>(ratings);
    }
}
