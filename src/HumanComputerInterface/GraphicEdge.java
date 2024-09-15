package HumanComputerInterface;


import HardCode.Edge;
import HumanComputerInterface.useless.GraphicObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;

import java.util.function.Function;

public class GraphicEdge extends GraphicObject {
    public Edge edge;
    public QuadCurve quad;

    public GraphicVertex start;
    public GraphicVertex end;

    public static double strokeWidth=3.;

    private Function<Double,Double> bisectionFormula;


    GraphicEdge(Edge edge_, GraphicVertex start_, GraphicVertex end_)
    {
        start=start_;
        end=end_;
        edge=edge_;

        quad = new QuadCurve();
        quad.toFront();
        quad.setStroke(Color.BLACK);
        quad.setFill(Color.TRANSPARENT);
        quad.setStrokeWidth(strokeWidth);



        quad.setControlX((start.circle.getCenterX()+end.circle.getCenterX())/2);
        quad.setControlY((start.circle.getCenterY()+end.circle.getCenterY())/2);

        resetBisection();

    }

    public void mouseDragged(double x_)
    {
        double x = x_;

        if(true)
        {
            quad.setControlX(x);
            quad.setControlY(bisectionFormula.apply(x));
        }

    }

    public void resetBisection()
    {
        bisectionFormula = Bisection.getFunction(start.circle,end.circle);
        resetQuadCurve();
    }

    private void resetQuadCurve()
    {
        quad.setStartX(start.circle.getCenterX());
        quad.setStartY(start.circle.getCenterY());
        quad.setEndX(end.circle.getCenterX());
        quad.setEndY(end.circle.getCenterY());
    }

    public void changeSize(double new_size)
    {
        strokeWidth=new_size;
        quad.setStrokeWidth(new_size);
    }



    public void graphicPrint(MasterRegion mr)
    {
        mr.getChildren().add(quad);
    }

    public void delete(MasterRegion mr)
    {
        mr.getChildren().remove(quad);
        bisectionFormula=null;
        edge=null;
        quad=null;
        start=null;
        end=null;
    }
}
