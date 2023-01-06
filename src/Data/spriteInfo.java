/* This is a way to pass a sprite's key information in one entity. (x, y, tag) */

package Data;

public class SpriteInfo {
	// Fields
	private Vector2D vec;
	private String tag;

	// bounding box and type
	private BoundingBoxBit boundingBox;

	// store last position of our sprite
	private Vector2D lastVec;

	// Constructor
	public SpriteInfo(Vector2D v2d, String tag) {
		this.vec = v2d;
		this.tag = tag; 

		this.lastVec = new Vector2D(0, 0);
		// set bounding box defaults
		this.boundingBox = new BoundingBoxBit(v2d.getX(), v2d.getY());
	}

	// Methods
	public String getTag() {
		return this.tag;
	}

	public Vector2D getCoords() {
		return this.vec;
	}

	public Vector2D getLastVec() {
		return this.lastVec;
	}

	public BoundingBoxBit getBoundingBox() {
		return boundingBox;
	}


	// SETTERS
	public void setTag(String newTag) {
		this.tag = newTag;
	}
	public void setVec(Vector2D vec) {
		this.vec = new Vector2D(vec.getX(), vec.getY());
	}

	// Misc methods for moving our sprite and setting the sprite accordingly 
	// Move sprites
	public void moveSprite(int x, int y) {
		this.lastVec.setX(this.vec.getX());
		this.lastVec.setY(this.vec.getY());

		this.vec.adjustX(x);
		this.vec.adjustY(y);

		this.boundingBox = new BoundingBoxBit(this.vec.getX(), this.vec.getY());
	}

	public void bounceBack() {
		
		this.vec.setX(this.lastVec.getX());
		this.vec.setY(this.lastVec.getY());
		this.boundingBox = new BoundingBoxBit(this.vec.getX(), this.vec.getY());
	}


	@Override
	public String toString() {
		return String.format("spriteInfo[%d, %d, %s]", vec.getX(), vec.getY(), this.tag);
	}
}
