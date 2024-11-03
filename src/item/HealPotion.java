package item;

import entity.Entity;
import entity.Player;
import inventory.Inventory;
import javafx.scene.image.WritableImage;
import logic.Cooldownable;
import logic.RenderableHolder;

public class HealPotion extends Item implements Cooldownable {

    private double cooldownTime = 500;
	private double lastClickTime;

    public HealPotion(int amount, double value) {
        super(amount, value);
        this.setName("HealPotion");
        this.setImage(new WritableImage(RenderableHolder.Tileset.getPixelReader(), 784, 720, 819 - 784, 763 - 720));
    }

    public void use(Entity entity){
        if(onCooldown()) return;

        entity.getData().setHp(entity.getData().getHp() + 50);
        this.setAmount(this.getAmount() - 1);

        Inventory inventory = Player.getPlayer().getInventory();
        if(this.getAmount() <= 0){
            int index = inventory.getUI().getSelectIndex();;
            inventory.removeItem(index);
            Player.getPlayer().setEquipment(inventory.getObject(index));
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        HealPotion clone = (HealPotion) super.clone();
        return clone;
    }

    @Override
	public boolean onCooldown() {
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastClickTime > cooldownTime) {
			lastClickTime = currentTime;
			return false;
		}else {
			return true;
		}
	}

}
