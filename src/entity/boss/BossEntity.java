package entity.boss;

import data.DataEntity;
import entity.Monster;
import logic.Hitbox;

public abstract class BossEntity extends Monster{

    private Hitbox bossHitbox;

    public BossEntity(String name, DataEntity data, Hitbox bHitbox) {
        super(name, 0, 0, data);
        this.bossHitbox = bHitbox;
    }

    public Hitbox getBossHitbox() {
        return bossHitbox;
    }

    public void setBossHitbox(Hitbox bossHitbox) {
        this.bossHitbox = bossHitbox;
    }

    
    
}
