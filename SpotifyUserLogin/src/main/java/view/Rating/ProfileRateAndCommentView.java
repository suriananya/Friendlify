package view.Rating;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileRateAndCommentView extends JPanel {
    private final JButton backButton;
    private final JPanel contentPanel;

    /**
     * The View for user profile's ratings page.
     */

    // Constructor
    public ProfileRateAndCommentView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Header label
        JLabel headerLabel = new JLabel("Your Friends' Ratings");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headerLabel, BorderLayout.NORTH);

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
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // Back button
        backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to add a listener to the back button
    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
