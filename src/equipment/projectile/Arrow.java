package equipment.projectile;

import data.Point;
import entity.Team;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Arrow extends BaseProjectile {

	private Color color = Color.YELLOW;

	public Arrow(double width, double height, double damage, Point speed, Point Position, Team team) {
		super(width, height, damage, speed, Position, team);
	}

	@Override
	public void draw(GraphicsContext gc) {
		Point pos = this.getPosition();
		gc.setFill(color);
		gc.fillRect(pos.getX(), pos.getY(), this.getWidth(), this.getHeight());
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}


	
}
