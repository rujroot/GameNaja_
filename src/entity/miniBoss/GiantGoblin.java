package entity.miniBoss;

import data.DataEntity;
import equipment.GiantGoblinAttack;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class GiantGoblin extends MiniBossEntity {

    private WritableImage image = new WritableImage(RenderableHolder.Tileset.getPixelReader(), 409, 728, 487-409, 831-728);

    public GiantGoblin(String name, double width, double height, DataEntity data) {
        super(name, width, height, data);
        this.setImage(image);

        this.setEquipment(new GiantGoblinAttack());
    }

    @Override
    public void specialAttack() {
       
    }
    
    
}
