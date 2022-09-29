package Main;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

import Data.Vector2D;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
	public static Color c = new Color(0, 148, 156);
	public static boolean isImageDrawn = false;
	public static stopWatchX timer = new stopWatchX(150); // set flashing timer to 250 or 1/4 of second
	// object to set coordinates of sprites
	public static Vector2D bottomRightVec1 = new Vector2D(1105, 575); // bottom right corner coords. for sprite

	// Queue to hold animation movements
	public static Queue<Vector2D> vecs1 = new LinkedList<Vector2D>();
	public static Queue<Vector2D> vecs2 = new LinkedList<Vector2D>();
	
	// obj to hold current vec in use
	public static Vector2D currentVec = new Vector2D(-100, -100); // position to start off screen intially.
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
		if (vecs1.isEmpty()) {
			for (int i=-128; i<1115+128;i+=16) {
				vecs1.add(new Vector2D(i, 0));
			}
		}
		// isImageDrawn = true;
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)
		// ctrl.drawString(1105, 719, "Fernando Mendoza", c);		// Test drawing text on screen where you want (Remove later! Test only!); CP#1

		
		ctrl.addSpriteToFrontBuffer(currentVec.getX(),currentVec.getY(), "fighter");		// Add a tester sprite to render list by tag (Remove later! Test only!)

		if (timer.isTimeUp()) {
				// update current vec
				currentVec = vecs1.remove();

				// add the removed vector to our vecs 2 so game can reset from left to right
				vecs2.add(currentVec);
				
				if (vecs1.isEmpty()) {
					while (!vecs2.isEmpty()) {
						vecs1.add(vecs2.remove());
					}
				}

				isImageDrawn = !isImageDrawn;
				// reset timer obj for next pass
				timer.resetWatch();
		}
		ctrl.drawString(100, 500, "" + vecs1.size(), Color.red);
		ctrl.drawString(100, 550, "" + vecs2.size(), Color.red);




		/** DEFAULT: 1280 X 720 */
		// ctrl.addSpriteToFrontBuffer(1115, 575, "f0");			// Add a tester sprite to render list by tag (Remove later! Test only!)
		// ctrl.drawString(1105, 719, "Fernando Mendoza", c);		// Test drawing text on screen where you want (Remove later! Test only!)
		
		/** SCREEN TWEAKED FOR MAC:  1280 X 800 */
		/** CHANGE RESOLUTION IN GRAPHICS CLASS; LINE 105~ TO 800 HEIGHT / Y PARAM. */
		/** CHECKPOINT #1 */
		// ctrl.addSpriteToFrontBuffer(1115, 660, "fighter");		// Add a tester sprite to render list by tag (Remove later! Test only!)
		// ctrl.drawString(1105, 799, "Fernando Mendoza", c);		// Test drawing text on screen where you want (Remove later! Test only!)
	}
	
	// Additional Static methods below...(if needed)

}
