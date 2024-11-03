package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.media.AudioClip;

public class RenderableHolder {
	private static final RenderableHolder instance = new RenderableHolder();
	
	private List<IRenderable> objects;
	private Comparator<IRenderable> comparator;

	public static Image Tileset, baseFloor, ores, character, atlas, sideWall, mainWall, demon, equipment1, enemy, slime, baseBossFloor;
	public static Image backUI, upUI, selectUI;
	public static AudioClip sound, openSound, gameOverSound;
	public static HashMap<String, Image[][]> imageAnimation = new HashMap<>();

	static {
		//Load Resource first time
		loadResource();
	}

	//set compare follow by Z
	public RenderableHolder() {
		objects = new ArrayList<IRenderable>();
		
		comparator = (IRenderable o1, IRenderable o2) -> {
			if (o1.getZ() > o2.getZ())
				return 1;
			return -1;
		};
	}
	
	//add object to game
	public void add(IRenderable object) {
		//System.out.println("add ");
		objects.add(object);
		Collections.sort(objects, comparator);
	}

	public static Image[] loadAnimation(String path, int n){
		Image[] image = new Image[n + 5];
        for (int i = 1; i <= n; ++i) {
            image[i] = new Image(ClassLoader.getSystemResource(path + Integer.toString(i) + ".png").toString());
        }
		return image;
    }
	
	public static void loadResource() {
		Tileset = new Image(ClassLoader.getSystemResource("res/image/DungeonTileset2.png").toString());
		baseFloor = new Image(ClassLoader.getSystemResource("res/image/floor_light.png").toString());
		baseBossFloor = new Image(ClassLoader.getSystemResource("res/image/floor_plain.png").toString());
		//baseFloor = new Image(ClassLoader.getSystemResource("res/image/baseFloor.png").toString());
		ores = new Image(ClassLoader.getSystemResource("res/image/ores.png").toString());
		character = new Image(ClassLoader.getSystemResource("res/image/character2.png").toString());
		atlas = new Image(ClassLoader.getSystemResource("res/image/atlas2.png").toString());
		backUI = new Image(ClassLoader.getSystemResource("res/image/backui1.png").toString());
		upUI = new Image(ClassLoader.getSystemResource("res/image/upui1.png").toString());
		selectUI = new Image(ClassLoader.getSystemResource("res/image/selectui1.png").toString());

		sideWall = new WritableImage(RenderableHolder.Tileset.getPixelReader(), 304 * 4, 12 * 4, 4 * 4, 32 * 4);
		mainWall = new WritableImage(RenderableHolder.Tileset.getPixelReader(), 1236, 176, 87, 79);
		
		sound = new AudioClip(ClassLoader.getSystemResource("res/audio/01OpeningCinematic.wav").toString());
		sound.setCycleCount(100);
		openSound = new AudioClip(ClassLoader.getSystemResource("res/audio/OpenTheme.wav").toString());
		gameOverSound = new AudioClip(ClassLoader.getSystemResource("res/audio/GameOverTheme.wav").toString());
		
		demon = new Image(ClassLoader.getSystemResource("res/image/0_Golem_Throwing_001.png").toString());
		enemy = new Image(ClassLoader.getSystemResource("res/image/Enemy.png").toString());
		slime = new Image(ClassLoader.getSystemResource("res/image/slime_frames_all.png").toString());
		equipment1 = new Image(ClassLoader.getSystemResource("res/image/fantasy_weapons_pack1_noglow.png").toString());
		
		// Load animation
		Image[][] demonSlime = new Image[5][20];
		demonSlime[0] = loadAnimation("res/image/DemonSlime/idel/", 6);
		demonSlime[1] = loadAnimation("res/image/DemonSlime/cleave/", 15);
		imageAnimation.put("DemonSlime", demonSlime);

		Image[][] FrostGuardain = new Image[5][20];
		FrostGuardain[0] = loadAnimation("res/image/FrostGuardian/idle/", 6);
		FrostGuardain[1] = loadAnimation("res/image/FrostGuardian/atk/", 14);
		imageAnimation.put("FrostGuardain", FrostGuardain);

	}
	
	// Update when entity remove
	public void update() {
		for (int i = objects.size() - 1; i >= 0; i--) {
			if (objects.get(i).isDestroyed())
				objects.remove(i);
		}
	}
	
	// share instance to every calss
	public static RenderableHolder getInstance() {
		return instance;
	}

	public List<IRenderable> getObjects() {
		return objects;
	}

}
