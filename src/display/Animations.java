package display;

import game.main.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Animations {
	
	public static int delay = 0;
	static int wd = GamePanel.WIDTH;
	static int ht = GamePanel.HEIGHT;
	Image[] arr;
	int arrLen;
	int aniCount;
	int curImg;
	int x, y;

	public Animations() {}
	
	public void setAnimate(Image[] arr, int x, int y) {
		this.arr = arr;
		arrLen = arr.length;
		aniCount = 0;
		curImg = 0;
		this.x = x;
		this.y = y;
	}
	
	public boolean animate(Graphics g) {
		g.drawImage(arr[curImg], x, y, null);
		if(aniCount == 1) {
			curImg++;
			aniCount = 0;
			if(curImg == arrLen) return false;
		}
		else aniCount++;
		return true;
	}
	
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
