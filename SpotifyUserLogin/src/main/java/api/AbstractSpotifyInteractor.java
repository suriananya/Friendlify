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
    protected static final String applicationScope =
            "user-follow-read user-library-read user-read-private playlist-read-private";
    protected String code;
    protected String accessToken;
    protected String refreshToken;

    // Initialize the API wrapper class
    protected final SpotifyApi api = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .setAccessToken(this.getAccessToken())
            .setRefreshToken(this.getRefreshToken())
            .build();

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
