package World;

import java.awt.Color;



public class GrassBlock extends BaseWorldBlock{
	
	public GrassBlock(int wd) {
		super(wd);
		walkable = true;
		col = Color.GREEN;
	}
}
