package animation;

import data.BaseObject;
import data.DataEntity;
import data.Point;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleAttackAnimation extends AnimationObject implements Attackable {

    private double damage;

    public CircleAttackAnimation(Point position, double endTime, double speed, double damage) {
        super(position, 0, 0);

        this.setEndTime(endTime);
        this.setSpeedAnim(speed);
        this.setDamage(damage);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(this.getPosition().getX() - this.getCurrTime(), this.getPosition().getY() - this.getCurrTime(), 2 * this.getCurrTime() , 2 * this.getCurrTime());
    }

    @Override
    public void attack() {
        if(isIntersectCircle(Player.getPlayer())){
            Player player = Player.getPlayer();
            DataEntity data = player.getData();
            data.setHp(data.getHp() - this.getDamage() );
        }

    }

    public boolean isIntersectCircle(BaseObject object){
        boolean intersectDistant = false;
        Point pos = object.getPosition();

        // Check Distant <= attackRange
        double rectX1 = pos.getX();
        double rectY1 = pos.getY();
        double rectX2 = pos.getX() + object.getWidth();
        double rectY2 = pos.getY() + object.getHeight();

        double centerX = this.getPosition().getX() + this.getCurrTime() / 2;
        double centerY = this.getPosition().getY() + this.getCurrTime() / 2;

        double cx = Math.max(rectX1, Math.min(centerX, rectX2));
        double cy = Math.max(rectY1, Math.min(centerY, rectY2));

        intersectDistant = Math.sqrt(Math.pow(centerX - cx, 2) + Math.pow(centerY - cy, 2)) <= this.getCurrTime() / 2;
        return intersectDistant;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    
    
}
