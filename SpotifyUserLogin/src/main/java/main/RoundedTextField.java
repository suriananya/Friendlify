package main;

import javax.swing.*;
import java.awt.*;

class RoundedTextField extends JTextField {
    private static final int ARC_WIDTH = 30; // Adjust for more roundness
    private static final int ARC_HEIGHT = 30;

    public RoundedTextField(int columns) {
        super(columns);
        setOpaque(false);
        setFont(new Font("Arial", Font.PLAIN, 14)); // Readable font size
        setForeground(Color.BLACK); // Ensure high contrast text color
        setBackground(Color.WHITE); // Set background to ensure readability
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

        // Draw border
        g2.setColor(new Color(76, 175, 80)); // Green border
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT);

        super.paintComponent(g);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        repaint();
    }
}

