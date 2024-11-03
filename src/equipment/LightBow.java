package equipment;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import logic.RenderableHolder;

public class LightBow extends Gun{

	private WritableImage image = new WritableImage(RenderableHolder.equipment1.getPixelReader(), 259, 198, 313-259, 253-198);

    public LightBow() {
		super();
		this.setImage(image);
		this.setCooldownTime(200);
		this.setArrowWidth(8);
		this.setArrowHight(8);
		this.setDamage(1);
		this.setColor(Color.YELLOW);
		this.setValue(60);
	}

	@Override
    public Object clone() throws CloneNotSupportedException {
        LightBow clone = (LightBow) super.clone();
        return clone;
    }
}
