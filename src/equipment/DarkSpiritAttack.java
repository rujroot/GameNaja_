package equipment;

import java.util.ArrayList;

import data.BaseObject;
import data.DataEntity;
import entity.NPC;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;

public class DarkSpiritAttack extends Melee {
    
    public DarkSpiritAttack() {
		super();
		this.setAttackDamage(0.5);
		this.setAttackRange(200);
		this.setAttackDegree(100);
        this.setCooldownTime(1000);
	}

    @Override
    public void draw(GraphicsContext gc){
        
	}

	@Override
	public void attackAbility(ArrayList<BaseObject> intersectObjects) {
		for (BaseObject object : intersectObjects) {
			if (object instanceof Player) {
				Player player = (Player) object;
				DataEntity data = player.getData();
				data.setHp(data.getHp() - this.getAttackDamage());
			}else if (object instanceof NPC) {
				NPC npc = (NPC) object;
				DataEntity data = npc.getData();
				data.setHp(data.getHp() - this.getAttackDamage());
			}
		}
	}
}