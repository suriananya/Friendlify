package main;

import entities.UserProfile;
import entities.Playlist;
import entities.Song;
import View.DashboardView;
import View.ProfileRateAndCommentView;
import View.ProfileView;
import View.MainMenuView;
import View.PlaylistView;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create the main frame and set up CardLayout
        JFrame frame = new JFrame("Spotify Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new CardLayout());

        // Mock UserProfile data
        List<Song> songs = Arrays.asList(
                new Song("Blinding Lights", "The Weeknd"),
                new Song("Shake It Off", "Taylor Swift"),
                new Song("Hotline Bling", "Drake")
        );
        Playlist playlist = new Playlist("Top Hits", songs);
        UserProfile userProfile = new UserProfile(
                "MusicLover123",
                List.of(playlist),
                Arrays.asList("Pop", "R&B", "Hip-Hop"),
                Arrays.asList("Taylor Swift", "The Weeknd", "Drake")
        );

        // Create instances of views
        MainMenuView mainMenuView = new MainMenuView();
        DashboardView dashboardView = new DashboardView();
        ProfileView profileView = new ProfileView();
        PlaylistView playlistView = new PlaylistView();
        ProfileRateAndCommentView rateCommentView = new ProfileRateAndCommentView();

        // Add views to the CardLayout with unique identifiers
        frame.getContentPane().add(mainMenuView, "MainMenu");
        frame.getContentPane().add(dashboardView.getPanel(), "Dashboard");
        frame.getContentPane().add(profileView, "Profile");
        frame.getContentPane().add(playlistView, "Playlist");
        frame.getContentPane().add(rateCommentView, "RateComment");

        // Access CardLayout for navigation
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();

        // MainMenu navigation
        mainMenuView.addProfileButtonListener(e -> {
            profileView.displayUserProfile(userProfile);
            cardLayout.show(frame.getContentPane(), "Profile");
        });

        mainMenuView.addSearchFriendsButtonListener(e -> cardLayout.show(frame.getContentPane(), "Dashboard"));

        // Profile navigation
        profileView.addBackButtonListener(e -> cardLayout.show(frame.getContentPane(), "MainMenu"));
        profileView.addPlaylistButtonListener(e -> {
            playlistView.displayPlaylist(userProfile.getPlaylist().get(0)); // Display the first playlist
            cardLayout.show(frame.getContentPane(), "Playlist");
        });
        profileView.addRatingsButtonListener(e -> cardLayout.show(frame.getContentPane(), "RateComment"));

        // Playlist back navigation
        playlistView.addBackButtonListener(e -> cardLayout.show(frame.getContentPane(), "Profile"));

        // RateComment back navigation
        rateCommentView.addBackButtonListener(e -> cardLayout.show(frame.getContentPane(), "Profile"));

        // Show the MainMenu view initially
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
