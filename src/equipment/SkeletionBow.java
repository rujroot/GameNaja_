package equipment;

import javafx.scene.paint.Color;

public class SkeletionBow extends Gun{

	public SkeletionBow() {
		super();
		this.setCooldownTime(1000);
		this.setArrowWidth(15);
		this.setArrowHight(15);
		this.setColor(Color.WHITE);
	}

	@Override
    public Object clone() throws CloneNotSupportedException {
        Bow clone = (Bow) super.clone();
        return clone;
    }
}
