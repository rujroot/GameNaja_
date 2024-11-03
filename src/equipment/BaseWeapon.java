package equipment;

import data.BaseObject;
import data.Point;
import entity.Entity;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.Hitbox;

public abstract class BaseWeapon extends BaseObject implements Cloneable{
	private double attackDamage, attackSpeed, value = 50;
	private Point offsetPosition = new Point(30.0, 20.0);
	private Entity entity;
	private Hitbox attackHitBox;
	private Image image;
	
	@Override
	public Point getPosition() {
		//Point Player
		Point pp = entity.getPosition();
		double px = pp.getX(), py = pp.getY();
		
		Point p = new Point(px + offsetPosition.getX(), py + offsetPosition.getY());
		return p;
	}

	public Point getPlayerPosition() {
		return Player.getPlayer().getPosition();
	}

	public Point getResolutionPostion(){
		Point pp = Player.getPlayer().getResolutionPosition();
		double px = pp.getX(), py = pp.getY();
		
		Point p = new Point(px + offsetPosition.getX(), py + offsetPosition.getY());
		return p;
	}

	
	
	public abstract void attack();

	public void draw(GraphicsContext gc){
		Point pos = this.getPosition();
		gc.drawImage(image, pos.getX()-30, pos.getY(), image.getWidth(), image.getHeight());
	}
	
	public BaseWeapon(double width, double height, double attackDamage, double attackSpeed) {
		super(new Point(0, 0), width, height);
		this.setAttackDamage(attackDamage);
		this.setAttackSpeed(attackSpeed);

		this.setEntity(Player.getPlayer());
	}
	
	public BaseWeapon(double width, double height, double attackDamage) {
		super(new Point(0, 0), width, height);
		this.setAttackDamage(attackDamage);
		this.setAttackSpeed(1);

		this.setEntity(Player.getPlayer());
	}

	public double getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(double attackDamage) {
		this.attackDamage = attackDamage;
	}

	public double getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(double attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public Hitbox getAttackHitBox() {
		return attackHitBox;
	}

	public void setAttackHitBox(Hitbox attackHitBox) {
		this.attackHitBox = attackHitBox;
	}
	
	public Point getOffsetPosition() {
		return offsetPosition;
	}
	
	public void setOffsetPosition(Point offsetPosition) {
		this.offsetPosition = offsetPosition;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
    public Object clone() throws CloneNotSupportedException {
        BaseWeapon clone = (BaseWeapon) super.clone();
        return clone;
    }

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
