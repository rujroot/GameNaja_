package entity;

import java.util.ArrayList;
import java.util.HashMap;

import data.BaseObject;
import data.DataEntity;
import data.Point;
import dungeon.Direction;
import dungeon.GenerateDungeon;
import dungeon.Path;
import dungeon.Room;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logic.Hitbox;
import logic.Main;
import ore.BaseOre;

public abstract class Entity extends BaseObject {
	private String name;
	private DataEntity data;
	
	public Entity(String name, double width, double height, DataEntity data){
		super(new Point(0 , 0), width, height);
		this.setName(name);
		this.setData(data);
	}
	
	public abstract void attack();

	public void drawHP(GraphicsContext gc) {
		DataEntity data = this.getData();
		double maxHP = data.getMaxHP(), currHP = data.getHp();
		Point pos = this.getPosition();
		gc.setFill(Color.WHITE);
		gc.fillRect(pos.getX(), pos.getY() - 20, this.getWidth(), 10);
		gc.setFill(Color.LIGHTGREEN);
		gc.fillRect(pos.getX(), pos.getY() - 20, (currHP / maxHP) * (this.getWidth()), 10);
	}

	public void move(double moveX, double moveY){
		Point pos = this.getPosition();
		
		for(double x = moveX; x > 0; --x){
			if(isLegalMove(x, 0)){
				pos.setX(pos.getX() + x);
				return;
			}
		}

		for(double x = moveX; x < 0; ++x){
			if(isLegalMove(x, 0)){
				pos.setX(pos.getX() + x);
				return;
			}
		}

		for(double y = moveY; y > 0; --y){
			if(isLegalMove(0, y)){
				pos.setY(pos.getY() + y);
				return;
			}
		}

		for(double y = moveY; y < 0; ++y){
			if(isLegalMove(0, y)){
				pos.setY(pos.getY() + y);
				return;
			}
		}
	}

	public boolean isLegalMove(double moveX, double moveY) {
		int currLevel = GenerateDungeon.getCurrLevel();
		ArrayList<Room> level = GenerateDungeon.getContainer().get(currLevel);
		
		Point pos = this.getPosition();
		Point newPos = new Point(pos.getX() + moveX, pos.getY() + moveY);
		
		ArrayList<BaseObject> allObject = Main.getLogic().getGameObjectContainer();
		Hitbox A = new Hitbox(newPos, this.getWidth(), this.getHeight());
		
		for(BaseObject object : allObject){

			if((object instanceof BaseOre && object.isVisible())){
				Hitbox B = new Hitbox(object.getPosition(), object.getWidth(), object.getHeight());
				if(A.isIntersect(B)) return false;
			}
		}

		double sum = 0;
		
		for(Room room : level) {
			
			Point posRoom = room.getPosition();
			
			double rect1X1 = posRoom.getX();
		    double rect1Y1 = posRoom.getY();
		    double rect1X2 = posRoom.getX() + room.getWidth();
		    double rect1Y2 = posRoom.getY() + room.getHeight();
		    
		    double X1 = newPos.getX();
		    double Y1 = newPos.getY();
		    double X2 = newPos.getX() + this.getWidth();
		    double Y2 = newPos.getY() + this.getHeight();

			// calculate the coordinates of the intersection rectangle
			double xLeft = Math.max(rect1X1, X1);
			double yTop = Math.max(rect1Y1, Y1);
			double xRight = Math.min(rect1X2, X2);
			double yBottom = Math.min(rect1Y2, Y2);
			
			// calculate the area of the intersection rectangle
			double intersectionArea = 0.0;
			if (xRight > xLeft && yBottom > yTop) {
				intersectionArea = (xRight - xLeft) * (yBottom - yTop);
			}

			HashMap<Direction, Path> connectPath = room.getConnectPath();
			sum +=  getSumPath(connectPath.get(Direction.UP), newPos);
			sum +=  getSumPath(connectPath.get(Direction.DOWN), newPos);
			sum +=  getSumPath(connectPath.get(Direction.LEFT), newPos);
			sum +=  getSumPath(connectPath.get(Direction.RIGHT), newPos);
			sum += intersectionArea;
		}

		return Math.round(sum) == Math.round(this.getWidth() * this.getHeight());   
	}

	public double getSumPath(Path path, Point newPosPlayer){
		if(!path.isVisible()) return 0;
		
		Point posPath = path.getPosition();
		
		double rect1X1 = posPath.getX();
		double rect1Y1 = posPath.getY();
		double rect1X2 = posPath.getX() + path.getWidth();
		double rect1Y2 = posPath.getY() + path.getHeight();
		
		double X1 = newPosPlayer.getX();
		double Y1 = newPosPlayer.getY();
		double X2 = newPosPlayer.getX() + this.getWidth();
		double Y2 = newPosPlayer.getY() + this.getHeight();

		// calculate the coordinates of the intersection rectangle
		double xLeft = Math.max(rect1X1, X1);
		double yTop = Math.max(rect1Y1, Y1);
		double xRight = Math.min(rect1X2, X2);
		double yBottom = Math.min(rect1Y2, Y2);
		
		// calculate the area of the intersection rectangle
		double intersectionArea = 0.0;
		if (xRight > xLeft && yBottom > yTop) {
			intersectionArea = (xRight - xLeft) * (yBottom - yTop);
		}
		return intersectionArea;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public DataEntity getData() {
		return data;
	}

	public void setData(DataEntity data) {
		this.data = data;
	}

	public boolean equals(Entity entity){
		return name.equals(entity.getName());
	}
}
