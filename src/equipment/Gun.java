package equipment;

import data.Point;
import entity.Entity;
import entity.Monster;
import entity.NPC;
import entity.Player;
import entity.Skeleton;
import entity.Team;
import equipment.projectile.Arrow;
import input.InputUtility;
import javafx.scene.paint.Color;
import logic.Cooldownable;
import logic.Main;

public abstract class Gun extends BaseWeapon implements Cooldownable{

    private double cooldownTime;
	private double lastClickTime;
    private double arrowWidth = 10, arrowHight = 10, damage = 1, speed = 30;
    private Color color = Color.YELLOW;

    public Gun() {
        super(0, 0, 1);
    }

    public void attack() {
		if(!onCooldown()) {
            Entity entity = this.getEntity();
            if(entity instanceof Player){
                // find direction to shoot
                Point pos = this.getResolutionPostion();
                double mouseX = InputUtility.mouseX, mouseY = InputUtility.mouseY;
                Point vector = new Point(mouseX - pos.getX(), mouseY - pos.getY());
                vector.unit();
                vector.multiply(this.getSpeed());

                // damge, speed, pos
                Arrow arrow = new Arrow(this.getArrowWidth() , this.getArrowHight() , this.getAttackDamage(), vector, this.getPosition(), Team.Player);
                arrow.setDamage(this.getDamage());
                arrow.setColor(this.getColor());
                Main.getLogic().addObject(arrow);
            }else if(entity instanceof NPC){
                // find direction to shoot
                Point vector = ((NPC) entity).getEntityVector();
                vector.multiply(this.getSpeed());

                // damge, speed, pos
                Arrow arrow = new Arrow(this.getArrowWidth() , this.getArrowHight() , this.getAttackDamage(), vector, this.getPosition(), Team.Player);
                arrow.setDamage(this.getDamage());
                arrow.setColor(this.getColor());
                Main.getLogic().addObject(arrow);
            }else if(entity instanceof Monster){
                Point vector = ((Skeleton) entity).getEntityVector();
                vector.multiply(this.getSpeed());

                // damge, speed, pos
                Arrow arrow = new Arrow(this.getArrowWidth() , this.getArrowHight() , this.getAttackDamage(), vector, this.getPosition(), Team.Monster);
                arrow.setDamage(this.getDamage());
                arrow.setColor(this.getColor());
                Main.getLogic().addObject(arrow);
            }
		}
	}

    @Override
	public boolean onCooldown() {
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastClickTime > cooldownTime) {
			lastClickTime = currentTime;
			return false;
		}else {
			return true;
		}
	}
  
    // gatter setter

    public double getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(double cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public double getArrowWidth() {
        return arrowWidth;
    }

    public void setArrowWidth(double arrowWidth) {
        this.arrowWidth = arrowWidth;
    }

    public double getArrowHight() {
        return arrowHight;
    }

    public void setArrowHight(double arrowHight) {
        this.arrowHight = arrowHight;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}
