package Data;

public class BoundingBoxBit {
    private int x1, x2, y1, y2;

    // default constructor
    public BoundingBoxBit(int x1, int y1) {
        this.x1 = x1;
        this.x2 = x1 +16;
        this.y1 = y1;
        this.y2 = y1 + 16;
    }

    // default 2 constructor
    public BoundingBoxBit(int x1, int width, int y1, int height) {
        this.x1 = x1;
        this.x2 = width;
        this.y1 = y1;
        this.y2 = height;
    }

    // constructor to set SPECIFIC coords
    public BoundingBoxBit(boolean set, int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    // GETTERS
    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }


    // SETTERS
    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }


    // Collision detection method
    public static boolean checkCollision(BoundingBoxBit spriteBox, BoundingBoxBit box2) {
        return !( (spriteBox.getX1() > box2.getX2()) || (spriteBox.getX2() < box2.getX1()) ||(spriteBox.getY1() > box2.getY2()) || (spriteBox.getY2() < box2.getY1()) );
    }
}
