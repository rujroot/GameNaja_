package data;

public class DataEntity {
	double hp, atk, def, spd, poisonStatus, maxHP;
	
	public DataEntity(double hp, double atk, double def, double spd, double poisonStatus) {
		this.setAtk(atk);
		this.setMaxHP(hp);
		this.setHp(hp);
		this.setDef(def);
		this.setSpd(spd);
		this.setPoisonStatus(poisonStatus);
	}
	
	public DataEntity(double hp, double atk, double def, double spd) {
		this.setAtk(atk);
		this.setMaxHP(hp);
		this.setHp(hp);
		this.setDef(def);
		this.setSpd(spd);
		this.setPoisonStatus(0);
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		if(hp <= 0){
			this.hp = 0;
		}else if(hp>this.getMaxHP()){
			this.hp = this.getMaxHP();
		}else {
			this.hp = hp;
		}
		
	}

	public double getAtk() {
		return atk;
	}

	public void setAtk(double atk) {
		this.atk = atk;
	}

	public double getDef() {
		return def;
	}

	public void setDef(double def) {
		this.def = def;
	}

	public double getSpd() {
		return spd;
	}

	public void setSpd(double spd) {
		this.spd = spd;
	}

	public double getPoisonStatus() {
		return poisonStatus;
	}

	public void setPoisonStatus(double poisonStatus) {
		this.poisonStatus = poisonStatus;
	}

	public double getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(double maxHP) {
		this.maxHP = maxHP;
	}
	
}
