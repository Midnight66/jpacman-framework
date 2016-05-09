package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.Action;
import nl.tudelft.jpacman.ui.PacManUI;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

import javax.swing.*;

/**
 * Creates and launches the JPacMan UI.
 *
 * @author Jeroen Roosen
 */
public class Launcher {

	/**
	 * The sprite store
	 */
	private static final PacManSprites SPRITE_STORE = new PacManSprites();

	/**
	 * L'instance du launcher
	 */
	private static Launcher launcher;

	/**
	 * The PacmanUI
	 */
	private static PacManUI pacManUI;

	/**
	 * The game
	 */
	private static Game game;

	/**
	 * Le .txt qui doit etre choisi comme map (définit dans PacManUI)
	 */
	private String boardToUse;

	/**
	 * Le constructeur de la classe Launcher
	 */
	public Launcher()
	{
		Launcher.launcher = this;
	}

	/**
	 * @return The game object this launcher will start when {@link #launch()}
	 *         is called.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Creates a new game using the level from {@link #makeLevel()}.
	 *
	 * @return a new Game.
	 */
	public Game makeGame() {
		final GameFactory gf = getGameFactory();
		final String[] board = {"Jeu normal", "Map infinie", "Jeu avec fruits", "Cheat Mode"};
		String nom = (String) JOptionPane.showInputDialog(null,
				"Veuillez choisir un mode de jeu !",
				"PACMAN GAME !",
				JOptionPane.QUESTION_MESSAGE,
				null,
				board,
				board[0]);

		Level level;
		if(nom.equals(board[1])) {
			boardToUse = "/boardExtendedBase.txt";
			level = makeLevel();
			level.infiniteMode = true;
			return gf.createSinglePlayerGame(level);
		}
		else if(nom.equals(board[2])){
			boardToUse = "/boardFruit.txt";
		}
		else if(nom.equals(board[3])){
			boardToUse = "/board.txt";
			level = makeLevel();
			level.cheatMode = true;
			return gf.createSinglePlayerGame(level);
		}
		else{
			boardToUse = "/board.txt";
		}
		level = makeLevel();
		level.infiniteMode = false;
		return gf.createSinglePlayerGame(level);
	}

	/**
	 * Creates a new level. By default this method will use the map parser to
	 * parse the default board stored in the <code>board.txt</code> resource.
	 *
	 * @return A new level.
	 */
	public Level makeLevel() {
		MapParser parser = getMapParser();
		try (InputStream boardStream = Launcher.class
				.getResourceAsStream(this.boardToUse)) {
			return parser.parseMap(boardStream);
		} catch (IOException e) {
			throw new PacmanConfigurationException("Unable to create level.", e);
		}
	}

	/**
	 * @return A new map parser object using the factories from
	 *         {@link #getLevelFactory()} and {@link #getBoardFactory()}.
	 */
	protected MapParser getMapParser() {
		return new MapParser(getLevelFactory(), getBoardFactory());
	}

	/**
	 * @return A new board factory using the sprite store from
	 *         {@link #getSpriteStore()}.
	 */
	protected BoardFactory getBoardFactory() {
		return new BoardFactory(getSpriteStore());
	}

	/**
	 * @return The default {@link PacManSprites}.
	 */
	protected PacManSprites getSpriteStore() {
		return SPRITE_STORE;
	}

	/**
	 * @return A new factory using the sprites from {@link #getSpriteStore()}
	 *         and the ghosts from {@link #getGhostFactory()}.
	 */
	protected LevelFactory getLevelFactory() {
		return new LevelFactory(getSpriteStore(), getGhostFactory());
	}

	/**
	 * @return A new factory using the sprites from {@link #getSpriteStore()}.
	 */
	protected GhostFactory getGhostFactory() {
		return new GhostFactory(getSpriteStore());
	}

	/**
	 * @return A new factory using the players from {@link #getPlayerFactory()}.
	 */
	protected GameFactory getGameFactory() {
		return new GameFactory(getPlayerFactory());
	}

	/**
	 * @return A new factory using the sprites from {@link #getSpriteStore()}.
	 */
	protected PlayerFactory getPlayerFactory() {
		return new PlayerFactory(getSpriteStore());
	}

	/**
	 * Adds key events UP, DOWN, LEFT and RIGHT to a game.
	 * @param builder The {@link PacManUiBuilder} that will provide the UI.
	 * @param game The game that will process the events.
	 */
	protected void addSinglePlayerKeys(final PacManUiBuilder builder,
									   final Game game) {
		final Player p1 = getSinglePlayer(game);

		builder.addKey(KeyEvent.VK_UP, new Action() {

			@Override
			public void doAction() {
				game.move(p1, Direction.NORTH);
			}
		}).addKey(KeyEvent.VK_DOWN, new Action() {

			@Override
			public void doAction() {
				game.move(p1, Direction.SOUTH);
			}
		}).addKey(KeyEvent.VK_LEFT, new Action() {

			@Override
			public void doAction() {
				game.move(p1, Direction.WEST);
			}
		}).addKey(KeyEvent.VK_RIGHT, new Action() {

			@Override
			public void doAction() {
				game.move(p1, Direction.EAST);
			}
		});

	}

	private Player getSinglePlayer(final Game game) {
		List<Player> players = game.getPlayers();
		if (players.isEmpty()) {
			throw new IllegalArgumentException("Game has 0 players.");
		}
		final Player p1 = players.get(0);
		return p1;
	}

	/**
	 * Creates and starts a JPac-Man game.
	 */
	public void launch() {
		game = makeGame();
		PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
		addSinglePlayerKeys(builder, game);
		pacManUI = builder.build(game);
		pacManUI.start();
	}

	/**
	 * Disposes of the UI. For more information see
	 * {@link javax.swing.JFrame#dispose()}.
	 */
	public void dispose() {
		pacManUI.dispose();
	}

	/**
	 * Main execution method for the Launcher.
	 *
	 * @param args
	 *            The command line arguments - which are ignored.
	 * @throws IOException
	 *             When a resource could not be read.
	 */
	public static void main(String[] args) throws IOException {
		new Launcher().launch();
	}

	/**
	 * Permet d'obtenir l'instance du launcher
	 * @return L'instance de launcher
	 */
	public static Launcher getLauncher()
	{
		return launcher;
	}

	/**
	 * Permet de mettre a jour la map a dessiner
	 * @param boardToUse Le nouveau fichier de la map
	 */
	public void setBoardToUse(String boardToUse) {
		this.boardToUse = boardToUse;
	}

	/**
	 * Permet d'obtenir le fichier correspondant a la map
	 * @return Le fichier de la map
	 */
	public String getBoardToUse() {
		return boardToUse;
	}
}
