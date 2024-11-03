package drawing;

import java.util.ArrayList;
import java.util.HashMap;

import data.Point;
import dungeon.Direction;
import dungeon.GenerateDungeon;
import dungeon.Path;
import dungeon.Room;
import entity.Player;
import input.InputUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import logic.IRenderable;
import logic.RenderableHolder;
public class GameScreen extends Canvas {

	private Point posCamera;
	public static Point resolution;
	public static GraphicsContext gc;

	public GameScreen(double width, double height) {
		super(width, height);
		this.setVisible(true);
		addListerner();
		resolution = new Point(width, height);
		
		gc = this.getGraphicsContext2D();
		Player player = Player.getPlayer();
		
		// first to set camera
		player.setResolutionPosition(new Point(width / 2, height / 2));
		gc.translate(width / 2, height / 2);
		posCamera = new Point(-width / 2, -height / 2);
	}
	
	//insert event input
	public void addListerner() {
		this.setOnKeyPressed((KeyEvent event) -> {
			InputUtility.setKeyPressed(event.getCode(), true);
		});

		this.setOnKeyReleased((KeyEvent event) -> {
			InputUtility.setKeyPressed(event.getCode(), false);
		});

		this.setOnMousePressed((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY)
				InputUtility.mouseLeftDown();
		});

		this.setOnMouseReleased((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY)
				InputUtility.mouseLeftRelease();
		});

		this.setOnMouseEntered((MouseEvent event) -> {
			InputUtility.mouseOnScreen = true;
		});

		this.setOnMouseExited((MouseEvent event) -> {
			InputUtility.mouseOnScreen = false;
		});

		this.setOnMouseMoved((MouseEvent event) -> {
			if (InputUtility.mouseOnScreen) {
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
			}
		});

		this.setOnMouseDragged((MouseEvent event) -> {
			if (InputUtility.mouseOnScreen) {
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
			}
		});
	}
	
	public void updatePlayer() throws CloneNotSupportedException {
		Player player = Player.getPlayer();
		Point pos = player.getPosition();

		// Prepar for new draw
		gc.setFill(Color.rgb(120,80,100));
		gc.fillRect(pos.getX() - this.getWidth() / 2, 
					pos.getY() - this.getHeight() / 2, 
					this.getWidth() * 1.2, 
					this.getHeight() * 1.2);
		// update player input
		player.update();
		// Track Camera follow player
		this.trackCamera();
	}

	// TrackCamera follow Player with offset
	public void trackCamera(){
		Point pos = Player.getPlayer().getPosition();

		Point posToTrack = new Point(pos.getX() - this.getWidth() / 2, pos.getY() - this.getHeight() / 2);
		double posCameraX = posCamera.getX(), posCameraY = posCamera.getY();

		// Track
		gc.translate(posCameraX - posToTrack.getX(), posCameraY - posToTrack.getY());
		posCamera.setX(posCameraX - (posCameraX - posToTrack.getX()));
		posCamera.setY(posCameraY - (posCameraY - posToTrack.getY()));
	}
	
	public void paintComponent() {
		gc.setFill(Color.BLACK);
		
		for (IRenderable object : RenderableHolder.getInstance().getObjects()) {
			if (object.isVisible() && !object.isDestroyed()) {
				object.draw(gc);
			}
		}

	}
	
	public void paintLevel() {
		int currLevel = GenerateDungeon.getCurrLevel();
		ArrayList<Room> level = GenerateDungeon.getContainer().get(currLevel);
		for (IRenderable room : level) {
			room.draw(gc);
		}
		
		for (Room room : level) {
			room.drawUpWall(gc);
			room.drawLeftWall(gc);
			room.drawRightWall(gc);
			room.drawDownWall(gc);
		}		
		for (Room room : level) {
			HashMap<Direction, Path> connectPath = room.getConnectPath();
			connectPath.get(Direction.DOWN).drawWall(gc);
			connectPath.get(Direction.UP).drawWall(gc);
			connectPath.get(Direction.LEFT).drawWall(gc);
			connectPath.get(Direction.RIGHT).drawWall(gc);
		}
		for (Room room : level) {
			HashMap<Direction, Path> connectPath = room.getConnectPath();
			connectPath.get(Direction.UP).draw(gc);
			connectPath.get(Direction.DOWN).draw(gc);
			connectPath.get(Direction.LEFT).draw(gc);
			connectPath.get(Direction.RIGHT).draw(gc);
		}

		
	}

	public static Point getResolution() {
		return resolution;
	}

	public static void drawImage(Image image, Point pos, double width, double height){
		for(int i = 0; i < width / image.getWidth(); ++i){
			for(int j = 0; j < height / image.getHeight(); ++j){
				if(image.getWidth() * (i+1) > width && image.getHeight() * (j+1) > height){
					gc.drawImage(image,
						pos.getX() + image.getWidth() * i,
						pos.getY() + image.getHeight() * j,
						width - image.getWidth() * (i),
						height - image.getHeight() * (j) );
				}else if(image.getWidth() * (i+1) > width){
					gc.drawImage(image,
						pos.getX() + image.getWidth() * i,
						pos.getY() + image.getHeight() * j,
						width - image.getWidth() * (i),
						image.getHeight() );
				}else if(image.getHeight() * (j+1) > height){
					gc.drawImage(image,
						pos.getX() + image.getWidth() * i,
						pos.getY() + image.getHeight() * j,
						image.getWidth(),
						height - image.getHeight() * (j) );
				}else{
					gc.drawImage(image,
						pos.getX() + image.getWidth() * i,
						pos.getY() + image.getHeight() * j,
						image.getWidth(),
						image.getHeight() );
				}
				
			}
		}
	}
	

}
