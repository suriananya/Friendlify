package interface_adapters.login;

public class LoginState {
    private String username;
    private String password;
    private String loginError;

    public LoginState(String username, String password, String loginError) {
        this.username = username;
        this.password = password;
        this.loginError = loginError;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginError() {
        return loginError;
    }

    public void setLoginError(String loginError) {
        this.loginError = loginError;
    }
}
