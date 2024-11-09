import api.SpotifyAPI;
import Use_case.LoginUseCase;
import interface_adapter.login.LoginViewModel;
import View.SpotifyLoginView;
import interface_adapter.login.LoginController;

import javax.swing.*;



public class Main {
    public static void main(String[] args) {
        // Create instances of dependencies
        SpotifyAPI spotifyAPI = new SpotifyAPI();
        LoginUseCase loginUseCase = new LoginUseCase(spotifyAPI);
        LoginViewModel loginViewModel = new LoginViewModel();

        // Initialize the login view (GUI)
        SpotifyLoginView loginView = new SpotifyLoginView(loginViewModel, null);

        // Initialize the controller and pass the login view
        LoginController loginController = new LoginController(loginView, loginViewModel, loginUseCase);

        // Set the action listener for the login view button (pass the controller's action listener)
        loginView = new SpotifyLoginView(loginViewModel, loginController);

        // Create a frame and add the login view
        JFrame frame = new JFrame("Spotify Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(loginView);
        frame.pack();
        frame.setVisible(true);
    }
}
