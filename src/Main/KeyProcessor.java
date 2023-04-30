/* This will handle the "Hot Key" system. */

package Main;

import Data.SpriteInfo;
import Data.Weapon;
import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static char lastKey = ' ';
	private static stopWatchX sw = new stopWatchX(250);

	// changing sprite image rendered
	private static int MOVE_PX = 4;
	private static String spriteInfo = "";
	private static int spriteMoveCount = 0;
	private static SpriteInfo character = Main.spriteRender;
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();

		character = Main.spriteRender;
		Weapon weapon = character.getWeapon();
		
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
			
		// My input handlers
		// NORTH
		case 'w':
			lastKey = 'w';
			spriteInfo = "up";
			moveSprite(spriteInfo, 0, -MOVE_PX, key);
			break;
		// WEST
		case 'a':
			lastKey = 'a';
			spriteInfo = "left";
			moveSprite(spriteInfo, -MOVE_PX, 0, key);
			break;
		// SOUTH
		case 's':
			lastKey = 's';
			spriteInfo = "down";
			moveSprite(spriteInfo, 0, MOVE_PX, key);
			break;
		// EAST
		case 'd':
			lastKey = 'd';
			spriteInfo = "right";
			moveSprite(spriteInfo, MOVE_PX, 0, key);
			break;

		// Spacebar trigger
		case '$':
			if (character.isWeaponPresent() && !Main.weaponCooldown.isTimeUp()) {
				weapon.setTrigger(true);
			}

			lastKey = '$';
			break;

		// p trigger; for weapons
		case 'p':
			lastKey = 'p';
			Main.spriteRender.setWeaponPresent(!Main.spriteRender.isWeaponPresent());
			break;

		// TODO: Remove prior to submission; TESTING PURPOSES
		case 'r':
			character.setTag("front0");
			character.getCoords().setX(95);
			character.getCoords().setY(50);
			break;
		case 'b':
			character.setTag("front0");
			character.getCoords().setX(825);
			character.getCoords().setY(230);
			break;
		// TODO: Remove this only for debugging purposes
		case 'n':
			MOVE_PX = (MOVE_PX == 32 ? 4 : 32);
			break;
		case 'm':
			lastKey ='m';
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
		}
	}


	private static void moveSprite(String spriteInfo, int moveCountX, int moveCountY, char key) {
		if (key == lastKey) {
			spriteMoveCount++;
			if (spriteMoveCount >= 4) {
				spriteMoveCount = 0;
			}
			character.setTag(spriteInfo + spriteMoveCount);
		} else {
			spriteMoveCount = 0;
			character.setTag(spriteInfo + spriteMoveCount);
		}
		character.moveSprite(moveCountX, moveCountY);
	}
}