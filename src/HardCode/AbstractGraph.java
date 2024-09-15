package HardCode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class AbstractGraph {
    private boolean isWeighted; //non weighted graphs have all edges weighting 1
    private boolean isLoopAuthorized;

    private HashSet<Vertex> vertices;
    private ArrayList<Edge> edges;

    public AbstractGraph(boolean isWeighted_, boolean isLoopAuthorized_)
    {
        isWeighted=isWeighted_;
        isLoopAuthorized=isLoopAuthorized_;

        vertices = new HashSet<>();
        edges = new ArrayList<>();
    }

    public void deleteVertex(Vertex v)
    {
        vertices.remove(v);
    }

    public void deleteEdge(Edge e)
    {
        edges.remove(e);
    }

    public Vertex addVertex(int id)
    {
        Vertex v = new Vertex(id);
        vertices.add(v);
        return v;
    }

    public void addVertices(HashSet<Integer> list_of_ids)
    {

        for(Integer i : list_of_ids)
        {
            addVertex(i);
        }

    }

    public Edge addEdge(int id_start, int id_end)
    {
        Vertex v_start = null;
        Vertex v_end = null;

        for(Vertex v : vertices)
        {
            if(id_start==v.getIdVertex())
                v_start=v;

            if(id_end==v.getIdVertex())
                v_end=v;
        }

        if(v_start==null || v_end==null)
            return null;
        else
            return addEdge(v_start,v_end);

    }

    public Edge addEdge(Vertex start, Vertex end)
    {
        if(!isLoopAuthorized && start.equals(end))
            return null;

        Edge e = new Edge(start,end);
        edges.add(e);
        return e;
    }

    public void displayVertices()
    {
        System.out.println("All vertices :");

        String temp="";
        for(Vertex v : this.vertices)
            temp+=v.toString()+';';

        System.out.print(temp);

    }

    public void displayEdges()
    {
        System.out.println("All edges :");

        String temp="";
        for(Edge e : this.edges)
            temp+=e.toString()+';';

        System.out.print(temp);
    }

    public void saveGraph(String name)
    {
        File file = new File(name+".graph");
        try(FileOutputStream fop = new FileOutputStream(file))
        {
            if(this.getClass().toString().equals("OrientedGraph"))
                fop.write(Byte.parseByte("0"));
            else
                fop.write(Byte.parseByte("1"));

            for(Edge e : this.edges)
            {
                String edge_to_write="";
                edge_to_write.concat(e.getStartVert().toString()+e.getEndVert().toString()+e.getWeight()+'\n');
                fop.write(edge_to_write.getBytes());
            }
        }
        catch(Exception e)
        {

        }
    }



    public abstract boolean hasCycle();
    public abstract boolean isTree();
    public abstract Graph BreadthFirstSearch(Vertex start);
    public abstract Graph DepthFirstSearch(Vertex start);
    public abstract Graph getMinimumWeightSpanningTree();
    public abstract int[][] getAdjacencyMatrix();
}
