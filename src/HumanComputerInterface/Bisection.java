package HumanComputerInterface;

import javafx.scene.shape.Circle;

import java.util.function.Function;

public abstract class Bisection
{
    public static Function getFunction(double x, double y, double x_, double y_)
    {
        //given two points or 4 coordinates
        //using the general equation of the bisection to have a function describing the bisection

        //if the ordinates are equals it means the bisection is in the form x = d
        //can't use the general equation in this case because there is a zero-division

        if(y==y_)
        {
            return (Function<Double, Double>) aDouble -> (y+y_)/2;
        }

        double a = (x-x_)/(y_-y);

        double b = ((x_*x_-x*x)/(y_-y)+y+y_)/2;

        return (Function<Double, Double>) aDouble -> a*aDouble+b;

    }

    public static Function getFunction(Circle start, Circle end)
    {
        return getFunction(start.getCenterX(),start.getCenterY(),end.getCenterX(),end.getCenterY());
    }

    public static void main(String[] args)
    {
        Function<Double,Double> test = getFunction(2,5,6,3);
        System.out.println(test.apply(10.));
    }
}
