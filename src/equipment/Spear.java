package equipment;

import java.util.ArrayList;

import data.BaseObject;
import data.DataEntity;
import entity.Monster;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class Spear extends Melee {
	private WritableImage image = new WritableImage(RenderableHolder.equipment1.getPixelReader(), 159*2, 95*2, (191-159)*2, (127-95)*2);
	
	public Spear() {
		super();
		this.setImage(image);
		this.setAttackDamage(2);
		this.setAttackRange(500);
		this.setAttackDegree(20);
		this.setValue(50);
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
        Spear clone = (Spear) super.clone();
        return clone;
    }

}
