package animation;

import data.Point;
import entity.Player;
import entity.Team;
import equipment.projectile.MagicArrow;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logic.Main;

public class ElementAttackAnimation extends AnimationObject implements Attackable {

    private double radius, cooldown, damage = 30;
    private Point center, offset = new Point(0, 0), targetPoint;
    private Color color = Color.BLUE;

    public ElementAttackAnimation(Point position, double endTime, double speed, double radius, Point offset) {
        super(position, 0, 0);

        this.setEndTime(endTime);
        this.setSpeedAnim(speed);
        this.setRadius(radius);
        this.setCenter(position);
        this.setOffset(offset);

    }

    public ElementAttackAnimation(Point position, double endTime, double speed, double radius) {
        super(position, 0, 0);

        this.setEndTime(endTime);
        this.setSpeedAnim(speed);
        this.setRadius(radius);
        this.setCenter(position);

    }

    @Override
    public void attack() {
        Point target = this.getTargetPoint();
        target.setX(target.getX() * 30);
        target.setY(target.getY() * 30);
        MagicArrow magicArrow = new MagicArrow(30 , 30, this.getDamage(), this.getTargetPoint(), this.getPosition(), Team.Monster);
        Main.getLogic().addObject(magicArrow);
    }

    @Override
    public void draw(GraphicsContext gc) {
        double currTime = this.getCurrTime();
        double r = this.getRadius();

        double x = Math.sin(Math.toRadians(currTime)) * r;
        double y = Math.cos(Math.toRadians(currTime)) * r;

        Point pos = this.getCenter();
        this.setPosition(new Point(pos.getX() + x + offset.getX(), pos.getY() + y + offset.getY()));
        
        gc.setFill(color);
        gc.fillOval(this.getPosition().getX() , this.getPosition().getY(), 30 , 30);
        
        Point pp = Player.getPlayer().getPosition();

        Point p = new Point(this.getPosition().getX() - pp.getX() , this.getPosition().getY() - pp.getY());
        double distance = Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());

        this.setTargetVector(new Point(-p.getX() / distance, -p.getY() / distance));
        
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Point getOffset() {
        return offset;
    }

    public void setOffset(Point offset) {
        this.offset = offset;
    }

    public Point getTargetPoint() {
        return targetPoint;
    }

    public void setTargetVector(Point targetPoint) {
        this.targetPoint = targetPoint;
    }

    public double getCooldown() {
        return cooldown;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }


    
    

}
