package animation;

import java.util.ArrayList;

import data.Point;
import javafx.scene.canvas.GraphicsContext;

public class AnimationManager extends AnimationObject{

    private ArrayList<ImageAnimation> queue = new ArrayList<>();
    private ImageAnimation currAnimation;
    
    public AnimationManager(Point position, double width, double height) {
        super(position, width, height);
    }

    @Override
    public void nextAnim(){
    	
        if(queue.size() == 0) return;

        ImageAnimation animation = queue.get(queue.size() - 1);
        currAnimation = animation;

        if(animation.isStop()){
            queue.remove(animation);
            return;
        }
        animation.nextAnim();
        
    }

    public void addAnimation(ImageAnimation animation){
        queue.add(animation);
        nextAnim();
    }

    public ImageAnimation getCurrAnimation() {
        return currAnimation;
    }

    public void setCurrAnimation(ImageAnimation currAnimation) {
        this.currAnimation = currAnimation;
    }

    @Override
    public void draw(GraphicsContext gc) {
        
    }

}
