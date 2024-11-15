package api;

import org.apache.hc.core5.http.ParseException;
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
    /**
     * Access token will expire after 1 hour.
     * This method will refresh the user's access token so it can continue being used
     */
    private void authorizationCodeRefresh() {
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

    /**
     * To access the SpotifyAPI, we need a user's access token. This token expires after 1 hour.
     * To prevent their access token from expiring, we need a refresh token to refresh it.
     * Using the authorization code pulled from the URI link,
     * this method extracts and sets the user's current access and refresh tokens.
     */
    private void authorizationCode() {
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

    /**
     * Sends a link to the URI link required for the user to give authorization.
     * We need this authorization so we are allowed to access the user's data.
     */
    private void authorizationCodeUri() {
        final AuthorizationCodeUriRequest authorizationCodeUriRequest = this.api.authorizationCodeUri()
                .scope(applicationScope)
                .build();
        final URI uri = authorizationCodeUriRequest.execute();

        System.out.println("Application Authorization: " + uri.toString());
    }

    public void login() {
        /*
        Login instructions
        1. When you run this file, it will send you a link to authenticate your account.
        2. Click on this link to authenticate your account.
        3. Afterward, it will send you to a blank page, where you will receive a connection error.
        4. Copy the link of the page you were sent to.
        5. Paste it into the command line, and press enter.
        6. From there, the code will generate for you an access and refresh token.
        7. If you receive no errors. This means that you've logged in.
         */
        Scanner scanner = new Scanner(System.in);

        System.out.println("Click on this link below to authenticate your account.");
        authorizationCodeUri();

        System.out.println("\nYou should have been lead to a page where you will receive a connection error.");
        System.out.print("Copy the link to that page. Paste it here:");
        String fullLink = scanner.nextLine();
        this.setCode(fullLink.substring(28));

        authorizationCode();
        authorizationCodeRefresh();
        System.out.println("Assuming you have received no errors, you have 'logged in'");

        scanner.close();
    }

    // Main function to make testing easier
    public static void main(String[] args) {
        SpotifyInteractor interactor = new SpotifyInteractor();
        interactor.login();
    }
}
