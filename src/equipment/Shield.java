package equipment;

import java.util.ArrayList;

import data.BaseObject;
import data.DataEntity;
import entity.Monster;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;
public class Shield extends Melee {
	//topleft -- bottomright
	private WritableImage image = new WritableImage(RenderableHolder.equipment1.getPixelReader(), 293*2, 0, (314-293)*2, (31-0)*2);
	
	public Shield() {
		super();
		this.setImage(image);
		this.setAttackDamage(3);
		this.setAttackRange(150);
		this.setAttackDegree(360);
		this.setCooldownTime(500);
		this.setValue(70);
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
        Shield clone = (Shield) super.clone();
        return clone;
    }

}
