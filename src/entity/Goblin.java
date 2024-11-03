package entity;

import data.DataEntity;
import equipment.BaseWeapon;
import equipment.GoblinAttack;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class Goblin extends Monster {

	private WritableImage image = new WritableImage(RenderableHolder.enemy.getPixelReader(), 485, 285, 63, 73);
	public Goblin(String name, double width, double height, DataEntity data) {
		super(name, width, height, data);
		this.setImage(image);
		this.setEquipment(new GoblinAttack());
		BaseWeapon weapon = this.getEquipment();
		weapon.setAttackDamage(data.getAtk());
	}
	
	@Override
	public void attack() {
		if(this.getTargetEntity() == null) return;

		Entity targetEntity = this.getTargetEntity();
		double findingRange = 500;
		double attackRange = 50;
		
		if(distance(targetEntity.getPosition()) <= findingRange){
			follow(targetEntity);
		}
		if(distance(targetEntity.getPosition()) <= attackRange){
			this.getEquipment().attack();
		}
	}

}
