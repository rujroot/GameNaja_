package equipment;

import animation.AnimationObject;
import data.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class AttackObject extends AnimationObject{

    private double startAngle, endAngle, damage;

    public AttackObject(Point position, double width, double height) {
        super(position, width, height);
        this.setStartAngle(0);
        this.setEndAngle(360);
    }

    public AttackObject(Point position, double width, double height, double startAngle, double endAngle) {
        super(position, width, height);
        this.setStartAngle(startAngle);
        this.setEndAngle(endAngle);

        this.setEndTime(endAngle);
        this.setSpeedAnim(this.getEndTime() / 5);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillArc(this.getPosition().getX(), this.getPosition().getY(), this.getWidth(), this.getHeight(), this.getStartAngle(), this.getCurrTime(), ArcType.ROUND);
			
    }

    public double getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    public double getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(double endAngle) {
        this.endAngle = endAngle;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

}
