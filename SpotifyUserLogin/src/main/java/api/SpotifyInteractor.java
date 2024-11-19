package api;

import org.apache.hc.core5.http.ParseException;
import org.json.JSONObject;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

/**
 * A class to help interact with the SpotifyAPI.
 */
public class SpotifyInteractor extends AbstractSpotifyInteractor{
    void authorizationCodeRefresh() {
        try {
            final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest =
                    this.api.authorizationCodeRefresh().build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            this.api.setAccessToken(authorizationCodeCredentials.getAccessToken());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    void authorizationCode() {
        try {
            final AuthorizationCodeRequest authorizationCodeRequest =
                    this.api.authorizationCode(this.getCode()).build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            this.setAccessToken(authorizationCodeCredentials.getAccessToken());
            this.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    void authorizationCodeUri() {
        final AuthorizationCodeUriRequest authorizationCodeUriRequest = this.api.authorizationCodeUri()
                .scope(applicationScope)
                .build();
        final URI uri = authorizationCodeUriRequest.execute();

        System.out.println("Application Authorization: " + uri.toString());
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Click on this link below to authenticate your account.");
        authorizationCodeUri();

        System.out.println("\nYou should have been lead to a page where you will receive a connection error.");
        System.out.print("Copy the link to that page. Paste it here:");
        String fullLink = scanner.nextLine();
        this.setCode(Utility.spliceString(fullLink,"code=",""));

        authorizationCode();
        authorizationCodeRefresh();
        System.out.println("Assuming you have received no errors, you have 'logged in'");

        scanner.close();
    }

    @Override
    JSONObject getArtist(String artistId) {
        return null;
    }

    @Override
    JSONObject getPlaylistItems(String playlistId, String fields, int limit, int offset) {
        return null;
    }

    @Override
    JSONObject getCurrentUserPlaylists() {
        return null;
    }

    @Override
    JSONObject getUserPlaylists(String userId) {
        return null;
    }

    @Override
    JSONObject getCurrentUserProfile() {
        return null;
    }

    @Override
    JSONObject getUserProfile(String userId) {
        return null;
    }

    // Main function to make testing easier
    public static void main(String[] args) {
        SpotifyInteractor interactor = new SpotifyInteractor();
        interactor.login();
    }
}
