package view.Menu;

import javax.swing.*;

public class MainAppView extends JPanel {

    public MainAppView() {
        // Set up the UI components for the main view
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel userLabel = new JLabel("[User 2]");
        JLabel genreLabel = new JLabel("Top Genre: [Genre]");
        JLabel artistLabel = new JLabel("Top Artists: [Name]");

        JButton playlistButton = new JButton("Playlist");
        JButton rouletteButton = new JButton("Roulette");

        this.add(userLabel);
        this.add(genreLabel);
        this.add(artistLabel);
        this.add(playlistButton);
        this.add(rouletteButton);
    }
}
