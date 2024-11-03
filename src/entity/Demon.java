package entity;

import animation.AnimationController;
import animation.ElementAttackAnimation;
import data.DataEntity;
import data.Point;
import equipment.BaseWeapon;
import equipment.DarkSpiritAttack;
import javafx.scene.paint.Color;
import logic.Main;
import logic.RenderableHolder;

public class Demon extends Monster{
	private int magicAttack;
	
	public Demon(String name, double width, double height, DataEntity data) {
		super(name, width, height, data);
		this.setMagicAttack(magicAttack);
		this.setImage(RenderableHolder.demon);
		this.setEquipment(new DarkSpiritAttack());
		BaseWeapon weapon = this.getEquipment();
		weapon.setAttackDamage(data.getAtk());
	}

	@Override
	public void attack() {
		
		if(this.getTargetEntity() == null) return;

		Entity targetEntity = this.getTargetEntity();

		double findingRange = 300;
		double attackRange = 400;
		
		if(distance(targetEntity.getPosition()) <= findingRange){
			follow(targetEntity);
		}
		if(distance(targetEntity.getPosition()) <= attackRange){
			this.setCooldownTime(1000);
			if(!onCooldown()) summonMagicArrow();
		}
	}

	public void summonMagicArrow(){
		ElementAttackAnimation element = new ElementAttackAnimation(this.getPosition(), 
											1000, 
											4, 
											100, 
											new Point(this.getWidth()/3, this.getHeight()/3));
		element.setColor(Color.BLACK);
		element.setDamage(this.getData().getAtk());
		AnimationController.animations.add(element);
		Main.getLogic().addObject(element);
	}

	public int getMagicAttack() {
		return magicAttack;
	}


	public void setMagicAttack(int magicAttack) {
		this.magicAttack = magicAttack;
	}
	
	
}
