package View;

import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The View for the Spotify login screen.
 */
public class SpotifyLoginView extends JPanel {

    // UI Components
    private final JTextField usernameInputField = new JTextField(15);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logInButton;
    private final JButton cancelButton;

    public SpotifyLoginView(LoginViewModel loginViewModel, ActionListener loginActionListener) {
        loginViewModel.addPropertyChangeListener(this::propertyChange);

        // Set up the UI components
        JLabel title = new JLabel("Spotify Login");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username and Password fields
        LabelTextPanel usernameInfo = new LabelTextPanel(new JLabel("Username"), usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(new JLabel("Password"), passwordInputField);

        JPanel buttons = new JPanel();

        logInButton = new JButton("Log In");
        logInButton.addActionListener(loginActionListener);
        buttons.add(logInButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this::handleCancel);
        buttons.add(cancelButton);

        // Listen for username field changes and update state
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        // Listen for password field changes and update state
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        // Layout configuration
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);                  // Add the title label
        this.add(usernameInfo);           // Add the username field panel
        this.add(usernameErrorField);     // Add the error label for the username field
        this.add(passwordInfo);           // Add the password field panel
        this.add(passwordErrorField);     // Add the error label for the password field
        this.add(buttons);                 // Add the buttons panel (Log In and Cancel)
    }

    // Action listener for the cancel button
    private void handleCancel(ActionEvent evt) {
        // Reset the input fields
        usernameInputField.setText("");
        passwordInputField.setText("");
        usernameErrorField.setText("");
        passwordErrorField.setText("");
    }

    // Handle property change events to update the view
    private void propertyChange(java.beans.PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());
    }

    // Update fields based on LoginState
    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
    }

    public String getViewName() {
        return "Spotify Login";
    }

    public JButton getLoginButton() {
        return logInButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
