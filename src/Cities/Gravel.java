package Cities;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import world.BaseWorldBlock;

public class Gravel extends BaseWorldBlock{
	
Image blockType = new ImageIcon("Sprites/Gravel.png").getImage();
	
	public Gravel(int wd) {
		this.wd = wd;
		walkable = true;
	}
	
	public void draw(Graphics g) {
		g.drawImage(blockType, x, y, wd, wd, null);
	}

}

