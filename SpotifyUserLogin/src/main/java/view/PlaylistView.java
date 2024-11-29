package view;
import entities.Playlist;
import entities.Song;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PlaylistView extends JPanel {
    private final JLabel playlistTitleLabel;
    private final JPanel songsPanel;
    private final JButton backButton;

    public PlaylistView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Header
        playlistTitleLabel = new JLabel("Playlist: ");
        playlistTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        playlistTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(playlistTitleLabel, BorderLayout.NORTH);

        // Songs panel
        songsPanel = new JPanel();
        songsPanel.setLayout(new BoxLayout(songsPanel, BoxLayout.Y_AXIS));
        songsPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(songsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // Back button
        backButton = new JButton("Back to Profile");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to display a playlist and its songs
    public void displayPlaylist(Playlist playlist) {
        playlistTitleLabel.setText("Playlist: " + playlist.getName());
        songsPanel.removeAll(); // Clear existing content

        List<Song> songs = playlist.getSongs();
        if (songs.isEmpty()) {
            JLabel noSongsLabel = new JLabel("No Songs Found");
            noSongsLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            noSongsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            songsPanel.add(noSongsLabel);
        } else {
            for (Song song : songs) {
                JPanel songItem = new JPanel();
                songItem.setLayout(new BorderLayout());
                songItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                songItem.setBackground(Color.WHITE);

                JLabel songLabel = new JLabel("Title: " + song.getTitle() + " | Artist: " + song.getArtist());
                songLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                songItem.add(songLabel, BorderLayout.CENTER);

                songsPanel.add(songItem);
                songsPanel.add(Box.createVerticalStrut(10)); // Spacing between items
            }
        }

        songsPanel.revalidate();
        songsPanel.repaint();
    }

    // Method to add a listener to the back button
    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
