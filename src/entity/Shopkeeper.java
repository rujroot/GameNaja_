package entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import data.BaseObject;
import data.DataEntity;
import data.Point;
import equipment.Axe;
import equipment.BaseWeapon;
import equipment.Bow;
import equipment.IceBow;
import equipment.Knife;
import equipment.LightBow;
import equipment.Punch;
import equipment.Shield;
import equipment.Spear;
import equipment.Wand;
import input.InputUtility;
import inventory.BaseUI;
import inventory.Inventory;
import inventory.SlotUI;
import item.Coal;
import item.Diamond;
import item.GoldIngot;
import item.HealPotion;
import item.IronIngot;
import item.Item;
import item.Stone;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Cooldownable;
import logic.RenderableHolder;

public class Shopkeeper extends Entity implements Cooldownable{

    private ArrayList<BaseWeapon> allBuyItem = new ArrayList<BaseWeapon>();

    // image entity
    private WritableImage image = new WritableImage(RenderableHolder.Tileset.getPixelReader(), 392, 960, 51, 63);

    private BaseUI currentUI, chooseUI, sellUI, buyUI;

    private String choose = "None";
    private double lastClickTime = 0, cooldownTime = 200;
    private int select = 0;

    private Item[] sellItem = new Item[5];
    private BaseObject[] buyWeapon = new BaseObject[4];

    public Shopkeeper(String name, double width, double height, DataEntity data) throws CloneNotSupportedException {
        super(name, width, height, data);
        this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());

        chooseUI = new BaseUI(new Point(-100, -150), 0, 0, 2, 20, this);
        chooseUI.setSelectIndex(0);
        SlotUI[] slotChoose = chooseUI.getPosIndex();
        slotChoose[0].setDescription("BUY");
        slotChoose[1].setDescription("SELL");

        buyUI = new BaseUI(new Point(-150, -150), 0, 0, 4, 10, this);
        buyUI.setSelectIndex(0);

        initShopSell();
        initShopBuy();
    }

    public void initShopSell(){
        sellUI = new BaseUI(new Point(-250, -150), 0, 0, 5, 10, this);
        sellUI.setSelectIndex(0);

        sellItem[0] = new Stone(0, 0);
        sellItem[1] = new Coal(0, 0);
        sellItem[2] = new IronIngot(0, 0);
        sellItem[3] = new GoldIngot(0, 0);
        sellItem[4] = new Diamond(0, 0);

        for(int i = 0; i < sellItem.length; ++i){
            sellUI.addItem(sellItem[i]);
        }
        SlotUI[] slot = sellUI.getPosIndex();
        slot[0].setDescription("0$");
        slot[1].setDescription("0$");
        slot[2].setDescription("0$");
        slot[3].setDescription("0$");
        slot[4].setDescription("0$");
    }

    public void shopSell(int index){
        Item itemToSell = sellItem[index];
        if(itemToSell.getAmount() <= 0) return;

        Player player = Player.player;
        Inventory inventory = Player.inventory;
        SlotUI[] slotInventory = inventory.getUI().getPosIndex();

        for(int i = 0; i < slotInventory.length; ++i){

            if(!(slotInventory[i].getObject() instanceof Item)) continue;

            Item itemInv = (Item) slotInventory[i].getObject();
            if(itemInv != null && itemInv.equals(itemToSell)){ 
                player.addMoney(itemInv.getValue());
                itemInv.setAmount(0);
                itemInv.setValue(0);
                inventory.removeItem(itemInv);
                updateShopSell();
                return;
            }  
        }
    }

    public void updateShopSell(){
        Inventory inventory = Player.inventory;
        SlotUI[] slotInventory = inventory.getUI().getPosIndex();
        SlotUI[] slotSell = sellUI.getPosIndex();

        for(int i = 0; i < slotSell.length; ++i){
            Item itemSell = (Item) slotSell[i].getObject();
            boolean found = false;

            for(int j = 0; j < slotInventory.length; ++j){
                if(!(slotInventory[j].getObject() instanceof Item)) continue;

                Item itemInv = (Item) slotInventory[j].getObject();

                if(itemInv != null && itemSell.equals(itemInv)){
                    BigDecimal bd = new BigDecimal(itemInv.getValue());
                    bd = bd.setScale(2, RoundingMode.HALF_DOWN);

                    double roundedNumber = bd.doubleValue();

                    itemSell.setAmount(itemInv.getAmount());
                    slotSell[i].setDescription(Double.toString(roundedNumber) + "$");
                    found = true;
                    break;
                }
            }

            if(!found){
                itemSell.setAmount(0);
                slotSell[i].setDescription("0$");
            }
        }

    }

    public void initShopBuy() throws CloneNotSupportedException{

        allBuyItem.add(new Knife());
        allBuyItem.add(new Axe());
        allBuyItem.add(new IceBow());
        allBuyItem.add(new LightBow());
        allBuyItem.add(new Punch());
        allBuyItem.add(new Shield());
        allBuyItem.add(new Spear());
        allBuyItem.add(new Wand());
        allBuyItem.add(new Bow());
        
        for(int i = 0; i < 3; ++i){
            int randNum = (int)(Math.random() * allBuyItem.size());

            BaseWeapon weapon = (BaseWeapon) allBuyItem.get(randNum).clone();
            weapon.setValue(weapon.getValue() + (int)(Math.random() * 20) - 20);

            buyWeapon[i] = weapon;
            buyUI.addItem(weapon);
            buyUI.getPosIndex()[i].setDescription(Double.toString(weapon.getValue()) + "$");
        }

        HealPotion Potion = new HealPotion(1, 3);
        buyWeapon[3] = Potion;
        buyUI.addItem(Potion);
        buyUI.getPosIndex()[3].setDescription(Double.toString(Potion.getValue()) + "$");

    }

    public void shopBuy(int index){
        Player player = Player.player;
        double money = player.getMoney();

        BaseObject object = buyWeapon[index];

        if(object instanceof BaseWeapon){

            BaseWeapon item = (BaseWeapon) object;
            double value = item.getValue();

            if(value <= money){
                try {
                    BaseWeapon weapon =  (BaseWeapon) item.clone();
                    player.setMoney(money - value);
                    player.getInventory().addItem(weapon);

                } catch (CloneNotSupportedException e) {
                    System.out.println("item can't clone()");
                    e.printStackTrace();
                }
            }

        }else{
            Item item = (Item) object;
            double value = item.getValue();

            if(value <= money){
                try {
                    Item newItem =  (Item) item.clone();
                    player.setMoney(money - value);
                    player.getInventory().addItem(newItem);

                } catch (CloneNotSupportedException e) {
                    System.out.println("item can't clone()");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {

        // Draw Entity
        Point pos = this.getPosition();
		gc.drawImage(image, pos.getX(), pos.getY(), image.getWidth() , image.getHeight());

        if(inRange() && currentUI == null){
            gc.setFont(new Font("Arial", 24));
            gc.setFill(Color.BLACK);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(5);

            gc.strokeText("E", pos.getX() + 20, pos.getY() - 20, 24);
            gc.fillText("E", pos.getX() + 20, pos.getY() - 20, 24);
        }

        if(!inRange()){
            currentUI = null;
            choose = "None";
            Player.player.setDescription("");
        }

        // Draw Shop
        if(currentUI != null){
            currentUI.draw(gc);
        }
    }

    public boolean inRange(){
        Point playerPos = Player.player.getPosition();
        Point pos = this.getPosition();

        return Math.sqrt( Math.pow(playerPos.getX() - pos.getX() , 2) + Math.pow( playerPos.getY() - pos.getY(), 2) ) <= 250;
    }

    public void updateInput(){
        // Player interact with this entity
        if (InputUtility.getKeyPressed(KeyCode.E) && inRange() && !onCooldown()){
            if(choose.equals("None")){
                choose = "Choose";
                this.setCurrentUI(chooseUI);
                Player.player.setDescription("< : Left | > : Right | E : Select | Q : Back");
            }
            else if(choose.equals("Choose")){
                int currSelect = currentUI.getSelectIndex();
                if(currSelect == 0){
                    this.setCurrentUI(buyUI);
                    choose = "Buy";
                }else{
                    updateShopSell();
                    this.setCurrentUI(sellUI);
                    choose = "Sell";
                }
            }
            else if(choose.equals("Buy")){
                int currSelect = currentUI.getSelectIndex();
                shopBuy(currSelect);
            }
            else if(choose.equals("Sell")){
                int currSelect = currentUI.getSelectIndex();
                shopSell(currSelect);
            }
		}

        if (InputUtility.getKeyPressed(KeyCode.Q) && inRange() && !onCooldown()){
            if(choose.equals("Choose")){
                currentUI = null;
                choose = "None";
                Player.player.setDescription("");
            }
            else if(choose.equals("Buy") || choose.equals("Sell")){
                this.setCurrentUI(chooseUI);
                choose = "Choose";
            }
		}

        if (InputUtility.getKeyPressed(KeyCode.LEFT) && currentUI != null && !onCooldown()){
			currentUI.setSelectIndex(currentUI.getSelectIndex() - 1);

		}
        if (InputUtility.getKeyPressed(KeyCode.RIGHT) && currentUI != null && !onCooldown()){
            currentUI.setSelectIndex(currentUI.getSelectIndex() + 1);

		}

    }
    
    @Override
    public void attack() {
       // This entity not attack 
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public BaseUI getCurrentUI() {
        return currentUI;
    }

    public void setCurrentUI(BaseUI currentUI) {
        if(this.currentUI != null) this.currentUI.setVisible(false);
        currentUI.setVisible(true);
        this.currentUI = currentUI;
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
