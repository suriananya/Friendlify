package interfaceAdapters.rating;

import useCase.Rating.RateSongUseCase;
import entities.Song;

import java.util.Map;

public class RateSongController {
    private static RateSongUseCase rateSongUseCase = null;
    private static Map<String, Song> songRepository = Map.of(); // A simple in-memory song repository

    public RateSongController(RateSongUseCase rateSongUseCase, Map<String, Song> songRepository) {
        this.rateSongUseCase = rateSongUseCase;
        this.songRepository = songRepository;
    }

    public static void rateSong(String songId, String userId, int score, String comment) {
        Song song = songRepository.get(songId); // Retrieve the song
        if (song != null) {
            rateSongUseCase.rateSong(song, userId, score, comment);
        } else {
            System.out.println("Song not found: " + songId);
        }
    }
}

