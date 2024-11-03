package drawing;

import data.BaseObject;
import data.Point;
import entity.Entity;
import inventory.SlotUI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Description extends BaseObject {

    private String text;
    private Entity entity;
    private SlotUI slotUI;

    public Description(Point position, double width, double height, Entity entity) {
        super(position, width, height);
        this.setEntity(entity);
    }

    public Description(Point position, double width, double height, SlotUI slotUI) {
        super(position, width, height);
        this.setSlotUI(slotUI);
    }

    @Override
    public void draw(GraphicsContext gc) {

        Point pos = this.getPosition();
        Point posParent = getPosParent();
        
        gc.setFont(new Font("Arial", 24));
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(5);

        gc.strokeText(text, pos.getX() + posParent.getX() + this.getWidth() / 5 , pos.getY() + posParent.getY() , this.getWidth());
        gc.fillText(text, pos.getX() + posParent.getX() + this.getWidth() / 5 , pos.getY() + posParent.getY(), this.getWidth());
    }

    public Point getPosParent(){
        if(entity != null) {
            return entity.getPosition();
        }
        else if(slotUI != null) {
            return slotUI.getRealPosition();
        }
        else return new Point(0, 0);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public SlotUI getSlotUI() {
        return slotUI;
    }

    public void setSlotUI(SlotUI slotUI) {
        this.slotUI = slotUI;
    }

    
    

}
