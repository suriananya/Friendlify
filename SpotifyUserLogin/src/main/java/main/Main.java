package main;

import api.SpotifyInteractor;
import entities.Song;
import entities.users.UserProfile;
import interfaceAdapters.editpreferences.EditPreferencesController;
import interfaceAdapters.editpreferences.EditPreferencesPresenter;
import interfaceAdapters.editpreferences.EditPreferencesState;
import interfaceAdapters.rating.RateSongController;
import interfaceAdapters.rating.RateSongPresenter;
import useCase.Editing.EditPreferencesUseCase;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.UUID;

public class Main {
    private static final String DATA_FILE = System.getProperty("user.home") + "/user_data.txt";

    public static void main(String[] args) {
        UserProfile userProfile = loadOrCreateUserProfile();
        SpotifyInteractor interactor = new SpotifyInteractor();

        JFrame frame = new JFrame("Spotify Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new CardLayout());

        if (!authenticate(interactor, frame)) {
            System.err.println("Authentication failed. Exiting application.");
            System.exit(1);
        }

        Map<String, Song> friendsSongs = fetchFriendsSongs();

        EditPreferencesUseCase useCase = new EditPreferencesUseCase(interactor, userProfile);
        EditPreferencesPresenter presenter = new EditPreferencesPresenter();
        EditPreferencesController controller = new EditPreferencesController(useCase, presenter);

        setupUI(frame, userProfile, friendsSongs, controller);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveUserProfile(userProfile)));
    }

    private static void setupUI(JFrame frame, UserProfile userProfile, Map<String, Song> friendsSongs, EditPreferencesController controller) {
        // Main Menu Panel
        RoundedPanel mainMenuView = new RoundedPanel(20);
        mainMenuView.setLayout(new BoxLayout(mainMenuView, BoxLayout.Y_AXIS));
        mainMenuView.setBackground(new Color(245, 245, 245)); // Light gray background
        mainMenuView.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel menuLabel = new JLabel("Main Menu");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 28));
        menuLabel.setForeground(Color.BLACK);
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedButton profileButton = new RoundedButton("View Profile");
        RoundedButton rateSongsButton = new RoundedButton("Rate Friends' Songs");

        profileButton.setPreferredSize(new Dimension(200, 50));
        rateSongsButton.setPreferredSize(new Dimension(200, 50));

        mainMenuView.add(menuLabel);
        mainMenuView.add(Box.createRigidArea(new Dimension(0, 40))); // Spacing
        mainMenuView.add(profileButton);
        mainMenuView.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        mainMenuView.add(rateSongsButton);

        // Profile Panel
        RoundedPanel profileView = new RoundedPanel(20);
        profileView.setLayout(new BoxLayout(profileView, BoxLayout.Y_AXIS));
        profileView.setBackground(new Color(245, 245, 245));
        profileView.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        RoundedButton backButton = new RoundedButton("◀");
        backButton.setPreferredSize(new Dimension(50, 50));
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username: " + userProfile.getUsername());
        JLabel genresLabel = new JLabel("Preferred Genres: " + String.join(", ", userProfile.getPreferredGenres()));
        JLabel artistsLabel = new JLabel("Preferred Artists: " + String.join(", ", userProfile.getPreferredArtists()));

        usernameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        genresLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        artistsLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JTextField genreField = new JTextField(20);
        JTextField artistField = new JTextField(20);
        styleTextField(genreField);
        styleTextField(artistField);

        RoundedButton editButton = new RoundedButton("Edit Preferences");
        RoundedButton saveButton = new RoundedButton("Save Preferences");

        genreField.setVisible(false);
        artistField.setVisible(false);
        saveButton.setVisible(false);

        JLabel updateGenre = new JLabel("Update Preferred Genres (comma-separated):");
        JLabel updateArtist = new JLabel("Update Preferred Artists (comma-separated):");

        updateGenre.setFont(new Font("Arial", Font.PLAIN, 14));
        updateArtist.setFont(new Font("Arial", Font.PLAIN, 14));

        updateGenre.setVisible(false);
        updateArtist.setVisible(false);

        profileView.add(backButton);
        profileView.add(Box.createRigidArea(new Dimension(0, 40))); // Spacing
        profileView.add(usernameLabel);
        profileView.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        profileView.add(genresLabel);
        profileView.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        profileView.add(artistsLabel);
        profileView.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        profileView.add(editButton);
        profileView.add(updateGenre);
        profileView.add(genreField);
        profileView.add(updateArtist);
        profileView.add(artistField);
        profileView.add(saveButton);

        editButton.addActionListener(e -> {
            genreField.setText(String.join(", ", userProfile.getPreferredGenres()));
            artistField.setText(String.join(", ", userProfile.getPreferredArtists()));

            updateGenre.setVisible(true);
            updateArtist.setVisible(true);
            genreField.setVisible(true);
            artistField.setVisible(true);
            saveButton.setVisible(true);

            profileView.revalidate();
            profileView.repaint();
        });

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

                updateGenre.setVisible(false);
                updateArtist.setVisible(false);
                genreField.setVisible(false);
                artistField.setVisible(false);
                saveButton.setVisible(false);

                profileView.revalidate();
                profileView.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Error updating preferences: " + state.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Rate Friends' Songs Panel
        RoundedPanel rateSongsView = new RoundedPanel(20);
        rateSongsView.setLayout(new BoxLayout(rateSongsView, BoxLayout.Y_AXIS));
        rateSongsView.setBackground(new Color(245, 245, 245));
        rateSongsView.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        RoundedButton backToMenuButton = new RoundedButton("◀");
        backToMenuButton.setPreferredSize(new Dimension(50, 50));
        backToMenuButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel rateSongsLabel = new JLabel("Rate Friends' Songs");
        rateSongsLabel.setFont(new Font("Arial", Font.BOLD, 28));
        rateSongsLabel.setForeground(Color.BLACK);
        rateSongsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rateSongsView.add(backToMenuButton);
        rateSongsView.add(Box.createRigidArea(new Dimension(0, 40))); // Spacing
        rateSongsView.add(rateSongsLabel);

        friendsSongs.forEach((songId, song) -> {
            JLabel songLabel = new JLabel("Song: " + song.getTitle() + " by " + song.getArtist());
            songLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            songLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            RoundedButton rateButton = new RoundedButton("Rate");
            rateButton.setPreferredSize(new Dimension(200, 50));
            rateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            rateButton.addActionListener(e -> {
                String ratingStr = JOptionPane.showInputDialog(frame, "Enter rating (1-5):", "Rate Song", JOptionPane.PLAIN_MESSAGE);
                if (ratingStr != null) {
                    try {
                        int rating = Integer.parseInt(ratingStr);
                        String comment = JOptionPane.showInputDialog(frame, "Enter a comment:", "Add Comment", JOptionPane.PLAIN_MESSAGE);
                        if (comment != null) {
                            RateSongController.rateSong(songId, userProfile.getUserId(), rating, comment);
                            JOptionPane.showMessageDialog(frame, "Song rated successfully!");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid rating. Please enter a number between 1 and 5.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            rateSongsView.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
            rateSongsView.add(songLabel);
            rateSongsView.add(rateButton);
        });

        // Add panels to frame
        frame.getContentPane().add(mainMenuView, "MainMenu");
        frame.getContentPane().add(profileView, "Profile");
        frame.getContentPane().add(rateSongsView, "RateSongs");

        // Navigation
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        profileButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "Profile"));
        rateSongsButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "RateSongs"));
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "MainMenu"));
        backToMenuButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "MainMenu"));
    }



    private static void styleButton(RoundedButton profileButton) {
        profileButton.setFont(new Font("Arial", Font.BOLD, 14));
        profileButton.setFocusPainted(false);
        profileButton.setBackground(Color.BLACK); // Black background
        profileButton.setForeground(Color.WHITE); // White text
        profileButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        profileButton.setOpaque(true);
        profileButton.setContentAreaFilled(true); // Ensures no default button styling
    }


    private static void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setMaximumSize(new Dimension(400, 30));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), // Black border
                BorderFactory.createEmptyBorder(5, 10, 5, 10)  // Padding inside the text field
        ));
    }

    private static String getUserId() {
        return UUID.randomUUID().toString();
    }

    private static UserProfile loadOrCreateUserProfile() {
        File file = new File(DATA_FILE);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String userId = reader.readLine();
                List<String> genres = Arrays.asList(reader.readLine().split(","));
                List<String> artists = Arrays.asList(reader.readLine().split(","));
                return new UserProfile(userId, genres, artists);
            } catch (IOException e) {
                System.err.println("Error reading user data file: " + e.getMessage());
            }
        }

        String userId = UUID.randomUUID().toString();
        return new UserProfile(userId, Arrays.asList("Pop", "Rock"), Arrays.asList("Artist1", "Artist2"));
    }

    private static void saveUserProfile(UserProfile userProfile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            writer.write(userProfile.getUserId() + "\n");
            writer.write(String.join(",", userProfile.getPreferredGenres()) + "\n");
            writer.write(String.join(",", userProfile.getPreferredArtists()) + "\n");
        } catch (IOException e) {
            System.err.println("Error saving user data: " + e.getMessage());
        }
    }

    private static Map<String, Song> fetchFriendsSongs() {
        Map<String, Song> songs = new HashMap<>();
        songs.put("song1", new Song("Song 1", "Artist 1", Arrays.asList("Pop", "Dance")));
        songs.put("song2", new Song("Song 2", "Artist 2", Arrays.asList("Rock", "Alternative")));
        return songs;
    }





    private static boolean authenticate(SpotifyInteractor interactor, JFrame frame) {
                // Redirect the standard output to capture the printed URI
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PrintStream originalOut = System.out; // Save the original System.out
                System.setOut(new PrintStream(outputStream));

                // Call the method that prints the URI
                interactor.authorizationCodeUri();

                System.out.flush();
                System.setOut(originalOut); // Restore the original System.out

                // Capture the output and sanitize the URI
                String authorizationUri = outputStream.toString().trim();

                // Remove unexpected prefixes like "Application Authorization:"
                if (authorizationUri.contains(": ")) {
                    String[] parts = authorizationUri.split(": ", 2); // Split only once
                    if (parts.length > 1) {
                        authorizationUri = parts[1].trim(); // Take the part after the prefix
                    }
                }

                // Create the login UI panel
                JPanel loginPanel = new JPanel();
                loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
                loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

                JLabel instructionsLabel = new JLabel("Please authenticate your account by visiting the link below:");
                instructionsLabel.setFont(new Font("Arial", Font.BOLD, 16));
                instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Create a clickable hyperlink
                JLabel authUrlLabel = new JLabel("<html><a href='" + authorizationUri + "'>" + authorizationUri + "</a></html>");
                authUrlLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                authUrlLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                authUrlLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                authUrlLabel.setForeground(new Color(0, 102, 204)); // Blue color for hyperlink

                // Add a MouseListener to open the link in the browser
                String finalAuthorizationUri = authorizationUri;
                authUrlLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        try {
                            System.out.println("Opening sanitized URI: " + finalAuthorizationUri);
                            Desktop.getDesktop().browse(new java.net.URI(finalAuthorizationUri)); // Open in browser
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Failed to open the link: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                JLabel pasteUrlLabel = new JLabel("Paste the redirected URL below:");
                pasteUrlLabel.setFont(new Font("Arial", Font.BOLD, 14));
                pasteUrlLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JTextField redirectedUrlField = new JTextField(30);
                redirectedUrlField.setMaximumSize(new Dimension(400, 30));
                redirectedUrlField.setAlignmentX(Component.CENTER_ALIGNMENT);

                JButton submitButton = new JButton("Submit");
                submitButton.setFont(new Font("Arial", Font.BOLD, 14));
                submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                submitButton.setBackground(new Color(76, 175, 80)); // Green button
                submitButton.setForeground(Color.BLACK);

                // Add components to the panel
                loginPanel.add(instructionsLabel);
                loginPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
                loginPanel.add(authUrlLabel); // Add the clickable link
                loginPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
                loginPanel.add(pasteUrlLabel);
                loginPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Spacing
                loginPanel.add(redirectedUrlField);
                loginPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
                loginPanel.add(submitButton);

                // Create a modal dialog for the login process
                JDialog loginDialog = new JDialog(frame, "Spotify Authentication", true);
                loginDialog.setContentPane(loginPanel);
                loginDialog.pack();
                loginDialog.setLocationRelativeTo(frame);

                final boolean[] isAuthenticated = {false};

                // Handle the Submit button click
                submitButton.addActionListener(e -> {
                    String fullUrl = redirectedUrlField.getText().trim();

                    try {
                        String authorizationCode = fullUrl.split("code=")[1].split("&")[0];
                        interactor.setCode(authorizationCode);
                        interactor.authorizationCode();

                        String accessToken = interactor.getAccessToken();
                        if (accessToken == null || accessToken.isEmpty()) {
                            JOptionPane.showMessageDialog(loginDialog, "Authentication failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(loginDialog, "Authentication successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            isAuthenticated[0] = true;
                            loginDialog.dispose(); // Close the dialog on success
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(loginDialog, "Authentication failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                // Display the login dialog
                loginDialog.setVisible(true);
                return isAuthenticated[0];
            }}
