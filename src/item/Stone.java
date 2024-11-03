package item;

import data.Point;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class Stone extends Item{

    public Stone(int amount, double value) {
        super(amount, value);
        this.setName("Stone");
        this.setImage(new WritableImage(RenderableHolder.ores.getPixelReader(), 0, 128, 32, 32));
    }

    public Stone(Point position, double width, double height, int z) {
        super(position, width, height, z);
        this.setName("Stone");
        this.setImage(new WritableImage(RenderableHolder.ores.getPixelReader(), 0, 128, 32, 32));
    }
    
}
