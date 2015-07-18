package game.gamestate;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class NameSelection extends GameState{
	
	int wd = GamePanel.WIDTH;
	int ht = GamePanel.HEIGHT;
	int border =  ht / 62;
	Image background;
	int blinkCount;
	int currentSpace;
	boolean isShiftPressed;
	char[] name;

	public NameSelection(GameStateManager gsm) {
		super(gsm);
	}

	
	public void init() {
		
		background = new ImageIcon("Sprites/MenuScreen.png").getImage();
		currentSpace = 0;
		isShiftPressed = false;
		name = new char[10];
		for(int i = 0; i < 10; i++) name[i] = ' ';
		
	}

	
	public void tick() {
		
		blinkCount++;
		if(blinkCount == 60) blinkCount = 0;
	}

	
	public void draw(Graphics g) {
		
		g.drawImage(background, 0, 0, wd, ht, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("pixelmix", Font.PLAIN, ht / 30));
		for(int i = 0; i < 10; i++) {
			if(i != currentSpace || blinkCount < 30) g.fillRect(wd / 4 + (i * wd / 20), ht / 3, border * 4, border);
			g.drawString("" + name[i], wd / 4 + (i * wd / 20) + border, ht / 3);
		}
		g.drawString("Please enter a name for your character.", wd / 4 - border * 2, ht / 2);

		
	}

	
	public void keyPressed(int k) {
		
		if(k == KeyEvent.VK_ENTER) {
			
			String nameString = "";
			for(int i = 0; i < 10; i++) nameString += name[i];
			ClassSelection.names[ClassSelection.selected] = nameString;
			ClassSelection.selected++;
			gsm.states.pop();
		}
		
		if(k == KeyEvent.VK_ESCAPE) {
			
			ClassSelection.names[ClassSelection.selected] = "";
			ClassSelection.classesSelected[ClassSelection.selected] = "";
			gsm.states.pop();
		}
		
		if(currentSpace < 10) {
			if(isShiftPressed) {
				if(k == KeyEvent.VK_A ) {
					name[currentSpace] = 'A';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_B ) {
					name[currentSpace] = 'B';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_C ) {
					name[currentSpace] = 'C';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_D ) {
					name[currentSpace] = 'D';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_E ) {
					name[currentSpace] = 'E';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_F ) {
					name[currentSpace] = 'F';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_G ) {
					name[currentSpace] = 'G';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_H ) {
					name[currentSpace] = 'H';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_I ) {
					name[currentSpace] = 'I';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_J ) {
					name[currentSpace] = 'J';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_K ) {
					name[currentSpace] = 'K';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_L ) {
					name[currentSpace] = 'L';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_M ) {
					name[currentSpace] = 'M';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_N ) {
					name[currentSpace] = 'N';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_O ) {
					name[currentSpace] = 'O';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_P ) {
					name[currentSpace] = 'P';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_Q ) {
					name[currentSpace] = 'Q';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_R ) {
					name[currentSpace] = 'R';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_S ) {
					name[currentSpace] = 'S';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_T ) {
					name[currentSpace] = 'T';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_U ) {
					name[currentSpace] = 'U';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_V ) {
					name[currentSpace] = 'V';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_W ) {
					name[currentSpace] = 'W';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_X ) {
					name[currentSpace] = 'X';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_Y ) {
					name[currentSpace] = 'Y';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_Z ) {
					name[currentSpace] = 'Z';
					currentSpace++;
					blinkCount = 30;
				}
				
				
			}
			else {
				if(k == KeyEvent.VK_A ) {
					name[currentSpace] = 'a';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_B ) {
					name[currentSpace] = 'b';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_C ) {
					name[currentSpace] = 'c';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_D ) {
					name[currentSpace] = 'd';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_E ) {
					name[currentSpace] = 'e';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_F ) {
					name[currentSpace] = 'f';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_G ) {
					name[currentSpace] = 'g';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_H ) {
					name[currentSpace] = 'h';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_I ) {
					name[currentSpace] = 'i';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_J ) {
					name[currentSpace] = 'j';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_K ) {
					name[currentSpace] = 'k';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_L ) {
					name[currentSpace] = 'l';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_M ) {
					name[currentSpace] = 'm';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_N ) {
					name[currentSpace] = 'n';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_O ) {
					name[currentSpace] = 'o';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_P ) {
					name[currentSpace] = 'p';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_Q ) {
					name[currentSpace] = 'q';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_R ) {
					name[currentSpace] = 'r';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_S ) {
					name[currentSpace] = 's';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_T ) {
					name[currentSpace] = 't';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_U ) {
					name[currentSpace] = 'u';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_V ) {
					name[currentSpace] = 'v';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_W ) {
					name[currentSpace] = 'w';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_X ) {
					name[currentSpace] = 'x';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_Y ) {
					name[currentSpace] = 'y';
					currentSpace++;
					blinkCount = 30;
				}
				if(k == KeyEvent.VK_Z ) {
					name[currentSpace] = 'z';
					currentSpace++;
					blinkCount = 30;
				}
			
			}

			if(k == KeyEvent.VK_SPACE) {
				name[currentSpace] = ' ';
				currentSpace++;
				blinkCount = 30;
			}
		}
		if(k == KeyEvent.VK_BACK_SPACE && currentSpace != 0) {
			name[currentSpace - 1] = ' '; 
			currentSpace--;
		}
		if(k == KeyEvent.VK_SHIFT) isShiftPressed = true;
	}

	
	public void keyReleased(int k) {
		
		if(k == KeyEvent.VK_SHIFT) isShiftPressed = false;
		
	}
	
}

