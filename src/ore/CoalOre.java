package ore;

import data.DataOre;
import data.Point;
import entity.Player;
import item.Coal;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class CoalOre extends BaseOre {

    public CoalOre(Point position, DataOre dataOre) {
        super(position, 0, 0, dataOre);
        this.setImage(new WritableImage(RenderableHolder.ores.getPixelReader(), 32 * 12, 32 * 5, 32, 32));
        this.setWidth(this.getImage().getWidth() * this.getMutliply());
        this.setHeight(this.getImage().getHeight() * this.getMutliply());
    }

    @Override
    public void onBreak() {
        DataOre data = this.getDataOre();

        Coal coal = new Coal(data.getAmount(), data.getValue());
        Player.inventory.addItem(coal);
    }
}
