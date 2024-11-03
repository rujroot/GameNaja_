package equipment;

import java.util.ArrayList;

import animation.AnimationController;
import data.BaseObject;
import data.Point;
import entity.Entity;
import entity.Monster;
import entity.NPC;
import entity.Player;
import logic.Cooldownable;
import logic.Main;

public abstract class Melee extends BaseWeapon implements Cooldownable{

    private double attackRange, attackDegree;
    private double cooldownTime = 300;
	private double lastClickTime;

    public abstract void attackAbility(ArrayList<BaseObject> intersectObjects);

    public Melee() {
        super(0, 0, 1);

        this.setAttackRange(150);
        this.setAttackDegree(60);

    }

    public boolean pointIntersectsRectangle(double x, double y, double rectX, double rectY, double rectWidth, double rectHeight) {
        if (x >= rectX && x <= rectX + rectWidth && y >= rectY && y <= rectY + rectHeight) {
            return true;
        }
        return false;
    }

    public double getDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public boolean intersectsCirclePart(BaseObject object, double angle) {
        
        boolean intersectDistant = false, intersectDegree = false;
        Point pos = object.getPosition();
        Entity entity = this.getEntity();

        // Check Distant <= attackRange
        double rectX1 = pos.getX();
        double rectY1 = pos.getY();
        double rectX2 = pos.getX() + object.getWidth();
        double rectY2 = pos.getY() + object.getHeight();

        double centerX = entity.getPosition().getX() + entity.getWidth() / 2;
        double centerY = entity.getPosition().getY() + entity.getHeight() / 2;

        double cx = Math.max(rectX1, Math.min(centerX, rectX2));
        double cy = Math.max(rectY1, Math.min(centerY, rectY2));

        intersectDistant = Math.sqrt(Math.pow(centerX - cx, 2) + Math.pow(centerY - cy, 2)) <= this.getAttackRange() / 2;

        // Check angle <= attackDegree
        Point posEntity = this.getEntity().getPosition();

        double angleObject = Math.atan2(pos.getY() - posEntity.getY(), pos.getX() - posEntity.getX());

        if (angleObject < 0) {
            angleObject += 2 * Math.PI;
        }
        angleObject = 360 - Math.toDegrees(angleObject);

        if(Math.max(angle, angleObject) >= 270 && Math.min(angle, angleObject) <= 90){
            intersectDegree = Math.abs(Math.max(angle, angleObject) - (Math.min(angle, angleObject) + 360)) <= this.getAttackDegree();
        }else{
            intersectDegree = Math.abs(angleObject - angle) <= this.getAttackDegree();
        }
        
        //System.out.println(intersectDistant+" "+intersectDegree);

        return intersectDistant && intersectDegree;
    }

    @Override
    public void attack(){

        if(onCooldown()) return;

        Entity entity = this.getEntity();
        double startAt = 0;
        if(entity instanceof Player){
            Player player = (Player) entity;
            startAt = player.getMouseAngle();
        }else if(entity instanceof NPC){
            NPC npc = (NPC) entity;
            startAt = npc.getEntityAngle();
        }else if(entity instanceof Monster){
            Monster monster = (Monster) entity;
            startAt = monster.getEntityAngle();
        }
 
        Point entityPosition = this.getEntity().getPosition();
        Point attackPosition = new Point(entityPosition.getX() - (getAttackRange() / 2) + (entity.getWidth() / 2), entityPosition.getY() - (getAttackRange() / 2) + (entity.getHeight() / 2));

        AttackObject attackObject = new AttackObject(attackPosition, getAttackRange(), getAttackRange(), startAt - getAttackDegree() / 2, getAttackDegree());
        Main.getLogic().addObject(attackObject);
        AnimationController.animations.add(attackObject);

        this.attackAbility(this.getIntersectObject(startAt));
    }    

    public ArrayList<BaseObject> getIntersectObject(double startAt){

        ArrayList<BaseObject> gameObjectContainer = Main.getLogic().getGameObjectContainer();
        ArrayList<BaseObject> instersectObject = new ArrayList<BaseObject>();

        for(BaseObject object : gameObjectContainer){
            if(object.isVisible() && this.intersectsCirclePart(object, startAt)){
                instersectObject.add(object);
            }
        }

        return instersectObject;
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

    // getter setter

    public double getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(double attackRange) {
        this.attackRange = attackRange;
    }

    public double getAttackDegree() {
        return attackDegree;
    }

    public void setAttackDegree(double attackDegree) {
        this.attackDegree = attackDegree;
    }

    public double getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(double cooldownTime) {
        this.cooldownTime = cooldownTime;
    }
   
    

}
