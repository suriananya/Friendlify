package useCase.Rating;

import entities.Rating;
import entities.Song;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class RateSongUseCase {

    private static final Logger logger = Logger.getLogger(RateSongUseCase.class.getName());

    /**
     * Adds or updates a rating for a specific song.
     *
     * @param song    the song to be rated; must not be null
     * @param userId  the ID of the user submitting the rating; must not be null or empty
     * @param score   the rating score; must be between 1 and 5
     * @param comment an optional comment; if provided, must not exceed 500 characters
     *
     * @throws IllegalArgumentException if the song is null, the score is invalid,
     *                                  or the comment exceeds the maximum length
     */
    public void rateSong(Song song, String userId, int score, String comment) {
        if (song == null) {
            logger.severe("Song not found!");
            throw new IllegalArgumentException("Song not found.");
        }

        // Validate rating score
        if (!isValidScore(score)) {
            logger.warning("Invalid score provided: " + score);
            throw new IllegalArgumentException("Score must be between 1 and 5.");
        }

        // Validate comment length (optional)
        if (comment != null && comment.length() > 500) {
            logger.warning("Comment too long: " + comment);
            throw new IllegalArgumentException("Comment cannot exceed 500 characters.");
        }

        // Check if the user has already rated this song
        Optional<Rating> existingRating = song.getRatings().stream()
                .filter(rating -> rating.getUserId().equals(userId))
                .findFirst();

        // If user has rated before, update the existing rating
        if (existingRating.isPresent()) {
            updateRating(existingRating.get(), score, comment);
            logger.info("Updated rating for user: " + userId + " on song: " + song.getTitle());
        } else {
            // Add new rating if no existing rating
            addNewRating(song, userId, score, comment);
            logger.info("Added new rating for user: " + userId + " on song: " + song.getTitle());
        }
    }

    /**
     * Validates whether the provided score is within the valid range (1 to 5).
     *
     * @param score the score to validate
     * @return true if the score is between 1 and 5 (inclusive), false otherwise
     */
    private boolean isValidScore(int score) {
        return score >= 1 && score <= 5;
    }

    /**
     * Updates an existing rating with a new score and comment.
     *
     * @param existingRating the existing {@link Rating} to be updated
     * @param newScore       the new score to set, must be between 1 and 5
     * @param newComment     the new comment to set; can be null
     */
    private void updateRating(Rating existingRating, int newScore, String newComment) {
        existingRating.setScore(newScore);
        existingRating.setComment(newComment);
    }

    /**
     * Adds a new rating to the given song.
     *
     * @param song    the {@link Song} to add the rating to; must not be null
     * @param userId  the ID of the user submitting the rating; must not be null or empty
     * @param score   the rating score; must be between 1 and 5
     * @param comment an optional comment; can be null
     */
    private void addNewRating(Song song, String userId, int score, String comment) {
        Rating newRating = new Rating(userId, score, comment);
        song.addRating(newRating);
    }

    /**
     * Retrieves all ratings for a specific song.
     *
     * @param song the song whose ratings are to be retrieved; must not be null
     * @return a list of all rating objects associated with the song
     *
     * @throws IllegalArgumentException if the song is null
     */
    public List<Rating> getAllRatings(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Song cannot be null.");
        }
        return song.getRatings();
    }
}
