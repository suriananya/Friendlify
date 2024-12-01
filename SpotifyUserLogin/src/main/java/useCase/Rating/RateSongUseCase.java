package useCase.Rating;

import entities.Rating;
import entities.Song;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class RateSongUseCase {

    private static final Logger logger = Logger.getLogger(RateSongUseCase.class.getName());

    // Rate a song with a score and comment
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

    // Check if the score is valid (between 1 and 5)
    private boolean isValidScore(int score) {
        return score >= 1 && score <= 5;
    }

    // Update an existing rating
    private void updateRating(Rating existingRating, int newScore, String newComment) {
        existingRating.setScore(newScore);
        existingRating.setComment(newComment);
    }

    // Add a new rating to the song
    private void addNewRating(Song song, String userId, int score, String comment) {
        Rating newRating = new Rating(userId, score, comment);
        song.addRating(newRating);
    }

    // Retrieve all ratings for a song
    public List<Rating> getAllRatings(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Song cannot be null.");
        }
        return song.getRatings();
    }
}
