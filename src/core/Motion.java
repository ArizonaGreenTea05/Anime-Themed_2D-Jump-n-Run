package core;
import controller.Controller;

public class Motion {

    private Vector2D vector;
    private final double speed;
    public static boolean falling;
    public static boolean sitting;
    private double gravity;

    public Motion(double speed) {
        this.speed = speed;
        this.vector = new Vector2D(0, 0);
    }

    public void update(Controller controller, Position position) {
        double deltaX = 0;
        double deltaY = 0;

        double x = position.getX();
        double y = position.getY();

        //wenn Position 64p (Character-Größe = 64, deswegen 128) über Boden wird fallling true
        //wenn Position größer als Boden und nicht Up requestet wird wird falling true
        if(y < ScreenSize.getGround()-128 || (!controller.isRequestingUp() && y < ScreenSize.getGround())){
            falling = true;
        }

        //wenn Position kleiner-gleich Boden wird falling false und gravity auf 0
        if(y >= ScreenSize.getGround()){
            falling = false;
            gravity = 0;
        }

        if(controller.isRequestingUp() && !falling) {
            deltaY -= getFallSpeed(gravity);
            gravity += 0.5;
            sitting = false;
        }

        if(falling) {
            deltaY += getFallSpeed(gravity);
            gravity -= 0.5;
            sitting = false;
        }

        if(controller.isRequestingDown()) {
            deltaY -= 1E-100;
            sitting = true;
        }

        if(controller.isRequestingLeft() && x > ScreenSize.getLeftBorder()) {
            deltaX -= 1.5;
            sitting = false;
        }

        if(controller.isRequestingRight() && x < ScreenSize.getRightBorder()) {
            deltaX  += 1.5;
            sitting = false;
        }


        vector = new Vector2D(deltaX, deltaY);
        vector.multiply(speed);

    }

    private double getFallSpeed(double x){
        double d = -0.01 * x*x + 2.8;
        if( d >= 0 ) return d;
        return 1;
    }

    public Vector2D getVector() {
        return vector;
    }

    public boolean isMoving() {
        return vector.length() > 0;
    }

    public static boolean getIsSitting(){
        return sitting;
    }
}
