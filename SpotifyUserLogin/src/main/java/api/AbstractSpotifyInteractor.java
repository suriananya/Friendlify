package api;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

public class AbstractSpotifyInteractor {
    // Technically I should not be sharing any of this information, but I cannot be bothered to hide it right now
    private static final String clientID = "53ee2a266cd542acaf19190e2ec3da41";
    private static final String clientSecret = "0567ae1ac8e1415ba72f748808a69377";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:3000");

    // Initialize important variables for app functionality
    protected static final String applicationScope =
            "user-follow-read user-library-read user-read-private playlist-read-private";
    protected static String code;
    protected static String accessToken;
    protected static String refreshToken;

    // Initialize the API wrapper class
    protected static final SpotifyApi api = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .setAccessToken(accessToken)
            .setRefreshToken(refreshToken)
            .build();

    /**
     * Beyond this point, there should be only getter and setter methods
     */

    public static String getCode() {
        return code;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setCode(String code) {
        SpotifyInteractor.code = code;
    }

    public static void setAccessToken(String accessToken) {
        SpotifyInteractor.accessToken = accessToken;
        api.setAccessToken(accessToken);
    }

    public static void setRefreshToken(String refreshToken) {
        SpotifyInteractor.refreshToken = refreshToken;
        api.setRefreshToken(refreshToken);
    }
}
