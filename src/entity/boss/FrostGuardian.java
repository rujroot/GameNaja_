package entity.boss;

import animation.AnimationController;
import animation.AnimationManager;
import animation.ElementAttackAnimation;
import animation.ImageAnimation;
import animation.RectAttackAnimation;
import data.DataEntity;
import data.Point;
import entity.Monster;
import entity.MonsterType;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.Hitbox;
import logic.Main;
import logic.RenderableHolder;

public class FrostGuardian extends BossEntity {
    
    private ImageAnimation[] imageAnimation = new ImageAnimation[10];
    private AnimationManager animationManager;
    private String status = "Idel";
    private boolean actionDone = true;

    private Point target;
    private int count = 0;

    private double cooldownTime = 2000;
	private double lastClickTime = 0;

    public FrostGuardian(String name, DataEntity data) {
        super(name, data, new Hitbox(new Point(150, 60), 250, 270));
        this.setWidth(260);
        this.setHeight(310);

        initAnimation();
    }

    public void initAnimation(){
        animationManager = new AnimationManager(position, 0, 0);

        Image[][] FrostGuardain = RenderableHolder.imageAnimation.get("FrostGuardain");

        // Idel Animation
        imageAnimation[0] = new ImageAnimation(this.getPosition(), this.getWidth(), this.getHeight(), 
                            FrostGuardain[0], 10, 7);
        imageAnimation[0].setLoop(true);

        // Attack animation
        imageAnimation[1] = new ImageAnimation(this.getPosition(), this.getWidth(), this.getHeight(), 
                                FrostGuardain[1], 7, 15);

        // set animation to manager
        animationManager.addAnimation(imageAnimation[0]);

        AnimationController.animations.add(animationManager);
        //AnimationController.animations.add(imageAnimation[1]);
    }

    @Override
    public void draw(GraphicsContext gc) {
        Image image = animationManager.getCurrAnimation().getCurrImage();
        Point pos = this.getPosition();
        Hitbox hitbox = this.getBossHitbox();
        gc.drawImage(image, pos.getX() - hitbox.getPosition().getX(), pos.getY() - hitbox.getPosition().getY(), image.getWidth(), image.getHeight());
        this.drawHP(gc);
    }

    @Override
    public void attack() {
        if(actionDone == false){
            playAction(status);
            return;
        }
        actionDone = false;
        // Random Action to play
        String[] action = {"Attacking10", "Attacking20", "Attacking30"};
        status = action[(int)(Math.random() * 3)];
        
    }

    private void playAction(String status) {
        if(status.equals("Attacking10") || status.equals("Attacking11") || status.equals("Attacking12") || status.equals("wait1")){
            playAttackP1();
        }else if(status.equals("Attacking20") || status.equals("Attacking21") || status.equals("Attacking22") || status.equals("Attacking23") || status.equals("Attacking24") || status.equals("wait2")){
            playAttackP2();
        }else if(status.equals("Attacking30") || status.equals("Attacking31") || status.equals("Attacking32") || status.equals("wait3")){
            playAttackP3();
        }else if(status.equals("Attacking13") || status.equals("Attacking25") || status.equals("Attacking33") ){
            cooldownTime = 5000;
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > cooldownTime) {
                this.setStatus("waitDone");
            }
        }else if(status.equals("waitDone")){
            count = 0;
            actionDone = true;
        }
    }

    public void playAttackP1(){
        if(this.getDistance() > 300 && !status.equals("wait1")){
            follow();
        }else if(status.equals("wait1")){
            cooldownTime = 1000;
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > cooldownTime) {
                count++;
                status = "Attacking1" + Integer.valueOf(count);
            }
        }else{
            status = "wait1";
            imageAnimation[1].setStop(false);
            animationManager.addAnimation(imageAnimation[1]);
            
            Point pos = Player.getPlayer().getPosition();
            RectAttackAnimation rect = new RectAttackAnimation(new Point(pos.getX(), pos.getY()), 100, 3, 40);
            AnimationController.animations.add(rect);
            Main.getLogic().addObject(rect);

            long currentTime = System.currentTimeMillis();
            lastClickTime = currentTime;
        }
    }

    public void playAttackP2(){
        if(status.equals("wait2")){
            cooldownTime = 330;
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > cooldownTime) {
                count++;
                status = "Attacking2" + Integer.valueOf(count);
            }
        }else{
            status = "wait2";
            imageAnimation[1].setStop(false);
            animationManager.addAnimation(imageAnimation[1]);
            
            Point pos = this.getPosition();
            ElementAttackAnimation element = new ElementAttackAnimation(pos, 1000, 4, 200, new Point(250/2, 270/2));
            AnimationController.animations.add(element);
            Main.getLogic().addObject(element);

            long currentTime = System.currentTimeMillis();
            lastClickTime = currentTime;
        }
    }

    public void playAttackP3(){
        if(status.equals("wait3")){
            cooldownTime = 3000;
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > cooldownTime) {
                count++;
                status = "Attacking3" + Integer.valueOf(count);
            }
        }else{
            status = "wait3";
            imageAnimation[1].setStop(false);
            animationManager.addAnimation(imageAnimation[1]);
            
            for(int i = 0; i < 2; ++i){
                Monster monster = MonsterType.getRandomMonster();
                Point pos = this.getPosition();
                monster.setPosition(new Point(pos.getX() + Math.random() * 200, pos.getY() + Math.random() * 200));
                Main.getLogic().addObject(monster);
            }

            long currentTime = System.currentTimeMillis();
            lastClickTime = currentTime;
        }
    }

    public double getDistance(){

        Point pp = Player.getPlayer().getPosition();
        double px = pp.getX() , py = pp.getY();

		Point p = new Point(this.getPosition().getX() - px, this.getPosition().getY() - py);
		double distance = Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());

        return distance;
    }

    public void follow() {

		Point pp = Player.getPlayer().getPosition();
        if(target != null) pp = target;

		double px = pp.getX() , py = pp.getY();

		Point p = new Point(this.getPosition().getX() - px , this.getPosition().getY() - py);
		double distance = Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());

		DataEntity data = this.getData();
        
		if (distance > 20) {
			this.move(-p.getX() / distance * data.getSpd(), 0);
			this.move(0, -p.getY() / distance * data.getSpd());
		}

	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
