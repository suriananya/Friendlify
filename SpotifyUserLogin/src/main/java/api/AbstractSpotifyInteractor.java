package api;

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
            "user-follow-read user-library-read user-read-private playlist-read-private";
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
     * Furthermore, there are instructions located below, in this JavaDoc.
     * <p>
     *  Login instructions
     *  1. When you run this file, it will send you a link to authenticate your account.
     *  2. Click on this link to authenticate your account.
     *  3. Afterward, it will send you to a blank page, where you will receive a connection error.
     *  4. Copy the link of the page you were sent to.
     *  5. Paste it into the command line, and press enter.
     *  6. From there, the code will generate for you an access and refresh token.
     *  7. If you receive no errors. This means that you've logged in.
     */
    abstract void login();

    /**
     * Beyond this point, there should be only getter and setter methods
     */

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
