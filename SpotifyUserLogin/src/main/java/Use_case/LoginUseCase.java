package Use_case;

import api.SpotifyAPI;  // Import the SpotifyAPI from the data layer

public class LoginUseCase {

    private final SpotifyAPI spotifyAPI;

    public LoginUseCase(SpotifyAPI spotifyAPI) {
        this.spotifyAPI = spotifyAPI;
    }

    /**
     * Executes the login logic by calling Spotify API to validate credentials.
     *
     * @param username the username to authenticate
     * @param password the password to authenticate
     * @return true if authentication is successful, false otherwise
     */
    public boolean execute(String username, String password) {
        // Validate user credentials using SpotifyAPI directly
        return spotifyAPI.validateCredentials(username, password);
    }
}
