package main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


import static main.Rectangle.rectangleHeight;
import static main.Rectangle.rectangleWidth;

public class Snake  extends JPanel { // draw this object on the screen, in main will have JFrame which will be used as a container on which draw this snake

    private static final Color color = new Color(59, 91, 219);
    private Color startColor = new Color(253, 252, 71);

    private Color endColor = new Color(36, 254, 65);

    private static final int start = 200;
    private static final int speed = 20;

    private ArrayList<Rectangle> body; // snake will be collection from rectangles
    private String direction; // direction where snake moves
    private Apple apple; // attaching an apple to snake, so all the drawing is happening at the same time
    private final Main window; // reference to main window, for close it


    public Snake(main.Main window) { // add reference because snake are called from Main
        this.window = window;

        // create new ArrayList
        this.body = new ArrayList<>();

        // add three body parts to ArrayList, snake consist from three body parts
        body.add(new Rectangle(start, start));
        Rectangle last = this.body.get(0);
        body.add(new Rectangle(last.getPositionX() - rectangleWidth, last.getPositionY()));
        Rectangle last_2 = this.body.get(1);
        body.add(new Rectangle(last_2.getPositionX() - rectangleWidth, last_2.getPositionY()));

        // first direction is right
        this.direction = "right"; // start by default right
    }

    // when user press the key set direction
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return this.direction;
    }

    // add new rectangle after the last element
    // get last element
    public void addPart() {
        Rectangle last = this.body.get(this.body.size() - 1);
        switch (this.direction) {
            // if moving right, add it to the left, have minus
            case "right" -> this.body.add(new Rectangle(last.getPositionX() - rectangleWidth, last.getPositionY()));
            // if moving left, add it to the right because the right is the end (tale)
            case "left" -> this.body.add(new Rectangle(last.getPositionX() + rectangleWidth, last.getPositionY()));
            case "up" -> this.body.add(new Rectangle(last.getPositionX(), last.getPositionY() + rectangleWidth));
            case "down" -> this.body.add(new Rectangle(last.getPositionX(), last.getPositionY() - rectangleWidth));
        }
    }

    public void checkCollision() {

        Rectangle snakeHead = this.body.get(0);
        int headX = snakeHead.getPositionX();
        int headY = snakeHead.getPositionY();

        // check for collision with the walls
        if (headX < 0 || headX >= this.window.getWidth() ||
                headY < 0 || headY >= this.window.getHeight()) {
            System.out.println("You hit the wall!");
            this.window.setVisible(false);

            JFrame parent = new JFrame("Game over!");
            String message = "Game over!\nYour score: " + this.body.size();
            Object[] options = {"Play Again", "Quit"};
            int choice = JOptionPane.showOptionDialog(parent, message, "Game Over",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);

            if (choice == JOptionPane.YES_OPTION) { // user clicked "Play Again"
                restartGame(); // restart the game

            } else { // user clicked "Quit"
                this.window.dispatchEvent(new WindowEvent(this.window, WindowEvent.WINDOW_CLOSING));
                System.exit(0);// exit the application
            }
        }

        // check for collision with the snake's body
        for (int i = 1; i < this.body.size(); i++) { // go from 1 and check all the body parts with intersects method
            Rectangle bodyPart = this.body.get(i);
            if (snakeHead.intersects(bodyPart)) { // if true, end the game
                System.out.println("You hit your own tail!");

                this.window.setVisible(false); // close the window

                JFrame parent = new JFrame("Game over!");
                String message = "Game over!\nYour score: " + this.body.size();
                Object[] options = {"Play Again", "Quit"};
                int choice = JOptionPane.showOptionDialog(parent, message, "Game Over",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);

                if (choice == JOptionPane.YES_OPTION) {
                    restartGame();

                } else {
                    this.window.dispatchEvent(new WindowEvent(this.window, WindowEvent.WINDOW_CLOSING));
                    System.exit(0);
                }
            }


            // Check for collision with the apple
            if (this.apple != null) { // if snake hit the apple, and intersect apple, add a part to the snake
                if (snakeHead.intersects(new Rectangle(this.apple.getPositionX(), this.apple.getPositionY()))) {
                    this.apple = null;
                    this.addPart();
                }
            }
        }
    }


    public void moveSnake(){
        ArrayList<Rectangle> newList = new ArrayList<>(); // create Arraylist where to store new position of the snake

        Rectangle first = this.body.get(0); // take first which is head
        Rectangle head = new Rectangle(first.getPositionX(), first.getPositionY()); // create new rectangle as the old position

        // move the snake's head, set position equal to speed, speed is 20, head will move for 20
        switch(this.direction){
            case "right" -> head.setPositionX(speed);
            case "left" -> head.setPositionX(-speed);
            case "up" -> head.setPositionY(-speed);
            case "down" -> head.setPositionY(speed);
        }
        newList.add(head); // add that head to new list

        // traverse the old list
        for(int i = 1; i < this.body.size(); i++){
            Rectangle previous = this.body.get(i-1); // previous
            Rectangle newRectangle = new Rectangle(previous.getPositionX(), previous.getPositionY()); // new rectangle will go the position of the previous rectangle
            newList.add(newRectangle); // add them to new list,
        }

        this.body = newList; // new list becomes the body of the snake
        checkCollision(); // once move snake, check the collision
    }


    public void restartGame() {
        reset(); // reset the snake's position, body, and direction
        this.window.setVisible(true); // start the game again
    }

    public void reset() {
        this.body.clear();
        this.body.add(new Rectangle(start, start));
        Rectangle last = this.body.get(0);
        this.body.add(new Rectangle(last.getPositionX() - rectangleWidth, last.getPositionY()));
        Rectangle last_2 = this.body.get(1);
        this.body.add(new Rectangle(last_2.getPositionX() - rectangleWidth, last_2.getPositionY()));
        this.direction = "right";
    }

    // graphic's part
    private void drawSnake(Graphics g){

        moveSnake(); // every 150 milliseconds move the snake and just draw the rectangles


        // draw moved snake, create graphics 2D object
        Graphics2D g2d = (Graphics2D) g;

        if(this.apple != null){
            g2d.setPaint(Color.red);
            g2d.drawRect(this.apple.getPositionX(), this.apple.getPositionY(), rectangleWidth, rectangleHeight); // draw rectangle with apple position
            g2d.fillRect(this.apple.getPositionX(), this.apple.getPositionY(), rectangleWidth, rectangleHeight); // fill it with color
        }

        g2d.setPaint(color);
        for(Rectangle rec: this.body){
            g2d.drawRect(rec.getPositionX(), rec.getPositionY(), rectangleWidth, rectangleHeight);
            g2d.fillRect(rec.getPositionX(), rec.getPositionY(), rectangleWidth, rectangleHeight);
        }
    }

    public void setApple( Apple apple){
        this.apple = apple;
    }

    public Apple getApple(){
        return this.apple;
    }

    // method from JPanel, called from main
    @Override
    public void paintComponent(Graphics g){ // redraws the screen
        super.paintComponent(g);

        GradientPaint gradient = new GradientPaint(
                0, 0, startColor, getWidth(), getHeight(), endColor);
        ((Graphics2D) g).setPaint(gradient);
        g.fillRect(0, 0, getWidth(), getHeight());

        drawSnake(g);
    }
}