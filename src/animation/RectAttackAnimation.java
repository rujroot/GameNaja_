package animation;

import data.BaseObject;
import data.DataEntity;
import data.Point;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logic.Hitbox;

public class RectAttackAnimation extends AnimationObject implements Attackable{

     private double damage;

    public RectAttackAnimation(Point position, double endTime, double speed, double damage) {
        super(position, 0, 0);

        this.setEndTime(endTime);
        this.setSpeedAnim(speed);
        this.setDamage(damage);

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(this.getPosition().getX() - this.getCurrTime(), this.getPosition().getY() - this.getCurrTime(), 2 * this.getCurrTime() , 2 * this.getCurrTime());
    }

    public void attack() {
        if(isIntersectRect(Player.getPlayer())){
            Player player = Player.getPlayer();
            DataEntity data = player.getData();
            data.setHp(data.getHp() - this.getDamage() );
        }

    }

    public boolean isIntersectRect(BaseObject object){
        Hitbox A = new Hitbox(object.getPosition(),  object.getWidth(), object.getHeight());
        Hitbox B = new Hitbox(this.getPosition(), this.getCurrTime() / 2, this.getCurrTime() / 2);

        return A.isIntersect(B);
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    
}
