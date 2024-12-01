package view.Rating;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RateFriendView extends JPanel {
    private final JButton backButton;

    public RateFriendView() {
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Rate a Friend");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        add(heading, BorderLayout.NORTH);

        backButton = new JButton("Back to Dashboard");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
