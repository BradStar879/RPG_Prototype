package World;

import java.awt.Color;
import java.awt.Graphics;

import game.main.GamePanel;

public class BaseWorldBlock {
	
	public int wd;
	int ht = GamePanel.HEIGHT / 12;
	public boolean walkable;
	Color col;
	public int x;
	public int y;
	
	public BaseWorldBlock(int wd) {
		this.wd = wd;
	}

	public BaseWorldBlock set(int x, int y) {
		this.x = x; 
		this.y = y;
		return this;
	}
	
	public void draw(Graphics g) {
		g.setColor(col);
		g.fillRect(x, y, wd, ht);
	}
}
