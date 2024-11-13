package apiNew;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;

public class SpotifyInteractor {
    // Technically I should not be sharing any of this information, but I cannot be bothered to hide it right now
    private static final String clientID = "53ee2a266cd542acaf19190e2ec3da41";
    private static final String clientSecret = "0567ae1ac8e1415ba72f748808a69377";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:3000");
    private static final String code = "AQCespXWzSVxMJCYpKJk_YiQLUVtGfkxinMhSnRzqptpzqRahm_jiWyW6RvIc_4_L0C9GRntgxjv6uBP6gJltZfv4h-hJUNm-K-40X3oEZwrchSezKaITKnlj1pooLwFADaOiAg4rkSXRxjyWt9rL-nZvM3eRrYH1ieONbSURPzcsmqjkbRL5-RX3QEaCPjDITIGHa191jSz5Osb24XFEQVJt_Byc0w2hq7hh6s4N2mEY4ZAL8DM8pewX38RMHTHHzE9nH7_eDs";
    private static final String accessToken = "BQB_Q3SCtQHukA8Gc0AKrbcqAO0YL5T2P5KyTdQhgR-bUepWwWXOrcemCGAO3egxFE5ZW5eP-E3VrCHTL3rwtFZD4pIo2Axwc3ElvvG_X-08d1gL-Lzgs8EF_EmqFck1_b6ln2bR8A8yL9h2pQuHJWfEjUrKwL3SCfdkieFtnrOuRVZrolBPb9BJOX5THvJekbavijnZjipZ465_vim0dkCjcWFxSxbMCo3W80j13XgvhCGtI8zOXQ";
    private static final String refreshToken = "AQDyVXRddNYz7G1n-vma9xh0WE2q5i99tlOiRWBctGcUEc90j0DfnVMk_0JueQIFDM_iA-Mu_dmBulny-uoi0lmp8iCgTqJCMvM1spga8Wk1LQxn7d1hiVfnnINmRiKBc_g";

    private static final SpotifyApi api = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .setAccessToken(accessToken)
            .setRefreshToken(refreshToken)
            .build();

    public static final String applicationScope =
            "user-follow-read user-library-read user-read-private playlist-read-private";
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = api.authorizationCodeUri()
            .scope(applicationScope)
            .show_dialog(true)
            .build();

    private static final AuthorizationCodeRequest authorizationCodeRequest = api.authorizationCode(code)
            .build();

    private static final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = api.authorizationCodeRefresh()
            .build();

    public static void authorizationCodeRefresh_Sync() {
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            api.setAccessToken(authorizationCodeCredentials.getAccessToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
            System.out.println(api.getAccessToken());
            System.out.println(api.getRefreshToken());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void authorizationCode_Sync() {
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            api.setAccessToken(authorizationCodeCredentials.getAccessToken());
            api.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
            System.out.println(authorizationCodeCredentials.getRefreshToken());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void authorizationCodeUriSync() {
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("URI: " + uri.toString());
    }

    public static void main(String[] args) {
        authorizationCodeUriSync();
        authorizationCode_Sync();
        authorizationCodeRefresh_Sync();
    }
}
