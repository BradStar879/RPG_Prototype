package world;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class GrassBlock extends BaseWorldBlock{
	
	Image blockType = new ImageIcon("Sprites/GrassBlock.png").getImage();
	
	public GrassBlock(int wd) {
		this.wd = wd;
		walkable = true;
		enter = false;
	}
	
	public void draw(Graphics g) {
		g.drawImage(blockType, x, y, wd, wd, null);
	}
}
