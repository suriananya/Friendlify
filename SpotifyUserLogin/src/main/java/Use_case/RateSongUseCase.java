package Use_case;

import entities.Rating;
import entities.Song;

public class RateSongUseCase {

    public void rateSong(Song song, String userId, int score, String comment) {
        if (song != null) {
            Rating rating = new Rating(userId, score, comment);
            song.addRating(rating);
            // Additional logic can be added here if needed
        }
    }
}

