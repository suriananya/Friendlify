package view.EditPreferences;

import interfaceAdapters.editpreferences.EditPreferencesController;
import interfaceAdapters.editpreferences.EditPreferencesState;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;


public class EditPreferencesView extends JPanel {
    private final JTextField genresField;
    private final JTextField artistsField;
    private final JLabel statusLabel;

    public EditPreferencesView(EditPreferencesController controller) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Genres Input
        JLabel genresLabel = new JLabel("Preferred Genres (comma-separated):");
        genresField = new JTextField();
        add(genresLabel);
        add(genresField);

        // Artists Input
        JLabel artistsLabel = new JLabel("Preferred Artists (comma-separated):");
        artistsField = new JTextField();
        add(artistsLabel);
        add(artistsField);

        // Buttons
        JButton updateButton = new JButton("Update Preferences");
        JButton dynamicUpdateButton = new JButton("Update Dynamically");
        add(updateButton);
        add(dynamicUpdateButton);

        // Status Label
        statusLabel = new JLabel();
        add(statusLabel);

        // Add Button Listeners
        updateButton.addActionListener(e -> {
            List<String> genres = Arrays.asList(genresField.getText().split(","));
            List<String> artists = Arrays.asList(artistsField.getText().split(","));
            controller.updatePreferencesManually(genres, artists);

            // Update status based on the controller's state
            EditPreferencesState state = controller.getState();
            if (state != null) {
                statusLabel.setText(state.getMessage());
                genresField.setText(String.join(", ", state.getGenres()));
                artistsField.setText(String.join(", ", state.getArtists()));
            } else {
                statusLabel.setText("Error: Could not fetch state.");
            }
        });

        dynamicUpdateButton.addActionListener(e -> {
            controller.updatePreferencesDynamically();

            // Update status based on the controller's state
            EditPreferencesState state = controller.getState();
            if (state != null) {
                statusLabel.setText(state.getMessage());
                genresField.setText(String.join(", ", state.getGenres()));
                artistsField.setText(String.join(", ", state.getArtists()));
            } else {
                statusLabel.setText("Error: Could not fetch state.");
            }
        });
    }
}

