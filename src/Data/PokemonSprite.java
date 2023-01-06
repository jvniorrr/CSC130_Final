package Data;

import java.util.Random;

// class for our pokemon objects 
public class PokemonSprite {
    private static final int MOVEMENT = 4; // testing; development should be 4ish

    // random gen for moving sprite
    private Random rand;

    // attributes
    private boolean visible;
    private String spriteTag;

    private SpriteInfo sprite;
    private Vector2D location; // will use this as initial point

    // Constructors
    public PokemonSprite() {
        rand = new Random();
        this.visible = true;
        this.spriteTag = "pokemon";
        this.location = new Vector2D(0, 0);
        this.sprite = new SpriteInfo(new Vector2D(this.location.getX(), this.location.getY()), spriteTag);
    }

    public PokemonSprite(boolean visible, Vector2D location, String spriteTag) {
        rand = new Random();
        this.visible = visible;
        this.location = location;
        this.spriteTag = spriteTag;
        this.sprite = new SpriteInfo(new Vector2D(this.location.getX(), this.location.getY()), this.spriteTag);
    }

    // GETTERS
    public SpriteInfo getSprite() {
        return sprite;
    }

    public boolean isVisible() {
        return visible;
    }

    public Vector2D getLocation() {
        return location;
    }

    public String getSpriteTag() {
        return spriteTag;
    }

    // SETTERS
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setSprite(SpriteInfo sprite) {
        this.sprite = sprite;
    }

    public void setLocation(Vector2D location) {
        this.location = location;
    }

    public void setSpriteTag(String spriteTag) {
        this.spriteTag = spriteTag;
    }

    // misc methods

    public void pokemonMovement() {
        int val = rand.nextInt(4);

        // if sprite is further than 10px from initial.
        Vector2D currPosition = this.sprite.getCoords();
        if (Math.abs(currPosition.getX() - this.location.getX()) > 10 ||
                Math.abs(currPosition.getY() - this.location.getY()) > 10) {
            this.sprite.setVec(this.location);
        }

        switch (val) {
            // NORTH
            case 0:
                this.sprite.moveSprite(0, MOVEMENT);
                break;
            // EAST
            case 1:
                this.sprite.moveSprite(MOVEMENT, 0);
                break;
            // SOUTH
            case 2:
                this.sprite.moveSprite(0, -MOVEMENT);
                break;
            // WEST
            case 3:
                this.sprite.moveSprite(-MOVEMENT, 0);
                break;
        }

    }

}
