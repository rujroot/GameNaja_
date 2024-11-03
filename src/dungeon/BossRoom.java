package dungeon;

import java.util.ArrayList;
import java.util.HashMap;

import data.BaseObject;
import data.DataEntity;
import data.Point;
import entity.NPC;
import entity.Player;
import entity.boss.BossEntity;
import entity.boss.FrostGuardian;
import entity.boss.PheuFire;
import entity.miniBoss.DarkSpirit;
import entity.miniBoss.GiantGoblin;
import entity.miniBoss.MiniBossEntity;
import inventory.Inventory;
import item.Item;
import logic.Main;
import logic.RenderableHolder;

public class BossRoom extends Room{

    private boolean visited = false;

    public BossRoom(Room parentRoom, Direction direction){
		this.setWidth(widthRoom * ((int) (Math.random() * 10) + 10));
		this.setHeight(heightRoom * ((int) (Math.random() * 10) + 10));

		Path path = parentRoom.getConnectPath().get(direction);
		Point pos = path.getPosition();
		if(direction.equals(Direction.UP)) {
			this.setPosition(new Point(pos.getX() + (path.getWidth() - this.getWidth()) / 2 , pos.getY() - this.getHeight() ));
		}else if(direction.equals(Direction.DOWN)){
			this.setPosition(new Point(pos.getX() + (path.getWidth() - this.getWidth()) / 2 , pos.getY() + path.getHeight() ));
		}else if(direction.equals(Direction.LEFT)) {
			this.setPosition(new Point(pos.getX() - this.getWidth(), pos.getY() + (path.getHeight() - this.getHeight()) / 2));
		}else {
			this.setPosition(new Point(pos.getX() + path.getWidth(), pos.getY() + (path.getHeight() - this.getHeight()) / 2));
		}
		
		this.setRoomSize(Size.BOSS);
        this.setImage(RenderableHolder.baseBossFloor);
		generatePath();
	}

    public void playerEntry(){

        HashMap<Direction, Room> connectRoom = this.getConnectRoom();

        HashMap<Direction, Path> connectPath = this.getConnectPath();

        Room roomU = connectRoom.get(Direction.UP);
        Room roomD = connectRoom.get(Direction.DOWN);
        Room roomL = connectRoom.get(Direction.LEFT);
        Room roomR = connectRoom.get(Direction.RIGHT);

        setPathVisible(connectPath, false);

        if(roomU != null) setPathVisible(roomU.getConnectPath(), false);
        if(roomD != null) setPathVisible(roomD.getConnectPath(), false);
        if(roomL != null) setPathVisible(roomL.getConnectPath(), false);
        if(roomR != null) setPathVisible(roomR.getConnectPath(), false);

        this.setVisited(true);

        clearObj();
        generateBoss();
    }

    public void clearObj(){
        ArrayList<BaseObject> gameObjectContainer = Main.getLogic().getGameObjectContainer();

        for (int i = gameObjectContainer.size() - 1; i >= 0; i--) {
            BaseObject object = gameObjectContainer.get(i);
			if(object instanceof Player) continue;
			if(object instanceof Inventory) continue;
			if(object instanceof Item) continue;
			if(object instanceof NPC && !((NPC) object).getState().equals("Idel")) {
                ((NPC) object).warpToEntity(Player.getPlayer());
                continue;
            }

			object.setDestroyed(true);
        }
    }

    public void generateBoss(){
        int currLevel = GenerateDungeon.getCurrLevel() + 1;

        if(currLevel % 5 == 0){
            String[] type = {"Fire", "Frost"};
            String choose = type[(int) (Math.random() * 2)];

            BossEntity boss;
            if(choose.equals("Fire")){
                boss = new PheuFire("PheuFire", new DataEntity(currLevel * 20, 1, 1, 12));
            }else{
                boss = new FrostGuardian("FrostGuardain", new DataEntity(currLevel * 20, 1, 1, 12));
            }

            Point pos = this.getPosition();
            boss.setPosition(new Point(pos.getX() + this.getWidth() / 2, pos.getY() + this.getHeight() / 2));
            Main.getLogic().addObject(boss);
        }else{
            String[] type = {"Giant", "Dark"};
            String choose = type[(int) (Math.random() * 2)];

            MiniBossEntity miniBoss;
            if(choose.equals("Giant")){
                miniBoss = new GiantGoblin("GiantGoblin", 0, 0, new DataEntity(currLevel * 10, 1, 1, 12));
            }else{
                miniBoss = new DarkSpirit("DarkSpirit", 0, 0, new DataEntity(currLevel * 10, 1, 1, 12));
            }

            Point pos = this.getPosition();
            miniBoss.setPosition(new Point(pos.getX() + this.getWidth() / 2, pos.getY() + this.getHeight() / 2));
            Main.getLogic().addObject(miniBoss);

        }

    }

    public void setPathVisible(HashMap<Direction, Path> connectPath, boolean visible){
        Path pathU = connectPath.get(Direction.UP);
        Path pathD = connectPath.get(Direction.DOWN);
        Path pathL = connectPath.get(Direction.LEFT);
        Path pathR = connectPath.get(Direction.RIGHT);
        
        if(pathU != null) pathU.setVisible(visible);
        if(pathD != null) pathD.setVisible(visible);
        if(pathL != null) pathL.setVisible(visible);
        if(pathR != null) pathR.setVisible(visible);
    }

    // getter setter

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
}
