package HumanComputerInterface.useless;

import HumanComputerInterface.MasterRegion;

import java.awt.event.MouseEvent;

public abstract class GraphicObject {

    abstract public void graphicPrint(MasterRegion mr);

    abstract public void delete(MasterRegion mr);

    public void mousePressed(MouseEvent event)
    {
        //Selecting an object
    }
}
