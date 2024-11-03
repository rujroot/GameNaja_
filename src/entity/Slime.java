package entity;

import data.DataEntity;
import equipment.BaseWeapon;
import equipment.SlimeAttack;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class Slime extends Monster{
	//slime_frames_all
	private WritableImage image = new WritableImage(RenderableHolder.slime.getPixelReader(), 18, 6, 35, 29);
	public Slime(String name, double width, double height, DataEntity data) {
		super(name, width, height, data);
		this.setImage(image);

		this.setEquipment(new SlimeAttack());
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


}
