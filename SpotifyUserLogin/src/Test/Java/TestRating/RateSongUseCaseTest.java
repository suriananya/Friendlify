package TestRating;

import entities.Rating;
import entities.Song;
import org.junit.Test;
import useCase.Rating.RateSongUseCase;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the RateSongUseCase.
 */
public class RateSongUseCaseTest {

    /**
     * Tests the rateSong method to ensure it correctly adds a rating to a song.
     */
    @Test
    public void testRateSong() {
        // Arrange: Mock data created
        List<String> genre = Arrays.asList("Jazz", "RnB");
        Song song = new Song("Test Song", "Test Artist", genre);
        String userId = "user123";
        int score = 5;
        String comment = "Amazing song!";
        RateSongUseCase rateSongUseCase = new RateSongUseCase();

        // Act: Perform the rating
        rateSongUseCase.rateSong(song, userId, score, comment);

        // Assert: Validate the added rating
        assertEquals(String.valueOf(1), song.getRatings().size(), 1);
        Rating addedRating = song.getRatings().get(0);
        assertEquals(userId, addedRating.getUserId(), "user123");
        assertEquals(String.valueOf(score), addedRating.getScore(), 5);
        assertEquals(comment, addedRating.getComment(), "Amazing song!");
    }
}
