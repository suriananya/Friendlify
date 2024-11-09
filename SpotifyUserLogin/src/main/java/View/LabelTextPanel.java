package View;
import javax.swing.*;


/**
 * A helper panel to hold a JLabel and a JTextField or JPasswordField.
 */
public class LabelTextPanel extends JPanel {

    public LabelTextPanel(JLabel label, JTextField textField) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(label);
        this.add(textField);
    }

    public LabelTextPanel(JLabel label, JPasswordField passwordField) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(label);
        this.add(passwordField);
    }
}

