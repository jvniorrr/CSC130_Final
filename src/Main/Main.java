package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Data.Vector2D;
import Data.spriteInfo;
import logic.Control;
import timer.stopWatchX;

public class Main {
	// Fields (Static) below...
	public static stopWatchX timer = new stopWatchX(150); // set flashing timer to 150 or 1/4 of second


	// collection to hold our animation frames
	public static ArrayList<spriteInfo> sprites = new ArrayList<>();
	public static int currentSpriteIndex = 0;

	// End Static fields...

	public static void main(String[] args) {
		Control ctrl = new Control(); // Do NOT remove!
		ctrl.gameLoop(); // Do NOT remove!
	}

	/* This is your access to things BEFORE the game loop starts */
	public static void start() {
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite
		// or drawString)
		int counterRef = 0;
		for (int i = -128; i < 1280 + 128; i += 32) {

			spriteInfo tempSprite = new spriteInfo(new Vector2D(i, 0), null);

			tempSprite.setTag(counterRef == 0 ? "walk0" : (counterRef == 1 ? "walk1" : (counterRef == 2 ? "walk2" : "walk3")));
			counterRef++;
			sprites.add(tempSprite);

			if (counterRef > 2) 
				counterRef = 0;
			// if (counterRef == 0) {
			// 	sprites.add(new spriteInfo(new Vector2D(i, 0), "walk0"));
			// 	counterRef++;
			// } else if (counterRef == 1) {
			// 	sprites.add(new spriteInfo(new Vector2D(i, 0), "walk1"));
			// 	counterRef++;
			// } else if (counterRef == 2) {
			// 	sprites.add(new spriteInfo(new Vector2D(i, 0), "walk2"));
			// 	counterRef++;
			// } else {
			// 	sprites.add(new spriteInfo(new Vector2D(i, 0), "walk3"));
			// 	counterRef = 0;
			// }
		}
	}

	/*
	 * This is your access to the "game loop" (It is a "callback" method from the
	 * Control class (do NOT modify that class!))
	 */
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you
		// how it works)

		spriteInfo currSprite = sprites.get(currentSpriteIndex);
		ctrl.addSpriteToFrontBuffer(currSprite.getCoords().getX(), currSprite.getCoords().getY(), currSprite.getTag()); // Add a tester sprite to render list by tag (Remove later! Test only!)

		if (timer.isTimeUp()) {

			if (currentSpriteIndex == (sprites.size() - 1) ) {
				currentSpriteIndex = 0;
			} else {
				currentSpriteIndex++;
			}

			// reset timer obj for next pass
			timer.resetWatch();
		}

	}

	// Additional Static methods below...(if needed)

}
