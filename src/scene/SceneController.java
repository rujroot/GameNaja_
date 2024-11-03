package scene;

import java.io.IOException;

import drawing.GameScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.Main;
import logic.RenderableHolder;
import animation.AnimationController;
import data.DataEntity;
import data.Point;
import dungeon.GenerateDungeon;
import dungeon.Room;

import entity.NPC;

import entity.Player;
import entity.Shopkeeper;
import input.InputUtility;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;

public class SceneController{

	public static GameLogic logic;
	public static GameScreen gameScreen;
	public static GenerateDungeon dungeon; 
	public static boolean gameStop = false;
	
	public void switchToStartGameScene(ActionEvent event) throws IOException {
		//dungeonImage = new Image(getClass().getResourceAsStream("Dungeon.jpg"));
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("/scene/Start.fxml"));
		Scene scene = new Scene(root);
		Stage stage = Main.stage;

		stage.setScene(scene);
		stage.setTitle("Game Naja");
		//myImageView.setImage(dungeonImage);
		stage.show();
		//RenderableHolder.gameOverSound.stop();
		//RenderableHolder.openSound.setVolume(0.1);// 0.0 to 1.0 (min to man volume)
		//RenderableHolder.openSound.play();
	}
	
	public void switchToHowToPlayScene() throws IOException {
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("/scene/HowToPlay.fxml"));
		Scene scene = new Scene(root);
		Stage stage = Main.stage;

		stage.setScene(scene);
		stage.setTitle("Game Naja");
		stage.show();
	}
	
	public void switchToGameOverScene() throws IOException {

		gameStop = true;
		
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("/scene/GameOver.fxml"));
		Scene scene = new Scene(root);
		Stage stage = Main.stage;

		stage.setScene(scene);
		stage.setTitle("Game Naja");
		stage.show();
		
		RenderableHolder.gameOverSound.setVolume(0.2);// 0.0 to 1.0 (min to man volume)
		RenderableHolder.gameOverSound.play();

	}
	
	public void switchToGameClearScene() throws IOException {

		gameStop = true;
		
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("/scene/GameClear.fxml"));
		Scene scene = new Scene(root);
		Stage stage = Main.stage;

		stage.setScene(scene);
		stage.setTitle("Game Naja");
		stage.show();
		
		RenderableHolder.gameOverSound.setVolume(0.2);// 0.0 to 1.0 (min to man volume)
		RenderableHolder.gameOverSound.play();

	}

	public void restartGame() throws CloneNotSupportedException{
		RenderableHolder.gameOverSound.stop();
		dungeon = new GenerateDungeon(1);

		Room firstRoom = GenerateDungeon.getContainer().get(0).get(0);
		Shopkeeper shopkeeper = new Shopkeeper("Shopkeeper", 50, 50, new DataEntity(999999, 1, 1, 0));
		shopkeeper.setPosition(new Point(firstRoom.getPosition().getX() + 20, firstRoom.getPosition().getY() + 20 ));
		logic.addObject(shopkeeper);

		Player player = new Player("Player", new DataEntity(100, 10000, 10000, 10));
		player.setPosition(new Point(firstRoom.getPosition().getX() + 100, firstRoom.getPosition().getY() + 100 ));
		player.initInventory();
		player.setResolutionPosition(new Point(1400 / 2, 800 / 2));
		logic.addObject(player);

		Stage stage = Main.stage;
		StackPane root = new StackPane();
		root.getChildren().add(gameScreen);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Game Naja");
		gameScreen.requestFocus();

		InputUtility.reset();
		gameStop = false;
		
		RenderableHolder.sound.setVolume(0.1);// 0.0 to 1.0 (min to man volume)
		RenderableHolder.sound.play();

	}
	
	public void switchToBodyGameScene() throws CloneNotSupportedException {
		RenderableHolder.openSound.stop();
		Player player = new Player("Player", new DataEntity(100, 10, 10, 10));

		logic = new GameLogic();
		dungeon = new GenerateDungeon(1);

		Stage stage = Main.stage;
		gameScreen = new GameScreen(1400,800);
		StackPane root = new StackPane();
		root.getChildren().add(gameScreen);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Game Naja");
		gameScreen.requestFocus();

		Room firstRoom = GenerateDungeon.getContainer().get(0).get(0);
		Shopkeeper shopkeeper = new Shopkeeper("Shopkeeper", 50, 50, new DataEntity(999999, 1, 1, 0));
		shopkeeper.setPosition(new Point(firstRoom.getPosition().getX() + 20, firstRoom.getPosition().getY() + 20 ));
		logic.addObject(shopkeeper);
		
		// Npc npc = new Npc("NPC", 10, 10, new DataEntity(100, 1, 1, 10));
		// npc.setPosition(new Point(firstRoom.getPosition().getX() + 200, firstRoom.getPosition().getY() + 200 ));
		// npc.setValue(0);
		// logic.addObject(npc);
		
		player.setPosition(new Point(firstRoom.getPosition().getX() + 100, firstRoom.getPosition().getY() + 100 ));
		player.initInventory();
		logic.addObject(player);

		// PheuFire demonslime = new PheuFire("PheuFire", new DataEntity(100, 1, 1, 12));
		// demonslime.setPosition(new Point(firstRoom.getPosition().getX() + 200, firstRoom.getPosition().getY() + 200 ));
		// logic.addObject(demonslime);

		// GiantGoblin goblin = new GiantGoblin("GiantGoblin", 0, 0, new DataEntity(100, 1, 1, 12));
		// goblin.setPosition(new Point(firstRoom.getPosition().getX() + 300, firstRoom.getPosition().getY() + 300 ));
		// logic.addObject(goblin);

		// DarkSpirit darkSpirit = new DarkSpirit("DarkSpirit", 0, 0, new DataEntity(100, 1, 1, 12));
		// darkSpirit.setPosition(new Point(firstRoom.getPosition().getX() + 200, firstRoom.getPosition().getY() + 200 ));
		// logic.addObject(darkSpirit);

		// FrostGuardian frostGuardain = new FrostGuardian("FrostGuardain", new DataEntity(100, 1, 1, 12));
		// frostGuardain.setPosition(new Point(firstRoom.getPosition().getX() + 300, firstRoom.getPosition().getY() + 300 ));
		// logic.addObject(frostGuardain);

		AnimationController animationController = new AnimationController();
		
		AnimationTimer animation = new AnimationTimer() {
			public void handle(long now) {
				if(SceneController.gameStop){
					animationController.reset();
					return;
				} 

				try {
					gameScreen.updatePlayer();
					gameScreen.paintLevel();
					gameScreen.paintComponent();
					animationController.run();
					logic.logicUpdate();
					RenderableHolder.getInstance().update();
					InputUtility.updateInputState();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				
			}
		};
		RenderableHolder.sound.setVolume(0.1);// 0.0 to 1.0 (min to man volume)
		RenderableHolder.sound.play();
		animation.start();
		
	}

	public static GameLogic getLogic() {
		return logic;
	}

	public static GameScreen getGameScreen() {
		return gameScreen;
	}

	

}