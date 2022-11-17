/* This is a way to pass a sprite's key information in one entity. (x, y, tag) */

package Data;

public class spriteInfo {
	// Fields
	private Vector2D vec;
	private String tag;

	// bounding box and type
	private BoundingBox boundingBox;

	// Constructor
	public spriteInfo(Vector2D v2d, String tag) {
		this.vec = v2d;
		this.tag = tag; 

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

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	// SETTERS
	public void setTag(String newTag) {
		this.tag = newTag;
	}

	public void setCoords(Vector2D newV2D) {
		this.vec = newV2D;
		this.boundingBox = new BoundingBox(newV2D);
	}

	public void setCoords(int x, int y) {
		this.vec.setX(x);
		this.vec.setY(y);
		this.boundingBox = new BoundingBox(this.vec);
	}
	
	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	// Move sprites
	public void moveSprite(int x, int y) {
		this.vec.adjustX(x);
		this.vec.adjustY(y);

		this.boundingBox = new BoundingBox(this.vec);
	}
	public void moveSpriteX(int x) {
		this.vec.adjustX(x);
		this.boundingBox = new BoundingBox(this.vec);
	}
	public void moveSpriteY(int y) {
		this.vec.adjustY(y);
		this.boundingBox = new BoundingBox(this.vec);
	}

	// @Override
	// public String toString() {
	// 	return "spriteInfo [" + vec.getX() + ", " + vec.getY() + ", " + tag + "]";
	// }

	@Override
	public String toString() {
		return String.format("spriteInfo[%d, %d, %s]", vec.getX(), vec.getY(), this.tag);
	}
}
