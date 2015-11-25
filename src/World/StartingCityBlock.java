package world;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class StartingCityBlock extends BaseWorldBlock{
	
	Image blockType = new ImageIcon("Sprites/StartingCityBlock.png").getImage();
	
	public StartingCityBlock(int wd) {
		this.wd = wd;
		walkable = true;
		enter = true;
	}
	
	public void draw(Graphics g) {
		g.drawImage(blockType, x, y, wd * 2, wd * 2, null);
	}
}