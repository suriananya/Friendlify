package api;

import org.json.JSONObject;

/**
 * A database for all the methods required in SpotifyAPI.
 * This will hopefully improve readability, since the SpotifyAPI
 * class is quite esoteric and challenging to read.
 */
public interface SpotifyDatabase {
    /**
     * Get all the playlist items from a chosen playlist.
     * @param playlistID is the ID of that targeted playlist
     * @return the Spotify API response for a "Get Playlist Items" call
     */
    JSONObject getPlaylistItems(String playlistID);

    /**
     * Get the playlists of the current user.
     * @return the Spotify API response for a "Get Current User Playlists" call
     */
    JSONObject getCurrentUserPlaylists();

    /**
     * Get the playlists of a chosen user
     * @param userID is the ID of the targeted user
     * @return the Spotify API response for a "Get User Playlists" call
     */
    JSONObject getUserPlaylists(String userID);

    /**
     * Get the profile of the current user.
     * @return the Spotify API response for a "Get Current User Profile" call.
     */
    JSONObject getCurrentUserProfile();
}
