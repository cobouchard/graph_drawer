package HumanComputerInterface;

import HardCode.Vertex;
import HumanComputerInterface.useless.GraphicObject;
import javafx.beans.binding.NumberBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphicVertex extends GraphicObject {
    public Circle circle;
    public Vertex vertex;
    public Text text;


    public static Float textWidth=12.f;
    private static double circleRadius = 6;
    private static double textOffsetX = circleRadius;
    private static double textOffsetY = circleRadius;



    private NumberBinding positionProperty;

    GraphicVertex(Vertex v_,double x, double y, String title)
    {
        circle = new Circle(x,y,circleRadius, Color.BLUE);
        circle.setStrokeWidth(2);
        vertex=v_;
        text=new Text();
        text.setText(title);
        changeTextStyle();
        resetTextPosition();

        positionProperty = circle.centerXProperty().add(circle.centerYProperty());

        positionProperty.addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o,Object oldVal,
                                          Object newVal){
                resetTextPosition();
            }
        });

    }

    public void changeTextStyle()
    {
        String number = String.valueOf(textWidth);
        String style = "-fx-font: " + number + " arial;";
        text.setStyle(style);
    }

    public void changeTextSize()
    {
        String old_style = text.getStyle();

        String pattern = "[0-9]+.?[0-9]+";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(old_style);
        m.find();

        Float old_value = Float.parseFloat(m.group(0));

        changeTextStyle();

    }

    public void changeSize(double new_radius)
    {
        GraphicVertex.circleRadius=new_radius;
        circle.setRadius(new_radius);
        textOffsetX=circleRadius;
        textOffsetY=circleRadius;
        changeTextSize();
        resetTextPosition();
    }

    private void resetTextPosition()
    {
        text.setX(circle.getCenterX()+textOffsetX);
        text.setY(circle.getCenterY()+textOffsetY);

    }

    public void mouseDragged(double x, double y)
    {
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    public void mousePressed(MouseEvent event)
    {
        System.out.println("pressed");
    }

    public void graphicPrint(MasterRegion mr)
    {
        mr.getChildren().add(circle);
        mr.getChildren().add(text);
    }

    public void delete(MasterRegion mr)
    {
        mr.getChildren().remove(text);
        mr.getChildren().remove(circle);

        circle=null;
        vertex=null;
        text=null;
    }
}
