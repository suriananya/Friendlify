package api;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A collection of functions required to interact with the Spotify API
 */
public class SpotifyAPI implements SpotifyDatabase{
    private static final OkHttpClient client = new OkHttpClient();
    private final String accessToken = requestToken().getString("access_token");

    /**
     * We need an access token to use Spotify API features.
     * This requests the related information directly from the API.
     * @return the response from Spotify requesting an access token, if possible. Otherwise, an empty JSONObject
     */
    private static JSONObject requestToken() {
        // Define the parameters for the request body
        // Technically I should not be sharing any of this information, but I cannot be bothered to hide it right now
        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", "53ee2a266cd542acaf19190e2ec3da41")
                .add("client_secret", "0567ae1ac8e1415ba72f748808a69377")
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
    public String getClientAccessToken() {
        return accessToken;
    }

    // Initializing a main function to help test code
    public static void main(String[] args) {
        SpotifyAPI api = new SpotifyAPI();
        System.out.println(api.getClientAccessToken());
    }

    @Override
    public JSONObject getPlaylistItems(String playlistID) {
        return null;
    }

    @Override
    public JSONObject getCurrentUserPlaylists() {
        return null;
    }

    @Override
    public JSONObject getUserPlaylists(String userID) {
        return null;
    }
}
