package core;

public class Position {
    private static double x;
    private static double y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int intX() {
        return (int) Math.round(x);
    }

    public int intY() {
        return (int) Math.round(y);
    }

    public static double getX() {
        return x;
    }

    public static double getY() {
        return y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void apply(Motion motion) {
        Vector2D vector = motion.getVector();
        this.x += vector.getX();
        this.y += vector.getY();
    }
}
