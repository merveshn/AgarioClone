package tr.org.kamp.linux.agarioclone.model;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Player extends GameObject {

	private int speed;
	private String playerName;

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public Player(int x, int y, int radius, Color color, int speed,String playerName) {
		super(x, y, radius, color);
		this.speed = speed;
		this.playerName=playerName;
	}
	@Override
	public void setRadius(int radius) {
		// TODO Auto-generated method stub
		super.setRadius(radius);
		if(getRadius()<5 )
			setRadius(5);
		else if(getRadius()>500)
			setRadius(250);
		
	}
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		super.draw(g2d);
		FontMetrics fontMetris=g2d.getFontMetrics(g2d.getFont());
		int width=fontMetris.stringWidth(playerName);
		int nameX=getX()+ (getRadius()-width)/2;
		int nameY=getY() - fontMetris.getHeight();
		g2d.drawString(playerName, nameX, nameY);
	}
	
	public int getSpeed() {
		return speed;
	}

	
}
