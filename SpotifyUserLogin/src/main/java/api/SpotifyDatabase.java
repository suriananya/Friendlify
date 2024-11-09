package api;

import org.json.JSONObject;

public interface SpotifyDatabase {
    /**
     * Get all the playlist items from a chosen playlist.
     * @param playlistID is the ID of that targeted playlist
     * @return a JSONObject containing all the playlist information.
     */
    JSONObject getPlaylistItems(String playlistID);

    /**
     * Get the playlists of the current user.
     * @return a JSONObject containing the current user's playlist information.
     */
    JSONObject getCurrentUserPlaylists();

    /**
     * Get the playlists of a chosen user
     * @param userID is the ID of the targeted user
     * @return a JSONObject containing a user's playlist information
     */
    JSONObject getUserPlaylists(String userID);

}
