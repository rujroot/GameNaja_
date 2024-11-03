package equipment;

import java.util.ArrayList;

import data.BaseObject;
import data.DataEntity;
import entity.Monster;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class Sword extends Melee {

	private WritableImage image = new WritableImage(RenderableHolder.Tileset.getPixelReader(), 462, 164, 497-462, 252-164);

	public Sword() {
		super();

		this.setImage(image);
		this.setAttackDamage(100);
		this.setAttackRange(100);
		this.setAttackDegree(100);

	}

	@Override
	public void attackAbility(ArrayList<BaseObject> intersectObjects) {
		for(BaseObject object : intersectObjects){			
			if(object instanceof Monster){
				Monster monster = (Monster) object;
				DataEntity data = monster.getData();
				data.setHp(data.getHp() - this.getAttackDamage());
			}
		}
	}

	@Override
    public Object clone() throws CloneNotSupportedException {
        Sword clone = (Sword) super.clone();
        return clone;
    }

}
