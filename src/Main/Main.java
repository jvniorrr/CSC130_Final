package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.text.Position;

import java.awt.Color;
import Data.BoundingBoxBit;
import Data.PokemonSprite;
import Data.Vector2D;
import Data.Weapon;
import Data.SpriteInfo;
import logic.Control;
import timer.stopWatchX;

public class Main {
	// Fields (Static) below...
	public static Color c = new Color(0, 6, 43);
	public static stopWatchX timer = new stopWatchX(300); // set flashing timer to 150 or 1/4 of second

	// collection to hold our animation frames
	public static ArrayList<SpriteInfo> sprites = new ArrayList<>();

	// collection to hold our bounding boxes
	private static ArrayList<BoundingBoxBit> bounds = new ArrayList<>();

	// Variables for rendering img/txt
	public static String spriteInfo = "front0";

	// hold our sprite reference
	public static Vector2D spriteCoords = new Vector2D(170, 80);
	public static SpriteInfo spriteRender = new SpriteInfo(spriteCoords, spriteInfo);

	public static Weapon weapon = new Weapon(spriteCoords, "downpistol");
	// FIXME: change this to a linked list or similar preferably. memory issues
	// could be present
	public static ArrayList<SpriteInfo> weaponActions = new ArrayList<>();
	public static HashMap<Integer, HashMap<String, Object>> weaponActionsMap = new HashMap<>(); // {"0": { "left":
																								// "Vector2D[x, y"}, }
	public static stopWatchX weaponCooldown = new stopWatchX(250);
	public static final int DEFAULT_ACTION_MOVEMENT = 2;

	public static Vector2D pokemonVector = new Vector2D(93, 320);
	public static PokemonSprite pokemon = new PokemonSprite(true, pokemonVector, "pokemon");
	public static stopWatchX pokemonStopWatch = new stopWatchX(300);

	public static ArrayList<SpriteInfo> characters = new ArrayList<>();
	// End Static fields...

	public static void main(String[] args) {
		Control ctrl = new Control(); // Do NOT remove!
		ctrl.gameLoop(); // Do NOT remove!
	}

	/* This is your access to things BEFORE the game loop starts */
	public static void start() {
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite
		// or drawString)
		// set new box for pokemon
		// pokemon.getSprite().getBoundingBox().setX2(pokemon.getSprite().getBoundingBox().getX2() + 32);
		// pokemon.getSprite().getBoundingBox().setY2(pokemon.getSprite().getBoundingBox().getY2() + 32);

		// Store images into some collection; requirement
		sprites.add(new SpriteInfo(new Vector2D(0, 0), "background"));

		// setup our bounding constraints
		bounds = setBoundingAreas();

		// setup our characters in array list
		spriteRender.setWeapon(weapon);

		characters.add(spriteRender);
		characters.add(pokemon.getSprite());
	}

	/*
	 * This is your access to the "game loop" (It is a "callback" method from the
	 * Control class (do NOT modify that class!))
	 */
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you

		// set background
		ctrl.addSpriteToFrontBuffer(0, 0, "newbg");

		// draw our characters and assure they are not colliding with boundaries or
		// other objects
		for (int i = 0; i < characters.size(); i++) {
			for (int j = 0; j < bounds.size(); j++) {
				if (BoundingBoxBit.checkCollision(characters.get(i).getBoundingBox(), bounds.get(j))) {
					characters.get(i).bounceBack();
				}
			}



			// assure our characters dont collide
			if (characters.size() > 1) {
				for (int j = i + 1; j < characters.size(); j++) {
					if (BoundingBoxBit.checkCollision(characters.get(i).getBoundingBox(),
							characters.get(j).getBoundingBox())) {
						characters.get(i).bounceBack();
					}
				}
			}

		}

		// draw weapon actions; ie bullets.
		for (int i = 0; i < weaponActions.size(); i++) {
			// retrieve inner map
			HashMap<String, Object> currInnerMap = weaponActionsMap.get(i);

			// retrieve vector
			Vector2D actionPosition = new Vector2D(0, 0); // default value

			// grab the bullets position by reference & direction they should be going
			String direction = "right";
			// MOVE CHARACTER LEFT / WEST
			if (currInnerMap.get("left") != null) {
				direction = "left";
				// adjust the position of the vector
				actionPosition = (Vector2D) currInnerMap.get(direction);
				actionPosition.adjustX(-DEFAULT_ACTION_MOVEMENT);
			}
			// MOVE CHARACTER RIGHT / EAST
			else if (currInnerMap.get("right") != null) {
				direction = "right";
				// adjust the position of the vector
				actionPosition = (Vector2D) currInnerMap.get(direction);
				actionPosition.adjustX(DEFAULT_ACTION_MOVEMENT);
			}
			// MOVE CHARACTER UP / NORTH
			else if (currInnerMap.get("up") != null) {
				direction = "up";
				// adjust the position of the vector
				actionPosition = ((Vector2D) currInnerMap.get(direction));
				actionPosition.adjustY(-DEFAULT_ACTION_MOVEMENT);
			} 
			// MOVE CHARACTER DOWN / SOUTH
			else if (currInnerMap.get("down") != null) {
				direction = "down";
				// adjust the position of the vector
				actionPosition =  ((Vector2D) currInnerMap.get(direction));
				actionPosition.adjustY(DEFAULT_ACTION_MOVEMENT);
			}

			// check every object in our characters assure isnt colliding
			for (int j=1; j<characters.size(); j++) {
				// variable to check if collides with any character
				boolean collides = false;
				// assure we havent already drawn the hit sprite one time prior
				// 0 means we have not displayed hit any times so show action instead
				int HIT_DISPLAYS = 8;
				if (Integer.parseInt(currInnerMap.get("displayed").toString()) < HIT_DISPLAYS) {
					// create tmp bounding box to verify
					BoundingBoxBit actionBoundingBox = new BoundingBoxBit(true, actionPosition.getX(), actionPosition.getX() + 24, actionPosition.getY(), actionPosition.getY() + 24);
					collides = BoundingBoxBit.checkCollision(characters.get(j).getBoundingBox(), actionBoundingBox); // check collision
					// check if collision below
					if (collides) {
						// retrieve vector value
						String tmpTag = "hit";
						if (direction.equalsIgnoreCase("right")) tmpTag = "righthit"; // EAST
						else if (direction.equalsIgnoreCase("left")) tmpTag = "lefthit"; // WEST
						else if (direction.equalsIgnoreCase("up")) tmpTag = "uphit"; // NORTH
						else if (direction.equalsIgnoreCase("down")) tmpTag = "downhit"; // SOUTH
						ctrl.addSpriteToFrontBuffer(weaponActions.get(i).getCoords().getX(), weaponActions.get(i).getCoords().getY(), tmpTag); // draw hit sprite 

						currInnerMap.put("displayed", Integer.parseInt(currInnerMap.get("displayed").toString()) + 1); // next time round it will draw nothing

						if (Integer.parseInt(currInnerMap.get("displayed").toString()) >= HIT_DISPLAYS) {

							// remove from array
							weaponActions.remove(i);
						}
					} else if (Integer.parseInt(currInnerMap.get("displayed").toString()) < 1) {
						// check if it hasnt been drawn and hasnt past a sprite image
						ctrl.addSpriteToFrontBuffer(weaponActions.get(i).getCoords().getX(),
								weaponActions.get(i).getCoords().getY(), weaponActions.get(i).getTag());
					}
				}
			}
		}

		// draw the weapon under char if facing up or down
		if (spriteRender.isWeaponPresent() && spriteRender.getTag().contains("up")) {
			weapon.setTag("uppistol");
			ctrl.addSpriteToFrontBuffer(weapon.getCoords().getX(), weapon.getCoords().getY(), weapon.getTag());
		}
		// draw character
		ctrl.addSpriteToFrontBuffer(spriteRender.getCoords().getX(), spriteRender.getCoords().getY(),
				spriteRender.getTag());

		// draw pokemons
		ctrl.addSpriteToFrontBuffer(pokemon.getSprite().getCoords().getX(), pokemon.getSprite().getCoords().getY(),
				pokemon.getSprite().getTag());

		// draw the weapon under if they are facing up / NORTH
		if (spriteRender.isWeaponPresent() && !(spriteRender.getTag().contains("up"))) {

			// check positioning of character
			if (spriteRender.getTag().contains("left")) {
				weapon.setTag("leftpistol");
			} else if (spriteRender.getTag().contains("up")) {
				weapon.setTag("uppistol");
			} else if (spriteRender.getTag().contains("down")) {
				weapon.setTag("downpistol2");
			} else {
				weapon.setTag("rightpistol");
			}
			// draw the weapon in front of the
			ctrl.addSpriteToFrontBuffer(weapon.getCoords().getX(), weapon.getCoords().getY(), weapon.getTag());
		}

		// reset the draw
		if (timer.isTimeUp())
			timer.resetWatch();

		// timewatch is for the movement of our pokemon
		if (pokemonStopWatch.isTimeUp()) {
			pokemon.pokemonMovement();
			pokemonStopWatch.resetWatch();
		}

		if (weaponCooldown.isTimeUp()) {
			// check if the user pressed p and they are holding the weapon
			if (spriteRender.isWeaponPresent() && weapon.isTrigger()) {
				weaponTrigger(ctrl);
			}

			weaponCooldown.resetWatch();
		}
	}

	// Additional Static methods below...(if needed)

	/*
	 * Method to set the bounds / constraints of map and paths
	 */
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

		// ORANGE HOUSE CONSTRAINTS
		BoundingBoxBit orangeHouse = new BoundingBoxBit(true, 175, 225, 40, 75); // orange house
		bounds.add(orangeHouse);
		BoundingBoxBit left_of_orange_house = new BoundingBoxBit(true, 125, 175, 0, 65); // left_of_orange_house
		bounds.add(left_of_orange_house);
		BoundingBoxBit right_of_orange_house = new BoundingBoxBit(true, 225, 275, 0, 65); // right_of_orange_house
		bounds.add(right_of_orange_house);

		// WHITE HOUSE CONSTRAINTS
		BoundingBoxBit whiteHouse = new BoundingBoxBit(true, 335, 400, 75, 120); // white house
		bounds.add(whiteHouse);
		BoundingBoxBit left_of_white_house = new BoundingBoxBit(true, 280, 335, 75, 120); // left_of_white_house
		bounds.add(left_of_white_house);
		BoundingBoxBit right_of_white_house = new BoundingBoxBit(true, 390, 390, 135, 170); // right_of_white_house
		bounds.add(right_of_white_house);
		BoundingBoxBit path_under_white_house = new BoundingBoxBit(true, 225, 390, 168, 168);
		bounds.add(path_under_white_house);

		// BROWN HOUSE CONSTRAINTS
		BoundingBoxBit top_brown_house = new BoundingBoxBit(true, 115, 140, 360, 475); // top of brown house path bounds
		bounds.add(top_brown_house);
		BoundingBoxBit top_left_brown_house = new BoundingBoxBit(true, 90, 110, 375, 495); // top of brown house, left
																							// side
		bounds.add(top_left_brown_house);
		BoundingBoxBit top_right_brown_house = new BoundingBoxBit(true, 145, 320, 375, 495); // top of brown house,
																								// right side to bridge
		bounds.add(top_right_brown_house);

		// BUILDING CONSTRAINTS
		BoundingBoxBit buildings = new BoundingBoxBit(true, 842, 1280, 385, 512); // buildings and grey sidewalk (below)
																					// constraints
		bounds.add(buildings);
		BoundingBoxBit bottom_buildings_path = new BoundingBoxBit(true, 842, 1175, 552, 720); // below buildings;
																								// tropical portion
		bounds.add(bottom_buildings_path);
		BoundingBoxBit building_path_right = new BoundingBoxBit(true, 1175, 1175, 517, 558);
		bounds.add(building_path_right);

		// TREASURE CONSTRANITS
		BoundingBoxBit top_treasure = new BoundingBoxBit(true, 125, 230, 120, 245); // top of treasure; assure path is
																					// contstraining
		bounds.add(top_treasure);
		BoundingBoxBit left_of_treasure = new BoundingBoxBit(true, 120, 205, 120, 320); // left_of_treasure
		bounds.add(left_of_treasure);
		BoundingBoxBit right_of_treasure = new BoundingBoxBit(true, 230, 315, 168, 320); // right_of_treasure
		bounds.add(right_of_treasure);

		// LIGHTER BRIDGE CONSTRAINTS
		BoundingBoxBit top_of_bridge = new BoundingBoxBit(true, 320, 478, 320, 320); // lighter bridge
		bounds.add(top_of_bridge);
		BoundingBoxBit bottom_of_bridge = new BoundingBoxBit(true, 320, 478, 383, 495); // lighter bridge
		bounds.add(bottom_of_bridge);

		// FOREST OR TREES CONSTRAINTS
		BoundingBoxBit forest = new BoundingBoxBit(true, 478, 787, 0, 320); // forest area
		bounds.add(forest);

		// GARDEN AREA CONSTRAINTS
		BoundingBoxBit top_of_garden = new BoundingBoxBit(true, 480, 787, 375, 495); // garden area
		bounds.add(top_of_garden);

		// MISC CONSTRAINTS
		BoundingBoxBit top_grey_sidewalk = new BoundingBoxBit(true, 842, 1280, 100, 310); // above the grey sidewalk
																							// area
		bounds.add(top_grey_sidewalk);
		BoundingBoxBit left_bushes = new BoundingBoxBit(true, 90, 90, 0, 375); // left side bushes
		bounds.add(left_bushes);
		BoundingBoxBit grave_path = new BoundingBoxBit(true, 0, 790, 551, 720); // grave_path
		bounds.add(grave_path);
		BoundingBoxBit top_of_stairs = new BoundingBoxBit(true, 787, 1280, 50, 50); // right of forest area, above
																					// stairs
		bounds.add(top_of_stairs);
		// apple trees constraints; this is TBD as may add more to game
		BoundingBoxBit apple_trees = new BoundingBoxBit(true, 900, 1280, 318, 385); // above trees, block off for now
		bounds.add(apple_trees);

		return bounds;
	}

	/*
	 * Method that is triggered when the user presses 'p' character
	 */
	public static void weaponTrigger(Control ctrl) {
		// get weapon and create new sprite for it
		Weapon weapon = spriteRender.getWeapon();
		SpriteInfo weaponAction = new SpriteInfo(new Vector2D(weapon.getCoords().getX(), weapon.getCoords().getY()),
				"rightbullet");

		// set the vector inside our map
		HashMap<String, Object> innerMap = new HashMap<>();

		// check the direction of character to set the action direction
		if (spriteRender.getTag().contains("left")) {
			weaponAction.setTag("leftbullet");
			weaponAction.getCoords().adjustX(-DEFAULT_ACTION_MOVEMENT);
			innerMap.put("left", weaponAction.getCoords());
		} else if (spriteRender.getTag().contains("right")) {
			weaponAction.setTag("rightbullet");
			weaponAction.getCoords().adjustX(DEFAULT_ACTION_MOVEMENT);
			innerMap.put("right", weaponAction.getCoords());
		} else if (spriteRender.getTag().contains("up")) {
			weaponAction.setTag("upbullet");
			weaponAction.getCoords().adjustY(DEFAULT_ACTION_MOVEMENT);
			innerMap.put("up", weaponAction.getCoords());
		} else if (spriteRender.getTag().contains("down")) {
			weaponAction.setTag("downbullet");
			weaponAction.getCoords().adjustY(-DEFAULT_ACTION_MOVEMENT);
			innerMap.put("down", weaponAction.getCoords());
		}

		innerMap.put("displayed", 0);
		// System.out.println(innerMap);

		// add the weapons action to an array to be populated and referenced in update()
		weaponActionsMap.put(weaponActions.size(), innerMap);
		weaponActions.add(weaponAction);

		// reset the trigger
		weapon.setTrigger(false);

	}


}
