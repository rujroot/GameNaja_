package item;

import data.Point;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class IronIngot extends Item {

    public IronIngot(int amount, double value) {
        super(amount, value);
        this.setName("Iron Ingot");
        this.setImage(new WritableImage(RenderableHolder.ores.getPixelReader(), 32 * 6, 32 * 5, 32, 32));
    }

    public IronIngot(Point position, double width, double height, int z) {
        super(position, width, height, z);
        this.setName("Iron Ingot");
        this.setImage(new WritableImage(RenderableHolder.ores.getPixelReader(), 32 * 6, 32 * 5, 32, 32));
    }

    
}
