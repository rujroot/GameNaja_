package animation;

import data.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageAnimation extends AnimationObject {

    private Image[] image;
    private int rate, currIndex = 1, maxIndex;
    private boolean stop = false, loop = false;

    public ImageAnimation(Point position, double width, double height, Image[] image, int rate, int maxIndex) {
        super(position, width, height);
        this.image = image;
        this.maxIndex = maxIndex;
        this.rate = rate;
    }

    @Override
    public void draw(GraphicsContext gc) {
        
    }

    @Override
    public void nextAnim(){
    	this.setCurrTime(this.getCurrTime()+this.getSpeedAnim());
        runIndex();
    }

    public Image getCurrImage(){
        return image[currIndex];
    }

    public void runIndex(){
        if(stop == true) return;

        if(this.getCurrTime() >= rate){
            this.setCurrTime(0);
            currIndex++;
            if(currIndex >= maxIndex && loop == true){
                currIndex = 1;
            }else if(currIndex >= maxIndex){
                currIndex = 1;
                stop = true;
            }
        }
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
    
    

}
