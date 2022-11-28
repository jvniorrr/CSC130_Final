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

		// if (cw.isTimeUp()) {
		// 	Main.coinPresent = true;
		// 	cw.resetWatch();
		// }

		Main.doorbell = ""; // reset
		Data.SpriteInfo sprite = Main.spriteRender;
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
			
		// My input handlers
		case 'w':
			lastKey = 'w';
			spriteInfo = "up";
			moveSprite(spriteInfo, 0, -MOVE_PX, key);
			break;
		case 'a':
			lastKey = 'a';
			spriteInfo = "left";
			moveSprite(spriteInfo, -MOVE_PX, 0, key);
			break;
		case 's':
			lastKey = 's';
			spriteInfo = "down";
			moveSprite(spriteInfo, 0, MOVE_PX, key);
			break;
		case 'd':
			lastKey = 'd';
			spriteInfo = "right";
			moveSprite(spriteInfo, MOVE_PX, 0, key);
			break;
		case '$':
			lastKey = '$';

			boolean doorCheck = ((Main.spriteRender.getBoundingBox().getY1()  - Main.doorBoundary.getY2() < 30) && Main.spriteRender.getBoundingBox().getX1() > Main.doorBoundary.getX1() && Main.spriteRender.getBoundingBox().getX1() < Main.doorBoundary.getX2() );
			if (doorCheck && Main.spriteRender.getTag().contains("up")) {
				Main.doorbell = "Next level"; //TODO: diff doorbell text
			}

			boolean coinCheck = ( (Main.spriteRender.getBoundingBox().getCenterHeight() >= Main.coinBoundary.getY1() || Main.coinBoundary.getY1() - Main.spriteRender.getBoundingBox().getCenterHeight() < 50) && (Main.spriteRender.getBoundingBox().getCenterHeight() <= Main.coinBoundary.getY2())  && (Main.coinBoundary.getX1() - Main.spriteRender.getBoundingBox().getX1() < 150));
			if (coinCheck && Main.spriteRender.getTag().contains("right") && Main.cw.isTimeUp()) {
				Main.cointText = "Coin collected"; // TOOD: add the actual coin when on WINDOWS
				Main.coinPresent = false;

				// play sound
				Main.coinSound.playWAV();

				Main.cw.resetWatch();
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