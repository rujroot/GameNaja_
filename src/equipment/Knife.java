package equipment;

import java.util.ArrayList;

import data.BaseObject;
import data.DataEntity;
import entity.Monster;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class Knife extends Melee{
	private WritableImage image = new WritableImage(RenderableHolder.Tileset.getPixelReader(), 607, 390, 637-607, 444-390);

    public Knife() {
		super();
		this.setImage(image);
		this.setAttackDamage(2);
		this.setAttackRange(150);
		this.setAttackDegree(70);
        this.setCooldownTime(100);
        this.setValue(30);
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
        Knife clone = (Knife) super.clone();
        return clone;
    }

}
