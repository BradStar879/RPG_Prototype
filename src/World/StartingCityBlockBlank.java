package world;

import java.awt.Graphics;

public class StartingCityBlockBlank extends BaseWorldBlock{
	
	public StartingCityBlockBlank(int wd) {
		this.wd = wd;
		walkable = true;
		enter = true;
	}
	
	public void draw(Graphics g) {}
}