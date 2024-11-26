package main;

import api.SpotifyInteractor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Song;
import entities.UserProfile;
import interface_adapter.editpreferences.EditPreferencesController;
import interface_adapter.editpreferences.EditPreferencesPresenter;
import interface_adapter.editpreferences.EditPreferencesState;
import interface_adapters.rating.RateSongController;
import interface_adapters.rating.RateSongPresenter;
import Use_case.Editing.EditPreferencesUseCase;
import Use_case.RateSongUseCase;
import utilities.UserProfileStorage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.UUID;

public class Main {
    private static final String DATA_FILE = System.getProperty("user.home") + "/user_data.txt";
    private static Map<String, UserProfile> userProfiles = new HashMap<>();

    public static void main(String[] args) {
        // Initialize SpotifyInteractor and authenticate
        UserProfile userProfile = loadOrCreateUserProfile();

        SpotifyInteractor interactor = new SpotifyInteractor();
        if (!authenticate(interactor)) {
            System.err.println("Authentication failed. Exiting application.");
            System.exit(1);
        }

        Map<String, Song> friendsSongs = fetchFriendsSongs();

        // Initialize UseCase, Presenter, and Controller
        EditPreferencesUseCase useCase = new EditPreferencesUseCase(interactor, userProfile);
        EditPreferencesPresenter presenter = new EditPreferencesPresenter();
        EditPreferencesController controller = new EditPreferencesController(useCase, presenter);

        // Create the main frame and set up CardLayout
        JFrame frame = new JFrame("Spotify Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new CardLayout());

        setupUI(frame, userProfile, friendsSongs, controller);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveUserProfile(userProfile)));

        // Display the main frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void setupUI(JFrame frame, UserProfile userProfile, Map<String, Song> friendsSongs, EditPreferencesController controller) {
        // Main Menu Panel
        JPanel mainMenuView = new JPanel();
        mainMenuView.setLayout(new BoxLayout(mainMenuView, BoxLayout.Y_AXIS));
        JLabel menuLabel = new JLabel("Main Menu");
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuView.add(menuLabel);

        // View Profile Button
        JButton profileButton = new JButton("View Profile");
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuView.add(profileButton);

        // Rate Friends Songs Button
        JButton rateSongsButton = new JButton("Rate Friends' Songs");
        rateSongsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuView.add(rateSongsButton);

        // Profile Panel
        JPanel profileView = new JPanel();
        profileView.setLayout(new BoxLayout(profileView, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("Username: " + userProfile.getUsername());
        JLabel genresLabel = new JLabel("Preferred Genres: " + String.join(", ", userProfile.getPreferredGenres()));
        JLabel artistsLabel = new JLabel("Preferred Artists: " + String.join(", ", userProfile.getPreferredArtists()));

        profileView.add(usernameLabel);
        profileView.add(genresLabel);
        profileView.add(artistsLabel);

        JTextField genreField = new JTextField(20);
        JTextField artistField = new JTextField(20);
        JButton editButton = new JButton("Edit Preferences");
        JButton saveButton = new JButton("Save Preferences");

        genreField.setVisible(false);
        artistField.setVisible(false);
        saveButton.setVisible(false);

        profileView.add(new JLabel("Update Preferred Genres (comma-separated):"));
        profileView.add(genreField);
        profileView.add(new JLabel("Update Preferred Artists (comma-separated):"));
        profileView.add(artistField);

        profileView.add(editButton);
        profileView.add(saveButton);

        JButton backButton = new JButton("Back to Main Menu");
        profileView.add(backButton);

        // Rate Songs Panel
        JPanel rateSongsView = new JPanel();
        rateSongsView.setLayout(new BoxLayout(rateSongsView, BoxLayout.Y_AXIS));
        JLabel rateSongsLabel = new JLabel("Rate Your Friends' Songs");
        rateSongsView.add(rateSongsLabel);

        friendsSongs.forEach((songId, song) -> {
            JLabel songLabel = new JLabel("Song: " + song.getTitle());
            JButton rateButton = new JButton("Rate");

            rateButton.addActionListener(e -> {
                String ratingStr = JOptionPane.showInputDialog(frame, "Enter rating (1-5):");
                String comment = JOptionPane.showInputDialog(frame, "Enter comment:");

                try {
                    int rating = Integer.parseInt(ratingStr);
                    RateSongController.rateSong(songId, getUserId(), rating, comment);
                    JOptionPane.showMessageDialog(frame, RateSongPresenter.prepareSuccessMessage());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid rating. Please enter a number between 1 and 5.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            rateSongsView.add(songLabel);
            rateSongsView.add(rateButton);
        });

        JButton backToMenuFromRateButton = new JButton("Back to Main Menu");
        rateSongsView.add(backToMenuFromRateButton);

        // Add panels to frame
        frame.getContentPane().add(mainMenuView, "MainMenu");
        frame.getContentPane().add(profileView, "Profile");
        frame.getContentPane().add(rateSongsView, "RateSongs");

        // Set up CardLayout for panel switching
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();

        // Profile button action (switch to profile view)
        profileButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Profile"));

        // Rate Songs button action (switch to rate songs view)
        rateSongsButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "RateSongs"));

        // Back buttons
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "MainMenu"));
        backToMenuFromRateButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "MainMenu"));

        // Edit preferences button action
        editButton.addActionListener(e -> {
            genreField.setText(String.join(", ", userProfile.getPreferredGenres()));
            artistField.setText(String.join(", ", userProfile.getPreferredArtists()));

            genreField.setVisible(true);
            artistField.setVisible(true);
            saveButton.setVisible(true);
            editButton.setVisible(false);

            profileView.revalidate();
            profileView.repaint();
        });

        // Save preferences button action
        saveButton.addActionListener(e -> {
            String[] newGenres = genreField.getText().split(",");
            String[] newArtists = artistField.getText().split(",");

            controller.updatePreferencesManually(
                    new ArrayList<>(Arrays.asList(newGenres)),
                    new ArrayList<>(Arrays.asList(newArtists))
            );

            EditPreferencesState state = controller.getState();
            if (state.isSuccess()) {
                JOptionPane.showMessageDialog(frame, "Preferences updated successfully!");
                userProfile.setPreferredGenres(Arrays.asList(newGenres));
                userProfile.setPreferredArtists(Arrays.asList(newArtists));

                genresLabel.setText("Preferred Genres: " + String.join(", ", state.getGenres()));
                artistsLabel.setText("Preferred Artists: " + String.join(", ", state.getArtists()));

                genreField.setVisible(false);
                artistField.setVisible(false);
                saveButton.setVisible(false);
                editButton.setVisible(true);

                profileView.revalidate();
                profileView.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Error updating preferences: " + state.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static String getUserId() {
        // Assuming you load the userProfile with a valid userId
        return UUID.randomUUID().toString();
    }

    private static UserProfile loadOrCreateUserProfile() {
        File file = new File(DATA_FILE);

        // If the file exists, read and return the user profile
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String userId = reader.readLine();
                List<String> genres = Arrays.asList(reader.readLine().split(","));
                List<String> artists = Arrays.asList(reader.readLine().split(","));
                System.out.println("Loaded existing user data: " + userId);
                return new UserProfile(userId, genres, artists);
            } catch (IOException e) {
                System.err.println("Error reading user data file: " + e.getMessage());
            }
        }

        // If no file exists, create a new user profile with a unique ID
        String userId = UUID.randomUUID().toString();
        List<String> genres = new ArrayList<>(List.of("Pop", "Rock"));
        List<String> artists = new ArrayList<>(List.of("Artist1", "Artist2"));

        System.out.println("Generated new user data: " + userId);
        return new UserProfile(UUID.randomUUID().toString(), Arrays.asList("Pop", "Rock"), Arrays.asList("Artist1", "Artist2"));
    }

    private static void saveUserProfile(UserProfile userProfile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            writer.write(userProfile.getUserId() + "\n");
            writer.write(String.join(",", userProfile.getPreferredGenres()) + "\n");
            writer.write(String.join(",", userProfile.getPreferredArtists()) + "\n");
            System.out.println("User data saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving user data: " + e.getMessage());
        }
    }


    private static Map<String, Song> fetchFriendsSongs() {
        Map<String, Song> songs = new HashMap<>();

        // Correcting the constructor call with required arguments
        songs.put("song1", new Song("Friend's Song 1", "Friend's Artist 1", Arrays.asList("Pop", "Rock")));
        songs.put("song2", new Song("Friend's Song 2", "Friend's Artist 2", Arrays.asList("Jazz", "Blues")));

        return songs;
    }

    private static boolean authenticate(SpotifyInteractor interactor) {
        System.out.println("Please authenticate your account by visiting:");
        interactor.authorizationCodeUri();

        System.out.println("After logging in, copy the redirected URL and paste it below.");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Enter the full redirected URL: ");
        String fullUrl = scanner.nextLine();

        try {
            // Extract authorization code
            String authorizationCode = fullUrl.split("code=")[1].split("&")[0];
            interactor.setCode(authorizationCode);
            interactor.authorizationCode();

            // Validate access token
            String accessToken = interactor.getAccessToken();
            if (accessToken == null || accessToken.isEmpty()) {
                System.err.println("Failed to retrieve a valid access token.");
                return false;
            }

            System.out.println("Authentication successful.");
            return true;
        } catch (Exception e) {
            System.err.println("Authentication failed: " + e.getMessage());
            return false;
        }
    }
}