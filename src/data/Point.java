package data;

public class Point {
	private double x, y;
	public Point(double x, double y) {
		this.setX(x);
		this.setY(y);
	}
	public double getX() {
		return this.x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return this.y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public String toString() {
		return "X = " + this.getX() + " " + "Y = " + this.getY() ;
	}
	public boolean equals(Point P){
		return this.getX() == P.getX() && this.getY() == P.getY();
	}

	public void unit(){
		double dis = Math.sqrt((this.x * this.x) + (this.y * this.y));
		this.setX(x / dis);
		this.setY(y / dis);
	}
	public void multiply(double value){
		this.setX(x * value);
		this.setY(y * value);
	}
	public double distant(Point P){
		double X = (this.x - P.getX());
		double Y = (this.y - P.getY());
		return Math.sqrt((X * X) + (Y * Y));
	}
	public Point plus(Point P){
		return new Point(this.getX() + P.getX(), this.getY() + P.getY());
	}
	public Point minus(Point P) {
		return new Point(this.getX() - P.getX(), this.getY() - P.getY());
	}

}
