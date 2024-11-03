package item;

import data.Point;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class Diamond extends Item{
    public Diamond(int amount, double value) {
        super(amount, value);
        this.setName("Diamond");
        this.setImage(new WritableImage(RenderableHolder.ores.getPixelReader(), 32 * 12, 32 * 3, 32, 32));
    }

    public Diamond(Point position, double width, double height, int z) {
        super(position, width, height, z);
        this.setName("Diamond");
        this.setImage(new WritableImage(RenderableHolder.ores.getPixelReader(), 32 * 12, 32 * 3, 32, 32));
    }
}
