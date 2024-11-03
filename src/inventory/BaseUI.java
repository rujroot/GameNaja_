package inventory;

import data.BaseObject;
import data.Point;
import entity.Entity;
import equipment.BaseWeapon;
import item.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import logic.RenderableHolder;

public class BaseUI extends BaseObject{
    
    private int maxIndex, currIndex = 0, selectIndex = 0;
    private double offset;
    private SlotUI[] posIndex;
    private boolean visible = false;
    private Entity entity;
    private Point basePoint;

    private Image backUI = RenderableHolder.backUI;
    private WritableImage backBtwUI = new WritableImage(backUI.getPixelReader(), 19, 0, 60, 96);

    public BaseUI(Point stPos, double width, double height, int amount, double offset, Entity entity){
        super(stPos, width, height);

        this.setMaxIndex(amount);
        this.setOffset(offset);
        this.setEntity(entity);
        posIndex = new SlotUI[amount];

        if(offset == 0){
            for(int i = 0; i < maxIndex - 1; ++i){

                if(i == 0){
                    posIndex[i] = new SlotUI(new Point(stPos.getX() - 18 , stPos.getY()),
                                            backUI.getWidth(), 
                                            backUI.getHeight(), 
                                            backUI, entity);
                    posIndex[i].getOffset().setX(18);
                    posIndex[i].getOffset().setY(18);

                    posIndex[maxIndex - 1] = new SlotUI(new Point(stPos.getX() + (backBtwUI.getWidth() * (maxIndex - 1)) - 18 , 
                                            stPos.getY()) , 
                                            backUI.getWidth(), 
                                            backUI.getHeight(), backUI , entity);
                    posIndex[maxIndex - 1].getOffset().setX(18);
                    posIndex[maxIndex - 1].getOffset().setY(18);

                }else{
                    posIndex[i] = new SlotUI(new Point(stPos.getX() + (backBtwUI.getWidth() * i) , stPos.getY()) , backBtwUI.getWidth(), backBtwUI.getHeight(), backBtwUI, entity);
                    posIndex[i].getOffset().setX(0);
                    posIndex[i].getOffset().setY(18);
                }

            }
        }else{
            for(int i = 0; i < maxIndex; ++i){

                posIndex[i] = new SlotUI(new Point(stPos.getX() + ((backUI.getWidth() + offset) * i) , stPos.getY()) , backUI.getWidth(), backUI.getHeight(), backUI, entity);
                posIndex[i].getOffset().setX(18);
                posIndex[i].getOffset().setY(18);
                
            }
        }

    }

    public void addItem(BaseObject object){
        if(object == null) return;

        for(int i = 0; i < maxIndex; ++i){
            if(object instanceof BaseWeapon) break;

            BaseObject obj = posIndex[i].getObject();
            if(obj == null || !(obj instanceof Item) ) continue;
            
            Item itemA = (Item) obj;
            Item itemB = (Item) object;

            if(itemA.equals(itemB)){
                itemA.addAmount(itemB.getAmount());
                itemA.addValue(itemB.getValue());
                return;
            }
        }

        for(int i = 0; i < maxIndex; ++i){
            if(posIndex[i].getObject() == null){
                posIndex[i].setObject(object);
                return;
            }
        }
    }

    public void removeItem(BaseObject object){

        for(int i = 0; i < maxIndex; ++i){

            BaseObject obj = posIndex[i].getObject();
            if(obj == null) continue;

            if(obj instanceof Item && object instanceof Item){

                Item itemA = (Item) obj;
                Item itemB = (Item) object;
                if(!itemA.equals(itemB)) continue;

                posIndex[i].setObject(null);
                return;
            }else if(obj instanceof BaseWeapon && object instanceof BaseWeapon){

                BaseWeapon itemA = (BaseWeapon) obj;
                BaseWeapon itemB = (BaseWeapon) object;
                if(!itemA.equals(itemB)) continue;

                posIndex[i].setObject(null);
                return;
            }
        }

    }

    public void removeItem(int index){
        posIndex[index].setObject(null);
    }

    public void addItem(Item item, int index){

    }

    @Override
    public void draw(GraphicsContext gc) {
        if(!visible) return;

        if(offset == 0){
            posIndex[maxIndex - 1].draw(gc);
            for(int i = 0; i < maxIndex - 1; ++i){
                posIndex[i].draw(gc);
            } 
        }else{
            for(int i = 0; i < maxIndex; ++i){
                posIndex[i].draw(gc);
            }  
        }
        
    }   

    public int getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public int getCurrIndex() {
        return currIndex;
    }

    public void setCurrIndex(int currIndex) {
        this.currIndex = currIndex;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Point getBasePoint() {
        return basePoint;
    }

    public void setBasePoint(Point basePoint) {
        this.basePoint = basePoint;
    }
    public SlotUI[] getPosIndex() {
        return posIndex;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        
        if(selectIndex >= maxIndex) selectIndex = maxIndex - 1;
        if(selectIndex < 0) selectIndex = 0;

        posIndex[this.selectIndex].setSelect(false);
        posIndex[selectIndex].setSelect(true);
        this.selectIndex = selectIndex;
    }

    


}
