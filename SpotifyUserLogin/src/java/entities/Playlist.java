package java.entities;
import java.util.List;

public class Playlist {
    private final String name;
    private final List<String> songs;

    public Playlist(String name, List<String> songs) {
        this.name = name;
        this.songs = songs;
    }
    public String getName() {
        return name;
    }
    public List<String> getSongs() {
        return songs;
    }
}
