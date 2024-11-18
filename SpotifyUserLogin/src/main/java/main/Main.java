package main;

import View.DashboardView;
import View.ProfileRateAndCommentView;
import View.RateFriendView;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import interface_adapter.login.LoginState;
import Use_case.LoginUseCase;
import View.SpotifyLoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set up the initial state, view model, and use case
        LoginState initialState = new LoginState("", "", "");
        LoginViewModel loginViewModel = new LoginViewModel(initialState);
        LoginUseCase loginUseCase = new LoginUseCase();

        JFrame frame = new JFrame("Spotify Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the view and controller, passing the frame reference to the view
        SpotifyLoginView loginView = new SpotifyLoginView(loginViewModel, null, frame);
        LoginController loginController = new LoginController(loginView, loginViewModel, loginUseCase);

        // Add the login action listener to the view
        loginView.getLoginButton().addActionListener(loginController);
        loginView.getCancelButton().addActionListener(loginController);

        // Create the frame to display the login view
        frame.getContentPane().add(loginView);
        frame.pack();
        frame.setLocationRelativeTo(null);  // Center the frame
        frame.setVisible(true);

        // Set views visible
        DashboardView dashboard = new DashboardView();
        dashboard.show();
        ProfileRateAndCommentView rateComment = new ProfileRateAndCommentView();
        rateComment.show();
        RateFriendView rate = new RateFriendView();
        rate.show();
    }
}

