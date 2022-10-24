/* This is a way to pass a sprite's key information in one entity. (x, y, tag) */

package Data;

public class spriteInfo {
	// Fields
	private Vector2D vec;
	private String tag;

	// Constructor
	public spriteInfo(Vector2D v2d, String tag) {
		this.vec = v2d;
		this.tag = tag; 

	}

	// Methods
	public String getTag() {
		return this.tag;
	}

	public Vector2D getCoords() {
		return this.vec;
	}

	public void setTag(String newTag) {
		this.tag = newTag;
	}

	public void setCoords(Vector2D newV2D) {
		this.vec = newV2D;
	}

	public void setCoords(int x, int y) {
		this.vec.setX(x);
		this.vec.setY(y);
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
