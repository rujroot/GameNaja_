package animation;

import data.BaseObject;
import data.Point;

public abstract class AnimationObject extends BaseObject {
    
    double startTime = 0, currTime = 0, endTime, speedAnim = 1;


    public AnimationObject(Point position, double width, double height) {
        super(position, width, height);
    }
    
    public double getStartTime() {
        return startTime;
    }
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }
    public double getCurrTime() {
        return currTime;
    }
    public void setCurrTime(double currTime) {
        this.currTime = currTime;
    }
    public double getEndTime() {
        return endTime;
    }
    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }
    public void nextAnim(){
        this.currTime = this.currTime + speedAnim;
    }
    public double getSpeedAnim() {
        return speedAnim;
    }
    public void setSpeedAnim(double speedAnim) {
        this.speedAnim = speedAnim;
    }

    
}
