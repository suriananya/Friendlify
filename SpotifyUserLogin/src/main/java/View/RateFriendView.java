package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The View for other user's rating page.
 */
public class RateFriendView {
    private final JFrame frame;
    private final JLabel headerLabel;
    private final JLabel rateLabel;
    private final JTextField rateField;
    private final JLabel commentLabel;
    private final JTextField commentField;
    private final JButton backButton;
    private final JButton submitButton;

    // Constructor
    // parameter of RateFriendView() should be RateFriendView(String username)
    public RateFriendView() {
        // Initialize the frame
        frame = new JFrame("Rate Friend's Song");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);

        // Header label
        // headerLabel = new JLabel("Rate " + username + "'s Song");
        headerLabel = new JLabel("Rate Usernames's Song");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBounds(50, 10, 300, 30);
        frame.add(headerLabel);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(10, 10, 80, 30);
        frame.add(backButton);

        // Rate label
        rateLabel = new JLabel("Rate the song between 1 - 100:");
        rateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        rateLabel.setBounds(50, 80, 300, 20);
        frame.add(rateLabel);

        // Rate text field
        rateField = new JTextField();
        rateField.setBounds(50, 110, 300, 30);
        frame.add(rateField);

        // Comment label
        commentLabel = new JLabel("Leave a comment for your friend:");
        commentLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        commentLabel.setBounds(50, 160, 300, 20);
        frame.add(commentLabel);

        // Comment text field
        commentField = new JTextField();
        commentField.setBounds(50, 190, 300, 30);
        frame.add(commentField);

        // Submit button
        submitButton = new JButton("Submit");
        submitButton.setBounds(150, 250, 100, 30);
        frame.add(submitButton);

        // Back button functionality
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Going back to the previous page!");
                frame.dispose(); // Close current view (optional)
            }
        });

        // Submit button functionality
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rating = rateField.getText();
                String comment = commentField.getText();

                if (rating.isEmpty() || comment.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in both fields before submitting.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Rating: " + rating + "\nComment: " + comment);
                    frame.dispose(); // Close after submission (optional)
                }
            }
        });
    }

    // Method to display the view
    public void show() {
        frame.setVisible(true);
    }
}
