package ore;

import data.DataOre;
import data.Point;
import entity.Player;
import item.Diamond;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class DiamondOre extends BaseOre {
	//private WritableImage image = new WritableImage(RenderableHolder.ores.getPixelReader(), 32 * 12, 32 * 3, 32, 32);
    public DiamondOre(Point position, DataOre dataOre) {
        super(position, 0, 0, dataOre);
        this.setImage(new WritableImage(RenderableHolder.ores.getPixelReader(), 32 * 12, 32 * 3, 32, 32));
        this.setWidth(this.getImage().getWidth() * this.getMutliply());
        this.setHeight(this.getImage().getHeight() * this.getMutliply());
    }

    @Override
    public void onBreak() {
        DataOre data = this.getDataOre();
        Diamond diamond = new Diamond(data.getAmount(), data.getValue());
        Player.inventory.addItem(diamond);
    }
}
