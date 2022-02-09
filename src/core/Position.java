package core;

import motionAndAbilities.MotionAndAbilities;

public class Position {
    private double x;
    private double y;

    // sets position of entities

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void apply(MotionAndAbilities motion) {
        Vector2D vector = motion.getVector();
        this.x += vector.getX();
        this.y += vector.getY();
    }


// getter methods (int)
    public int intX() {
        return (int) Math.round(x);
    }

    public int intY() {
        return (int) Math.round(y);
    }


// getter methods (double)

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


// setter methods

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }
}
