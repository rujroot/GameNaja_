package data;

import java.util.ArrayList;

import item.Item;

public class DataOre {
    private double durability, value;
    private ArrayList<Item> toDrop;
    private int amount;

    public DataOre(double durability, double value, ArrayList<Item> toDrop, int amount){
        this.setAmount(amount);
        this.setDurability(durability);
        this.setToDrop(toDrop);
        this.setValue(value);
    }

    public DataOre(double durability, double value){
        this.setDurability(durability);
        this.setValue(value);
        this.setAmount(1);
        this.toDrop = new ArrayList<>();
    }

    public double getDurability() {
        return durability;
    }
    public void setDurability(double durability) {
        this.durability = durability;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public ArrayList<Item> getToDrop() {
        return toDrop;
    }
    public void setToDrop(ArrayList<Item> toDrop) {
        this.toDrop = toDrop;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
}
