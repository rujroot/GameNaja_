package entity;

import java.util.ArrayList;

import data.BaseObject;
import data.DataEntity;
import data.Point;
import entity.boss.BossEntity;
import equipment.BaseWeapon;
import equipment.Gun;
import equipment.Melee;
import equipment.Pickaxe;
import input.InputUtility;
import inventory.Inventory;
import item.HealPotion;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Cooldownable;
import logic.RenderableHolder;

public class NPC extends Entity implements Cooldownable {

	private double cooldownTime = 1000, backTime = 5000;
	private double lastClickTime = 0;

	private WritableImage image = new WritableImage(RenderableHolder.Tileset.getPixelReader(), 644, 964, 59, 59);
	private Entity followEntity, targetEntity;
	private double maxDistance = 700;
	private BaseWeapon equipment;
	private String state = "Idel";
	private double value = 25;

	private Point offSet;

	private boolean runBack = false;

	public NPC(String name, double width, double height, DataEntity data) {
		super(name, width, height, data);
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
		offSet = new Point(Math.random() * 200 - 100, Math.random() * 200 - 100);
	}

	public boolean inRange(double range){
        Point playerPos = Player.player.getPosition();
        Point pos = this.getPosition();

        return Math.sqrt( Math.pow(playerPos.getX() - pos.getX() , 2) + Math.pow( playerPos.getY() - pos.getY(), 2) ) <= range;
    }

	public void updateInput(){
		cooldownTime = 500;
		if(inRange(100)){

			if(this.getState().equals("Idel")){
				Player player = Player.player;
        		double money = player.getMoney();
				if (InputUtility.getKeyPressed(KeyCode.R) && money >= value && !onCooldown()){
					this.setState("Follow");
					player.setMoney(player.getMoney() - value);
					this.setFollowEntity(player);
				}

			}else{

				Player player = Player.player;
				if (InputUtility.getKeyPressed(KeyCode.R) && !onCooldown()){
					Inventory inventory = player.getInventory();
					int currIndex = inventory.getUI().getSelectIndex();
					BaseObject obj = inventory.getObject(currIndex);
					
					if(obj != null && obj instanceof BaseWeapon && !(obj instanceof Pickaxe)){
						BaseWeapon weapon =  (BaseWeapon) obj;
						inventory.removeItem(inventory.getUI().getSelectIndex());
						inventory.addItem(this.getEquipment());
						this.setEquipment(weapon);
						player.setEquipment(inventory.getObject(inventory.getUI().getSelectIndex()));
					}
					else if(obj != null && obj instanceof HealPotion){
						HealPotion potion =  (HealPotion) obj;
						potion.use(this);
					}
					else if(this.getEquipment() != null){
						inventory.addItem(this.getEquipment());
						this.setEquipment(null);
						player.setEquipment(inventory.getObject(inventory.getUI().getSelectIndex()));
					}

				}

			}
		}
		
		if (InputUtility.getKeyPressed(KeyCode.Q) && !onCooldown()){
			this.setRunBack(true);
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		Point pos = this.getPosition();
		gc.drawImage(image, pos.getX(), pos.getY(), image.getWidth(), image.getHeight());
		this.drawHP(gc);

		if (equipment != null) {
			equipment.draw(gc);
		}

		if(this.getState().equals("Idel")){
			gc.setFill(Color.GREY);
			double x = pos.getX();
			double y = pos.getY();
			gc.fillRect(x - 5, y - 5, 8, 70);
			gc.fillRect(x + 15, y - 5, 8, 70);
			gc.fillRect(x + 35, y - 5, 8, 70);
			gc.fillRect(x + 55, y - 5, 8, 70);
			gc.fillRect(x - 5, y - 15, 68, 10);
			gc.fillRect(x - 5, y + 65, 68, 10);
		}

		if(inRange(100)){
			gc.setFont(new Font("Arial", 24));
            gc.setFill(Color.BLACK);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(5);

            gc.strokeText("R", pos.getX() + 20, pos.getY() - 40, 24);
            gc.fillText("R", pos.getX() + 20, pos.getY() - 40, 24);

			if(this.getState().equals("Idel")){
				gc.setFont(new Font("Arial", 24));
				gc.setFill(Color.BLACK);
				gc.setStroke(Color.WHITE);
				gc.setLineWidth(5);

				gc.strokeText(Double.toString(value)+"$", pos.getX(), pos.getY() + 100, 100);
				gc.fillText(Double.toString(value)+"$", pos.getX(), pos.getY() + 100, 100);
			}
		}
	}

	@Override
	public void attack() {
		if(equipment == null) {
			follow(followEntity); 
			return;
		}

		if(targetEntity != null && !(targetEntity instanceof Player)){
			if(equipment instanceof Melee){
				follow(targetEntity);
				if(distance(targetEntity.getPosition()) <= 100)
					equipment.attack();
			}else if(equipment instanceof Gun){
				if(equipment instanceof Gun && distance(followEntity.getPosition()) >= 300)
					follow(followEntity);
				equipment.attack(); 	
			}

		} 
		else follow(followEntity);
	}

	public void doBehavior() {
		if(this.getState().equals("Idel")) return;
		
		if(this.isRunBack()){
			onBack();
			follow(followEntity);
		}
		else attack();
	}

	public void follow(Entity entity) {
		if(entity == null) return;

		Point pp = entity.getPosition();
		if(entity instanceof Player) pp = new Point(pp.getX() + offSet.getX(), pp.getY() + offSet.getY());

		double px = pp.getX(), py = pp.getY();

		Point p = new Point(this.getPosition().getX() - px, this.getPosition().getY() - py );
		double distance = Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());

		DataEntity data = this.getData();

		if (distance < 600) {
			if(entity instanceof Player && distance < 150) return;
			// double mx = -p.getX() / distance * data.getSpd();
			// double my = -p.getY() / distance * data.getSpd();
			// Check for obstacles
			// if (!this.isLegalMove(mx, my)) {
			// 	// Turn 90 degrees
			// 	double newX = -p.getY();
			// 	double newY = p.getX();
			// 	double newDistance = Math.sqrt(newX * newX + newY * newY);
			// 	this.move(newX / newDistance * data.getSpd(), 0);
            //     this.move(0, newY / newDistance * data.getSpd());
			// } else {
				// Move towards target
				this.move(-p.getX() / distance * data.getSpd(), 0);
                this.move(0, -p.getY() / distance * data.getSpd());
			//}
		}else if(distance >= 600 && entity instanceof Player){
			warpToEntity(entity);
		}

	}

	public void warpToEntity(Entity entity){
		this.getPosition().setX(entity.getPosition().getX());
		this.getPosition().setY(entity.getPosition().getY());
	}

	public double distance(Point pos1) {
		Point pos2 = this.getPosition();
		return Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2));
	}

	public void findNearestMonster(ArrayList<BaseObject> gameObjectContainer) {
		Entity nearestMonster = Player.getPlayer();
		double minDistance = 1e9;

		for (int i = gameObjectContainer.size() - 1; i >= 0; i--) {
			BaseObject object = gameObjectContainer.get(i);
			if ((object instanceof Monster || object instanceof BossEntity) && distance(object.getPosition()) <= maxDistance) {
				if (distance(object.getPosition()) <= minDistance) {
					nearestMonster = (Entity) object;
					minDistance = distance(object.getPosition());
				}
			}
		}

		targetEntity = nearestMonster;
	}

	public double getEntityAngle() {

		Point pos = this.getPosition();

		double posX = targetEntity.getPosition().getX();
		double posY = targetEntity.getPosition().getY();

		double startAt = Math.atan2(posY - (pos.getY() + this.getHeight() / 2),
				posX - (pos.getX() + this.getWidth() / 2));
		if (startAt < 0) {
			startAt += 2 * Math.PI;
		}
		return 360 - Math.toDegrees(startAt);
	}

	public Point getEntityVector(){
		Point pos = this.getPosition();

		double posX = targetEntity.getPosition().getX();
		double posY = targetEntity.getPosition().getY();
		Point vector = new Point(posX - pos.getX(), posY - pos.getY());
		vector.unit();

		return vector;
	}

	// getter setter 

	public Entity getFollowEntity() {
		return followEntity;
	}

	public void setFollowEntity(Entity followEntity) {
		this.followEntity = followEntity;
	}

	public double getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(double maxDistance) {
		this.maxDistance = maxDistance;
	}

	public BaseWeapon getEquipment() {
		return equipment;
	}

	public void setEquipment(BaseWeapon equipment) {
		if(equipment == null) {
			this.equipment = null;
			return;
		}

		this.equipment = equipment;
		equipment.setEntity(this);
	}

	public Entity getTargetEntity() {
		return targetEntity;
	}

	public void setTargetEntity(Entity targetEntity) {
		this.targetEntity = targetEntity;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isRunBack() {
		return runBack;
	}

	public void setRunBack(boolean runBack) {
		this.runBack = runBack;
	}

	@Override
	public boolean onCooldown() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastClickTime > cooldownTime) {
			lastClickTime = currentTime;
			return false;
		} else {
			return true;
		}
	}

	public boolean onBack() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastClickTime > backTime) {
			this.setRunBack(false);
			lastClickTime = currentTime;
			return false;
		} else {
			return true;
		}
	}

}
