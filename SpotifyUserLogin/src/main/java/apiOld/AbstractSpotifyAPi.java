package apiOld;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A database for all the methods required in SpotifyAPI.
 * This will hopefully improve readability, since the SpotifyAPI
 * class is quite esoteric and challenging to read.
 */
public abstract class AbstractSpotifyAPi {
    private static final OkHttpClient client = new OkHttpClient();

    // Technically I should not be sharing any of this information, but I cannot be bothered to hide it right now
    private static final String clientID = "53ee2a266cd542acaf19190e2ec3da41";
    private static final String clientSecret = "0567ae1ac8e1415ba72f748808a69377";

    /**
     * We need an access token to use Spotify API features.
     * This requests the related information directly from the API.
     * @return the response from Spotify requesting an access token, if possible. Otherwise, an empty JSONObject
     */
    protected JSONObject accessTokenResponse() {
        // Define the parameters for the request body
        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", clientID)
                .add("client_secret", clientSecret)
                .build();

        // Create the POST request to collect the API token
        Request postRequest = new Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(requestBody)
                .build();

        try {
            // Collect the response from the API requests, and return the access token
            Response response = client.newCall(postRequest).execute();
            assert response.body() != null;
            return new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // If there's an error, return an empty JSONObject
        return new JSONObject();
    }

    /**
     * A method that extract the access token from the associated raw Spotify response.
     * @return the access token.
     */
    protected String requestToken() {
        return accessTokenResponse().getString("access_token");
    }

    /**
     * Get all the playlist items from a chosen playlist.
     * @param playlistID is the ID of that targeted playlist
     * @return the Spotify API response for a "Get Playlist Items" call
     */
    abstract JSONObject getPlaylistItems(String playlistID);

    /**
     * Get the playlists of the current user.
     * @return the Spotify API response for a "Get Current User Playlists" call
     */
    abstract JSONObject getCurrentUserPlaylists();

    /**
     * Get the playlists of a chosen user
     * @param userID is the ID of the targeted user
     * @return the Spotify API response for a "Get User Playlists" call
     */
    abstract JSONObject getUserPlaylists(String userID);

    /**
     * Get the profile of the current user.
     * @return the Spotify API response for a "Get Current User Profile" call.
     */
    abstract JSONObject getCurrentUserProfile();
}
