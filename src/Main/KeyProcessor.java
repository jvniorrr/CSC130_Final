/* This will handle the "Hot Key" system. */

package Main;

import Graphics.Graphic;
import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static char lastKey = ' ';
	private static stopWatchX sw = new stopWatchX(250);

	// changing sprite image rendered
	private static final int MOVE_PX = 16;
	private static String spriteInfo = "";
	private static int spriteMoveCount = 0;
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
			
		// My input handlers
		case 'w':
			lastKey = 'w';
			spriteInfo = "up";
			Main.trigger = "w has been triggered";
			moveSprite(spriteInfo, 0, -MOVE_PX, key);
			break;
		case 'a':
			lastKey = 'a';
			spriteInfo = "left";
			Main.trigger = "a has been triggered";
			moveSprite(spriteInfo, -MOVE_PX, 0, key);
			break;
		case 's':
			lastKey = 's';
			spriteInfo = "down";
			Main.trigger = "s has been triggered";
			moveSprite(spriteInfo, 0, MOVE_PX, key);
			break;
		case 'd':
			lastKey = 'd';
			spriteInfo = "right";
			Main.trigger = "d has been triggered";
			moveSprite(spriteInfo, MOVE_PX, 0, key);
			break;


		// TODO: Remove prior to submission; TESTING PURPOSES
		case 'r':
			Main.spriteRender.setTag("front0");
			Main.spriteRender.getCoords().setX(0);
			Main.spriteRender.getCoords().setY(0);
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
			Main.spriteRender.setTag(spriteInfo + spriteMoveCount);
		} else {
			spriteMoveCount = 0;
			Main.spriteRender.setTag(spriteInfo + spriteMoveCount);
		}
		Main.spriteRender.moveSprite(moveCountX, moveCountY);
	}
}