package entities;

import java.util.List;

public class Song {
    private final String title;
    private final String artist;
    private final List<String> genres;

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
}
