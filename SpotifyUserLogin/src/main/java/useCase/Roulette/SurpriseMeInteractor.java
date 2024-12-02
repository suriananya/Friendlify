package useCase.Roulette;

import api.SpotifyInteractor;
import entities.Song;
import entities.users.UserProfile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;
import java.util.List;

public class SurpriseMeInteractor {
    private final SpotifyInteractor spotifyInteractor;
    private final UserProfile userProfile; // Initialize this properly

    public SurpriseMeInteractor(SpotifyInteractor spotifyInteractor, UserProfile userProfile) {
        this.spotifyInteractor = spotifyInteractor;
        this.userProfile = userProfile; // Assign the passed userProfile
    }

    public Song getRandomSongFromFriends() {
        // Retrieve friend IDs
        List<String> friendIds = userProfile.getFriendsList("id");

        // Randomly pick a friend and fetch their playlists
        Random random = new Random();
        for (String friendId : friendIds) {
            try {
                // Fetch playlists for the friend
                JSONObject playlistsJson = spotifyInteractor.getUserPlaylists(friendId, 10, 0);
                JSONArray playlists = playlistsJson.optJSONArray("items");

                if (playlists != null && playlists.length() > 0) {
                    // Pick a random playlist
                    JSONObject randomPlaylist = playlists.getJSONObject(random.nextInt(playlists.length()));
                    String playlistId = randomPlaylist.getString("id");

                    // Fetch tracks from the playlist
                    JSONObject playlistItemsJson = spotifyInteractor.getPlaylistItems(playlistId, 50, 0);
                    JSONArray tracks = playlistItemsJson.optJSONArray("items");

                    if (tracks != null && tracks.length() > 0) {
                        // Pick a random track
                        JSONObject randomTrack = tracks.getJSONObject(random.nextInt(tracks.length())).getJSONObject("track");
                        String title = randomTrack.getString("name");
                        String artist = randomTrack.getJSONArray("artists").getJSONObject(0).getString("name");

                        // Return the selected song
                        return new Song(title, artist, null);
                    }
                }
            } catch (Exception e) {
                // Skip this friend if any error occurs (e.g., no playlists or tracks)
                System.err.println("Error fetching data for friend " + friendId + ": " + e.getMessage());
            }
        }

        // If no songs are found after iterating through all friends, throw an exception
        throw new RuntimeException("No tracks found in friends' playlists.");
    }
}