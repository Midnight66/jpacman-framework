package nl.tudelft.jpacman.sprite;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.npc.ghost.GhostColor;

/**
 * Sprite Store containing the classic Pac-Man sprites.
 * 
 * @author Jeroen Roosen 
 */
public class PacManSprites extends SpriteStore {

	/**
	 * The sprite files are vertically stacked series for each direction, this
	 * array denotes the order.
	 */
	private static final Direction[] DIRECTIONS = { Direction.NORTH,
			Direction.EAST, Direction.SOUTH, Direction.WEST };

	/**
	 * The image size in pixels.
	 */
	private static final int SPRITE_SIZE = 16;

	/**
	 * The amount of frames in the pacman animation.
	 */
	private static final int PACMAN_ANIMATION_FRAMES = 4;

	/**
	 * The amount of frames in the pacman dying animation.
	 */
	private static final int PACMAN_DEATH_FRAMES = 11;
	
	/**
	 * The amount of frames in the ghost exploading animation.
	 */
	private static final int GHOST_EXPLODE_FRAMES = 5;
	
	/**
	 * The amount of frames in the ghost animation.
	 */
	private static final int GHOST_ANIMATION_FRAMES = 2;
	
	/**
	 * The amount of frames in a multi-direction object that has no animation.
	 */
	private static final int NO_ANIMATION_FRAMES = 1;

	/**
	 * The delay between frames.
	 */
	private static final int ANIMATION_DELAY = 200;

	/**
	 * @return A map of animated Pac-Man sprites for all directions.
	 */
	public Map<Direction, Sprite> getPacmanSprites() {
		return directionSprite("/sprite/pacman.png", PACMAN_ANIMATION_FRAMES);
	}
	
	/**
	 * @return A map of animated angry Pac-Man sprites for all directions.
	 */
	public Map<Direction, Sprite> getPacmanAngrySprite(){
		return directionSprite("/sprite/pacman_angry.png", PACMAN_ANIMATION_FRAMES);
	}
	
	/**
	 * @return A map of animated insisible Pac-Man sprites for all directions.
	 */
	public Map<Direction, Sprite> getPacmanInvisibleSprite(){
		return directionSprite("/sprite/pacman_invisible.png", PACMAN_ANIMATION_FRAMES);
	}
	
	/**
	 * @return A map of animated paralized Pac-Man sprites for all directions.
	 */
	public Map<Direction, Sprite> getPacmanParalizedSprites() {
		return directionSprite("/sprite/pacman_paralized.png", PACMAN_ANIMATION_FRAMES);
	}
	
	/**
	 * @return A map of animated paralized ghost sprites for all directions.
	 */
	public Map<Direction, Sprite> getParalizedGhostSprite() {
		return directionSprite("/sprite/ghost_paralized.png", GHOST_ANIMATION_FRAMES);
	}

	/**
	 * @return A map of animated angry ghost sprites for all directions.
	 */
	public Map<Direction, Sprite> getAngryGhostSprite() {
		return directionSprite("/sprite/ghost_angry.png", GHOST_ANIMATION_FRAMES);
	}


	/**
	 * @return The animation of a dying Pac-Man.
	 */
	public AnimatedSprite getPacManDeathAnimation() {
		String resource = "/sprite/dead.png";

		Sprite baseImage = loadSprite(resource);
		AnimatedSprite animation = createAnimatedSprite(baseImage, PACMAN_DEATH_FRAMES,
				ANIMATION_DELAY, false);
		animation.setAnimating(false);

		return animation;
	}
	
	/**
	 * @return The animation of an exploding Ghost.
	 */
	public AnimatedSprite getGhostExplodeAnimation() {
		String resource = "/sprite/ghost_explode.png";
		
		Sprite baseImage = loadSprite(resource);
		AnimatedSprite animation = createAnimatedSprite(baseImage, GHOST_EXPLODE_FRAMES,
				ANIMATION_DELAY, false);
		animation.setAnimating(false);

		return animation;
	}

	/**
	 * Returns a new map with animations for all directions.
	 * @param resource The resource name of the sprite.
	 * @param frames The number of frames in this sprite.
	 * @return The animated sprite facing the given direction.
	 */
	private Map<Direction, Sprite> directionSprite(String resource, int frames) {
		Map<Direction, Sprite> sprite = new HashMap<>();

		Sprite baseImage = loadSprite(resource);
		for (int i = 0; i < DIRECTIONS.length; i++) {
			Sprite directionSprite = baseImage.split(0, i * SPRITE_SIZE, frames
					* SPRITE_SIZE, SPRITE_SIZE);
			AnimatedSprite animation = createAnimatedSprite(directionSprite,
					frames, ANIMATION_DELAY, true);
			animation.setAnimating(true);
			sprite.put(DIRECTIONS[i], animation);
		}

		return sprite;
	}

	/**
	 * Returns a map of animated ghost sprites for all directions.
	 * 
	 * @param color
	 *            The colour of the ghost.
	 * @return The Sprite for the ghost.
	 */
	public Map<Direction, Sprite> getGhostSprite(GhostColor color) {
		assert color != null;

		String resource = "/sprite/ghost_" + color.name().toLowerCase()
				+ ".png";
		return directionSprite(resource, GHOST_ANIMATION_FRAMES);
	}

	/**
	 * @return The sprite for the wall.
	 */
	public Sprite getWallSprite() {
		return loadSprite("/sprite/wall.png");
	}

	/**
	 * @return The sprite for the ground.
	 */
	public Sprite getGroundSprite() {
		return loadSprite("/sprite/floor.png");
	}

	/**
	 * @return The sprite for the pellet
	 */
	public Sprite getPelletSprite() {
		return loadSprite("/sprite/pellet.png");
	}
	
	/**
	 * @return The sprite for the trap.
	 */
	public Sprite getHoleSprite() {
		return loadSprite("/sprite/trap.png");
	}
	
	/**
	 * @return The sprite for the pomgranate.
	 */
	public Sprite getPomgranateSprite() {
		return loadSprite("/sprite/pomgranate.png");
	}
	
	/**
	 * @return The sprite for the bell pepper.
	 */
	public Sprite getBellPepperSprite() {
		return loadSprite("/sprite/bell_pepper.png");
	}
	
	/**
	 * @return The sprite for the tomato.
	 */
	public Sprite getTomatoSprite() {
		return loadSprite("/sprite/tomato.png");
	}
	
	/**
	 * @return The sprite for the kidney bean.
	 */
	public Sprite getKidneyBeanSprite() {
		return loadSprite("/sprite/kidney_bean.png");
	}
	
	/**
	 * @return The sprite for the potato.
	 */
	public Sprite getPotatoSprite() {
		return loadSprite("/sprite/potato.png");
	}
	
	/**
	 * @return The sprite for the fish.
	 */
	public Sprite getFishSprite() {
		return loadSprite("/sprite/fish.png");
	}
	
	/**
	 * @return The sprite for the bullet.
	 */
	public Sprite getBulletSprite() {
		return loadSprite("/sprite/bullet.png");
	}
	
	/**
	 * @return The sprite for the teleport.
	 */
	public Sprite getTeleportSprite() {
		return loadSprite("/sprite/teleport.png");
	}
	
	/**
	 * @return The sprite for the bridge.
	 */
	public Map<Direction, Sprite> getBridgeSprites() {
		return directionSprite("/sprite/bridge.png", NO_ANIMATION_FRAMES);
	}

	/**
	 * @return The sprite for the super pellet.
	 */
	public Sprite getSuperPelletSprite() {
		return loadSprite("/sprite/superPellet.png");
	}

	/**
	 * Overloads the default sprite loading, ignoring the exception. This class
	 * assumes all sprites are provided, hence the exception will be thrown as a
	 * {@link RuntimeException}.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Sprite loadSprite(String resource) {
		try {
			return super.loadSprite(resource);
		} catch (IOException e) {
			throw new PacmanConfigurationException("Unable to load sprite: " + resource, e);
		}
	}
}
