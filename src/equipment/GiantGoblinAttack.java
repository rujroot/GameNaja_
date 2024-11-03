package equipment;

import java.util.ArrayList;

import data.BaseObject;
import entity.NPC;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import data.DataEntity;

public class GiantGoblinAttack extends Melee {

    public GiantGoblinAttack() {
		super();
		this.setAttackDamage(2);
		this.setAttackRange(300);
		this.setAttackDegree(180);
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
