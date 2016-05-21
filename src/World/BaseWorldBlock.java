package world;

import game.main.GamePanel;

import java.awt.Graphics;
import java.awt.Image;

public class BaseWorldBlock {
	
	public int wd;
	int ht = GamePanel.HEIGHT / 12;
	public boolean walkable;
	public boolean hasPerson = false;
	public boolean enter = false;
	public boolean exit = false;
	public int x;
	public int y;
	Image blockType;
	public String name;
	
	public BaseWorldBlock() {}

	public BaseWorldBlock set(int x, int y) {
		this.x = x; 
		this.y = y;
		return this;
	}
	
	public void draw(Graphics g) {
		g.drawImage(blockType, x, y, wd, wd, null);
	}
}