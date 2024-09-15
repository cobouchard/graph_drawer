package HumanComputerInterface;

public abstract class Zoom {

    private static final double min_width=14.8;
    private static final double max_width=16;
    private static double start_width=15;
    private static int width_max_or_min_state=0;

    public static float getMultiplier(double deltaY)
    {
        float multiplier=0;

        if(deltaY<0 && width_max_or_min_state!=1) // zoom in
        {
            multiplier = 0.90f;
            start_width-=0.10;
            if(start_width<min_width)
                width_max_or_min_state=1;
            else
                width_max_or_min_state=0;
        }

        else if(deltaY>0 && width_max_or_min_state!=2)//zoom out
        {
            multiplier = 1.10f;
            start_width+=0.10;
            if(start_width>max_width)
                width_max_or_min_state=2;
            else
                width_max_or_min_state=0;
        }

        return multiplier;
    }

}
