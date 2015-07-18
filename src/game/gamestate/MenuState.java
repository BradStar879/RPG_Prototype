package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import player.Saver;


public class MenuState extends GameState{

	private String[] options;
	private int currentSelection;
	Saver saver = new Saver();
	public static boolean isSavedGame = false;
	Image menuScreen;
	int wd;
	int ht;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		wd = GamePanel.WIDTH;
		ht = GamePanel.HEIGHT;
		options = new String[]{"New Game", "Load Game", "Help", "Quit"};
		currentSelection = 0;
		menuScreen = new ImageIcon("Sprites/MenuScreen.png").getImage();
	}

	public void tick() {}

	public void draw(Graphics g) {
		
		g.drawImage(menuScreen, 0, 0, wd, ht, null);
		for(int i = 0; i < options.length; i++){
			if(i == currentSelection){
				g.setColor(Color.GREEN);
			}
			else{
				g.setColor(Color.WHITE);
			}
			g.setFont(new Font("pixelmix", Font.PLAIN, ht / 7));
			g.drawString(options[i], wd / 3, ht / 5 + i * ht / 5);
		}
		
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_DOWN){
			currentSelection++;
			if(currentSelection >= options.length){
				currentSelection = 0;
			}
		}
		else if(k == KeyEvent.VK_UP){
			currentSelection--;
			if(currentSelection < 0){
				currentSelection = options.length - 1;
			}
		}
		else if(k == KeyEvent.VK_ENTER){
			if(currentSelection == 0){
				gsm.states.push(new ClassSelection(gsm));
			}
			else if(currentSelection == 1) {
				saver.load();
				if(isSavedGame) gsm.states.push(new World(gsm));
			}
			else if(currentSelection == 2){
				gsm.states.push(new Help(gsm));
			}
			else if(currentSelection == 3){
				System.exit(0);
			}
			
		}
		else if(k == KeyEvent.VK_ESCAPE) System.exit(0);
	}

	public void keyReleased(int k) {
		
	}

}
