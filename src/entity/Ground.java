package entity;

public class Ground extends CustomSizeBlock{

    public Ground(int width, int height, int posX, int posY, String texture){
        super(width, height, posX,  posY, texture);
        solid = true;
    }

    @Override
    public void update() {
        super.update();
    }
}
