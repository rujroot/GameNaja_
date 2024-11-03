package dungeon;

public class Wall {
    private double up, down, left, right;

    public Wall(){
        up = 0;
        down = 0;
        left = 0;
        right = 0;
    }

    public void add(Direction direction, double width, double height){
        if(direction.equals(Direction.UP)){
            this.setUp(width);
        }else if(direction.equals(Direction.DOWN)){
            this.setDown(width);
        }else if(direction.equals(Direction.LEFT)){
            this.setLeft(height);
        }else{
            this.setRight(height);
        }
    }

    public double getUp() {
        return up;
    }

    public void setUp(double up) {
        this.up = up;
    }

    public double getDown() {
        return down;
    }

    public void setDown(double down) {
        this.down = down;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    

    


}
