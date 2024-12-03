package TestRoulette;

import entities.Song;
import org.junit.Test;
import useCase.Roulette.SurpriseMeInteractor;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TestSurpriseMeInteractor {

    private final MockSpotifyInteractor mockSpotifyInteractor = new MockSpotifyInteractor();
    private final MockUserProfile mockUserProfile = new MockUserProfile(mockSpotifyInteractor);
    private final SurpriseMeInteractor surpriseMeInteractor = new SurpriseMeInteractor(mockSpotifyInteractor,
            mockUserProfile);

    @Test
    public void testRandomSongFromFriendWithSongs() {
        Song randomSong = surpriseMeInteractor.getRandomSongFromFriends();
        assertNotNull("The song should not be null.", randomSong);
        assertTrue(
                "The song's title should match one of the predefined songs.",
                randomSong.getTitle().equals("Song 1") || randomSong.getTitle().equals("Song 2")
        );
        assertTrue(
                "The song's artist should match the corresponding artist.",
                randomSong.getArtist().equals("Artist 1") || randomSong.getArtist().equals("Artist 2")
        );
    }

    @Test
    public void testFriendWithNoPlaylists() {
        mockUserProfile.setMockFriends(Arrays.asList("friend3"));
        Song randomSong = surpriseMeInteractor.getRandomSongFromFriends();
        assertNotNull("The song should not be null when other friends have valid playlists.", randomSong);
    }

    @Test
    public void testPlaylistWithNoSongs() {
        mockUserProfile.setMockFriends(Arrays.asList("friend4"));
        Song randomSong = surpriseMeInteractor.getRandomSongFromFriends();
        assertNotNull("The song should not be null.", randomSong);
        assertNotNull("The song should not be null when other playlists have valid songs.", randomSong);
    }

    @Test
    public void testNoValidSongsFound() {
        MockUserProfile mockEmptyProfile = new MockUserProfile(mockSpotifyInteractor) {

            public List<String> getFriendsList(String type) {
                return Arrays.asList("friend3", "friend4");
            }
        };

        SurpriseMeInteractor emptyInteractor = new SurpriseMeInteractor(mockSpotifyInteractor, mockEmptyProfile);
        emptyInteractor.getRandomSongFromFriends();
    }
}
