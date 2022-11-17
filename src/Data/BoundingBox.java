package Data;

public class BoundingBox {
    // fields
    private Vector2D spriteLocation;
    private int x1, x2, y1, y2;
    

    // TODO: Add a circle collision in case want to later do some circles

    // CONSTRUCTORS
    // Bounding box 'default' constructor so to say since we need a sprite
    public BoundingBox(Vector2D spriteLocation) {
        // Default image size's 
        this.spriteLocation = spriteLocation;
        this.x1 = spriteLocation.getX();
        this.x2 = spriteLocation.getX() + 128;
        this.y1 = spriteLocation.getY();
        this.y2 = spriteLocation.getY() + 128;
    }

    // Custom bounding box barriers
    public BoundingBox(Vector2D spriteLocation, int width, int height) {
        // Default image size's 
        this.spriteLocation = spriteLocation;
        this.x1 = spriteLocation.getX();
        this.x2 = spriteLocation.getX() + width;

        this.y1 = spriteLocation.getY();
        this.y2 = spriteLocation.getY() + height;
    }
    public BoundingBox(int width, int height) {
        this.spriteLocation = new Vector2D(0, 0);
        this.x1 = spriteLocation.getX();
        this.x2 = spriteLocation.getX() + width;
        this.y1 = spriteLocation.getX();
        this.y2 = spriteLocation.getX() + height;
    }



    // GETTERS
    public Vector2D getSpriteLocation() {
        return spriteLocation;
    }

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
    // SETTERS
    public void setSpriteLocation(Vector2D spriteLocation) {
        this.spriteLocation = spriteLocation;
        // default values
        this.x1 = spriteLocation.getX();
        this.x2 = spriteLocation.getX() + 128;

        this.y1 = spriteLocation.getY();
        this.y2 = spriteLocation.getY() + 128;
    }

    // Custom method to specify bounding barriers
    public void setSpriteLocation(Vector2D spriteLocation, int widthAdj, int heighAdj) {
        this.spriteLocation = spriteLocation;
        this.x1 = spriteLocation.getX();
        this.x2 = spriteLocation.getX() + widthAdj;

        this.y1 = spriteLocation.getY();
        this.y2 = spriteLocation.getY() + heighAdj;
    }

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
}
