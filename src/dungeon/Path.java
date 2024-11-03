package dungeon;

import data.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.IRenderable;
import logic.RenderableHolder;

public class Path implements IRenderable {

    private double width, height;
	private Point position;
    private boolean visible = false;
    private Direction direction;

    private Image image = RenderableHolder.baseFloor;
    private Image sideWall = RenderableHolder.sideWall;
	private Image mainWall = RenderableHolder.mainWall;

	private double widthRoom = mainWall.getWidth();
	private double heightRoom = sideWall.getHeight();

	public Path(Room parentRoom, Direction direction) {
		if(direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) {
			this.setWidth(widthRoom * 3);
			this.setHeight(heightRoom * 2 + mainWall.getHeight());
		}else {
			this.setWidth(widthRoom * 4 + sideWall.getWidth()*2);
			this.setHeight(heightRoom * 2 + mainWall.getHeight());
		}
        this.setDirection(direction);
		
		Point pos = parentRoom.getPosition();
		if(direction.equals(Direction.UP)) {
			this.setPosition(new Point(pos.getX() + (parentRoom.getWidth() - this.getWidth()) / 2 , pos.getY() - this.getHeight() ));
		}else if(direction.equals(Direction.DOWN)){
			this.setPosition(new Point(pos.getX() + (parentRoom.getWidth() - this.getWidth()) / 2 , pos.getY() + parentRoom.getHeight() ));
		}else if(direction.equals(Direction.LEFT)) {
			this.setPosition(new Point(pos.getX() - this.getWidth(), pos.getY() + (parentRoom.getHeight() - this.getHeight()) / 2));
		}else {
			this.setPosition(new Point(pos.getX() + parentRoom.getWidth(), pos.getY() + (parentRoom.getHeight() - this.getHeight()) / 2));
		}

        
	}

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(!visible) return;

        gc.drawImage(
			image, position.getX(),
			position.getY(), 
			this.getWidth(), 
			this.getHeight());
        
    }

    public void drawWall(GraphicsContext gc){
        if(!this.visible) return;

        Point pos = this.getPosition();
        if(direction.equals(Direction.UP) || direction.equals(Direction.DOWN)){
            for(int i = 0; i < height / heightRoom - 1 ; ++i){
                gc.drawImage(sideWall,
                            pos.getX() - sideWall.getWidth(),
                            pos.getY() + sideWall.getHeight() * i,
                            sideWall.getWidth(),
                            sideWall.getHeight());
            }
            for(int i = 0; i < height / heightRoom - 1 ; ++i){
                gc.drawImage(sideWall,
                            pos.getX() + width,
                            pos.getY() + sideWall.getHeight() * i,
                            sideWall.getWidth(),
                            sideWall.getHeight());
            }
        }
        else{
            for(int i = 0; i < width / widthRoom - 1; ++i){
                gc.drawImage(mainWall,
                            pos.getX() + mainWall.getWidth() * i,
                            pos.getY() - mainWall.getHeight(),
                            mainWall.getWidth(),
                            mainWall.getHeight());
            }
            for(int i = 0; i < width / widthRoom - 1; ++i){
                gc.drawImage(mainWall,
                            pos.getX() + mainWall.getWidth() * i + sideWall.getWidth(),
                            pos.getY() + height,
                            mainWall.getWidth(),
                            mainWall.getHeight());
            }
            gc.drawImage(mainWall,
                            pos.getX() + mainWall.getWidth() * (width / widthRoom - 1),
                            pos.getY() - mainWall.getHeight(),
                            mainWall.getWidth(),
                            mainWall.getHeight());
        }
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    
}
