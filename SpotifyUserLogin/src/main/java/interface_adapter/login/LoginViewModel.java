package interface_adapter.login;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel {

    private LoginState state;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public LoginViewModel() {
        this.state = new LoginState("", "", "");
    }

    public void setState(LoginState state) {
        LoginState oldState = this.state;
        this.state = state;
        support.firePropertyChange("state", oldState, state);
    }

    public LoginState getState() {
        return state;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}


