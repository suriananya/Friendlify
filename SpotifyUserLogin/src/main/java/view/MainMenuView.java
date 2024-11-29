package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuView extends JPanel {
    private final JButton profileButton;
    private final JButton searchFriendsButton;

    public MainMenuView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        JLabel heading = new JLabel("Main Menu");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setAlignmentX(CENTER_ALIGNMENT);

        profileButton = new JButton("View Profile");
        profileButton.setAlignmentX(CENTER_ALIGNMENT);

        searchFriendsButton = new JButton("Search Friends");
        searchFriendsButton.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(20)); // Spacing
        add(heading);
        add(Box.createVerticalStrut(20)); // Spacing
        add(profileButton);
        add(Box.createVerticalStrut(10)); // Spacing
        add(searchFriendsButton);
    }

    // Methods to add listeners to the buttons
    public void addProfileButtonListener(ActionListener listener) {
        profileButton.addActionListener(listener);
    }

    public void addSearchFriendsButtonListener(ActionListener listener) {
        searchFriendsButton.addActionListener(listener);
    }
}
