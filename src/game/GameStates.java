package game;

public enum GameStates {
	
	PLAYING,
	MENU,
	LEVELS,
	GAMEOVER,
	GAMEWON;

	public static GameStates gameState = MENU;
	
	public static void setGameState(GameStates state) {
		gameState = state;
	}
	
}
