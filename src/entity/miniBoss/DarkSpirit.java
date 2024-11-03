package entity.miniBoss;

import animation.AnimationController;
import animation.ElementAttackAnimation;
import data.DataEntity;
import data.Point;
import equipment.DarkSpiritAttack;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import logic.Main;
import logic.RenderableHolder;

public class DarkSpirit extends MiniBossEntity {

    private WritableImage image = new WritableImage(RenderableHolder.Tileset.getPixelReader(), 532, 712, 619 - 532, 831 - 712);

    public DarkSpirit(String name, double width, double height, DataEntity data) {
        super(name, width, height, data);
        this.setImage(image);

        this.setEquipment(new DarkSpiritAttack());
    }

    public void specialAttack(){
        this.setCooldownTime(1000);
        if(!onCooldown()){
            Point pos = this.getPosition();
            Point newPos = new Point(pos.getX() + Math.random() * 400 - 200, pos.getY() + Math.random() * 400 - 200);
            ElementAttackAnimation element = new ElementAttackAnimation(
                                            newPos, 
											1000, 
											Math.random()*5+1, 
											Math.random() * 100 + 50, 
											new Point(0, 0));
            element.setColor(Color.BLACK);
            element.setDamage(10);
            AnimationController.animations.add(element);
            Main.getLogic().addObject(element);
        }
    }
    
}
