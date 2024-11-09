package Use_case;

public class LoginUseCase {
    public LoginResult execute(String username, String password) {
        // Dummy logic for demonstration
        if ("correctUsername".equals(username) && "correctPassword".equals(password)) {
            return new LoginResult(true, "");
        } else {
            return new LoginResult(false, "Invalid credentials");
        }
    }
}
