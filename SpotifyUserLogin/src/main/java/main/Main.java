package main;

import api.SpotifyInteractor;
import entities.UserProfile;
import interface_adapter.editpreferences.EditPreferencesController;
import interface_adapter.editpreferences.EditPreferencesPresenter;
import interface_adapter.editpreferences.EditPreferencesState;
import Use_case.Editing.EditPreferencesUseCase;
import Use_case.Editing.EditPreferencesResponse;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Initialize SpotifyInteractor and authenticate
        SpotifyInteractor interactor = new SpotifyInteractor();
        if (!authenticate(interactor)) {
            System.err.println("Authentication failed. Exiting application.");
            System.exit(1);
        }

        // Fetch user profile (initial data)
        UserProfile userProfile = fetchUserProfile(interactor);

        // Initialize UseCase, Presenter, and Controller
        EditPreferencesUseCase useCase = new EditPreferencesUseCase(interactor, userProfile);
        EditPreferencesPresenter presenter = new EditPreferencesPresenter();
        EditPreferencesController controller = new EditPreferencesController(useCase, presenter);

        // Create the main frame and set up CardLayout
        JFrame frame = new JFrame("Spotify Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new CardLayout());

        // Create MainMenu and Profile views
        JPanel mainMenuView = new JPanel();
        mainMenuView.setLayout(new BoxLayout(mainMenuView, BoxLayout.Y_AXIS));
        JLabel menuLabel = new JLabel("Main Menu");
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuView.add(menuLabel);

        JButton profileButton = new JButton("View Profile");
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuView.add(profileButton);

        JPanel profileView = new JPanel();
        profileView.setLayout(new BoxLayout(profileView, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel();
        JLabel genresLabel = new JLabel();
        JLabel artistsLabel = new JLabel();

        profileView.add(usernameLabel);
        profileView.add(genresLabel);
        profileView.add(artistsLabel);

        JButton backButton = new JButton("Back to Main Menu");
        profileView.add(backButton);

        frame.getContentPane().add(mainMenuView, "MainMenu");
        frame.getContentPane().add(profileView, "Profile");

        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();

        // MainMenu -> Profile Navigation
        profileButton.addActionListener(e -> {
            // Fetch and display preferences
            controller.updatePreferencesDynamically();
            EditPreferencesState state = controller.getState();

            if (state != null && state.isSuccess()) {
                usernameLabel.setText("Username: " + userProfile.getUsername());
                genresLabel.setText("Preferred Genres: " + String.join(", ", state.getGenres()));
                artistsLabel.setText("Preferred Artists: " + String.join(", ", state.getArtists()));
                cardLayout.show(frame.getContentPane(), "Profile");
            } else {
                JOptionPane.showMessageDialog(frame, "Error updating preferences: " + (state != null ? state.getMessage() : "Unknown error"), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Profile -> MainMenu Navigation
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "MainMenu"));

        // Display the main frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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

    private static UserProfile fetchUserProfile(SpotifyInteractor interactor) {
        try {
            // Fetch initial user profile details
            var userProfileJson = interactor.getCurrentUserProfile();
            String username = userProfileJson.optString("display_name", "Unknown User");

            // Return a new user profile
            return new UserProfile(interactor);
        } catch (Exception e) {
            System.err.println("Failed to fetch user profile: " + e.getMessage());
            return new UserProfile(interactor);
        }
    }
}
