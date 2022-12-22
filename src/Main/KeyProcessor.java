/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static char lastKey = ' ';
	private static stopWatchX sw = new stopWatchX(250);
	// private static stopWatchX cw = new stopWatchX(1000);

	// changing sprite image rendered
	private static int MOVE_PX = 4;
	private static String spriteInfo = "";
	private static int spriteMoveCount = 0;
	private static Data.SpriteInfo spriteObject = Main.spriteRender;
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();

		// if (cw.isTimeUp()) {
		// 	Main.coinPresent = true;
		// 	cw.resetWatch();
		// }

		Main.doorbell = ""; // reset
		spriteObject = Main.spriteRender;
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
			
		// My input handlers
		// Move North
		case 'w':
			lastKey = 'w';
			spriteInfo = "up";
			moveSprite(spriteInfo, 0, -MOVE_PX, key);
			break;
		// Move West
		case 'a':
			lastKey = 'a';
			spriteInfo = "left";
			moveSprite(spriteInfo, -MOVE_PX, 0, key);
			break;
		// Move South
		case 's':
			lastKey = 's';
			spriteInfo = "down";
			moveSprite(spriteInfo, 0, MOVE_PX, key);
			break;
		// Move East
		case 'd':
			lastKey = 'd';
			spriteInfo = "right";
			moveSprite(spriteInfo, MOVE_PX, 0, key);
			break;

		// Spacebar trigger
		case '$':
			lastKey = '$';
			break;

		// TODO: Remove prior to submission; TESTING PURPOSES
		case 'r':
			spriteObject.setTag("front0");
			spriteObject.getCoords().setX(80);
			spriteObject.getCoords().setY(50);
			break;
		case 'b':
			spriteObject.setTag("front0");
			spriteObject.getCoords().setX(825);
			spriteObject.getCoords().setY(230);
			break;
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
			spriteObject.setTag(spriteInfo + spriteMoveCount);
		} else {
			spriteMoveCount = 0;
			spriteObject.setTag(spriteInfo + spriteMoveCount);
		}
		spriteObject.moveSprite(moveCountX, moveCountY);
	}
}