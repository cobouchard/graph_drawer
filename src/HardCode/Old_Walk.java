package HardCode;

import java.util.ArrayList;

//i don't know yet if this useful
public class Old_Walk extends Graph implements java.lang.Comparable<Old_Walk>
{
    //Unlike in math, defined by a list of edges and not a list of vertices
    //ArrayList<Edge> walk;

    //finding the smallest path is the same if the graph weighted or not since unweighted graph is a graph with all vertices weighting 1
    Double weight;

    Old_Walk(ArrayList<Edge> edges_)
    {
        super(true,true,true); //by default
        weight = 0.;
        edges=edges_;

        for(Edge e : edges)
        {
            //we need to add vertices
            if(!vertices.contains(e.getStartVert()))
                vertices.add(e.getStartVert());

            if(!vertices.contains(e.getEndVert()))
                vertices.add(e.getEndVert());

            weight+=e.getWeight();
        }

    }

    public boolean isTrail()
    {
        //if the edges of a walk are distinct, the walk is a trail
        for(Edge e : edges)
        {
            //not optimized (edges are compaired with each others multiple times)
            for(Edge e2 : edges)
            {
                if(e.equals(e2))
                    return false;
            }
        }

        return true;
    }

    public boolean isPath()
    {
        //if the vertices of a walk are distinct, the walk is a path

        for(Vertex v : vertices)
            for(Vertex v2 : vertices)
                if(v.equals(v2))
                    return false;

        return true;
    }


    //public boolean addEdgeAtStart(Edge e){}
    public boolean addEdgeAtEnd(Edge e)
    {
        boolean test=false;

        if( edges.get(edges.size()-1).getEndVert(). equals (e.getStartVert()) )
        {
            test=true;
            edges.add(e);
            weight+=e.getWeight();
        }

        return test;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Old_Walk o) {
        return this.weight.compareTo(o.weight);
    }
}
