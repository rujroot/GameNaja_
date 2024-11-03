package dungeon;

import data.BaseObject;
import data.Point;
import entity.Player;
import input.InputUtility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Cooldownable;
import logic.Main;
import logic.RenderableHolder;

public class Ladder extends BaseObject implements Cooldownable {

    private WritableImage image = new WritableImage(RenderableHolder.Tileset.getPixelReader(), 1156, 463, 1212 - 1156, 499 - 463);
    private double lastClickTime = 0, cooldownTime = 3000;
    
    public Ladder(Point position) {
        super(position, 0, 0);

        this.setWidth(image.getWidth());
        this.setHeight(image.getHeight());
    }

    @Override
    public void draw(GraphicsContext gc) {

        Point pos = this.getPosition();
		gc.drawImage(image, pos.getX(), pos.getY(), image.getWidth() , image.getHeight());

        if(inDistance()){
            gc.setFont(new Font("Arial", 24));
            gc.setFill(Color.BLACK);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(5);

            gc.strokeText("E", pos.getX() + 20, pos.getY() - 20, 24);
            gc.fillText("E", pos.getX() + 20, pos.getY() - 20, 24);
        }


    }

    public boolean inDistance(){
        Point playerPos = Player.player.getPosition();
        Point pos = this.getPosition();

        return Math.sqrt( Math.pow(playerPos.getX() - pos.getX() , 2) + Math.pow( playerPos.getY() - pos.getY(), 2) ) <= 250;
    }
    
    public void updateInput() throws CloneNotSupportedException{
        // Player interact with this entity
        if (InputUtility.getKeyPressed(KeyCode.E) && inDistance() && !onCooldown()){
			Main.getLogic().nextFloor();
        }
    }

    @Override
    public boolean onCooldown() {
        long currentTime = System.currentTimeMillis();
		if(currentTime - lastClickTime > cooldownTime) {
			lastClickTime = currentTime;
			return false;
		}else {
			return true;
		}
    }
}
