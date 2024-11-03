package item;

import data.BaseObject;
import data.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
public abstract class Item extends BaseObject implements Cloneable {

    private int amount = 0, index;
    private double value = 0;
    private String name;
    private WritableImage image;

    public Item(int amount, double value) {
        super(new Point(0, 0), 0, 0);
        this.setAmount(amount);
        this.setValue(value);
    }

    public Item(Point position, double width, double height, int z) {
        super(position, width, height, z);
    }

    @Override
    public void draw(GraphicsContext gc) {
    	Point pos = this.getPosition();
		
        if(this.getName().equals("HealPotion")){
            gc.drawImage(image, pos.getX() + 10, pos.getY() + 10, image.getWidth()  , image.getHeight());
            this.setWidth(image.getWidth() * 1.3);
            this.setHeight(image.getHeight() * 1.15);
        }else {
            gc.drawImage(image, pos.getX() + 5, pos.getY() + 5, image.getWidth()*1.5 , image.getHeight()*1.5);
            this.setWidth(image.getWidth() * 1.5);
            this.setHeight(image.getHeight() * 1.5);
        }

        gc.setFont(new Font("Arial", 24));
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(5);

        String itemAmount = Integer.toString(getAmount());

        if(this.getName().equals("HealPotion")){
            gc.strokeText(itemAmount, pos.getX() + this.getWidth() - itemAmount.length() * 10 , pos.getY() + this.getHeight(), 100);
            gc.fillText(itemAmount, pos.getX() + this.getWidth() - itemAmount.length() * 10, pos.getY() + this.getHeight(), 100);
        }else{
            gc.strokeText(itemAmount, pos.getX() + this.getWidth() - itemAmount.length() * 10 , pos.getY() + this.getHeight(), 100);
            gc.fillText(itemAmount, pos.getX() + this.getWidth() - itemAmount.length() * 10, pos.getY() + this.getHeight(), 100);
        }
    }

    public boolean equals(Item item){
        return this.getName().equals(item.getName());
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addAmount(int amount){
        this.amount += amount;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WritableImage getImage() {
        return image;
    }

    public void setImage(WritableImage image) {
        this.image = image;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    } 

    public void addValue(double value){
        this.value += value;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Item clone = (Item) super.clone();
        return clone;
    }
}
