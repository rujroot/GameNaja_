package equipment;

import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class Bow extends Gun {

	private WritableImage image = new WritableImage(RenderableHolder.equipment1.getPixelReader(), 129*2, 0, (159-129)*2, (32-0)*2);
	
	public Bow() {
		super();
		this.setImage(image);

		this.setDamage(5);
		this.setCooldownTime(500);
		this.setValue(60);
	}

	@Override
    public Object clone() throws CloneNotSupportedException {
        Bow clone = (Bow) super.clone();
        return clone;
    }
	
}
