package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.awt.Color;

import Data.BoundingBox;
import Data.Vector2D;
import Data.Wave;
import Data.SpriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main {
	// Fields (Static) below...
	public static Color c = new Color(0, 6, 43);
	public static Color coinColor = new Color(252, 219, 3);
	public static stopWatchX timer = new stopWatchX(300); // set flashing timer to 150 or 1/4 of second

	// collection to hold our animation frames
	public static ArrayList<SpriteInfo> sprites = new ArrayList<>();
	public static int currentSpriteIndex = 0;

	// collection to hold our bounding boxes
	private static ArrayList<BoundingBox> bounds = new ArrayList<>();

	// Map to store character strings
	public static HashMap<String, String> map = new HashMap<>();

	// Variables for rendering img/txt
	public static String trigger = "";
	public static String doorbell = "", cointText = "";
	public static String spriteInfo = "front0";

	// hold our sprite reference
	public static Vector2D spriteCoords = new Vector2D(170, 80);
	public static SpriteInfo spriteRender = new SpriteInfo(spriteCoords, spriteInfo);
	public static BoundingBox doorBoundary, coinBoundary;

	// coin count
	public static stopWatchX cw = new stopWatchX(3000);
	public static boolean coinPresent = true;
	public static Wave coinSound = new Wave("sounds/coinsSound.wav");
	public static int coinsCollected = 0;

	// End Static fields...

	public static void main(String[] args) {
		Control ctrl = new Control(); // Do NOT remove!
		ctrl.gameLoop(); // Do NOT remove!
	}

	/* This is your access to things BEFORE the game loop starts */
	public static void start() {
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite
		// or drawString)

		// TODO: Add boundaries based on window dimensions



		// Store images into some collection; requirement
		sprites.add(new SpriteInfo(new Vector2D(0, 0), "background"));
		sprites.add(new SpriteInfo(new Vector2D(1090, 500), "coin"));
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

		// set background
		// ctrl.addSpriteToFrontBuffer(0, 0, "background");
		ctrl.addSpriteToFrontBuffer(sprites.get(0).getCoords().getX(), sprites.get(0).getCoords().getY(), "newbg");

		// draw coin
		// if coins collected show on screen
		if (coinsCollected > 0) {
			ctrl.drawString(1175, 50, String.format("%d Coin(s)", coinsCollected), coinColor);
		}

		// ctrl.drawString(100, 205, trigger, c);
		// Door bell text
		ctrl.drawString(550, 205, doorbell, c);


		// for (int i = 0; i < bounds.size(); i++) {
		// 	if (checkCollision(spriteRender.getBoundingBox(), bounds.get(i))) {
		// 		spriteRender.bounceBack();
		// 	}
		// }
		ctrl.addSpriteToFrontBuffer(spriteRender.getCoords().getX(), spriteRender.getCoords().getY(), spriteRender.getTag());


		if (timer.isTimeUp()) timer.resetWatch();

		if (cw.isTimeUp()) {
			Main.coinPresent = true;
			coinSound.resetWAV();
		}
	}

	// Additional Static methods below...(if needed)
	public static boolean checkCollision(BoundingBox spriteBox, BoundingBox box2) {
		return !( (spriteBox.getX1() > box2.getX2()) || (spriteBox.getX2() < box2.getX1()) ||(spriteBox.getY1() > box2.getY2()) || (spriteBox.getY2() < box2.getY1()) );
	}

}
