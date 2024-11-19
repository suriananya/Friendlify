package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DashboardView {
    private final JPanel panel;
    private final JButton userButton;

    public DashboardView() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel heading = new JLabel("Search Friends!", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(heading, BorderLayout.NORTH);

        userButton = new JButton("User 1");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(userButton);
        panel.add(buttonPanel, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void addUserButtonListener(ActionListener listener) {
        userButton.addActionListener(listener);
    }
}
