package World;

import java.awt.Color;

public class WaterBlock extends BaseWorldBlock{
	
	public WaterBlock(int wd) {
		super(wd);
		walkable = false;
		col = Color.BLUE;
	}
	
}
