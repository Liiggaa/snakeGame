package main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;


public class Main extends JFrame implements KeyListener, ActionListener {

    Snake snake; // reference to snake, for access it

    public Main() {

        // create the snake, keep a reference of this snake, when press some keys, access that snake and change the direction
        this.snake = new Snake(this);

        // timer for redrawing the screen, every 150 milliseconds redrew the screen and method paintComponent gets called
        Timer timer = new Timer(150, this);
        timer.start();

        // timer for drawing apples on the screen, creating scheduled task
        java.util.Timer drawApples = new java.util.Timer();
        Apple st = new Apple(this.snake);
        drawApples.schedule(st,0,2000); // every 2 seconds, create apple with run method, that's separate thread

        // window creation & drawing, JFrame part
        add(this.snake);
        setTitle("Snake Game");
        setSize(525, 525);
        this.addKeyListener(this); // add key listener, for drag the keyboard
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();

     // 39 equals to right arrow, if snake goes left, it cannot go right, if press right arrow and if snake direction isn't left set direction to right
        if (c == 39 && !this.snake.getDirection().equals("left")) {
            this.snake.setDirection("right"); // right arrow pressed
        }

        else if (c == 37 && !this.snake.getDirection().equals("right")) {
            this.snake.setDirection("left"); // left arrow pressed
        }

        else if (c == 38 && !this.snake.getDirection().equals("down")) {
            this.snake.setDirection("up"); // up arrow pressed
        }

        else if (c == 40 && !this.snake.getDirection().equals("up")) {
            this.snake.setDirection("down"); // down arrow pressed
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // redraw the screen
        repaint();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            StartWindow startWindow = new StartWindow();
            startWindow.setVisible(true);
        });
    }
}