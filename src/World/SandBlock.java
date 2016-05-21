package world;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class SandBlock extends BaseWorldBlock{
	
	Image blockType = new ImageIcon("Sprites/SandBlock.png").getImage();
	
	public SandBlock(int wd) {
		this.wd = wd;
		walkable = true;
		enter = false;
		name = "Sand";
	}
	
	public void draw(Graphics g) {
		g.drawImage(blockType, x, y, wd, wd, null);
	}
}
