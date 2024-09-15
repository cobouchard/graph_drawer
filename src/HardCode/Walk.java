package HardCode;

import HardCode.Edge;
import HardCode.Vertex;

import java.util.ArrayList;

public class Walk
{

    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;


    Walk(ArrayList<Vertex> vertices_)
    {
        vertices=vertices_;
        edges = new ArrayList<>();

        for(int i=0; i!= vertices.size()-1; i++)
        {
            edges.add(new Edge(vertices.get(i),vertices.get(i+1)));
        }
    }

    public int getLenght()
    {
        return edges.size();
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
        //if the vertices of a trail are distinct , then the trail is a path
        if(!isTrail())
            return false;

        for(Vertex v : vertices)
            for(Vertex v2 : vertices)
                if(v.equals(v2))
                    return false;



        return true;
    }

    public boolean isCircuit()
    {
        //if the starting vertex and the ending vertex of the sequence is the same, the Trail is a circuit

        if(!isTrail())
            if(vertices.get(0).equals(vertices.get(vertices.size()-1)))
                return true;
        return false;
    }

    public boolean isCycle()
    {

        if(isCircuit() && isPath())
            return true;

        return false;
    }



}
