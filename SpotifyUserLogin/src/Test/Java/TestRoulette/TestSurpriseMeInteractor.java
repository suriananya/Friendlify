package TestRoulette;

import entities.Song;
import org.junit.Test;
import useCase.Roulette.SurpriseMeInteractor;

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
        Song randomSong = surpriseMeInteractor.getRandomSongFromFriends();
        assertNotNull("The song should not be null.", randomSong);
    }


}
