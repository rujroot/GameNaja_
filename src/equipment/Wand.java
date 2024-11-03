package equipment;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import logic.RenderableHolder;

public class Wand extends Gun {
	private WritableImage image = new WritableImage(RenderableHolder.equipment1.getPixelReader(), 162*2, 66*2, (191-162)*2, (94-66)*2);

	public Wand() {
		super();
		this.setImage(image);

		this.setDamage(1);
		this.setColor(Color.WHITE);
		this.setSpeed(15);
		this.setCooldownTime(200);
		this.setValue(45);
	}

	@Override
    public Object clone() throws CloneNotSupportedException {
        Wand clone = (Wand) super.clone();
        return clone;
    }

}
