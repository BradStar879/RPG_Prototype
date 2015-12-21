package world;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class ExitCityBlock extends BaseWorldBlock{
	
	Image blockType = new ImageIcon("Sprites/WaterBlock.png").getImage();
	
	public ExitCityBlock(int wd) {
		this.wd = wd;
		walkable = true;
		exit = true;
	}
	
	public void draw(Graphics g) {
		g.drawImage(blockType, x, y, wd, wd, null);
	}
}
