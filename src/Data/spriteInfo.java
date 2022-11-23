/* This is a way to pass a sprite's key information in one entity. (x, y, tag) */

package Data;

public class spriteInfo {
	// Fields
	private Vector2D vec;
	private String tag;

	// bounding box and type
	private BoundingBox boundingBox;

	// store last position of our sprite
	private Vector2D lastVec;

	// Constructor
	public spriteInfo(Vector2D v2d, String tag) {
		this.vec = v2d;
		this.tag = tag; 

		this.lastVec = new Vector2D(0, 0);
		// set bounding box defaults
		this.boundingBox = new BoundingBox(v2d);
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

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}


	// SETTERS
	public void setTag(String newTag) {
		this.tag = newTag;
	}

	public void setCoords(Vector2D newV2D) {
		this.lastVec = vec;

		this.vec = newV2D;
		this.boundingBox = new BoundingBox(newV2D);
	}

	public void setCoords(int x, int y) {
		// reset our old coordinates
		this.lastVec = this.vec;

		this.vec.setX(x);
		this.vec.setY(y);
		this.boundingBox = new BoundingBox(this.vec);
	}
	
	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public void setLastVec(Vector2D lastVec) {
		this.lastVec = lastVec;
	}

	// Misc methods for moving our sprite and setting the sprite accordingly 
	// Move sprites
	public void moveSprite(int x, int y) {
		this.lastVec.setX(this.vec.getX());
		this.lastVec.setY(this.vec.getY());

		this.vec.adjustX(x);
		this.vec.adjustY(y);

		this.boundingBox = new BoundingBox(this.vec);
	}
	public void moveSpriteX(int x) {
		// this.lastVec = vec;
		this.lastVec.setX(this.vec.getX());
		// this.lastVec.setY(this.vec.getY());

		this.vec.adjustX(x);
		this.boundingBox = new BoundingBox(this.vec);
	}
	public void moveSpriteY(int y) {
		this.lastVec.setY(this.vec.getY());

		this.vec.adjustY(y);
		this.boundingBox = new BoundingBox(this.vec);
	}

	public void bounceBack() {
		
		this.vec.setX(this.lastVec.getX());;
		this.vec.setY(this.lastVec.getY());;
		this.boundingBox = new BoundingBox(this.vec);
	}

	@Override
	public String toString() {
		return String.format("spriteInfo[%d, %d, %s]", vec.getX(), vec.getY(), this.tag);
	}
}
