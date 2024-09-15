package HumanComputerInterface.useless;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.ArrayList;
/*
// this class is bad because canvas is not the object I should use
public class MasterCanvas extends Canvas
{
    private ArrayList<Circle> circles;
    private final double circle_radius = 10.;

    private Circle selected_circle;
    private GraphicsContext gc;
    //left click : move canvas or select object, move this object

    //right click : add a vertex or add an edge

    MasterCanvas(double width, double height)
    {
        super(width,height);

        this.circles=new ArrayList<>();

        this.maxWidth(Double.MAX_VALUE);
        this.maxHeight(Double.MAX_VALUE);
        gc = getGraphicsContext2D();





        this.addEventHandler(MouseEvent.MOUSE_PRESSED,
                event -> {

                    if(event.getButton().toString().equals("PRIMARY"))
                        leftClick();
                    else if(event.getButton().toString().equals("SECONDARY"))
                        rightClick();


                });


        //createCircle(100,100);
        selected_circle = new Circle(150,100,circle_radius);


        createCircle(null,140,90);
        createCircle(null,140,100);
        createCircle(null,150,90);
        createCircle(null,150,100);
        createCircle(null,150,110);
        createCircle(null,160,100);
        createCircle(null,160,110);
        createCircle(null,200,100);




    }

    private void createCircle(Circle c, double x, double y)
    {
        circles.add(new Circle(x,y,circle_radius));

        gc = getGraphicsContext2D();

        Stop[] stops = new Stop[]{new Stop(0, Color.LIGHTSKYBLUE), new Stop(1, Color.BLUE)};
        LinearGradient gradient = new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE, stops);
        gc.setFill(gradient);
        gc.fillOval(x, y, circle_radius, circle_radius);
        gc.translate(0, 0);
        gc.fill();
        gc.stroke();
    }

    private void eraseSelectedCircle(boolean delete)
    {
        //if delete is true will remove from the collection circles
        //set delete as true only if you're deleting the vertex

        System.out.println(selected_circle.x);
        gc.clearRect(selected_circle.x,selected_circle.y,circle_radius,circle_radius);


        if(delete)
        {
            circles.remove(selected_circle);
            selected_circle=null;
        }

    }

    private void moveSelectedCircle()
    {

    }

    private void leftClick()
    {
        System.out.println("do stuff left click");

    }

    private void rightClick()
    {
        System.out.println("do stuff right click");

    }


}
*/
