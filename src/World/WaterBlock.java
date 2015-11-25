package world;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class WaterBlock extends BaseWorldBlock{
	
	Image blockType = new ImageIcon("Sprites/WaterBlock.png").getImage();
	
	public WaterBlock(int wd) {
		this.wd = wd;
		walkable = false;
		enter = false;
	}
	
	public void draw(Graphics g) {
		g.drawImage(blockType, x, y, wd, wd, null);
	}
	
}
