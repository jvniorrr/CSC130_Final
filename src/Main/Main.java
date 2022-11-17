package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.awt.Color;

import Data.BoundingBox;
import Data.Vector2D;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main {
	// Fields (Static) below...
	public static Color c = new Color(0, 148, 156);
	public static stopWatchX timer = new stopWatchX(300); // set flashing timer to 150 or 1/4 of second


	// collection to hold our animation frames
	public static ArrayList<spriteInfo> sprites = new ArrayList<>();
	public static int currentSpriteIndex = 0;

	// collection to hold our bounding boxes
	private static ArrayList<BoundingBox> bounds = new ArrayList<>();

	// Map to store character strings
	public static HashMap<String, String> map = new HashMap<>();

	// Variables for rendering img/txt
	public static String trigger = "";
	public static String spriteInfo = "front0";

	// hold our sprite reference
	public static Vector2D spriteCoords = new Vector2D(0, 0);
	public static spriteInfo spriteRender = new spriteInfo(spriteCoords, spriteInfo);

	// reference to move character back; maybe set this in spriteInfo Fields
	public static Vector2D oldCoords = new Vector2D(0, 0);


	// End Static fields...

	public static void main(String[] args) {
		Control ctrl = new Control(); // Do NOT remove!
		ctrl.gameLoop(); // Do NOT remove!
	}

	/* This is your access to things BEFORE the game loop starts */
	public static void start() {
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite
		// or drawString)

		// add all of our bounds
		bounds.add(new BoundingBox(new Vector2D(0, -128), 1400, 0)); // TOP Boundary
		bounds.add(new BoundingBox(new Vector2D(-128, 0), 0, 830)); // LEFT Boundary



		sprites.add(spriteRender);

		
		// read file; txt for our character
		EZFileRead ezr = new EZFileRead("Arthur.txt");
		String st;
		while (!(st = ezr.getNextLine()).equalsIgnoreCase("END OF FILE")) {
			StringTokenizer tokenizer = new StringTokenizer(st, "*");
			map.put(tokenizer.nextToken(), tokenizer.nextToken());
		}
		

	}

	/*
	 * This is your access to the "game loop" (It is a "callback" method from the
	 * Control class (do NOT modify that class!))
	 */
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you
		oldCoords = spriteRender.getCoords();
		ctrl.drawString(100, 205, trigger, c);
		ctrl.addSpriteToFrontBuffer(spriteRender.getCoords().getX(), spriteRender.getCoords().getY(), spriteRender.getTag());


		// for (int i = 0; i < bounds.size(); i++) {
		// 	if (checkCollision(spriteRender.getBoundingBox(), bounds.get(i))) {
		// 		System.out.println("Conflicting boundaries #" + i);
		// 		moveCharacterBack();
		// 	}
		// }

		if (timer.isTimeUp()) timer.resetWatch();



		
		// spriteInfo currSprite = sprites.get(currentSpriteIndex);
		// ctrl.addSpriteToFrontBuffer(130, 50, "front0"); // Add a tester sprite to render list by tag (Remove later! Test only!)
		// System.out.println(spriteInfo);

		// if (timer.isTimeUp()) {

		// 	if (currentSpriteIndex == (sprites.size() - 1) ) {
		// 		currentSpriteIndex = 0;
		// 	} else {
		// 		currentSpriteIndex++;
		// 	}

		// 	// reset timer obj for next pass
		// 	timer.resetWatch();
		// }

	}

	// Additional Static methods below...(if needed)
	public static boolean checkCollision(BoundingBox box1, BoundingBox box2) {
		if ( (box1.getX1() > box2.getX2()) || (box1.getX2() < box2.getX1()) ||(box1.getY1() > box2.getY2()) || (box1.getY2() < box2.getY1()) ) {
			return false;
		}
		return true;
	}

	public static void moveCharacterBack() {
		// spriteRender.setCoords(oldCoords);
		spriteRender.moveSprite(oldCoords.getX(), oldCoords.getY());
	}
}
