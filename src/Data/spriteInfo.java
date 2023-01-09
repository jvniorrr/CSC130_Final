/* This is a way to pass a sprite's key information in one entity. (x, y, tag) */

package Data;

public class SpriteInfo {
	// Fields

	// store last position of our sprite
	private Vector2D lastVec;
	private Vector2D vec;
	private String tag;
	private int health;

	// bounding box and type
	private BoundingBoxBit boundingBox;

	// store weapon item
	private Weapon weapon;
	private boolean weaponPresent;
	

	// Constructor
	public SpriteInfo(Vector2D v2d, String tag) {
		this.vec = v2d;
		this.tag = tag;

		this.lastVec = new Vector2D(0, 0);
		// set bounding box defaults
		this.boundingBox = new BoundingBoxBit(v2d.getX(), v2d.getY());

		int midX = this.boundingBox.getAverageX();
		int midY = this.boundingBox.getAverageY();
		// this.weapon = new Weapon(new Vector2D(midX, midY), "tag")
		// this.weapon.setVec(new Vector2D(midX, midY));

		this.weaponPresent = false;

		this.health = 100;
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

	public Weapon getWeapon() {
		return weapon;
	}

	public boolean isWeaponPresent() {
		return weaponPresent;
	}
	public int getHealth() {
		return health;
	}


	// SETTERS
	public void setTag(String newTag) {
		this.tag = newTag;
	}

	public void setVec(Vector2D vec) {
		this.vec = new Vector2D(vec.getX(), vec.getY());
	}

	public void setWeaponPresent(boolean weaponPresent) {
		this.weaponPresent = weaponPresent;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	// Misc methods for moving our sprite and setting the sprite accordingly
	/* 
	 * Method to move the sprite coordinates and bounds 
	 */
	public void moveSprite(int x, int y) {
		// set the previous vectors for the use of moveBack method
		this.lastVec.setX(this.vec.getX());
		this.lastVec.setY(this.vec.getY());

		// adjust the vectors accordingly
		this.vec.adjustX(x);
		this.vec.adjustY(y);

		// reset the bounding box
		this.boundingBox = new BoundingBoxBit(this.vec.getX(), this.vec.getY());

		if (this.weapon != null) {
			int midX = this.boundingBox.getAverageX();
			int midY = this.boundingBox.getAverageY();
			if (this.tag.contains("left")) {
				midX -= 20;
			} else if (this.tag.contains("up")) {
				midX -= 5;
				midY -= 21;
			} else if (this.tag.contains("down")) {
				midX -= 2;
				midY += 2;
			} else {
				midX += 4;
			}
			this.weapon.setVec(new Vector2D(midX, midY));
		}
	}

	public void bounceBack() {

		this.vec.setX(this.lastVec.getX());
		this.vec.setY(this.lastVec.getY());
		// reset our bounds
		this.boundingBox = new BoundingBoxBit(this.vec.getX(), this.vec.getY());

		// FIXME: assure we have a weapon since not in constructor currently
		if (this.weapon != null) {
			// get the middle position of our bounding box so we can position objects in center of it
			int midX = this.boundingBox.getAverageX();
			int midY = this.boundingBox.getAverageY();

			// adjust accoringly
			if (this.tag.contains("left")) {
				midX -= 20;
			} else if (this.tag.contains("up")) {
				midX -= 5;
				midY -= 21;
			} else if (this.tag.contains("down")) {
				midX -= 5;
				midY += 7;
			} else {
				midX += 4;
			}
			this.weapon.setVec(new Vector2D(midX, midY));
		}
	}

	/* 
	 * Method to remove or add health
	 */
	public int adjustHealth(int health) {
		this.health += health;
		return this.health;
	}

	@Override
	public String toString() {
		return String.format("SpriteInfo[%d, %d, %s]", vec.getX(), vec.getY(), this.tag);
	}
}
