package main;
public class Rectangle {

    // position on 2D screen
    private int positionX; // x coordinate
    private int positionY; // y coordinate

    // rectangle size
    public static final int rectangleWidth = 20;
    public static final int rectangleHeight = 20;

    // create rectangle
    public Rectangle(int positionX, int positionY) {
        this.positionX = positionX; //
        this.positionY = positionY;
    }

    // two rectangles can intersect and if they are intersecting that means, there is collision, meaning - lost the game:
    // - hit the wall
    // - hit the tale
    // - hit an apple

    public boolean intersects (Rectangle r2){
        /* return true if x and y coordinates of this and r2 are the same */
        return this.positionX == r2.getPositionX() && this.positionY == r2.getPositionY();
    }

    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }


    // used for moving object across the screen
    public void setPositionX(int increment) {
        this.positionX = this.positionX + increment;
    }
    public void setPositionY(int increment) {
        this.positionY = this.positionY + increment;
    }
}