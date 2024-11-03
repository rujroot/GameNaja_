package ore;

import data.BaseObject;
import data.DataOre;
import data.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

public abstract class BaseOre extends BaseObject  {
    
    private DataOre dataOre;
    private WritableImage image;
    private double mutliply = 1.5;

    public BaseOre(Point position, double width, double height, DataOre dataOre){
        super(position, width, height);
        this.setPosition(position);
        this.setDataOre(dataOre); 
    }

    public abstract void onBreak();

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, this.getPosition().getX(), this.getPosition().getY(), image.getWidth() * mutliply, image.getHeight() * mutliply);
    }

    public DataOre getDataOre() {
        return dataOre;
    }

    public void setDataOre(DataOre dataOre) {
        this.dataOre = dataOre;
    }

    public double getMutliply() {
        return mutliply;
    }

    public void setMutliply(double mutliply) {
        this.mutliply = mutliply;
    }

    public WritableImage getImage() {
        return image;
    }

    public void setImage(WritableImage image) {
        this.image = image;
    }

    

}
