package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import player.*;
import tile.TileManager;
import weapon.*;

public class GamePanel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	// GAME SCREEN
	public final int TILE_SIZE_PIXELS = 16; // pixels
	public final int SCALE = 3;
	public final int ROWS = 16;
	public final int COLUMNS = 16;
	public final int SCREEN_PADDING = 30; // pixels
	public final int TILE_SIZE = TILE_SIZE_PIXELS * SCALE;
	public final int FIELD_HEIGHT = ROWS * TILE_SIZE;
	public final int FIELD_WIDTH = COLUMNS * TILE_SIZE;
	
	// TITLE SCREEN
	public final int GAME_TITLE_TOP_MARGIN = 100; // pixels
	public final int GAME_TITLE_BOTTOM_MARGIN = 250; // pixels
	public final int AUTHOR_TITLE_BOTTOM_MARGIN = 50; // pixels
	public final int TITLE_MENU_TOP_Y = GAME_TITLE_TOP_MARGIN + GAME_TITLE_BOTTOM_MARGIN; // pixels
	public final int TITLE_MENU_Y_GAP = 70; // pixels
	public final String GAME_TITLE = "Tactical battle";
	public final String AUTHOR_TITLE = "(made by PetkaKotletka)";
	public final String[] TITLE_MENU = {"new game", "rules", "quit"};
	public final String DEFAULT_FONT_STRING = "Comic Sans MS";
	public final Font GAME_TITLE_FONT = new Font(DEFAULT_FONT_STRING, Font.BOLD, 60);
	public final Font AUTHOR_TITLE_FONT = new Font(DEFAULT_FONT_STRING, Font.ITALIC, 30);
	public final Font TITLE_MENU_FONT = new Font(DEFAULT_FONT_STRING, Font.BOLD, 40);
	
	// SET PLAYERS AMOUNT
	public final int ENTER_PLAYERS_AMOUNT_Y = 200; // pixels
	public final int PLAYERS_AMOUNT_OFFSET = 70; // pixels
	public final int PLAYERS_AMOUNT_BUTTONS_OFFSET = 35; // pixels
	public final Point CONTINUE_LABEL_RIGHT_BOTTOM_OFFSET = new Point(200, 70); // pixels
	public final int CONTINUE_ARROW_OFFSET = 70; // pixels
	public final int MIN_PLAYERS = 2;
	public final int MAX_PLAYERS = 4;
	public final String ENTER_PLAYERS_AMOUNT_STRING = "Players";
	public final String CONTINUE_STRING = "continue";
	public final Font ENTER_PLAYERS_AMOUNT_FONT = new Font(DEFAULT_FONT_STRING, Font.BOLD, 50);
	public final Font CONTINUE_FONT = new Font(DEFAULT_FONT_STRING, Font.BOLD, 30);
	
	// SET PLAYERS
	public final int CONFIGURE_PLAYERS_TOP_MARGIN = 100;
	public final int PLAYERS_LABELS_Y = 300;
	public final int PLAYERS_LABELS_X_MARGIN = 130;
	public final int PLAYER_LABEL_SIZE = 60;
	public final int PLAY_LABEL_Y = 600;
	public final String CONFIGURE_PLAYERS_STRING = "Configure players";
	public final String PLAY_STRING = "Play";
	public final Font CONFIGURE_PLAYERS_FONT = new Font(DEFAULT_FONT_STRING, Font.BOLD, 50);
	public final Font PLAY_FONT = new Font(DEFAULT_FONT_STRING, Font.BOLD, 60);
	
	// RULES
	public final String RULES_TITLE = "Rules";
	public final String RULES_CONTENT = "    In this game players make moves in an order\n"
									  + "and fight each other on the field.\n"
									  + "    Player has two moves on his turn. He can either\n"
									  + "hit anyone he can reach, move to the next\n"
									  + "unoccupied tile or mount a shield (in this\n"
									  + "case his turn is over).\n"
									  + "    At start each player chooses a character and\n"
									  + "a weapon. There are three types of characters:\n"
									  + "strong one hits harder but has less protection,\n"
									  + "tough character has strong protection and\n"
									  + "fast character can move further.\n"
									  + "Likewise there are three types of weapons:\n"
									  + "melee makes more damage in the short range,\n"
									  + "range weapon hits far away and sweep weapon hits\n"
									  + "everyone in it's range.\n"
									  + "    The one who stands last wins. Good luck!";
	public final String BACK_STRING = "back";
	public final Point RULES_TOP_LEFT_POSITION = new Point(50, 200);
	public final Font RULES_TITLE_FONT = new Font(DEFAULT_FONT_STRING, Font.BOLD, 60);
	public final Font RULES_FONT = new Font(DEFAULT_FONT_STRING, Font.BOLD, 30);
	public final Font BACK_FONT = new Font(DEFAULT_FONT_STRING, Font.BOLD, 30);
	
	// CONTROL PANEL
	public final int CONTROL_PANEL_PADDING = 30; // pixels
	public final int CONTROL_PANEL_HEIGHT = 150;
	public final String DESCRIPTION_FONT_STRING = "Arial";
	public final int DESCRIPTION_SIZE = 35;
	public final Color DESCRIPTION_COLOR = Color.YELLOW;
	public final Font DESCRIPTION_FONT = new Font(DESCRIPTION_FONT_STRING, Font.BOLD, DESCRIPTION_SIZE);
	public final Point DESCRIPTION_POSITION = new Point(SCREEN_PADDING + CONTROL_PANEL_PADDING, 
														FIELD_HEIGHT + 2 * SCREEN_PADDING + CONTROL_PANEL_PADDING);
	
	// SCREEN
	public final String WINDOW_TITLE = "Version 2";
	public final int SCREEN_WIDTH = FIELD_WIDTH + 2 * SCREEN_PADDING;
	public final int SCREEN_HEIGHT = FIELD_HEIGHT + 2 * SCREEN_PADDING + CONTROL_PANEL_HEIGHT;
	
	// GAME STATES
	public final int TITLE_SCREEN_STATE = 0;
	public final int GAME_RUNNING_STATE = 1;
	public final int SET_PLAYERS_AMOUNT_STATE = 2;
	public final int SET_PLAYERS_STATE = 3;
	public final int RULES_STATE = 4;
	public final int END_GAME_STATE = 5;
	
	// PERFORMANCE
	public final int FPS = 60;
	public final double DRAWING_TIME = 1e9 / FPS; // nanoseconds
	
	public final int[][] PLAYER_POSITION = {{1, 1}, {1, 14}, {14, 14}, {14, 1}};
	
	private JFrame _window;
	private TileManager _tm;
	private ArrayList<Player> _players;
	private int _playersAmount;
	private PlayerDescription[] _playersDescriptions;
	private PlayerSetup[] _playersSetup;
	private Thread _gameThread;
	private MouseHandler _mouseHandler;
	private int _game_state;
	private TextLabel _gameTitle, _authorTitle, _setPlayersAmountLabel, _leftButton0, _rightButton0, _playersAmountLabel;
	private TextLabel _continueLabel, _continueArrow, _configurePlayersLabel, _playLabel, _rulesTitle, _backLabel, _rulesText;
	private TextLabel _congratsLabel, _newGameLabel;
	private TextLabel[] _titleMenu;
	
	public GamePanel(JFrame window) {
		// WINDOW
		this._window = window;
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(new Color(32, 32, 32));
		this._mouseHandler = new MouseHandler();
		this.addMouseListener(_mouseHandler);
		this.addMouseMotionListener(_mouseHandler);
		this._tm = new TileManager(this, this._mouseHandler);
		
		// TITLE SCREEN
		this._game_state = TITLE_SCREEN_STATE;
		this._gameTitle = new TextLabel(GAME_TITLE, new Point(SCREEN_WIDTH / 2, GAME_TITLE_TOP_MARGIN), GAME_TITLE_FONT);
		this._authorTitle = new TextLabel(AUTHOR_TITLE, new Point(SCREEN_WIDTH / 2, SCREEN_HEIGHT - AUTHOR_TITLE_BOTTOM_MARGIN), AUTHOR_TITLE_FONT);
		this._authorTitle.setColor(Color.DARK_GRAY);
		this._authorTitle.setShadow(false);
		this._titleMenu = new TextLabel[TITLE_MENU.length];
		for (int i = 0; i < TITLE_MENU.length; ++i) {
			this._titleMenu[i] = new TextLabel(TITLE_MENU[i], 
											   new Point(SCREEN_WIDTH / 2, TITLE_MENU_TOP_Y + TITLE_MENU_Y_GAP * i), 
											   TITLE_MENU_FONT);
		}
		
		// RULES SCREEN
		this._rulesTitle = new TextLabel(RULES_TITLE, new Point(SCREEN_WIDTH / 2, GAME_TITLE_TOP_MARGIN), RULES_TITLE_FONT);
		this._backLabel = new TextLabel(BACK_STRING, new Point(SCREEN_WIDTH - CONTINUE_LABEL_RIGHT_BOTTOM_OFFSET.x + CONTINUE_ARROW_OFFSET, 
													  		   SCREEN_HEIGHT - CONTINUE_LABEL_RIGHT_BOTTOM_OFFSET.y), BACK_FONT);
		this._rulesText = new TextLabel(RULES_CONTENT, RULES_TOP_LEFT_POSITION, RULES_FONT);
		this._rulesText.setShadow(false);
		
		// SECOND SCREEN (PLAYERS AMOUNT)
		this._setPlayersAmountLabel = new TextLabel(ENTER_PLAYERS_AMOUNT_STRING, 
												    new Point(SCREEN_WIDTH / 2, ENTER_PLAYERS_AMOUNT_Y), 
												    ENTER_PLAYERS_AMOUNT_FONT);
		this._playersAmountLabel = new TextLabel(Integer.toString(MIN_PLAYERS), new Point(SCREEN_WIDTH / 2, ENTER_PLAYERS_AMOUNT_Y + PLAYERS_AMOUNT_OFFSET), ENTER_PLAYERS_AMOUNT_FONT);
		this._leftButton0 = new TextLabel("<", 
										new Point(SCREEN_WIDTH / 2 - PLAYERS_AMOUNT_BUTTONS_OFFSET, ENTER_PLAYERS_AMOUNT_Y + PLAYERS_AMOUNT_OFFSET), 
										ENTER_PLAYERS_AMOUNT_FONT);
		this._rightButton0 = new TextLabel(">", 
										  new Point(SCREEN_WIDTH / 2 + PLAYERS_AMOUNT_BUTTONS_OFFSET, ENTER_PLAYERS_AMOUNT_Y + PLAYERS_AMOUNT_OFFSET), 
										  ENTER_PLAYERS_AMOUNT_FONT);
		this._continueLabel = new TextLabel(CONTINUE_STRING, 
									        new Point(SCREEN_WIDTH - CONTINUE_LABEL_RIGHT_BOTTOM_OFFSET.x, SCREEN_HEIGHT - CONTINUE_LABEL_RIGHT_BOTTOM_OFFSET.y),
									        CONTINUE_FONT);
		this._continueArrow = new TextLabel(">", 
											new Point(SCREEN_WIDTH - CONTINUE_LABEL_RIGHT_BOTTOM_OFFSET.x + CONTINUE_ARROW_OFFSET, 
													  SCREEN_HEIGHT - CONTINUE_LABEL_RIGHT_BOTTOM_OFFSET.y),
											CONTINUE_FONT);
		this._continueLabel.setShadow(false);
		this._continueArrow.setShadow(false);
		this._continueArrow.setVisible(false);
		
		// THIRD SCREEN (CONFIGURE PLAYERS)
		this._configurePlayersLabel = new TextLabel(CONFIGURE_PLAYERS_STRING, new Point(SCREEN_WIDTH / 2, CONFIGURE_PLAYERS_TOP_MARGIN), CONFIGURE_PLAYERS_FONT);
		this._playLabel = new TextLabel(PLAY_STRING, new Point(SCREEN_WIDTH / 2, PLAY_LABEL_Y), PLAY_FONT);
		
		// CONTROL PANEL
		this._congratsLabel = new TextLabel("not implemented", new Point(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 120), RULES_FONT);
		this._newGameLabel = new TextLabel("new game", new Point(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 70), RULES_TITLE_FONT);
	}
	
	public void setDefaultPlayers(int PlayersAmount) {
		this._playersAmount = PlayersAmount;
		this._playersDescriptions = new PlayerDescription[this._playersAmount];
		this._playersSetup = new PlayerSetup[this._playersAmount];
		int gapX = (SCREEN_WIDTH - PLAYERS_LABELS_X_MARGIN * 2) / (this._playersAmount - 1);
		for (int i = 0; i < this._playersAmount; ++i) {
			this._playersSetup[i] = new PlayerSetup(PLAYERS_LABELS_X_MARGIN + gapX * i - PLAYER_LABEL_SIZE / 2, PLAYERS_LABELS_Y, PLAYER_LABEL_SIZE, "Player " + Integer.toString(i + 1));
		}
	}
	
	public void setPlayers() {
		this._players = new ArrayList<Player>();
		for (int i = 0; i < this._playersAmount; ++i) {
			switch (this._playersDescriptions[i].type) {
			case "STRONG":
				this._players.add(new StrongPlayer());
				break;
			case "TOUGH":
				this._players.add(new ToughPlayer());
				break;
			case "FAST":
				this._players.add(new FastPlayer());
				break;
			}
			switch (this._playersDescriptions[i].weapon) {
			case "MELEE":
				this._players.get(i).setWeapon(new MeleeWeapon());
				break;
			case "RANGE":
				this._players.get(i).setWeapon(new RangeWeapon());
				break;
			case "SWEEP":
				this._players.get(i).setWeapon(new SweepWeapon());
				break;
			}
			this._players.get(i).setGamePanel(this);
			this._players.get(i).setTileManager(this._tm);
			this._players.get(i).setName(this._playersDescriptions[i].name);
			this._players.get(i).setPosition(this.PLAYER_POSITION[i][0], 
										 this.PLAYER_POSITION[i][1]);
			this._players.get(i).setImages();
		}
		this._players.get(0).activate();
	}
	
	public void startGameThread() {
		this._gameThread = new Thread(this);
		this._gameThread.start();
	}

	public void run() {
		long prevTimeStamp, curTimeStamp;
		double delta = 0.;
		prevTimeStamp = System.nanoTime();
		while (this._gameThread != null) {
			curTimeStamp = System.nanoTime();
			delta += (double)(curTimeStamp - prevTimeStamp) / DRAWING_TIME;
			prevTimeStamp = curTimeStamp;
			
			if (delta >= 1.) {
				this.update();
				this.repaint();
				this._mouseHandler.isClicked = false;
				delta = 0.;
			}
		}
	}
	
	private void endGameThread() {
		this._gameThread = null;
		this._window.dispatchEvent(new WindowEvent(this._window, WindowEvent.WINDOW_CLOSING));
	}
	
	private void activateNext() {
		int activeIdx = 0;
		for (int i = 0; i < this._players.size(); ++i) {
			if (this._players.get(i).isActive()) {
				if (!this._players.get(i).noMoreMoves()) {
					return;
				}
				activeIdx = i;
				this._players.get(i).deactivate();
			}
		}
		activeIdx = (activeIdx + 1) % this._players.size();
		this._players.get(activeIdx).activate();
	}
	
	public boolean isPlayerOnTile(int row, int column) {
		for (Player p : this._players) {
			if (p.isThisTile(row, column) && p.isAlive()) {
				return true;
			}
		}
		return false;
	}
	
	public Player getPlayerOnTile(int row, int column) {
		for (Player p : this._players) {
			if (p.isThisTile(row, column) && p.isAlive()) {
				return p;
			}
		}
		return null;
	}
	
	// UPDATE
	private void updateGame() {
		for (int i = this._players.size() - 1; i >= 0; --i) {
			if (!this._players.get(i).isAlive()) {
				this._players.remove(i);
			}
		}
		if (this._players.size() == 1) {
			this._game_state = END_GAME_STATE;
			this._congratsLabel.setText(this._players.get(0).getName() + " won!");
		}
		for (Player p : this._players) {
			p.update();
		}
		this.activateNext();
	}
	
	private void updateTitleScreen() {
		for (TextLabel option : this._titleMenu) {
			if (option.isMouseOnLabel(this._mouseHandler.mousePosition)) {
				option.setShadow(false);
				if (this._mouseHandler.isClicked) {
					switch (option.getText()) {
					case "new game":
						this._game_state = SET_PLAYERS_AMOUNT_STATE;
						break;
					case "rules":
						this._game_state = RULES_STATE;
						break;
					case "quit":
						this.endGameThread();
						break;
					}
				}
			} else {
				option.setShadow(true);
			}
		}
	}
	
	private void updateRulesScreen() {
		if (this._backLabel.isMouseOnLabel(this._mouseHandler.mousePosition)) {
			this._backLabel.setShadow(false);
			if (this._mouseHandler.isClicked) {
				this._game_state = TITLE_SCREEN_STATE;
			}
		} else {
			this._backLabel.setShadow(true);
		}
	}
	
	private void updateSecondScreen() {
		int playersAmount;
		if (this._leftButton0.isMouseOnLabel(this._mouseHandler.mousePosition)) {
			playersAmount = Integer.parseInt(this._playersAmountLabel.getText());
			if (playersAmount > MIN_PLAYERS) {
				this._leftButton0.setShadow(false);
				if (this._mouseHandler.isClicked) {
					this._playersAmountLabel.setText(Integer.toString(playersAmount - 1));
				}
			}
		} else {
			this._leftButton0.setShadow(true);
		}
		if (this._rightButton0.isMouseOnLabel(this._mouseHandler.mousePosition)) {
			playersAmount = Integer.parseInt(this._playersAmountLabel.getText());
			if (playersAmount < MAX_PLAYERS) {
				this._rightButton0.setShadow(false);
				if (this._mouseHandler.isClicked) {
					this._playersAmountLabel.setText(Integer.toString(playersAmount + 1));
				}
			}
		} else {
			this._rightButton0.setShadow(true);
		}
		if (this._continueLabel.isMouseOnLabel(this._mouseHandler.mousePosition)) {
			this._continueArrow.setVisible(true);
			if (this._mouseHandler.isClicked) {
				this.setDefaultPlayers(Integer.parseInt(this._playersAmountLabel.getText()));
				this._game_state = SET_PLAYERS_STATE;
			}
		} else {
			this._continueArrow.setVisible(false);
		}
	}
	
	private void updateThirdScreen() {
		for (PlayerSetup playerSetup : this._playersSetup) {
			playerSetup.update(this._mouseHandler.mousePosition, this._mouseHandler.isClicked);
		}
		if (this._playLabel.isMouseOnLabel(this._mouseHandler.mousePosition)) {
			this._playLabel.setShadow(false);
			if (this._mouseHandler.isClicked) {
				for (int i = 0; i < this._playersAmount; ++i) {
					this._playersDescriptions[i] = this._playersSetup[i].getInfo();
				}
				this.setPlayers();
				this._game_state = GAME_RUNNING_STATE;
			}
		} else {
			this._playLabel.setShadow(true);
		}
	}
	
	private void updateEndScreen() {
		if (this._newGameLabel.isMouseOnLabel(this._mouseHandler.mousePosition)) {
			this._newGameLabel.setShadow(false);
			if (this._mouseHandler.isClicked) {
				this._game_state = TITLE_SCREEN_STATE;
			}
		} else {
			this._newGameLabel.setShadow(true);
		}
	}
	
	public void update() {
		switch (this._game_state) {
		case TITLE_SCREEN_STATE:
			this.updateTitleScreen();
			break;
		case RULES_STATE:
			this.updateRulesScreen();
			break;
		case SET_PLAYERS_AMOUNT_STATE:
			this.updateSecondScreen();
			break;
		case SET_PLAYERS_STATE:
			this.updateThirdScreen();
			break;
		case GAME_RUNNING_STATE:
			this.updateGame();
			break;
		case END_GAME_STATE:
			this.updateEndScreen();
			break;
		}
	}
	
	// DRAW
	private void drawDescription(Graphics2D g2) {
		g2.setFont(DESCRIPTION_FONT);
		g2.setColor(DESCRIPTION_COLOR);
		for (Player player : this._players) {
			if (player.isActive()) {
				g2.drawString("Next move: " + player.getName(),
					          DESCRIPTION_POSITION.x, 
					          DESCRIPTION_POSITION.y);
			}
		}
	}
	
	private void drawGame(Graphics2D g2) {
		this._tm.draw(g2);
		for (Player p : this._players) {
			p.draw(g2);
		}
		this.drawDescription(g2);
	}
	
	private void drawEndScreen(Graphics2D g2) {
		this._tm.draw(g2);
		this._players.get(0).draw(g2);
		this._congratsLabel.draw(g2);
		this._newGameLabel.draw(g2, true);
		
	}
	
	private void drawTitleScreen(Graphics2D g2) {
		this._gameTitle.draw(g2);
		this._authorTitle.draw(g2);
		for (TextLabel option : this._titleMenu) {
			option.draw(g2, true);
		}
	}
	
	private void drawRulesScreen(Graphics2D g2) {
		this._rulesTitle.draw(g2);
		this._rulesText.drawMulti(g2);
		this._backLabel.draw(g2, true);
	}
	
	private void drawSecondScreen(Graphics2D g2) {
		this._setPlayersAmountLabel.draw(g2);
		this._playersAmountLabel.draw(g2);
		this._leftButton0.draw(g2, true);
		this._rightButton0.draw(g2, true);
		this._continueLabel.draw(g2);
		this._continueArrow.draw(g2);
	}
	
	private void drawThirdScreen(Graphics2D g2) {
		this._configurePlayersLabel.draw(g2);
		this._playLabel.draw(g2, true);
		for (PlayerSetup playerSetup : this._playersSetup) {
			playerSetup.draw(g2);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		switch (this._game_state) {
		case TITLE_SCREEN_STATE:
			this.drawTitleScreen(g2);
			break;
		case RULES_STATE:
			this.drawRulesScreen(g2);
			break;
		case SET_PLAYERS_AMOUNT_STATE:
			this.drawSecondScreen(g2);
			break;
		case SET_PLAYERS_STATE:
			this.drawThirdScreen(g2);
			break;
		case GAME_RUNNING_STATE:
			this.drawGame(g2);
			break;
		case END_GAME_STATE:
			this.drawEndScreen(g2);
			break;
		}
	}
}
