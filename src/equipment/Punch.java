package equipment;

import java.util.ArrayList;

import data.BaseObject;
import data.DataEntity;
import entity.Monster;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class Punch extends Melee {
	private WritableImage image = new WritableImage(RenderableHolder.equipment1.getPixelReader(), 261*2, 5*2, (285-261)*2, (26-5)*2);
	
	public Punch() {
		super();
		this.setImage(image);
		this.setAttackDamage(10);
		this.setAttackRange(150);
		this.setAttackDegree(30);
		this.setCooldownTime(700);
		this.setValue(40);
	}

	@Override
	public void attackAbility(ArrayList<BaseObject> intersectObjects) {

		for (BaseObject object : intersectObjects) {
			if (object instanceof Monster) {
				Monster monster = (Monster) object;
				DataEntity data = monster.getData();
				data.setHp(data.getHp() - this.getAttackDamage());
			}
		}
	}

	@Override
    public Object clone() throws CloneNotSupportedException {
        Punch clone = (Punch) super.clone();
        return clone;
    }

}
