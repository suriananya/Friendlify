package api;

import org.json.JSONObject;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

/**
 * A class to dictate and document the methods required for the SpotifyInteractor.
 * Ideally, this class will help increase readability.
 */
public abstract class AbstractSpotifyInteractor {
    // Technically I should not be sharing any of this information, but I cannot be bothered to hide it right now
    private static final String clientID = "53ee2a266cd542acaf19190e2ec3da41";
    private static final String clientSecret = "0567ae1ac8e1415ba72f748808a69377";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:3000");

    // Initialize important variables for app functionality
    public static final String applicationScope =
            "user-follow-read user-read-private playlist-read-private playlist-read-collaborative";
    String code;
    String accessToken;
    String refreshToken;

    // Initialize the API wrapper class
    final SpotifyApi api = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .setAccessToken(this.getAccessToken())
            .setRefreshToken(this.getRefreshToken())
            .build();

    // *** Methods beyond this point ***

    /**
     * Access token will expire after 1 hour.
     * This method will refresh the user's access token so it can continue being used
     */
    abstract void authorizationCodeRefresh();

    /**
     * To access the SpotifyAPI, we need a user's access token. This token expires after 1 hour.
     * To prevent their access token from expiring, we need a refresh token to refresh it.
     * Using the authorization code pulled from the URI link,
     * this method extracts and sets the user's current access and refresh tokens.
     */
    abstract void authorizationCode();

    /**
     * Sends a link to the URI link required for the user to give authorization.
     * We need this authorization so we are allowed to access the user's data.
     */
    abstract void authorizationCodeUri();

    /**
     * Completes the login scheme by combining a series of methods to log in the user.
     * This method will provide instructions to help log in through the command line.
     * Furthermore, there are instructions located below, in this JavaDoc.<p>
     *  Login instructions<p>
     *  1. When you run this file, it will send you a link to authenticate your account.<p>
     *  2. Click on this link to authenticate your account.<p>
     *  3. Afterward, it will send you to a blank page, where you will receive a connection error.<p>
     *  4. Copy the link of the page you were sent to.<p>
     *  5. Paste it into the command line, and press enter.<p>
     *  6. From there, the code will generate for you an access and refresh token.<p>
     *  7. If you receive no errors. This means that you've logged in.
     */
    abstract void login();

    /**
     * Make a call to the Spotify API to collect artist data. Read more here:
     * <a href="https://developer.spotify.com/documentation/web-api/reference/get-an-artist">Get Artist</a>
     * @param artistId the id of the targeted artist.
     * @return the response from the Spotify API. null if there is an error.
     */
    public abstract JSONObject getArtist(String artistId);

    /**
     * Makes a call to the Spotify API to collect a playlist's items. Read more here:
     * <a href="https://developer.spotify.com/documentation/web-api/reference/get-playlists-tracks">
     * Get Playlist Items</a>
     * @param playlistId the id of the targeted playlist.
     * @param limit the maximum number of items to return.
     * @param offset the index of the first item to return.
     * @return the response from the Spotify API. null if there is an error.
     */
    public abstract JSONObject getPlaylistItems(String playlistId, int limit, int offset);

    /**
     * Makes a call to the Spotify API to collect the current user's playlist information. Read more here:
     * <a href="https://developer.spotify.com/documentation/web-api/reference/get-a-list-of-current-users-playlists">
     *     Get Current User's Playlists</a>
     * @param limit the maximum number of items to return.
     * @param offset the index of the first item to return.
     * @return the response from the Spotify API. null if there is an error.
     */
    public abstract JSONObject getCurrentUserPlaylists(int limit, int offset);

    /**
     * Makes a call to the Spotify API to collect a user's playlist information. Read more here:
     * <a href="https://developer.spotify.com/documentation/web-api/reference/get-list-users-playlists">
     * Get User's Playlists</a>
     * @param userId the id of the targeted user.
     * @param limit the maximum number of items to return.
     * @param offset the index of the first item to return.
     * @return the response from the Spotify API. null if there is an error.
     */
    public abstract JSONObject getUserPlaylists(String userId, int limit, int offset);

    /**
     * Makes a call to the Spotify API to collect the current user's profile information. Read more here:
     * <a href="https://developer.spotify.com/documentation/web-api/reference/get-current-users-profile">
     *     Get Current User's Profile</a>
     * @return the response from the Spotify API. null if there is an error.
     */
    public abstract JSONObject getCurrentUserProfile();

    /**
     * Makes a call to the Spotify API to collect a user's profile information. Read more here:
     * <a href="https://developer.spotify.com/documentation/web-api/reference/get-users-profile">Get User's Profile</a>
     * @param userId the id of the targeted user.
     * @return the response from the Spotify API. null if there is an error.
     */
    public abstract JSONObject getUserProfile(String userId);

    // *** Beyond this point, there should only be default getter and setter methods ***

    public String getCode() {
        return code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        api.setAccessToken(accessToken);
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        api.setRefreshToken(refreshToken);
    }
}
