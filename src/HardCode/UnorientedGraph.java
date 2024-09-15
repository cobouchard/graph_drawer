package HardCode;

import HardCode.AbstractGraph;
import HardCode.Graph;

public class UnorientedGraph extends AbstractGraph
{
    public UnorientedGraph(boolean isWeighted, boolean isLoopAuthorized){super(isWeighted,isLoopAuthorized);}

    @Override
    public boolean hasCycle() {
        return false;
    }

    @Override
    public boolean isTree() {
        return false;
    }

    @Override
    public Graph BreadthFirstSearch(Vertex start) {
        return null;
    }

    @Override
    public Graph DepthFirstSearch(Vertex start) {
        return null;
    }

    @Override
    public Graph getMinimumWeightSpanningTree() {
        return null;
    }

    @Override
    public int[][] getAdjacencyMatrix() {
        return new int[0][];
    }
}
