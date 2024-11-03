package entity;

import java.util.ArrayList;

import data.DataEntity;
import dungeon.GenerateDungeon;

public enum MonsterType {
    DEMON, GOBLIN, SKELETON, SLIME, ZOMBIE;

    public static ArrayList<MonsterType> getAllType(){
        ArrayList<MonsterType> types = new ArrayList<>();
        types.add(MonsterType.SLIME);
        types.add(MonsterType.ZOMBIE);
        types.add(MonsterType.SKELETON);
        types.add(MonsterType.GOBLIN);
        types.add(MonsterType.DEMON);
        return types;
    }

    public static Monster getRandomMonster(){

        int level = GenerateDungeon.getCurrLevel() + 1;
        ArrayList<MonsterType> types = MonsterType.getAllType();
        int chooseType = (int)(Math.random() * Math.min(Math.ceil((level + 5) / 5), types.size()) );
        MonsterType type = types.get(chooseType);

        Monster monster;
        if(type.equals(MonsterType.SLIME)){
            monster = new Slime("Slime", 0, 0, new DataEntity(Math.min(40, level * 2), Math.min(3, level * 0.8), 5, 10));
        }else if(type.equals(MonsterType.ZOMBIE)){
            monster = new Zombie("Zombie", 0, 0, new DataEntity(Math.min(40, level * 2), Math.min(3, level * 0.8), 5, 7));
        }else if(type.equals(MonsterType.GOBLIN)){
            monster = new Goblin("Goblin", 0, 0, new DataEntity(Math.min(20, level * 1.5), Math.min(3, level), 5, 15));
        }else if(type.equals(MonsterType.SKELETON)){
            monster = new Skeleton("Skeleton", 0, 0, new DataEntity(Math.min(30, level * 2), Math.min(3, level), 5, 5));
        }else{
            monster = new Demon("Demon", 0, 0, new DataEntity(Math.min(20, level * 1.5), Math.min(3, level * 0.8), 5, 10));
        }

        return monster;
    }

}
