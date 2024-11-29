package view;

import entities.users.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileView extends JPanel {
    private final JLabel usernameLabel;
    private final JTextArea genresArea;
    private final JTextArea artistsArea;
    private final JButton backButton;
    private final JButton playlistButton;
    private final JButton ratingsButton;

    public ProfileView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Header
        JLabel heading = new JLabel("Your Profile");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        add(heading, BorderLayout.NORTH);

        // Center panel for user information
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setAlignmentX(CENTER_ALIGNMENT);

        genresArea = new JTextArea(5, 20);
        genresArea.setEditable(false);
        genresArea.setBorder(BorderFactory.createTitledBorder("Preferred Genres"));

        artistsArea = new JTextArea(5, 20);
        artistsArea.setEditable(false);
        artistsArea.setBorder(BorderFactory.createTitledBorder("Preferred Artists"));

        centerPanel.add(usernameLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(genresArea);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(artistsArea);

        add(centerPanel, BorderLayout.CENTER);

        // Buttons
        playlistButton = new JButton("Go to Playlist");
        ratingsButton = new JButton("View Ratings & Comments");
        backButton = new JButton("Back to Main Menu");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playlistButton);
        buttonPanel.add(ratingsButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to display user profile information
    public void displayUserProfile(UserProfile userProfile) {
        usernameLabel.setText("Username: " + userProfile.getUsername());
        genresArea.setText(String.join("\n", userProfile.getPreferredGenres()));
        artistsArea.setText(String.join("\n", userProfile.getPreferredArtists()));
    }

    // Methods to add action listeners to buttons
    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addPlaylistButtonListener(ActionListener listener) {
        playlistButton.addActionListener(listener);
    }

    public void addRatingsButtonListener(ActionListener listener) {
        ratingsButton.addActionListener(listener);
    }
}
