package logic;

import java.io.IOException;
import java.util.ArrayList;
import data.BaseObject;
import data.DataEntity;
import data.DataOre;
import data.Point;
import dungeon.GenerateDungeon;
import dungeon.Ladder;
import dungeon.Room;
import entity.Entity;
import entity.Monster;
import entity.NPC;
import entity.Player;
import entity.Shopkeeper;
import entity.Team;
import entity.boss.BossEntity;
import entity.miniBoss.MiniBossEntity;
import equipment.projectile.BaseProjectile;
import inventory.Inventory;
import item.Item;
import ore.BaseOre;
import scene.SceneController;

public class GameLogic {

	private static ArrayList<BaseObject> gameObjectContainer ;
	
	public GameLogic() {
		// create new ObjectContainer
		GameLogic.gameObjectContainer = new ArrayList<BaseObject>();
	}
	
	// Add object to gameObject and Renderable
	public void addObject(BaseObject object) {
		gameObjectContainer.add(object);
		RenderableHolder.getInstance().add(object);
	}
	
	//This method should run every sec
	public void logicUpdate() throws CloneNotSupportedException{
		for (int i = gameObjectContainer.size() - 1; i >= 0; i--) {
			BaseObject object = gameObjectContainer.get(i);
			if(object.isDestroyed()) gameObjectContainer.remove(object);
			if(object instanceof Entity){
					Entity entity = (Entity) object;
					
					if(entity.getData().getHp() <= 0) {
						if(entity instanceof MiniBossEntity || entity instanceof BossEntity){
							if(entity instanceof MiniBossEntity) Player.getPlayer().addMoney(1 * ((int)(Math.random() * 5) + 5));
							else Player.getPlayer().addMoney(5 * ((int)(Math.random() * 5) + 1));

							Ladder ladder = new Ladder(entity.getPosition());
							this.addObject(ladder);
						}
						Player.getPlayer().addMoney(0.01 * (int)(Math.random() * 10));
						entity.setDestroyed(true);
						gameObjectContainer.remove(entity);
					}

					if(entity instanceof Monster) {
						if(entity instanceof BossEntity){
							BossEntity bossEntity = (BossEntity) object;
							bossEntity.attack();
							continue;
						}

						if(entity instanceof MiniBossEntity){
							MiniBossEntity miniBossEntity = (MiniBossEntity) object;
							miniBossEntity.findNearestEntity(gameObjectContainer);
							miniBossEntity.attack();
							miniBossEntity.specialAttack();
							continue;
						}
						
						Monster monster = (Monster) entity;
						monster.findNearestEntity(gameObjectContainer);
						monster.attack();
					}
					
					if(entity instanceof Player) {
						Player player = (Player) entity;
						player.checkPlayerInBossRoom();
						if(entity.getData().getHp()<=0) {
							RenderableHolder.sound.stop();
							SceneController sceneController = new SceneController();
							resetGame();
							try {
								sceneController.switchToGameOverScene();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					if(object instanceof Shopkeeper){
						Shopkeeper shopkeeper = (Shopkeeper) object;
						shopkeeper.updateInput();
					}
				
					if(object instanceof NPC){
						NPC npc = (NPC) object;
						npc.updateInput();
						npc.findNearestMonster(gameObjectContainer);
						npc.doBehavior();
					}
			
				}
			
			else if(object instanceof BaseProjectile){
				BaseProjectile arrow = (BaseProjectile) object;
				arrow.update();
				if(!arrow.inRoom()){
					arrow.setDestroyed(true);
					gameObjectContainer.remove(arrow);
					continue;
				}

				Team team = arrow.getTeam();
				for (int j = gameObjectContainer.size() - 1; j >= 0; j--) {
					BaseObject thatObject = gameObjectContainer.get(j);
					if(team.equals(Team.Player) && (thatObject instanceof Monster) && arrow.hit((Entity)thatObject)){
						arrow.makeDamge((Entity)thatObject);
						arrow.setDestroyed(true);
						gameObjectContainer.remove(arrow);
					}else if(team.equals(Team.Monster) && ((thatObject instanceof Player) || (thatObject instanceof NPC)) && arrow.hit((Entity)thatObject)){
						arrow.makeDamge((Entity)thatObject);
						arrow.setDestroyed(true);
						gameObjectContainer.remove(arrow);
					}
					
				}
			}

			else if(object instanceof BaseOre){
				BaseOre ore = (BaseOre) object;
				DataOre data = ore.getDataOre();
				if(data.getDurability() <= 0){
					ore.onBreak();
					ore.setDestroyed(true);
					gameObjectContainer.remove(ore);
				}
			}
	
			else if(object instanceof Ladder){
				Ladder ladder = (Ladder) object;
				ladder.updateInput();
			}
		}
	}

	public void nextFloor() throws CloneNotSupportedException{
		this.clearObject();
		SceneController.dungeon.increaseFloor(1);
		
		//Get next dungeon
		int currentLevel = GenerateDungeon.getCurrLevel();
		if(currentLevel >= GenerateDungeon.getLevel() - 1) return;

		GenerateDungeon.setCurrLevel(currentLevel + 1);

        if(GenerateDungeon.getCurrLevel() + 1 >= 31) {
        	RenderableHolder.sound.stop();
            SceneController sceneController = new SceneController();
            resetGame();
            try {
                sceneController.switchToGameClearScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
		//Get first room
		ArrayList<Room> nextLevel = GenerateDungeon.getContainer().get(currentLevel + 1);
		Room firstRoom = nextLevel.get(0);

		for(int i = 1; i < nextLevel.size(); ++i){
			for(BaseOre ore : nextLevel.get(i).getOres()){
				ore.setVisible(true);
			}
		}

		Shopkeeper shopkeeper = new Shopkeeper("Shopkeeper", 50, 50, new DataEntity(999999, 1, 1, 0));
		shopkeeper.setPosition(new Point(firstRoom.getPosition().getX() + 20, firstRoom.getPosition().getY() + 20 ));
		Main.getLogic().addObject(shopkeeper);

		if((currentLevel + 1) % 5 == 0){
			NPC npc = new NPC("NPC", 10, 10, new DataEntity(100, 1, 1, 10));
			npc.setPosition(new Point(firstRoom.getPosition().getX() + 200, firstRoom.getPosition().getY() + 200 ));
			npc.setValue(Math.max(currentLevel * 5 + (int)(Math.random() * 10) - 20, 5));
			Main.getLogic().addObject(npc);
		}
		
		Player player = Player.getPlayer();
		// Get postion to spawn
		Point spawnPoint = new Point(firstRoom.getPosition().getX() + 500, firstRoom.getPosition().getY() + 500 );

		// Warp player and npc
		player.setPosition(spawnPoint);
		for (int i = gameObjectContainer.size() - 1; i >= 0; i--) {
			BaseObject object = gameObjectContainer.get(i);
			if(object instanceof NPC){
				NPC npc = (NPC) object;
				Point pos = player.getPosition();
				npc.setPosition(new Point(pos.getX(), pos.getY()));
			}
		}


	}

	public void clearObject(){
		for (int i = gameObjectContainer.size() - 1; i >= 0; i--) {
			BaseObject object = gameObjectContainer.get(i);
			if(object instanceof Player) continue;
			if(object instanceof BaseOre && !object.isVisible()) continue;
			if(object instanceof Inventory) continue;
			if(object instanceof Item) continue;
			if(object instanceof NPC) continue;

			object.setVisible(false);
			object.setDestroyed(true);
			gameObjectContainer.remove(object);
		}

	}
	
	public void resetGame(){
		for (int i = gameObjectContainer.size() - 1; i >= 0; i--) {
			BaseObject object = gameObjectContainer.get(i);
			object.setDestroyed(true);
			gameObjectContainer.remove(object);
		}

	}

	public ArrayList<BaseObject> getGameObjectContainer() {
		return gameObjectContainer;
	}

	public static void removeObj(BaseObject object){
		gameObjectContainer.remove(object);
	}
	

}
