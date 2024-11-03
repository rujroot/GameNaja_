package logic;

import data.Point;

public class Hitbox {
	private Point Position;
	private double length, width;
	
	public Hitbox(Point Position, double width, double length) {
		this.setPosition(Position);
		this.setLength(length);
		this.setWidth(width);
	}
	
	public boolean isIntersect(Hitbox A) {
		Point TLA = A.getPosition();
		double lengthA = A.getLength();
		double widthA = A.getWidth();
		
		Point TLB = this.getPosition();
		double lengthB = this.getLength();
		double widthB = this.getWidth();
		
		// Intersect X
		if(Math.min( TLA.getX() + widthA, TLB.getX() + widthB) >= Math.max(TLA.getX(), TLB.getX())) {
			// Intersect Y
			if(Math.min( TLA.getY() + lengthA, TLB.getY() + lengthB) >= Math.max(TLA.getY(), TLB.getY())) {
				return true;
			}
			return false;
		}
		return false;
		
			
	}
	
	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public Point getPosition() {
		return Position;
	}

	public void setPosition(Point position) {
		Position = position;
	}


}
