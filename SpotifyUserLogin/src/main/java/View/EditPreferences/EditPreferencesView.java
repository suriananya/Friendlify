package View.EditPreferences;

import interface_adapter.editpreferences.EditPreferencesController;
import interface_adapter.editpreferences.EditPreferencesState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        // Submit Button
        JButton submitButton = new JButton("Update Preferences");
        add(submitButton);

        // Status Label
        statusLabel = new JLabel();
        add(statusLabel);

        // Add Button Listener for Manual Update
        submitButton.addActionListener(e -> {
            String genresInput = genresField.getText().trim();
            String artistsInput = artistsField.getText().trim();

            // Validate input
            if (genresInput.isEmpty() || artistsInput.isEmpty()) {
                statusLabel.setText("Genres and Artists fields cannot be empty.");
                return;
            }

            // Parse input
            List<String> newGenres = Arrays.stream(genresInput.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            List<String> newArtists = Arrays.stream(artistsInput.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());


            // Get updated state
            EditPreferencesState state = controller.getState();

            // Update UI based on the state
            if (state.isSuccess()) {
                statusLabel.setText("Preferences updated successfully.");
                genresField.setText(String.join(", ", state.getGenres()));
                artistsField.setText(String.join(", ", state.getArtists()));
            } else {
                statusLabel.setText("Error: " + state.getMessage());
            }
        });

        // Optional: Add Button for Dynamic Update
        JButton dynamicUpdateButton = new JButton("Fetch Preferences from Spotify");
        add(dynamicUpdateButton);

        dynamicUpdateButton.addActionListener(e -> {
            controller.updatePreferencesDynamically();

            // Get updated state
            EditPreferencesState state = controller.getState();

            // Update UI based on the state
            if (state.isSuccess()) {
                statusLabel.setText("Preferences fetched successfully.");
                genresField.setText(String.join(", ", state.getGenres()));
                artistsField.setText(String.join(", ", state.getArtists()));
            } else {
                statusLabel.setText("Error: " + state.getMessage());
            }
        });
    }
}
