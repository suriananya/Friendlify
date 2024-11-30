package useCase;

import api.SpotifyInteractor;
import entities.Song;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class SurpriseMeInteractor {
    private final SpotifyInteractor spotifyInteractor;

    public SurpriseMeInteractor(SpotifyInteractor spotifyInteractor) {
        this.spotifyInteractor = spotifyInteractor;
    }

    public Song getRandomSongFromFriends(boolean usePreferences) {
        // Retrieve playlists that are not owned by the user (friends' playlists)
        JSONObject playlistsJson = spotifyInteractor.getNonOwnedPlaylists(20, 0);

        JSONArray playlists = playlistsJson.optJSONArray("items");

        if (playlists == null || playlists.isEmpty()) {
            throw new RuntimeException("No friend playlists available.");
        }

        // Pick a random playlist
        Random random = new Random();
        JSONObject randomPlaylist = playlists.getJSONObject(random.nextInt(playlists.length()));

        // Fetch items (songs) from the playlist
        JSONObject playlistItemsJson = spotifyInteractor.getPlaylistItems(randomPlaylist.getString("id"), 50, 0);
        JSONArray tracks = playlistItemsJson.optJSONArray("items");

        if (tracks == null || tracks.isEmpty()) {
            throw new RuntimeException("No tracks available in the selected playlist.");
        }

        // Pick a random track from the playlist
        JSONObject randomTrack = tracks.getJSONObject(random.nextInt(tracks.length())).getJSONObject("track");

        String title = randomTrack.getString("name");
        String artist = randomTrack.getJSONArray("artists").getJSONObject(0).getString("name");

        return new Song(title, artist, null);
        // NOTE: Genres are not being fetched, but can be added to later
    }
}