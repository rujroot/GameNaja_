package entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import data.BaseObject;
import data.DataEntity;
import data.Point;
import drawing.Description;
import drawing.GameScreen;
import dungeon.BossRoom;
import dungeon.GenerateDungeon;
import dungeon.Room;
import equipment.*;
import input.InputUtility;
import inventory.Inventory;
import item.HealPotion;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import logic.Cooldownable;
import logic.Main;
import logic.RenderableHolder;
public class Player extends Entity implements Cooldownable {

	public static Player player;
	private double cooldownTime = 2000;
	private double lastClickTime = 0;
	private double money = 0;

	public static Inventory inventory;

	private BaseWeapon equipment;
	private Point resolutionPosition;
	private WritableImage image = new WritableImage(RenderableHolder.Tileset.getPixelReader(), 960, 944, 59, 79);
	private Description description, descriptionMoney, descriptionFloor;

	public Player(String name, DataEntity data) {
		super(name, 0, 0, data);
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());
		player = this;
	}

	public double getMouseAngle() {
		// Attack Object section
		Point pos = this.getPosition();
		Point res = GameScreen.resolution;
		double mouseX = InputUtility.mouseX, mouseY = InputUtility.mouseY;

		double posMouseX = pos.getX() + mouseX - res.getX() / 2;
		double posMouseY = pos.getY() + mouseY - res.getY() / 2;

		double startAt = Math.atan2(posMouseY - (pos.getY() + this.getHeight() / 2),
				posMouseX - (pos.getX() + this.getWidth() / 2));
		if (startAt < 0) {
			startAt += 2 * Math.PI;
		}
		return 360 - Math.toDegrees(startAt);
	}

	public void update() throws CloneNotSupportedException {
		// Move Section
		DataEntity data = this.getData();

		if (InputUtility.getKeyPressed(KeyCode.W) && isLegalMove(0, -1)) {
			this.move(0, -data.getSpd());
		}
		if (InputUtility.getKeyPressed(KeyCode.A) && isLegalMove(-1, 0)) {
			this.move(-data.getSpd(), 0);
		}
		if (InputUtility.getKeyPressed(KeyCode.D) && isLegalMove(1, 0)) {
			this.move(data.getSpd(), 0);
		}
		if (InputUtility.getKeyPressed(KeyCode.S) && isLegalMove(0, 1)) {
			this.move(0, data.getSpd());
		}
		if (InputUtility.getKeyPressed(KeyCode.DIGIT1)) {
			inventory.selectIndex(0);
			this.setEquipment(inventory.getObject(0));
		}
		if (InputUtility.getKeyPressed(KeyCode.DIGIT2)) {
			inventory.selectIndex(1);
			this.setEquipment(inventory.getObject(1));
		}
		if (InputUtility.getKeyPressed(KeyCode.DIGIT3)) {
			this.setEquipment(inventory.getObject(2));
			inventory.selectIndex(2);
		}
		if (InputUtility.getKeyPressed(KeyCode.DIGIT4)) {
			this.setEquipment(inventory.getObject(3));
			inventory.selectIndex(3);
		}
		if (InputUtility.getKeyPressed(KeyCode.DIGIT5)) {
			inventory.selectIndex(4);
			this.setEquipment(inventory.getObject(4));
		}
		if (InputUtility.getKeyPressed(KeyCode.DIGIT6)) {
			inventory.selectIndex(5);
			this.setEquipment(inventory.getObject(5));
		}
		if (InputUtility.getKeyPressed(KeyCode.DIGIT7)) {
			inventory.selectIndex(6);
			this.setEquipment(inventory.getObject(6));
		}
		if (InputUtility.getKeyPressed(KeyCode.DIGIT8)) {
			inventory.selectIndex(7);
			this.setEquipment(inventory.getObject(7));
		}
		if (InputUtility.getKeyPressed(KeyCode.DIGIT9)) {
			inventory.selectIndex(8);
			this.setEquipment(inventory.getObject(8));
		}
		// Action Section
		if ((InputUtility.getKeyPressed(KeyCode.SPACE) || InputUtility.isLeftClickTriggered())) {
			attack();

			int index = inventory.getUI().getSelectIndex();
			BaseObject object = inventory.getObject(index);
			if(object instanceof HealPotion) {
				HealPotion potion = (HealPotion) object;
				potion.use(this);
			}
		}
		if (InputUtility.getKeyPressed(KeyCode.Z) && !onCooldown()) {
			if(this.getEquipment() instanceof Pickaxe) return;
			inventory.removeItem(inventory.getUI().getSelectIndex());
			this.setEquipment(inventory.getObject(inventory.getUI().getSelectIndex()));
		}
	}

	public void initInventory() {
		// create inventory
		inventory = new Inventory();
		Main.getLogic().addObject(inventory);

		inventory.addItem(new Pickaxe());
		inventory.addItem(new HealPotion(3, 10));

		inventory.selectIndex(0);
		this.setEquipment(inventory.getObject(0));

		// create description above inventory
		Point resolution = GameScreen.getResolution();
		Point basePoint = new Point(-resolution.getX() / 4 + 100, resolution.getY() / 3 - 25);
		description = new Description(basePoint, 400, 100, this);

		descriptionMoney = new Description(new Point(-resolution.getX() / 4 + 600, resolution.getY() / 3 - 25), 100.0,
				100.0, this);
		descriptionFloor = new Description(new Point(-resolution.getX() / 4 + 600, resolution.getY() / 3 - 65), 100.0,
				100.0, this);

	}

	@Override
	public void draw(GraphicsContext gc) {
		Point pos = this.getPosition();
		gc.drawImage(image, pos.getX(), pos.getY(), image.getWidth(), image.getHeight());
		this.drawHP(gc);
		description.draw(gc);
		displayMoney(gc);
		displayFloor(gc);

		if (equipment != null) {
			equipment.draw(gc);
		}

	}

	@Override
	public void attack() {
		if (equipment != null)
			equipment.attack();
	}

	public void displayMoney(GraphicsContext gc) {
		BigDecimal bd = new BigDecimal(money);
        bd = bd.setScale(2, RoundingMode.HALF_DOWN);

        double roundedNumber = bd.doubleValue();

		descriptionMoney.setText(Double.toString(roundedNumber) + "$");
		descriptionMoney.draw(gc);
	}
	
	public void displayFloor(GraphicsContext gc) {
		descriptionFloor.setText("Floor " + Integer.toString(GenerateDungeon.getCurrLevel() + 1));
		descriptionFloor.draw(gc);
	}

	public void checkPlayerInBossRoom(){

		int currLevel = GenerateDungeon.getCurrLevel();
		ArrayList<Room> level = GenerateDungeon.getContainer().get(currLevel);

		BossRoom bossRoom = (BossRoom) level.get(level.size() - 1);
		if(bossRoom.isVisited()) return;
		
		double rect1X1 = bossRoom.getPosition().getX();
		double rect1Y1 = bossRoom.getPosition().getY();
		double rect1X2 = bossRoom.getPosition().getX() + bossRoom.getWidth();
		double rect1Y2 = bossRoom.getPosition().getY() + bossRoom.getHeight();
		
		double X1 = this.getPosition().getX();
		double Y1 = this.getPosition().getY();
		double X2 = this.getPosition().getX() + this.getWidth();
		double Y2 = this.getPosition().getY() + this.getHeight();

		// calculate the coordinates of the intersection rectangle
		double xLeft = Math.max(rect1X1, X1);
		double yTop = Math.max(rect1Y1, Y1);
		double xRight = Math.min(rect1X2, X2);
		double yBottom = Math.min(rect1Y2, Y2);

		double intersectionArea = 0.0;
		if (xRight > xLeft && yBottom > yTop) {
			intersectionArea = (xRight - xLeft) * (yBottom - yTop);
		}

		if(intersectionArea >= this.getWidth() * this.getHeight()){
			bossRoom.playerEntry();
		}

	}

	//Getter setter

	public static Player getPlayer() {
		return player;
	}

	public BaseWeapon getEquipment() {
		return equipment;
	}

	public void setEquipment(BaseObject equipment) {
		if (equipment instanceof BaseWeapon) {
			((BaseWeapon) equipment).setEntity(this);
			this.equipment = (BaseWeapon) equipment;
		} else {
			this.equipment = null;
		}
	}

	public Point getResolutionPosition() {
		return resolutionPosition;
	}

	public void setResolutionPosition(Point resolutionPosition) {
		this.resolutionPosition = resolutionPosition;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(String text) {
		this.description.setText(text);
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public void addMoney(double money) {
		this.money += money;
	}

	public Inventory getInventory() {
		return inventory;
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
}
