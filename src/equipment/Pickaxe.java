package equipment;

import java.util.ArrayList;

import data.BaseObject;
import data.DataEntity;
import data.DataOre;
import entity.Monster;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;
import ore.BaseOre;

public class Pickaxe extends Melee{
    private WritableImage image = new WritableImage(RenderableHolder.equipment1.getPixelReader(), 191*2, 32*2, (223-191)*2, (61-32)*2);

    public Pickaxe() {
        super();

        this.setImage(image);
        this.setAttackDamage(1);
		this.setAttackRange(225);
		this.setAttackDegree(60);
    }

    @Override
    public void attackAbility(ArrayList<BaseObject> intersectObjects) {
        for(BaseObject object : intersectObjects){
            if(object instanceof BaseOre){
                BaseOre ore = (BaseOre) object;
                DataOre data = ore.getDataOre();
                data.setDurability(0);
            }else if(object instanceof Monster){
            	Monster monster = (Monster) object;
                DataEntity data = monster.getData();
                data.setHp(data.getHp() - this.getAttackDamage());
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Pickaxe clone = (Pickaxe) super.clone();
        return clone;
    }
    
}
