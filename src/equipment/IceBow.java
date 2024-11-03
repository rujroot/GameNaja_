package equipment;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import logic.RenderableHolder;

public class IceBow extends Gun {

	private WritableImage image = new WritableImage(RenderableHolder.equipment1.getPixelReader(), 258, 66, 59, 59);

    public IceBow() {
		super();
		this.setImage(image);
		this.setDamage(10);
		this.setCooldownTime(1000);
		this.setArrowWidth(20);
		this.setArrowHight(20);
		this.setColor(Color.BLUE);
		this.setValue(80);
	}

	@Override
    public Object clone() throws CloneNotSupportedException {
        IceBow clone = (IceBow) super.clone();
        return clone;
    }
}
