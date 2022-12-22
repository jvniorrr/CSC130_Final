package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.omg.CORBA.ARG_IN;

import java.awt.Color;

import Data.BoundingBox;
import Data.BoundingBoxBit;
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
	private static ArrayList<BoundingBoxBit> bounds = new ArrayList<>();


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

		// Store images into some collection; requirement
		sprites.add(new SpriteInfo(new Vector2D(0, 0), "background"));
		// sprites.add(new SpriteInfo(new Vector2D(1090, 500), "coin"));

		// setup our bounding constraints
		 bounds = setBoundingAreas();
	}

	/*
	 * This is your access to the "game loop" (It is a "callback" method from the
	 * Control class (do NOT modify that class!))
	 */
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you

		// set background
		// ctrl.addSpriteToFrontBuffer(0, 0, "background");
		ctrl.addSpriteToFrontBuffer(0, 0, "newbg");


		for (int i = 0; i < bounds.size(); i++) {
			if (BoundingBoxBit.checkCollision(spriteRender.getBoundingBox(), bounds.get(i))) {
				spriteRender.bounceBack();
			}
		}
		ctrl.addSpriteToFrontBuffer(spriteRender.getCoords().getX(), spriteRender.getCoords().getY(), spriteRender.getTag());


		if (timer.isTimeUp()) timer.resetWatch();

		// if (cw.isTimeUp()) {
		// 	Main.coinPresent = true;
		// 	coinSound.resetWAV();
		// }
	}

	// Additional Static methods below...(if needed)
	public static ArrayList<BoundingBoxBit> setBoundingAreas() {
		ArrayList<BoundingBoxBit> bounds = new ArrayList<>();

		// SET WINDOW CONSTRAINTS
		BoundingBoxBit top = new BoundingBoxBit(true, 0, 1280, 0, 0);
		bounds.add(top);
		BoundingBoxBit bottom = new BoundingBoxBit(true, 0, 1280, 715, 720);
		bounds.add(bottom);
		BoundingBoxBit left = new BoundingBoxBit(true, 0, 0, 0, 720);
		bounds.add(left);
		BoundingBoxBit right = new BoundingBoxBit(true, 1280, 1280, 0, 720);
		bounds.add(right);

		BoundingBoxBit orangeHouse = new BoundingBoxBit(true, 175, 225, 40, 75); // orange house
		bounds.add(orangeHouse);

		BoundingBoxBit left_of_orange_house = new BoundingBoxBit(true, 125, 175, 0, 65); // left_of_orange_house
		bounds.add(left_of_orange_house);

		BoundingBoxBit whiteHouse = new BoundingBoxBit(true, 335, 400, 75, 120); // white house
		bounds.add(whiteHouse);

		BoundingBoxBit left_bushes = new BoundingBoxBit(true, 90, 90, 0, 375);
		bounds.add(left_bushes);

		BoundingBoxBit left_of_treasure = new BoundingBoxBit(true, 120, 205, 120, 325); // left_of_treasure
		bounds.add(left_of_treasure);

		return bounds;
	}
	

}
