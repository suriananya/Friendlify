package entities.users;

import api.SpotifyInteractor;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.Utility;

import java.util.*;
import java.io.*;

public class UserProfile extends AbstractUserProfile{
    private final JSONArray friendsList = new JSONArray();

    public UserProfile(SpotifyInteractor interactor) {
        super(interactor);
        initFriendsList();
    }

    public UserProfile(String userId, List<String> genres, List<String> artists) {
        super(userId, genres, artists);
    }

    /**
     * Initializes the friends list with a workaround method.
     * <p>
     * The Spotify Web API does not allow us to fetch who a user is following, and a user's followers.
     * Therefore, the method used to determine if someone is a "friend" is to check their added playlists,
     * and if they have added someone else's playlist, then add that person as a friend.
     */
    private void initFriendsList() {
        JSONObject playlistsJSON = this.getUserPlaylistsJSON(5, 0);

        // Validate this user's playlists to confirm it is possible to look at them
        if (playlistsJSON == null || !playlistsJSON.has("items")) {
            System.out.println("User does not appear to have playlists.");
            return;
        }

        // Collect their list of playlists from the initial API response
        JSONArray playlists = playlistsJSON.getJSONArray("items");
        playlists = Utility.sanitizeJSONArray(playlists);

        // Loop through the playlists to check their owners
        for (int i = 0; i < playlists.length(); i++) {
            JSONObject playlistOwner = playlists.getJSONObject(i).getJSONObject("owner");

            // Check if the playlist owner is the same as the current user. If not, add them as a friend
            if (!Objects.equals(playlistOwner.getString("id"), this.userID)) {
                addToFriendsList(playlistOwner.getString("id"), playlistOwner.getString("displayName"));
            }
        }
    }

    private void addToFriendsList(String id, String displayName) {
        JSONObject friendsJson = new JSONObject().put("id", id).put("displayName", displayName);
        this.friendsList.put(friendsJson);
    }

    public JSONArray getFriendsList() {
        return friendsList;
    }

    /**
     * More readable alternative to overloading getFriendsList.
     * Handles id collection logic for you.
     * @return a list of friend ids.
     */
    public List<String> getFriendsListIds() {
        return getFriendsList("id");
    }

    /**
     * More readable alternative to overloading getFriendsList.
     * Handles display_name collection logic for you.
     * @return a list of friend display names.
     */
    public List<String> getFriendsListNames() {
        return getFriendsList("display_name");
    }

    /**
     * An overloaded version for getFriendsList.
     * Instead, in this case, you get to choose what information to get, instead of all of it.
     * @param type the type of information. Accepted values are "id" and "display_name".
     * @return a list of friend ids or display names.
     */
    public List<String> getFriendsList(String type) {
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < this.friendsList.length(); i++) {
            String friendStat = this.friendsList.getJSONObject(i).getString(type);
            temp.add(friendStat);
        }
        return temp;
    }

    @Override
    JSONObject getUserPlaylistsJSON(int limit, int offset) {
        return interactor.getCurrentUserPlaylists(limit, offset);
    }

    @Override
    JSONObject getUserProfileJSON() {
        return interactor.getCurrentUserProfile();
    }

    /**
     * Sets the preferred genres of the user.
     * @param newGenres The list of genres to set.
     */
    public void setPreferredGenres(List<String> newGenres) {
        if (newGenres != null && !newGenres.isEmpty()) {
            this.preferredGenres = new ArrayList<>(newGenres);
        } else {
            System.err.println("Cannot set preferred genres with null or empty list.");
        }
    }

    /**
     * Sets the preferred artists of the user.
     * @param newArtists The list of artists to set.
     */
    public void setPreferredArtists(List<String> newArtists) {
        if (newArtists != null && !newArtists.isEmpty()) {
            this.preferredArtists = new ArrayList<>(newArtists);
        } else {
            System.err.println("Cannot set preferred artists with null or empty list.");
        }
    }

    /**
     * Updates the list of preferred genres by adding new genres.
     * @param newGenres The list of genres to add.
     */
    public void updatePreferredGenres(List<String> newGenres) {
        if (newGenres != null && !newGenres.isEmpty()) {
            this.preferredGenres = new ArrayList<>(newGenres);
        } else {
            System.err.println("Cannot update preferred genres with null or empty list.");
        }
    }

    /**
     * Updates the list of preferred artists by adding new artists.
     * @param newArtists The list of artists to add.
     */
    public void updatePreferredArtists(List<String> newArtists) {
        if (newArtists != null && !newArtists.isEmpty()) {
            this.preferredArtists = new ArrayList<>(newArtists);
        } else {
            System.err.println("Cannot update preferred artists with null or empty list.");
        }
    }

public class UserIDManager {
    private static final String USER_DATA_FILE = System.getProperty("user.home") + File.separator + "user_data.txt";

    /**
     * Retrieves the user's ID. If no ID exists, a new one is generated, stored, and returned.
     *
     * @return The user's unique ID.
     */
    public static String getUserID() {
        try {
            // Check if the user data file exists
            File file = new File(USER_DATA_FILE);
            if (file.exists()) {
                // Read the file and return the stored user ID
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String userId = reader.readLine();
                reader.close();
                if (userId != null && !userId.isEmpty()) {
                    return userId.trim();
                }
            }

            // If the file doesn't exist or is empty, generate a new user ID
            String newUserId = generateUserID();
            saveUserID(newUserId);
            return newUserId;

        } catch (IOException e) {
            System.err.println("Error reading or creating user data file: " + e.getMessage());
            throw new RuntimeException("Unable to retrieve user ID.", e);
        }
    }

    /**
     * Saves the user's ID to the file for future retrieval.
     *
     * @param userId The user ID to save.
     */
    private static void saveUserID(String userId) {
        try {
            // Write the user ID to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE));
            writer.write(userId);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving user ID: " + e.getMessage());
            throw new RuntimeException("Unable to save user ID.", e);
        }
    }

    /**
     * Generates a new unique user ID.
     * This is a placeholder implementation and can be replaced with a more sophisticated generator if needed.
     *
     * @return A new unique user ID.
     */
    private static String generateUserID() {
        return "user-" + System.currentTimeMillis();
    }
}
}