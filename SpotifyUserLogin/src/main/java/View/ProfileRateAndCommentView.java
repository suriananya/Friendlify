package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The View for user profile's ratings page.
 */
public class ProfileRateAndCommentView {
    // Private final variables
    private final JFrame frame;
    private final JLabel headerLabel;
    private final JButton backButton;
    private final JScrollPane scrollPane;
    private final JPanel contentPanel;

    // Constructor
    public ProfileRateAndCommentView() {
        // Initialize the frame
        frame = new JFrame("Profile - Ratings and Comments");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(null);

        // Header label
        headerLabel = new JLabel("Your Friends' Ratings");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBounds(150, 10, 200, 30);
        frame.add(headerLabel);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(10, 10, 80, 30);
        frame.add(backButton);

        // Content panel (to hold the ratings and comments)
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // Example data (To be replaced with real data later)
        for (int i = 1; i <= 20; i++) {
            JPanel ratingPanel = new JPanel();
            ratingPanel.setLayout(new GridLayout(3, 1));
            ratingPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            ratingPanel.setBackground(Color.WHITE);

            JLabel usernameLabel = new JLabel("Username: User" + i);
            JLabel ratingLabel = new JLabel("Rating: " + (i % 5 + 1) + " stars");
            JLabel commentLabel = new JLabel("Comment: This is a comment from User" + i);

            ratingPanel.add(usernameLabel);
            ratingPanel.add(ratingLabel);
            ratingPanel.add(commentLabel);
            contentPanel.add(ratingPanel);
        }

        // Scroll pane
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBounds(10, 50, 460, 300);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);

        // Back button functionality
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Going back to MainAppView!");
                frame.dispose(); // Close current view (optional)
            }
        });
    }

    // Method to display the view
    public void show() {
        frame.setVisible(true);
    }
}
