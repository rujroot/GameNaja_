package inventory;

import java.util.ArrayList;

import data.BaseObject;
import data.Point;
import drawing.GameScreen;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;

public class Inventory extends BaseObject{

    private BaseUI UI;
    
    public Inventory(){
        super(new Point(0, 0) , 700, 60, 10);

        Point resolution = GameScreen.getResolution();
        Point basePoint = new Point(-resolution.getX() / 4 + 100, resolution.getY() / 3);
        UI = new BaseUI(basePoint, 0, 0, 9, 0, Player.player);
        UI.setVisible(true);
    }

    public void addItem(ArrayList<BaseObject> objects){
        for(BaseObject object : objects){
            UI.addItem(object);
        }
    }

    public void addItem(BaseObject object){
        UI.addItem(object);
    }

    public void selectIndex(int index){
        UI.setSelectIndex(index);
    }

    public void removeItem(BaseObject baseObject){
        UI.removeItem(baseObject);
    }

    public void removeItem(int index){
        UI.removeItem(index);
    }

    public BaseObject getObject(int index){
        BaseObject object = UI.getPosIndex()[index].getObject();
        return object;
    }

    @Override
    public void draw(GraphicsContext gc) {
        UI.draw(gc);

    }

    public BaseUI getUI() {
        return UI;
    }

    public void setUI(BaseUI uI) {
        UI = uI;
    }
}
