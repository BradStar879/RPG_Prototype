package world;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class MountainBlock extends BaseWorldBlock{
	
Image blockType = new ImageIcon("Sprites/Mountains.png").getImage();
	
	public MountainBlock(int wd) {
		this.wd = wd;
		walkable = false;
		enter = false;
		name = "Mountain";
	}
	
	public void draw(Graphics g) {
		g.drawImage(blockType, x, y, wd, wd, null);
	}

}
