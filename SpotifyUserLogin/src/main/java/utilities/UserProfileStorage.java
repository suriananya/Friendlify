package utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.users.UserProfile;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserProfileStorage {

    private static final String STORAGE_FILE = "user_profile.json"; // File to store user data
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Save UserProfile to a JSON file
    public static void saveUserProfile(UserProfile userProfile) {
        try (FileWriter writer = new FileWriter(STORAGE_FILE)) {
            gson.toJson(userProfile, writer);
            System.out.println("User profile saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving user profile: " + e.getMessage());
        }
    }

    // Load UserProfile from a JSON file
    public static UserProfile loadUserProfile() {
        try (FileReader reader = new FileReader(STORAGE_FILE)) {
            return gson.fromJson(reader, UserProfile.class);
        } catch (IOException e) {
            System.err.println("Error loading user profile: " + e.getMessage());
            return null; // Return null if the file doesn't exist or there's an error
        }
    }
}
