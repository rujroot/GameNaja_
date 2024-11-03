package equipment.projectile;

import data.Point;
import entity.Team;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MagicArrow extends BaseProjectile{

    public MagicArrow(double width, double height, double damage, Point speed, Point Position, Team team) {
		super(width, height, damage, speed, Position, team);
	}

	@Override
	public void draw(GraphicsContext gc) {
		Point pos = this.getPosition();
		gc.setFill(Color.RED);
		gc.fillOval(pos.getX(), pos.getY(), this.getWidth(), this.getHeight());
	}
    
}
