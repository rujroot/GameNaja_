package entity;

import data.DataEntity;
import data.Point;
import equipment.BaseWeapon;
import equipment.SkeletionBow;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class Skeleton extends Monster {
	private int protection;
	private WritableImage image = new WritableImage(RenderableHolder.enemy.getPixelReader(), 337, 411, 52, 68);

	public Skeleton(String name, double Height, double Width, DataEntity data) {
		super(name, Height, Width, data);
		this.setProtection(protection);
		this.setImage(image);
		this.setEquipment(new SkeletionBow());
		BaseWeapon weapon = this.getEquipment();
		weapon.setAttackDamage(data.getAtk());
	}

	public int getProtection() {
		return protection;
	}

	public void setProtection(int protection) {
		this.protection = protection;
	}

	public Point getEntityVector(){
		Point pos = this.getPosition();
		Entity targetEntity = this.getTargetEntity();

		double posX = targetEntity.getPosition().getX();
		double posY = targetEntity.getPosition().getY();
		Point vector = new Point(posX - pos.getX(), posY - pos.getY());
		vector.unit();

		return vector;
	}


	@Override
	public void attack() {
		if(this.getTargetEntity() == null) return;

		Entity targetEntity = this.getTargetEntity();

		double findingRange = 400;
		double attackRange = 400;
		
		if(distance(targetEntity.getPosition()) <= findingRange){
			follow(targetEntity);
		}
		if(distance(targetEntity.getPosition()) <= attackRange){
			this.getEquipment().attack();
		}
	}


}
