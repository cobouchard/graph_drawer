package HumanComputerInterface.useless;

import HumanComputerInterface.GraphicEdge;
import HumanComputerInterface.GraphicVertex;

import java.util.ArrayList;

public final class graphicContainer {
    private ArrayList<GraphicEdge> graphicEdges;
    private ArrayList<GraphicVertex> graphicVertices;


    public graphicContainer(ArrayList<GraphicEdge> graphicEdges, ArrayList<GraphicVertex> graphicVertices) {
        this.graphicEdges = graphicEdges;
        this.graphicVertices = graphicVertices;
    }
}
