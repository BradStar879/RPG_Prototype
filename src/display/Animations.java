package display;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics;

public class Animations {
	
	public static int delay = 0;
	static int wd = GamePanel.WIDTH;
	static int ht = GamePanel.HEIGHT;

	public Animations() {}
	
	public static boolean splitScreenVert(Graphics g, int delayMax) {

		if(delay != delayMax) {
			g.copyArea(0, 0, (wd / 2), ht, (-wd  * delay / delayMax), 0);
			g.copyArea(wd / 2, 0, (wd / 2), ht, (wd  * delay / delayMax), 0);
			g.setColor(Color.BLACK);
			g.fillRect(wd / 2 - (wd  * delay / delayMax), 0, (wd  * delay / delayMax * 2), ht);
			delay++;
		}
		if(delay == delayMax) return true;
		else return false;
	}
	
}
