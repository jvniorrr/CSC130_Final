package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Color;

import Data.BoundingBox;
import Data.Vector2D;
import Data.SpriteInfo;
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
	public static Vector2D spriteCoords = new Vector2D(80, 50);
	// public static Vector2D spriteCoords = new Vector2D(230, 135);
	public static SpriteInfo spriteRender = new SpriteInfo(spriteCoords, spriteInfo);
	public static BoundingBox doorBoundary, coinBoundary;

	// coin count
	public static stopWatchX cw = new stopWatchX(3000);
	public static boolean coinPresent = true;
	public static int coinsCollected = 0;

	public static stopWatchX doorbellWatch = new stopWatchX(2500);
	// End Static fields...

	public static void main(String[] args) {
		Control ctrl = new Control(); // Do NOT remove!
		ctrl.gameLoop(); // Do NOT remove!
	}

	/* This is your access to things BEFORE the game loop starts */
	public static void start() {
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite
		// or drawString)

		// Bounds for game; window dimensions
		bounds.add(new BoundingBox(new Vector2D(-128, -128), 1400, 160)); // TOP Boundary
		bounds.add(new BoundingBox(new Vector2D(-128, 700), 1400, 100)); // BOTTOM Boundary
		bounds.add(new BoundingBox(new Vector2D(-128, -128), 170, 800)); // LEFT Boundary
		bounds.add(new BoundingBox(new Vector2D(1260, -128), 150, 800)); // RIGHT Boundary

		// Path boundaries ; 
		bounds.add(new BoundingBox(new Vector2D(235, 0), 575, 225)); // above middle row path, tree and left side of house boundaries
		bounds.add(new BoundingBox(new Vector2D(235, 460), 685, 400)); // below middle row path boundaries

		bounds.add(new BoundingBox(new Vector2D(955, 230), 400, 0)); // right side bush area, and right side of house boundaries
		bounds.add(new BoundingBox(new Vector2D(1100, 465), 300, 40)); // little garden area (btm right corner)
		// boundary for tree objects
		// bounds.add(new BoundingBox(new Vector2D(230, 125), 100, 105)); // beautiful tree boundaries
		bounds.add(new BoundingBox(new Vector2D(280, 555), 100, 105)); // stick tree boundaries
		// house boundaries
		doorBoundary = new BoundingBox(new Vector2D(740, 110), 100, 0);
		bounds.add(doorBoundary);
		coinBoundary = new BoundingBox(new Vector2D(1090, 480), 5, 30);
		bounds.add(coinBoundary);


		// Store images into some collection; requirement
		sprites.add(new SpriteInfo(new Vector2D(0, 0), "background"));
		sprites.add(new SpriteInfo(new Vector2D(1090, 500), "coin"));
		sprites.add(new SpriteInfo(new Vector2D(560, 0), "house"));
		sprites.add(spriteRender);

	}

	/*
	 * This is your access to the "game loop" (It is a "callback" method from the
	 * Control class (do NOT modify that class!))
	 */
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you
		// set background
		ctrl.addSpriteToFrontBuffer(sprites.get(0).getCoords().getX(), sprites.get(0).getCoords().getY(), sprites.get(0).getTag());

		// draw coin
		if (coinPresent) {
			// ctrl.addSpriteToFrontBuffer(1090, 500, "coin");
			ctrl.addSpriteToFrontBuffer(sprites.get(1).getCoords().getX(), sprites.get(1).getCoords().getY(), sprites.get(1).getTag());
		} else {
			ctrl.drawString(1090, 500, cointText, c);
		}
		// if coins collected show on screen
		if (coinsCollected > 0) {
			ctrl.drawString(1175, 50, String.format("%d Coin(s)", coinsCollected), coinColor);
		}

		// ctrl.drawString(100, 205, trigger, c);
		ctrl.drawString(550, 205, doorbell, c);


		for (int i = 0; i < bounds.size(); i++) {
			if (checkCollision(sprites.get(3).getBoundingBox(), bounds.get(i))) {
				sprites.get(3).bounceBack();
			}
		}
		// draw interactable house obj
		ctrl.addSpriteToFrontBuffer(sprites.get(2).getCoords().getX(), sprites.get(2).getCoords().getY(), sprites.get(2).getTag());

		ctrl.addSpriteToFrontBuffer(sprites.get(3).getCoords().getX(), sprites.get(3).getCoords().getY(), sprites.get(3).getTag());


		if (timer.isTimeUp()) timer.resetWatch();

		if (cw.isTimeUp()) {
			Main.coinPresent = true;
		}
	}

	// Additional Static methods below...(if needed)
	public static boolean checkCollision(BoundingBox spriteBox, BoundingBox box2) {
		return !( (spriteBox.getX1() > box2.getX2()) || (spriteBox.getX2() < box2.getX1()) ||(spriteBox.getY1() > box2.getY2()) || (spriteBox.getY2() < box2.getY1()) );
	}

}
