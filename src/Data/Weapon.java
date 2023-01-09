package Data;

import java.util.ArrayList;
import java.util.Arrays;

public class Weapon {
	// Fields

	// store last position of our sprite
	private Vector2D vec;
	private String tag, type;

	// store weapon item
	private SpriteInfo weapon;
	private boolean weaponPresent, trigger;
	private int triggerCount = 0;

	private String defaultName;

	// Constructor
	public Weapon(Vector2D v2d, String tag) {
		this.vec = v2d;
		this.tag = tag;

		this.weaponPresent = false;
		this.trigger = false;
		setDefaultName();
		setType();

	}

	// GETTERS
	public String getTag() {
		return this.tag;
	}

	public int getTriggerCount() {
		return triggerCount;
	}

	public Vector2D getCoords() {
		return this.vec;
	}

	public SpriteInfo getWeapon() {
		return weapon;
	}

	public boolean isWeaponPresent() {
		return weaponPresent;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public boolean isTrigger() {
		return trigger;
	}
	

	public String getType() {
		return type;
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

	public void setWeapon(SpriteInfo weapon) {
		this.weapon = weapon;
	}

	public void setDefaultName() {
		String pattern = "(left|right|up|down)";
		this.defaultName = this.tag.replaceAll(pattern, "");
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public void setTrigger() {
		this.trigger = !this.trigger;
	}

	public void setTrigger(boolean trigger) {
		this.trigger = trigger;
	}
	
	public void setType() {
		String[] categories = {"common", "rare", "epic", "legendary"};
		
		if (this.getDefaultName().matches("(?i)pistol|sword")) {
			this.type = categories[0];
		} else if (this.getDefaultName().matches("(?i)automatic|shotgun")) { 
			this.type = categories[1];
		}
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format("Weapon[%d, %d, %s]", vec.getX(), vec.getY(), this.tag);
	}
}
