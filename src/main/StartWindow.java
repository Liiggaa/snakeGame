package main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartWindow extends JFrame implements ActionListener {

    private final JButton startButton;

    public StartWindow() {
        setTitle("Welcome to Snake Game");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create welcome message label
        JLabel welcomeLabel = new JLabel("Welcome to Snake Game!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        add(welcomeLabel, BorderLayout.NORTH);

        // create start button
        startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            dispose(); // close the start window
            new Main(); // start the game
        }
    }
}