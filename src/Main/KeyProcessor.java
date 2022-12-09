/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor {
	// Static Fields
	private static char last = ' '; // For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);

	// changing sprite image rendered
	private static final int MOVE_PX = 16;
	private static String spriteInfo = "";
	private static int spriteMoveCount = 0;
	private static Data.SpriteInfo spriteObject = Main.sprites.get(3); // give few test runs unsure if  referencing correctly 

	// Static Method(s)
	public static void processKey(char key) {
		if (key == ' ')
			return;
		// Debounce routine below...
		if (key == last)
			if (sw.isTimeUp() == false)
				return;
		last = key;
		sw.resetWatch();

		Main.doorbell = ""; // reset
		spriteObject = Main.spriteRender;

		/* TODO: You can modify values below here! */
		switch (key) {
			case '%': // ESC key
				System.exit(0);
				break;

			// My input handlers
			case 'w':
				spriteInfo = "up";
				moveSprite(spriteInfo, 0, -MOVE_PX, key);
				break;
			case 'a':
				spriteInfo = "left";
				moveSprite(spriteInfo, -MOVE_PX, 0, key);
				break;
			case 's':
				spriteInfo = "down";
				moveSprite(spriteInfo, 0, MOVE_PX, key);
				break;
			case 'd':
				spriteInfo = "right";
				moveSprite(spriteInfo, MOVE_PX, 0, key);
				break;
			case '$':
				boolean doorCheck = ((spriteObject.getBoundingBox().getY1() - Main.doorBoundary.getY2() < 30)
						&& spriteObject.getBoundingBox().getX1() > Main.doorBoundary.getX1()
						&& spriteObject.getBoundingBox().getX1() < Main.doorBoundary.getX2());
				if (doorCheck && spriteObject.getTag().contains("up") && Main.doorbellWatch.isTimeUp()) {
					Main.doorbell = "Ding Dong";
					Main.doorbellWatch.resetWatch();
				}

				boolean coinCheck = ((spriteObject.getBoundingBox().getCenterHeight() >= Main.coinBoundary.getY1()
						|| Main.coinBoundary.getY1() - spriteObject.getBoundingBox().getCenterHeight() < 50)
						&& (spriteObject.getBoundingBox().getCenterHeight() <= Main.coinBoundary.getY2())
						&& (Main.coinBoundary.getX1() - spriteObject.getBoundingBox().getX1() < 150));
				if (coinCheck && spriteObject.getTag().contains("right") && Main.cw.isTimeUp()) {
					Main.cointText = "Coin collected";
					Main.coinPresent = false;
					Main.coinsCollected++;

					// play sound
					Main.cw.resetWatch();
				}
				break;
			case 'm':
				// For mouse coordinates
				Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
				break;
		}
	}

	private static void moveSprite(String spriteInfo, int moveCountX, int moveCountY, char key) {
		if (key == last) {
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