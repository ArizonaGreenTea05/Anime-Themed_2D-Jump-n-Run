package core;

import core.motion.Motion;

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

    public static Direction fromMotion(Motion motion){
        double x = motion.getVector().getX();
        double y = motion.getVector().getY();

        if(motion.isSitting()) return D;
        if(x < 0 && y >= 0) return L;
        if(x == 0 && y < 0) return O;
        if(x > 0 && y >= 0) return R;
        if(x < 0 && y < 0) return OL;
        if(x > 0 && y < 0) return OR;

        return R;
    }


    public int getAnimationRow(){
        return animationRow;
    }

}
