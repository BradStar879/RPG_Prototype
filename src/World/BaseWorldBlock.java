package world;

import game.main.GamePanel;

import java.awt.Graphics;
import java.awt.Image;

public class BaseWorldBlock {
	
	public int wd;
	int ht = GamePanel.HEIGHT / 12;
	public boolean walkable;
	public boolean enter;
	public int x;
	public int y;
	Image blockType;
	
	public BaseWorldBlock() {}

	public BaseWorldBlock set(int x, int y) {
		this.x = x; 
		this.y = y;
		return this;
	}
	
	public void draw(Graphics g) {
		g.fillRect(x, y, wd, ht);
	}
}
