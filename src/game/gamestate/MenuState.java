package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class MenuState extends GameState{

	private String[] options = {"Play", "Help", "Quit"};
	private int currentSelection = 0;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {}

	public void tick() {}

	public void draw(Graphics g) {
		g.setColor(new Color(50, 150, 200));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		for(int i = 0; i < options.length; i++){
			if(i == currentSelection){
				g.setColor(Color.GREEN);
				g.setFont(new Font("Arial", Font.BOLD, GamePanel.HEIGHT / 5));
			}
			else{
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.PLAIN, GamePanel.HEIGHT / 7));
			}
			g.drawString(options[i], GamePanel.WIDTH / 2 - GamePanel.HEIGHT / 7, GamePanel.HEIGHT / 5 + i * GamePanel.HEIGHT / 5);
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
			else if(currentSelection == 1){
				gsm.states.push(new Help(gsm));
			}
			else if(currentSelection == 2){
				System.exit(0);
			}
			
		}
		else if(k == KeyEvent.VK_ESCAPE) System.exit(0);
	}

	public void keyReleased(int k) {
		
	}

}
