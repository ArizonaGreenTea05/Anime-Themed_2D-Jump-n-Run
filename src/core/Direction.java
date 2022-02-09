package core;

import controller.Controller;

public enum Direction {

    L(0),
    R(1),
    OL(2),
    OR(3),
    O(3),
    D(4);

    private int animationRow;

    Direction(int animationRow){
        this.animationRow = animationRow;
    }

    public static Direction fromMotion(Controller controller){
        // defines direction regarding action requested by controller
        boolean right = controller.isRequestingRight();
        boolean left = controller.isRequestingLeft();
        boolean up = controller.isRequestingUp();
        boolean down = controller.isRequestingDown();

        if(down) {
            return D;
        }
        if(left && !up) {
            return L;
        }
        if(up && !left && !right) {
            return O;
        }
        if(right && !up) {
            return R;
        }
        if(left && up) {
            return OL;
        }
        if(right && up) {
            return OR;
        }

        return R;
    }


    public int getAnimationRow(){
        return animationRow;
    }

}
