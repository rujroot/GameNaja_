package entity;

import java.util.ArrayList;

import data.BaseObject;
import data.DataEntity;
import data.Point;
import equipment.BaseWeapon;
import equipment.Melee;
import equipment.PunchMonster;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import logic.Cooldownable;
import logic.Hitbox;

public class Monster extends Entity implements Cooldownable {

	private double cooldownTime = 1000;
	private double lastClickTime = 0;
	private Image image;
	private BaseWeapon equipment;
	private Entity targetEntity;
	
	public Monster(String name, double width, double height, DataEntity data) {
		super(name, width, height, data);
	}

	@Override
	public void draw(GraphicsContext gc) {
		Point pos = this.getPosition();
		gc.drawImage(image, pos.getX(), pos.getY(), image.getWidth(), image.getHeight());
		this.drawHP(gc);

	}

	public boolean isIntersectPlayer() {
		// check 2 rectangle
		Point MonBox = new Point(this.getPosition().getX()-10,this.getPosition().getY()-10);
		Hitbox A = new Hitbox(MonBox, this.getWidth()+20, this.getHeight()+20);
		Hitbox B = new Hitbox(Player.getPlayer().getPosition(), Player.getPlayer().getWidth(), Player.getPlayer().getHeight());
 		return A.isIntersect(B);
	}

	@Override
	public void attack() {
		if(equipment == null) this.setEquipment(new PunchMonster());
		// decrease player's hp

		if(targetEntity == null) return;

		double findingRange = 500;
		double attackRange = 150;

		if(distance(targetEntity.getPosition()) >= findingRange){
			follow(targetEntity);
		}
		if(equipment instanceof Melee){
			if(distance(targetEntity.getPosition()) <= attackRange){
				equipment.attack();
			}
			follow(targetEntity);
			
		}
		
	}

	public void follow(Entity entity) {
		if(entity == null) return;
		
		Point pp = entity.getPosition();

		double px = pp.getX(), py = pp.getY();

		Point p = new Point(this.getPosition().getX() - px, this.getPosition().getY() - py);
		double distance = Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());

		DataEntity data = this.getData();

		if (distance > 60 && distance <= 700) {
			this.move(-p.getX() / distance * data.getSpd(), 0);
			this.move(0, -p.getY() / distance * data.getSpd());
		}

	}

	public void findNearestEntity(ArrayList<BaseObject> gameObjectContainer) {
		Entity nearestEntity = Player.getPlayer();
		double minDistance = 1e9;
		double maxDistance = 500;

		for (int i = gameObjectContainer.size() - 1; i >= 0; i--) {
			BaseObject object = gameObjectContainer.get(i);
			if (((object instanceof NPC) || (object instanceof Player)) && distance(object.getPosition()) <= maxDistance) {
				if (distance(object.getPosition()) <= minDistance) {
					nearestEntity = (Entity) object;
					minDistance = distance(object.getPosition());
				}
			}
		}

		targetEntity = nearestEntity;
	}

	public double distance(Point pos1) {
		Point pos2 = this.getPosition();
		return Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2));
	}

	public double getEntityAngle() {

		Point pos = this.getPosition();

		double posX = targetEntity.getPosition().getX();
		double posY = targetEntity.getPosition().getY();

		double startAt = Math.atan2(posY - (pos.getY() + this.getHeight() / 2),
				posX - (pos.getX() + this.getWidth() / 2));
		if (startAt < 0) {
			startAt += 2 * Math.PI;
		}
		return 360 - Math.toDegrees(startAt);
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
	
	public BaseWeapon getEquipment() {
		return equipment;
	}

	public void setEquipment(BaseWeapon equipment) {
		this.equipment = equipment;
		equipment.setEntity(this);
	}

	public void setImage(WritableImage image) {
        this.image = image;
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
    }
	public void setImage(Image image) {
        this.image = image;
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
    }

	public Entity getTargetEntity() {
		return targetEntity;
	}

	public void setTargetEntity(Entity targetEntity) {
		this.targetEntity = targetEntity;
	}

	public double getCooldownTime() {
		return cooldownTime;
	}

	public void setCooldownTime(double cooldownTime) {
		this.cooldownTime = cooldownTime;
	}

	

	
}
