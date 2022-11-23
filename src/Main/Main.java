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
	public static Color c = new Color(0, 6, 43);
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
	public static String doorbell = "";
	public static String spriteInfo = "front0";

	// hold our sprite reference
	public static Vector2D spriteCoords = new Vector2D(80, 0);
	// public static Vector2D spriteCoords = new Vector2D(230, 135);
	public static spriteInfo spriteRender = new spriteInfo(spriteCoords, spriteInfo);
	public static BoundingBox doorBoundary;

	// End Static fields...

	public static void main(String[] args) {
		Control ctrl = new Control(); // Do NOT remove!
		ctrl.gameLoop(); // Do NOT remove!
	}

	/* This is your access to things BEFORE the game loop starts */
	public static void start() {
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite
		// or drawString)
		// TODO: Add boundaries based on window dimensions; Mac resolution differs

		// Bounds for game; window dimensions
		bounds.add(new BoundingBox(new Vector2D(-128, -128), 1400, 100)); // TOP Boundary
		bounds.add(new BoundingBox(new Vector2D(-128, 720), 1400, 100)); // BOTTOM Boundary
		bounds.add(new BoundingBox(new Vector2D(-128, -128), 170, 800)); // LEFT Boundary
		bounds.add(new BoundingBox(new Vector2D(1280, -128), 150, 800)); // RIGHT Boundary

		// Path boundaries
		bounds.add(new BoundingBox(new Vector2D(210, 0), 0, 210)); // left path (above middle row path)
		bounds.add(new BoundingBox(new Vector2D(210, 460), 0, 450)); // left path (below middle row path)
		// left to right path
		bounds.add(new BoundingBox(new Vector2D(210, 460), 720, 400)); // middle row (bottom) TODO: figure out why the hieght is not being set accordingly
		bounds.add(new BoundingBox(new Vector2D(210, 230), 580, 0)); // left of left bush
		bounds.add(new BoundingBox(new Vector2D(955, 230), 400, 0)); // right side bush
		bounds.add(new BoundingBox(new Vector2D(1100, 475), 300, 65)); // little garden area (btm right corner)
		

		// boundary for objects
		bounds.add(new BoundingBox(new Vector2D(230, 125), 100, 105)); // beutiful tree
		bounds.add(new BoundingBox(new Vector2D(280, 555), 100, 105)); // stick tree
		// house boundaries
		doorBoundary = new BoundingBox(new Vector2D(740, 110), 100, 0);
		bounds.add(doorBoundary); // house door boundaries



		sprites.add(new spriteInfo(new Vector2D(0, 0), "background"));
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
		ctrl.addSpriteToFrontBuffer(0, 0, "background");
		// oldCoords = spriteRender.getCoords();
		ctrl.drawString(100, 205, trigger, c);
		ctrl.drawString(550, 205, doorbell, c);


		for (int i = 0; i < bounds.size(); i++) {
			if (checkCollision(spriteRender.getBoundingBox(), bounds.get(i))) {
				moveCharacterBack();
			}
		}

		ctrl.addSpriteToFrontBuffer(spriteRender.getCoords().getX(), spriteRender.getCoords().getY(), spriteRender.getTag());


		if (timer.isTimeUp()) timer.resetWatch();

	}

	// Additional Static methods below...(if needed)
	public static boolean checkCollision(BoundingBox box1, BoundingBox box2) {
		return !( (box1.getX1() > box2.getX2()) || (box1.getX2() < box2.getX1()) ||(box1.getY1() > box2.getY2()) || (box1.getY2() < box2.getY1()) );
	}

	public static void moveCharacterBack() {
		spriteRender.bounceBack();
	}
}
