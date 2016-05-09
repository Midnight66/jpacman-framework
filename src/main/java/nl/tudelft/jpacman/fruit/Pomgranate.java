package nl.tudelft.jpacman.fruit;

import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * The Pomgranate is a fruit that when eaten by Pac-Man instantly kill ghosts
 * that are at a distance of four square away from it.
 */
public class Pomgranate extends Fruit {
	
	/**
	 *  The level where this fruit was created.
	 */
	private final Level level;

	private Ghost g;
	
	/**
	 * Create a Pomgranate object
	 * @param sprite the sprite of this fruit
	 * @param lifetime the time for which this fruit will remain on the board
	 * @param effectDuration the time for which the power of this fruit is active.
	 * @param l the level where this fruit was created.
	 */
	protected Pomgranate(Sprite sprite, int lifetime, int effectDuration, Level l) {
		super(sprite, lifetime, effectDuration);
		level = l;
	}

	/**
	 * Activate the effect
	 * @param p the player that ate this fruit.
     */
	@Override
	public void fruitEffect(Player p) {
		Timer timer;
		TimerTask timerTask;
		TimerTask timerTask2;
		Board board = level.getBoard();
		int X = p.getSquare().getCoordX();
		int Y = p.getSquare().getCoordY();
		for(int x=Math.max(0,X-4); x<Math.min(X+4,board.getWidth()-1); x++){
			for(int y=Math.max(0,Y-4); y<Math.min(Y+4,board.getHeight()-1); y++){
				for(Unit u : board.squareAt(x, y).getOccupants()){
					if(u instanceof Ghost) {
						g = (Ghost) u;
						g.setExplode(true);
						level.respawnParticularGhost(g);
					}
				}
			}
		}
		timerTask = new TimerTask() {
			public void run() {
				synchronized (level.startStopLockCharacter){
					level.stopCharacters();
					level.startCharacters();
				}
			}
		};
		timerTask2 = new TimerTask() {
			public void run() {
				g.leaveSquare();
			}
		};
		timer = new Timer();
		timer.schedule(timerTask, 5000);
		timer.schedule(timerTask2, 1000);
	}
}

