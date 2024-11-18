package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The view for the dashboard with search bar and profile button.
 */
public class DashboardView {

    private final JFrame frame;
    private JLabel heading;
    private JTextField searchBar;
    private final JButton userButton;

    // Constructor
    public DashboardView() {
        // Initialize components
        frame = new JFrame("Search Friends");
        heading = new JLabel("Search Friends!");
        searchBar = new JTextField();
        userButton = new JButton("User 1");

        // Set up the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        // Configure heading
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBounds(100, 20, 200, 30);
        frame.add(heading);

        // Configure search bar
        searchBar.setBounds(100, 80, 200, 30);
        frame.add(searchBar);

        // Configure user button
        userButton.setBounds(300, 20, 80, 30);
        frame.add(userButton);

        // Add button functionality
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchBar.getText();
                JOptionPane.showMessageDialog(frame, "You clicked on User 1! Search text: " + searchText);
            }
        });
    }

    // Method to display the interface
    public void show() {
        frame.setVisible(true);
    }
}
