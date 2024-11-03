package entity.miniBoss;

import data.DataEntity;
import entity.Monster;

public abstract class MiniBossEntity extends Monster{

    public MiniBossEntity(String name, double width, double height, DataEntity data) {
        super(name, width, height, data);
    }

    public abstract void specialAttack();
    
}
