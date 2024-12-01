package interfaceAdapters.rating;

import useCase.Rating.RateSongUseCase;
import entities.Song;

import java.util.Map;

/**
 * Controller for handling song rating requests.
 * Communicates with the RateSongUseCase and manages the song repository.
 */
public class RateSongController {
    private static RateSongUseCase rateSongUseCase = null;
    private static Map<String, Song> songRepository = Map.of(); // A simple in-memory song repository

    /**
     * Constructs a RateSongController instance.
     *
     * @param rateSongUseCase the use case for handling song ratings
     * @param songRepository a map representing the song repository
     */
    public RateSongController(RateSongUseCase rateSongUseCase, Map<String, Song> songRepository) {
        this.rateSongUseCase = rateSongUseCase;
        this.songRepository = songRepository;
    }

    /**
     * Processes a request to rate a song.
     *
     * @param songId the ID of the song to be rated
     * @param userId the ID of the user submitting the rating
     * @param score the rating score, must be between 1 and 5
     * @param comment an optional comment provided with the rating
     */
    public static void rateSong(String songId, String userId, int score, String comment) {
        Song song = songRepository.get(songId); // Retrieve the song
        if (song != null) {
            rateSongUseCase.rateSong(song, userId, score, comment);
        } else {
            System.out.println("Song not found: " + songId);
        }
    }
}

