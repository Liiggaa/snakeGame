package main;
import java.util.Random;
import java.util.TimerTask;

public class Apple extends TimerTask {

    // also rectangle
    private int positionX; // apple position
    private int positionY;

    // reference to snake
    private Snake snake;

    public Apple(Snake snake){
        this.snake = snake;
    }

    // screen will be 500 X 500
    // generate number between 500 x and y direction
    // when create apple, set the position, don't  change it until snake eat the apple
    public Apple(){
        this.positionX = 20 * new Random().nextInt(25);
        this.positionY = 20 * new Random().nextInt(25);
    }

    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }


    // use TimerTask, every 2 seconds if the apple is null, draw new apple on the screen
    // every 2 seconds have a thread, that is checking every 2 seconds if the apple is null, if is null, create new apple

    @Override
    public void run(){
        if(this.snake.getApple() == null){
            this.snake.setApple(new Apple()); // create new apple
        }
    }
}