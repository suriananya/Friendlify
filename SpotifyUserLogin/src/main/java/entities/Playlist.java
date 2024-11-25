package entities;

import java.util.List;

public class Playlist {
    private final String id; // Playlist ID
    private final String name; // Playlist name
    private final List<Song> songs; // Songs in the playlist

    public Playlist(String id, String name, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.songs = songs;
    }

    // Getter for ID
    public String getId() {
        return id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for songs
    public List<Song> getSongs() {
        return songs;
    }
}
