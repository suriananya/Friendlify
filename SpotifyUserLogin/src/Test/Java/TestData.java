
import entities.Playlist;
import entities.Song;
import entities.UserProfile;
import java.util.Arrays;
import java.util.List;

public class TestData {
    // Method to create mock user profile
    public static UserProfile createMockUserProfile() {
        List<Song> songs = Arrays.asList(
                new Song("Blinding Lights", "The Weeknd"),
                new Song("Shake It Off", "Taylor Swift"),
                new Song("Hotline Bling", "Drake")
        );
        Playlist playlist = new Playlist("Top Hits", songs);

        return new UserProfile(
                "MusicLover123",
                List.of(playlist),
                Arrays.asList("Pop", "R&B", "Hip-Hop"),
                Arrays.asList("Taylor Swift", "The Weeknd", "Drake")
        );
    }
}
