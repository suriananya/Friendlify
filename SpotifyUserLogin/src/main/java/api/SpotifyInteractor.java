package api;

import org.apache.hc.core5.http.ParseException;
import org.json.JSONObject;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetUsersProfileRequest;
import utilities.Utility;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

/**
 * A class to help interact with the SpotifyAPI. I recommend reading:
 * <a href="https://github.com/spotify-web-api-java/spotify-web-api-java">spotify-web-api-java</a>
 */
public class SpotifyInteractor extends AbstractSpotifyInteractor{
    void authorizationCodeRefresh() {
        // Build the request
        final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = this.api.authorizationCodeRefresh()
                .build();

        try {
            // Make the request
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            this.api.setAccessToken(authorizationCodeCredentials.getAccessToken());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void authorizationCode() {
        // Build the request
        final AuthorizationCodeRequest authorizationCodeRequest = this.api.authorizationCode(this.getCode())
                .build();

        try {
            // Make the request
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            this.setAccessToken(authorizationCodeCredentials.getAccessToken());
            this.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void authorizationCodeUri() {
        // Build the request
        final AuthorizationCodeUriRequest authorizationCodeUriRequest = this.api.authorizationCodeUri()
                .scope(applicationScope)
                .show_dialog(false)
                .build();
        // Make the request
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
    public void refreshAccessToken() {
        authorizationCodeRefresh();
    }

    @Override
    public JSONObject getArtist(String artistId) {
        // Build the request
        final GetArtistRequest getArtistRequest = this.api.getArtist(artistId).build();

        try {
            // Execute the request and return the result as a JSONObject
            return new JSONObject(getArtistRequest.execute());
        } catch (IOException e) {
            System.err.println("IO Error while fetching artist with ID: " + artistId + " - " + e.getMessage());
        } catch (SpotifyWebApiException e) {
            System.err.println("Spotify API Error while fetching artist with ID: " + artistId + " - " + e.getMessage());
            if (e.getMessage().contains("rate limit")) {
                System.err.println("Rate limit exceeded. Consider retrying after some time.");
            }
        } catch (ParseException e) {
            System.err.println("Parsing error while processing artist with ID: " + artistId + " - " + e.getMessage());
        }

        // Return an empty JSON object instead of null
        return new JSONObject();
    }


    @Override
    public JSONObject getPlaylistItems(String playlistId, int limit, int offset) {
        // Build the request
        final GetPlaylistsItemsRequest getPlaylistsItemsRequest = this.api.getPlaylistsItems(playlistId)
                .limit(limit)
                .offset(offset)
                .build();

        try {
            // Make the request
            return new JSONObject(getPlaylistsItemsRequest.execute());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Return null after catch (if there is an error)
        return new JSONObject();
    }

    @Override
    public JSONObject getCurrentUserPlaylists(int limit, int offset) {
        // Build the request
        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = this.api
                .getListOfCurrentUsersPlaylists()
                .limit(limit)
                .offset(offset)
                .build();

        try {
            // Make the request
            return new JSONObject(getListOfCurrentUsersPlaylistsRequest.execute());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Return null after catch (if there is an error)
        return new JSONObject();
    }

    @Override
    public JSONObject getUserPlaylists(String userId, int limit, int offset) {
        // Build the request
        final GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest = this.api.getListOfUsersPlaylists(userId)
                .limit(limit)
                .offset(offset)
                .build();

        try {
            // Make the request
            return new JSONObject(getListOfUsersPlaylistsRequest.execute());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Return null after catch (if there is an error)
        return new JSONObject();
    }

    @Override
    public JSONObject getCurrentUserProfile() {
        // Build the request
        final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = this.api.getCurrentUsersProfile()
                .build();

        try {
            // Make the request
            return new JSONObject(getCurrentUsersProfileRequest.execute());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Return null after catch (if there is an error)
        return new JSONObject();
    }

    @Override
    public JSONObject getUserProfile(String userId) {
        // Build the request
        final GetUsersProfileRequest getUsersProfileRequest = this.api.getUsersProfile(userId)
                .build();

        try {
            // Make the request
            return new JSONObject(getUsersProfileRequest.execute());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Return null after catch (if there is an error)
        return new JSONObject();
    }
}
