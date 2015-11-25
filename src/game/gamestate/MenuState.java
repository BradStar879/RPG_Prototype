package game.gamestate;

import game.main.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import physics.Sounds;
import player.Saver;


public class MenuState extends GameState{

	private String[] options;
	private int currentSelection;
	Saver saver = new Saver();
	public static boolean isSavedGame = false;
	Image menuScreen;
	int wd;
	int ht;
	int loadTime;
	float alpha;
	Sounds menuSelectSound = new Sounds("Sounds/menu select.wav");
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		wd = GamePanel.WIDTH;
		ht = GamePanel.HEIGHT;
		loadTime = -60;
		alpha = 1.0f;
		options = new String[]{"New Game", "Load Game", "Help", "Quit"};
		currentSelection = 0;
		menuScreen = new ImageIcon("Sprites/MenuScreen.png").getImage();
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("pixelmix.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
	}

	public void tick() {
		if(loadTime < 30) loadTime++;
		if(loadTime == 30) alpha = 0.0f;
		else if(loadTime > 27) alpha = 0.1f;
		else if(loadTime > 24) alpha = 0.2f;
		else if(loadTime > 21) alpha = 0.3f;
		else if(loadTime > 18) alpha = 0.4f;
		else if(loadTime > 15) alpha = 0.5f;
		else if(loadTime > 12) alpha = 0.6f;
		else if(loadTime > 9) alpha = 0.7f;
		else if(loadTime > 6) alpha = 0.8f;
		else if(loadTime > 3) alpha = 0.9f;
	}

	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;	
		if(loadTime >= 0) {
		g2d.drawImage(menuScreen, 0, 0, wd, ht, null);
			for(int i = 0; i < options.length; i++){
				if(i == currentSelection){
					g2d.setColor(Color.GREEN);
				}
				else{
					g2d.setColor(Color.WHITE);
				}
				g2d.setFont(new Font("pixelmix", Font.PLAIN, ht / 7));
				g2d.drawString(options[i], wd / 3, ht / 5 + i * ht / 5);
				//g2d.rotate(Math.PI / 2);
				g2d.setFont(new Font("pixelmix", Font.PLAIN, ht / 30));
				g2d.setColor(Color.WHITE);
				g2d.drawString("By Brad Patterson", wd * 3 / 4, ht * 14 / 15);
				g2d.setFont(new Font("pixelmix", Font.PLAIN, ht / 15));
				g2d.setColor(Color.YELLOW);
				g2d.drawString("Alpha 1.0", wd / 20, ht / 5);
				//g2d.rotate(-Math.PI / 2);
			}
		}
		
		g2d.setColor(Color.BLACK);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2d.fillRect(0, 0, wd, ht);
		
	}

	public void keyPressed(int k) {
		if(loadTime >= 0) {
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
			/*else if(k == KeyEvent.VK_F) {
				Game.frame.setUndecorated(true);
				Game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			}*/
			else if(k == KeyEvent.VK_ENTER){
				if(currentSelection == 0){
					menuSelectSound.play();
					gsm.states.push(new ClassSelection(gsm));
				}
				else if(currentSelection == 1) {
					saver.load();
					if(isSavedGame) {
						menuSelectSound.play();
						gsm.states.push(new World(gsm));
					}
				}
				else if(currentSelection == 2){
					menuSelectSound.play();
					gsm.states.push(new Help(gsm));
				}
				else if(currentSelection == 3){
					System.exit(0);
				}		
			}
		}
		if(k == KeyEvent.VK_ESCAPE) System.exit(0);
	}

	public void keyReleased(int k) {
		
	}

}
