package view.Friends;

import entities.users.FriendProfile;
import useCase.FriendsList.FriendProfileUseCase;
import useCase.FriendsList.FriendsListUseCase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class FriendProfileView extends JPanel {
    private final JButton backButton = new JButton("Back");
    private final JPanel profilePanel;

    public FriendProfileView() {
        this.defineLayout();

        profilePanel = new JPanel();
        this.defineFriendsPanel();

        this.defineBackButton();
    }

    private void defineLayout() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
    }

    private void defineFriendsPanel() {
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(Color.WHITE);

        add(profilePanel, BorderLayout.CENTER);
    }

    private void defineBackButton() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void displayFriendProfile(FriendProfileUseCase friendProfile) {
        profilePanel.removeAll();
        addUsernameLabel(friendProfile.getUsername());
        addPreferences("Preferred Genres", friendProfile.getPreferredGenres());
        addPreferences("Preferred Artists", friendProfile.getPreferredArtists());
    }

    private void addUsernameLabel(String username) {
        JLabel usernameLabel = new JLabel("Username: %s".formatted(username));
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setAlignmentX(CENTER_ALIGNMENT);
        profilePanel.add(usernameLabel);
    }

    private void addPreferences(String areaType, List<String> content) {
        JTextArea newTextArea = new JTextArea(5, 20);
        newTextArea.setEditable(false);
        newTextArea.setBorder(BorderFactory.createTitledBorder(areaType));
        newTextArea.setText(String.join("\n", content));
        profilePanel.add(newTextArea);
    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
