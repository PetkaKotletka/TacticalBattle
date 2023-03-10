package main;

import javax.swing.JFrame;

public class Game {
	
	private JFrame _window;
	private GamePanel _gamePanel;
	
	public Game() {
		this._window = new JFrame();
		this._window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this._window.setResizable(false);
		this._gamePanel = new GamePanel(this._window);
		this._window.add(this._gamePanel);
		this._window.setTitle(this._gamePanel.WINDOW_TITLE);
		this._window.pack();
		this._window.setLocationRelativeTo(null);
	}
	
	public void startGame() {
		this._window.setVisible(true);
		this._gamePanel.startGameThread();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.startGame();
	}
}
