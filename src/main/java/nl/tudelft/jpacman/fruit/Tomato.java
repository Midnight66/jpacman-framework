package nl.tudelft.jpacman.fruit;

import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A Tomato is a vegetable that make pacman temporarily invincible when he eat it.
 */
public class Tomato extends Fruit{

	/**
	 * Create a Tomato object
	 * @param sprite the sprite of this Tomato
	 * @param lifetime the time for which this Tomato will remain on the board
	 * @param effectDuration the time for which the power of this Tomato is active.
	 */
	protected Tomato(Sprite sprite, int lifetime, int effectDuration) {
		super(sprite, lifetime, effectDuration);
	}

	/**
	 * Activate the effect
	 * @param p the player that ate this fruit.
     */
	@Override
	public void fruitEffect(Player p) {
		p.setInvincible(true);
		p.setSprites(new PacManSprites().getPacmanInvisibleSprite());
		TimerTask timerTask = new TimerTask() {
		    public void run() {
		        p.setInvincible(false);
		        p.setSprites(new PacManSprites().getPacmanSprites());
		    }
		};
		Timer timer = new Timer();
		timer.schedule(timerTask, getEffectDuration() * 1000);
	}
}
