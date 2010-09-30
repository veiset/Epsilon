package epsilontest;

/**
 * Test class that extends entity
 *
 * @author Marius
 */
public class TestEntity extends Entity {

    private int ticker;

    public TestEntity(String[] urls,int posX,int posY) {
        super(urls, posX, posY);
        ticker = 0;
    }

    @Override
    public void move() {
        super.move(super.posX+2, super.posY);
        if (ticker < 5) {
            ticker++;
        } else {
            ticker = 0;
            super.nextImage();
        }
    }

}
