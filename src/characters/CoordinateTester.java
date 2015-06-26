package characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;




public class CoordinateTester {
	
	int x;
	int y;
	int velX = 0;
	int velY = 0;
	int speed = 5;
	
	public CoordinateTester(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick() {
		this.x += velX;
		this.y += velY;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 30, 60);
		g.setFont(new Font("Arial", Font.PLAIN, 15));
		g.drawString(x + ", " + y, x, y - 30);
	}
	
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_W) velY = -speed;
		if (k == KeyEvent.VK_A) velX = -speed;
		if (k == KeyEvent.VK_S) velY = speed;
		if (k == KeyEvent.VK_D) velX = speed;
		if (k == KeyEvent.VK_Q) speed -= 1;
		if (k == KeyEvent.VK_E) speed += 1;
	}
	
	public void keyReleased(int k) {
		if (k == KeyEvent.VK_W) velY = 0;
		if (k == KeyEvent.VK_A) velX = 0;
		if (k == KeyEvent.VK_S) velY = 0;
		if (k == KeyEvent.VK_D) velX = 0;
	}

}
