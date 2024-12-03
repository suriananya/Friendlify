package TestRoulette;

import entities.Song;
import org.json.JSONObject;
import org.junit.Test;
import useCase.Roulette.SurpriseMeInteractor;
import java.util.List;

import static org.junit.Assert.*;

public class TestSurpriseMeInteractor {

    private final MockSpotifyInteractor mockSpotifyInteractor = new MockSpotifyInteractor();
    private final MockUserProfile mockUserProfile = new MockUserProfile(mockSpotifyInteractor);
    private final SurpriseMeInteractor surpriseMeInteractor = new SurpriseMeInteractor(mockSpotifyInteractor,
            mockUserProfile);

    @Test
    public void testRandomSongFromFriendWithSongs() {
        mockUserProfile.setMockFriends(List.of("friend1", "friend2"));
        Song randomSong = surpriseMeInteractor.getRandomSongFromFriends();
        assertNotNull("The song should not be null.", randomSong);
        assertTrue("The song's title should match one of the predefined songs.",
                List.of("Song 1", "Song 2", "Song 3").contains(randomSong.getTitle()));
    }

    @Test
    public void testFriendWithNoPlaylists() {
        mockUserProfile.setMockFriends(List.of("friend3")); // Friend with no playlists
        Song randomSong = surpriseMeInteractor.getRandomSongFromFriends();
        assertNotNull("The song should not be null when other friends have valid playlists.", randomSong);
    }

    @Test
    public void testPlaylistWithNoSongs() {
        mockUserProfile.setMockFriends(List.of("friend4", "friend1"));
        Song randomSong = surpriseMeInteractor.getRandomSongFromFriends();
        assertNotNull("The song should not be null when other playlists have valid songs.", randomSong);
    }

    @Test
    public void testNoValidSongsFound() {
        MockUserProfile mockEmptyProfile = new MockUserProfile(mockSpotifyInteractor) {
            @Override
            public List<String> getFriendsList(String type) {
                return List.of("friend3", "friend4");
            }
        };

        SurpriseMeInteractor emptyInteractor = new SurpriseMeInteractor(mockSpotifyInteractor, mockEmptyProfile);

        try {
            emptyInteractor.getRandomSongFromFriends();
            fail("Expected a RuntimeException when no valid songs are found.");
        } catch (RuntimeException e) {
            assertEquals("No tracks found in friends' playlists.", e.getMessage());
        }
    }

    @Test
    public void testFriendWithNullPlaylists() {
        // Friend with null playlists
        MockSpotifyInteractor nullPlaylistSpotifyInteractor = new MockSpotifyInteractor() {
            @Override
            public JSONObject getUserPlaylists(String userId, int limit, int offset) {
                return new JSONObject().put("items", JSONObject.NULL); // Simulating null playlists
            }
        };
        SurpriseMeInteractor interactor = new SurpriseMeInteractor(nullPlaylistSpotifyInteractor, mockUserProfile);

        try {
            interactor.getRandomSongFromFriends();
            fail("Expected a RuntimeException when no valid playlists are found.");
        } catch (RuntimeException e) {
            assertEquals("No tracks found in friends' playlists.", e.getMessage());
        }
    }

    @Test
    public void testExceptionHandling() {
        MockSpotifyInteractor exceptionSpotifyInteractor = new MockSpotifyInteractor() {
            @Override
            public JSONObject getUserPlaylists(String userId, int limit, int offset) {
                throw new RuntimeException("Simulated exception");
            }
        };
        SurpriseMeInteractor interactor = new SurpriseMeInteractor(exceptionSpotifyInteractor, mockUserProfile);

        try {
            interactor.getRandomSongFromFriends();
            fail("Expected a RuntimeException when an exception occurs in the interactor.");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("No tracks found in friends' playlists."));
        }
    }
}
