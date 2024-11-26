package interface_adapters.login;

import Use_case.LoginResult;
import Use_case.LoginUseCase;
import View.SpotifyLoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    private final SpotifyLoginView view;
    private final LoginViewModel loginViewModel;
    private final LoginUseCase loginUseCase;

    public LoginController(SpotifyLoginView view, LoginViewModel loginViewModel, LoginUseCase loginUseCase) {
        this.view = view;
        this.loginViewModel = loginViewModel;
        this.loginUseCase = loginUseCase;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getLoginButton()) {
            handleLogin();
        } else if (e.getSource() == view.getCancelButton()) {
            handleCancel();
        }
    }

    private void handleLogin() {
        // Get the username and password from the LoginState
        LoginState currentState = loginViewModel.getState();
        String username = currentState.getUsername();
        String password = currentState.getPassword();

        // Perform login via the LoginUseCase and get a detailed result
        LoginResult result = loginUseCase.execute(username, password);

        // Update the LoginState based on authentication result
        if (result.isSuccess()) {
            currentState.setLoginError(""); // Clear error message
        } else {
            currentState.setLoginError(result.getErrorMessage());
        }

        // Update the LoginViewModel with the new state
        loginViewModel.setState(currentState);
    }

    private void handleCancel() {
        // Reset the login fields and clear errors
        loginViewModel.clearState();
    }
}
