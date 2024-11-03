package entity;

import data.DataEntity;
import equipment.BaseWeapon;
import equipment.PunchMonster;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class Zombie extends Monster {
	private double poisonDamage;
	private WritableImage image = new WritableImage(RenderableHolder.enemy.getPixelReader(), 255, 407, 54, 72);

	public Zombie(String name, double Height, double Width, DataEntity data) {
		super(name, Height, Width, data);
		this.setPoisonDamage(poisonDamage);
		this.setImage(image);

		this.setEquipment(new PunchMonster());
		BaseWeapon weapon = this.getEquipment();
		weapon.setAttackDamage(data.getAtk());
	}

	@Override
	public void attack() {
		if(this.getTargetEntity() == null) return;

		Entity targetEntity = this.getTargetEntity();

		double findingRange = 600;
		double attackRange = 100;
		
		if(distance(targetEntity.getPosition()) <= findingRange){
			follow(targetEntity);
		}
		if(distance(targetEntity.getPosition()) <= attackRange){
			this.getEquipment().attack();
		}
	}
	
	public double getPoisonDamage() {
		return poisonDamage;
	}

	public void setPoisonDamage(double poisonDamage) {
		this.poisonDamage = poisonDamage;
	}

}
