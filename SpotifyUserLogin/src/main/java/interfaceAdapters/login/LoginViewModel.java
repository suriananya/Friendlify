package interfaceAdapters.login;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel {
    private LoginState state;
    private final PropertyChangeSupport support;

    public LoginViewModel(LoginState initialState) {
        this.state = initialState;
        this.support = new PropertyChangeSupport(this);
    }

    public LoginState getState() {
        return state;
    }

    public void setState(LoginState newState) {
        LoginState oldState = this.state;
        this.state = newState;
        support.firePropertyChange("state", oldState, newState);  // Notify listeners of the change
    }

    // Add a property change listener
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }


    // Clears the state to an empty one
    public void clearState() {
        setState(new LoginState("", "", ""));
    }
}
