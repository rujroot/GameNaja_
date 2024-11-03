package animation;

import data.Point;
import drawing.GameScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class AttackEffect extends Thread {

    private Point position;
    double start, end;

    public AttackEffect(Point position, double start, double end) {
        this.setPosition(position);
        this.setStart(start);
        this.setEnd(end);
    }

    public void run(){
        GraphicsContext gc = GameScreen.gc;
        try {
			for(int i = (int) 0; i < end; ++i){
                gc.setFill(Color.WHITE);
                gc.fillArc(position.getX(), position.getY(), 100, 100, start, i, ArcType.ROUND);
                Thread.sleep(10);
            }
		} catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
		}
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }
}
// package animation;

// import data.Point;
// import drawing.GameScreen;
// import javafx.scene.canvas.GraphicsContext;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.ArcType;

// public class AttackEffect extends Thread {

//     private Point position;
//     double start, end;

//     public AttackEffect(Point position, double start, double end) {
//         this.setPosition(position);
//         this.setStart(start);
//         this.setEnd(end);
//     }

//     public void run(){
//         GraphicsContext gc = GameScreen.gc;
//         try {
// 			for(int i = (int) 0; i < end; ++i){
//                 gc.setFill(Color.WHITE);
//                 gc.fillArc(position.getX(), position.getY(), 100, 100, start, i, ArcType.ROUND);
//                 Thread.sleep(10);
//             }
// 		} catch (Exception e) {
//             System.out.println("Error");
//             e.printStackTrace();
// 		}
//     }

//     public Point getPosition() {
//         return position;
//     }

//     public void setPosition(Point position) {
//         this.position = position;
//     }

//     public double getStart() {
//         return start;
//     }

//     public void setStart(double start) {
//         this.start = start;
//     }

//     public double getEnd() {
//         return end;
//     }

//     public void setEnd(double end) {
//         this.end = end;
//     }

    
// }
