/* This will handle the "Hot Key" system. */

package Main;

import Data.BoundingBox;
import Graphics.Graphic;
import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static char lastKey = ' ';
	private static stopWatchX sw = new stopWatchX(250);

	// changing sprite image rendered
	private static final int MOVE_PX = 32;
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

		Main.doorbell = ""; // reset
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
			
		// My input handlers
		case 'w':
			lastKey = 'w';
			spriteInfo = "up";
			// Main.trigger = "w has been triggered";
			moveSprite(spriteInfo, 0, -MOVE_PX, key);
			break;
		case 'a':
			lastKey = 'a';
			spriteInfo = "left";
			// Main.trigger = "a has been triggered";
			moveSprite(spriteInfo, -MOVE_PX, 0, key);
			break;
		case 's':
			lastKey = 's';
			spriteInfo = "down";
			// Main.trigger = "s has been triggered";
			moveSprite(spriteInfo, 0, MOVE_PX, key);
			break;
		case 'd':
			lastKey = 'd';
			spriteInfo = "right";
			// Main.trigger = "d has been triggered";
			moveSprite(spriteInfo, MOVE_PX, 0, key);
			break;
		case '$':
			lastKey = '$';
			// (box1.getX1() > box2.getX2()) || (box1.getX2() < box2.getX1()) ||(box1.getY1() > box2.getY2()) || (box1.getY2() < box2.getY1())
			// boolean nearby = !( (Main.spriteRender.getBoundingBox().getX1() >= Main.doorBoundary.getX2()) || (Main.spriteRender.getBoundingBox().getX2() <= Main.doorBoundary.getX1()) || (Main.spriteRender.getBoundingBox().getY1() >= Main.doorBoundary.getY2()) || (Main.spriteRender.getBoundingBox().getY2() <= Main.doorBoundary.getY1()));
			boolean doorCheck = ((Main.doorBoundary.getY2() - Main.spriteRender.getBoundingBox().getY1() > -20) && Main.spriteRender.getBoundingBox().getX1() > Main.doorBoundary.getX1() && Main.spriteRender.getBoundingBox().getX1() < Main.doorBoundary.getX2() );
			if ( doorCheck && Main.spriteRender.getTag().contains("up")) {
				System.out.println("some text");
				Main.doorbell = "Next level";
			} else {
				Main.doorbell = "get closer";
			}
			break;

		// TODO: Remove prior to submission; TESTING PURPOSES
		case 'r':
			Main.spriteRender.setTag("front0");
			Main.spriteRender.getCoords().setX(100);
			Main.spriteRender.getCoords().setY(0);
			break;
		case 'b':
			Main.spriteRender.setTag("front0");
			// Main.spriteRender.getCoords().setX(1095);
			Main.spriteRender.getCoords().setX(825);
			// Main.spriteRender.getCoords().setY(580);
			Main.spriteRender.getCoords().setY(230);
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