package game;

import java.util.ArrayList;

import javax.swing.*;

import handlers.TileHandler;
import helperMethods.LoadSave;
import scenes.GameOver;
import scenes.GameWon;
import scenes.Levels;
import scenes.Menu;
import scenes.Playing;

public class Game extends JFrame implements Runnable{
	
	private static final long serialVersionUID = 1L;
	private GameScreen gameScreen;
	private Thread gameThread;
	
	private final double FPS = 120.0;
	private final double UPS = 60.0;
	
	private Render render;
	private Menu menu;
	private ArrayList<Playing> playing = new ArrayList<>();
	private Levels levels;
	private GameOver gameOver;
	private GameWon gameWon;
	private TileHandler tileHandler;
	
	private int selectedLevel = -1;
	private int lvlsDone;
	
	public Game() {
		initClasses();
		setResizable(false);
		setTitle("DIY Tower Defense");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		add(gameScreen);
		pack();
		
		setVisible(true);
	}
	
	private void initClasses() {
		tileHandler = new TileHandler();
		render = new Render(this);
		gameScreen = new GameScreen(this);
		menu = new Menu(this);
		levels = new Levels(this);
		gameOver = new GameOver(this);
		gameWon = new GameWon(this);
		lvlsDone = LoadSave.getLvlsDone("lvlsDone");
		for(int i = 1; i <= 10; i++) {
			playing.add(new Playing(this, i));
			System.out.println(i);
		}
	}
	
	private void start() {
		gameThread = new Thread(this) {};
		gameThread.start();
	}
	
	private void updateGame() {
		switch(GameStates.gameState) {
		case MENU:
			break;
		case PLAYING:
			playing.get(selectedLevel).update();
			break;
		case LEVELS:
			break;
		case GAMEOVER:
			break;
		case GAMEWON:
			break;
		default:
			break;
		
		}
	}

	public static void main(String[] args) {
		
		Game game = new Game();
		game.gameScreen.initInputs();
		game.start();
	
		
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS;
		
		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();
		
		int frames = 0;
		int updates = 0;
		
		long now;
		
		while(true) {
			now = System.nanoTime();
			if(now - lastFrame >= timePerFrame) {
				repaint();
				lastFrame = now;
				frames++;
			}
			if(now - lastUpdate >= timePerUpdate) {
				updateGame();
				lastUpdate = now;
				updates++;
			}
			if(System.currentTimeMillis() - lastTimeCheck >= 1000) {
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
				lastTimeCheck = System.currentTimeMillis();
			}
		}
	}
	
	//Getterek setterek
	public Render getRender() {
		return render;
	}
	
	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing.get(selectedLevel);
	}
	
	public Levels getLevels() {
		return levels;
	}
	
	public GameOver getGameOver() {
		return gameOver;
	}
	
	public GameWon getGameWon() {
		return gameWon;
	}
	
	public int getLvlsDone() {
		return lvlsDone;
	}
	
	public void setLvlsDone(int lvl) {
		if(lvlsDone <= lvl) {
			lvlsDone = lvl;
			LoadSave.saveLvlsDone("lvlsDone", lvlsDone);
		}
		
	}
	
	public TileHandler getTileHandler() {
		return tileHandler;
	}
	
	public int getSelectedLevel() {
		return selectedLevel;
	}
	
	public void setSelectedLevel(int id) {
		selectedLevel = id;
	}
	
}
