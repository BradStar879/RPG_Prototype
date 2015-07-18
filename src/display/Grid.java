package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Grid {
	
	int wd;
	int[] gridX;
	int[] gridY;
	int row0;
	int row1;
	int row2;
	int row3;
	int x0;
	int x1;
	int x2;
	int x3;
	int x4;
	int x5;
	int x6;
	int x7;
	int x8;
	int x9;
	int x10;
	int x11;
	int x12;
	int x13;
	int x14;
	int x15;
	
	int[] topLX;
	int[] topMX;
	int[] topRX;
	int[] midLX;
	int[] midMX;
	int[] midRX;
	int[] botLX;
	int[] botMX;
	int[] botRX;
	
	int[] topLY;
	int[] topMY;
	int[] topRY;
	int[] midLY;
	int[] midMY;
	int[] midRY;
	int[] botLY;
	int[] botMY;
	int[] botRY;
	
	Polygon topL;
	Polygon topM;
	Polygon topR;
	Polygon midL;
	Polygon midM;
	Polygon midR;
	Polygon botL;
	Polygon botM;
	Polygon botR;
	
	boolean[] beingAttacked = new boolean[9];
	
	public Grid(int wd, int[] gridX, int[] gridY) {
		
		this.wd = wd;
		this.gridX = gridX;
		this.gridY = gridY;
		row0 = gridY[0];
		row1 = wd * 105 / 256;
		row2 = wd * 79 / 256;
		row3 = gridY[1];
		x0 = gridX[0];
		x1 = (wd / 5 + (((wd - (wd / 5)) - wd / 5) / 3));
		x2 = wd / 5 + (2 * ((wd - (wd / 5)) - wd / 5) / 3);
		x3 = gridX[3];
		x4 = wd * 65 / 256;
		x5 = wd * 107 / 256;
		x6 = wd * 149 / 256;
		x7 = wd * 191 / 256;
		x8 = wd * 19 / 64;
		x9 = wd * 111 / 256;
		x10 = wd * 145 / 256;
		x11 = wd * 45 / 64;
		x12 = gridX[1];
		x13 = wd / 3 + ((wd - (wd / 3) - wd / 3) / 3);
		x14 =  wd / 3 + (2 * (wd - (wd / 3) - wd / 3) / 3);
		x15 = gridX[2];
		
		topLX = new int[]{x8, x12, x13, x9};
		topMX = new int[]{x9, x13, x14, x10};
		topRX = new int[]{x10, x14, x15, x11};
		midLX = new int[]{x4, x8, x9, x5};
		midMX = new int[]{x5, x9, x10, x6};
		midRX = new int[]{x6, x10, x11, x7};
		botLX = new int[]{x0, x4, x5, x1};
		botMX = new int[]{x1, x5, x6, x2};
		botRX = new int[]{x2, x6, x7, x3};
		
		topLY = new int[]{row2, row3, row3, row2};
		topMY = new int[]{row2, row3, row3, row2};
		topRY = new int[]{row2, row3, row3, row2};
		midLY = new int[]{row1, row2, row2, row1};
		midMY = new int[]{row1, row2, row2, row1};
		midRY = new int[]{row1, row2, row2, row1};
		botLY = new int[]{row0, row1, row1, row0};
		botMY = new int[]{row0, row1, row1, row0};
		botRY = new int[]{row0, row1, row1, row0};
		
		topL = new Polygon(topLX, topLY, 4);
		topM = new Polygon(topMX, topMY, 4);
		topR = new Polygon(topRX, topRY, 4);
		midL = new Polygon(midLX, midLY, 4);
		midM = new Polygon(midMX, midMY, 4);
		midR = new Polygon(midRX, midRY, 4);
		botL = new Polygon(botLX, botLY, 4);
		botM = new Polygon(botMX, botMY, 4);
		botR = new Polygon(botRX, botRY, 4);
		
		for(int i = 0; i < 9; i++) {
			beingAttacked[i] = false;
		}
		
	}
	
	public void draw(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.drawPolygon(gridX, gridY, 4);
		//Left vertical
		g.drawLine(wd / 5 + (((wd - (wd / 5)) - wd / 5) / 3), gridY[0], wd / 3 + ((wd - (wd / 3) - wd / 3) / 3), gridY[1]);
		//Right vertical
		g.drawLine(wd / 5 + (2 * ((wd - (wd / 5)) - wd / 5) / 3), gridY[0], wd / 3 + (2 * (wd - (wd / 3) - wd / 3) / 3), gridY[1]);
		//Top horizontal
		g.drawLine(wd * 19 / 64, wd * 79 / 256, wd * 45 / 64, wd * 79 / 256);
		//Bottom horizontal
		g.drawLine(wd * 65 / 256, wd * 105 / 256, wd * 191 / 256, wd * 105 / 256);
		
		g.setColor(Color.RED);
		if(beingAttacked[0]) g.fillPolygon(topL);
		if(beingAttacked[1]) g.fillPolygon(topM);
		if(beingAttacked[2]) g.fillPolygon(topR);
		if(beingAttacked[3]) g.fillPolygon(midL);
		if(beingAttacked[4]) g.fillPolygon(midM);
		if(beingAttacked[5]) g.fillPolygon(midR);
		if(beingAttacked[6]) g.fillPolygon(botL);
		if(beingAttacked[7]) g.fillPolygon(botM);
		if(beingAttacked[8]) g.fillPolygon(botR);
		
		
	}
	
	public void attackWarning(int pos) {
		beingAttacked[pos] = true;
		
	}
	
	public void attackWarningOff(int pos) {
		beingAttacked[pos] = false;
	}

}
