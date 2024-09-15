package HardCode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;


public class Graph {
    private boolean isOriented; //if false, start and end edges are equivalent
    private boolean isWeighted; //non weighted graphs have all edges weighting 1
    private boolean isLoopAutorized;


    //The graph is defined normally by a set of vertices and a list of edges
    //It's in fact here a pseudo-multigraph
    protected HashSet<Vertex> vertices;
    protected ArrayList<Edge> edges;

    Graph(boolean isOriented_, boolean isWeighted_, boolean isLoopAutorized_)
    {
        isOriented=isOriented_;
        isWeighted=isWeighted_;
        isLoopAutorized=isLoopAutorized_;

        vertices = new HashSet<>();
        edges = new ArrayList<>();
    }

    public boolean addVertex(int id)
    {
        return vertices.add(new Vertex(id));
    }



    public boolean addVertices(HashSet<Integer> list_of_ids)
    {
        //return true if every Vertices had been added, false otherwise
        //don't stop to add Vertices if one is not added

        boolean test = true;

        for(Integer i : list_of_ids)
        {
            if(!addVertex(i))
                test=false;
        }
        return test;
    }


    public boolean addEdge(int id_start, int id_end, double weight)
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
            return false;
        else
            return addEdge(v_start,v_end,weight);

    }

    public boolean addEdge(int id_start, int id_end)
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
            return false;
        else
            return addEdge(v_start,v_end);

    }

    private boolean addEdge(Vertex start, Vertex end, double weight)
    {
        if(!isLoopAutorized && start.equals(end))
            return false;


        return edges.add(new Edge(start,end,weight));
    }

    private boolean addEdge(Vertex start, Vertex end)
    {
        if(!isLoopAutorized && start.equals(end))
            return false;

        return edges.add(new Edge(start,end));
    }


    public boolean addEdges()
    {
        //not implemented
        return false;
    }

    private void resetStates()
    {
        for(Vertex v : vertices)
            v.setState('0');
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

    public boolean hasCycle(){return false;}

    public boolean isTree()
    {
        boolean test=true;

        if(hasCycle())
            test = false;

        else
        {
            HashSet<Vertex> temp_vertices = new HashSet<>();

            for(Edge e : edges)
            {
                temp_vertices.add(e.getStartVert());
                temp_vertices.add(e.getEndVert());
            }

            if(!(temp_vertices==vertices))
                test=false;


        }

        return test;

    }

    public Graph BreadthFirstSearch(Vertex start)
    {
        Graph search = new Graph(isOriented,false,false);
        search.vertices=vertices;

        ArrayList<Vertex> queue = new ArrayList<>();

        queue.add(start);

        while(!queue.isEmpty())
        {
            Vertex temp = queue.get(0);
            //int position = file.size()-1;
            for(Edge e : edges)
            {
                if(e.getStartVert().equals(temp))
                {
                    Vertex neighbour = e.getEndVert();
                    queue.add(neighbour);
                    search.addEdge(temp,neighbour);
                }
            }

            queue.remove(0);
        }

        return search;
    }

    public Graph DepthFirstSearch(Vertex start)
    {
        resetStates();
        Graph search = new Graph(isOriented,false,false);
        search.vertices=vertices;

        ArrayList<Vertex> stack = new ArrayList<>();
        stack.add(start); start.setState('1');

        while(!stack.isEmpty())
        {
            Vertex v = stack.get(stack.size()-1);
            boolean hasSuccessor = false;
            for(Edge e : edges)
            {
                if(hasSuccessor)
                    continue;

                if(e.getStartVert().equals(v))
                {
                    Vertex temp = e.getEndVert();
                    if(temp.getState()=='0')
                    {
                        temp.setState('1');
                        stack.add(temp);
                        search.addEdge(v,temp);
                        hasSuccessor = true;
                    }
                }
            }

            if(!hasSuccessor)
            {
                v.setState('2');
                stack.remove(v);
            }

        }

        return search;
    }

    public Graph getMinimumWeightSpanningTree()
    {
        //Kruskal's Algorithm
        Graph tree =  new Graph(false,true,false);

        for(Vertex v : vertices)
            tree.vertices.add(v);

        ArrayList<Edge> temp_edges = edges;

        while(!tree.isTree())
        {
            Edge edge_to_add = temp_edges.get(0);
            for(Edge e : temp_edges)
            {
                if(edge_to_add.getWeight()>e.getWeight())
                    edge_to_add=e;
            }

            tree.edges.add(edge_to_add);

            if(!tree.isTree())
                tree.edges.remove(edge_to_add);

            temp_edges.remove(edge_to_add);

        }

        return tree;
    }

    public int[][] getAdjacencyMatrix()
    {
        //only for unoriented graph for the moment
        int size = vertices.size();
        int[][] matrix = new int[size][size];

        for(int i=0; i!= size;i++)
            for(int k=0; k!= size;k++)
                matrix[i][k]=0;

        for(int i=0; i!= size;i++)
        {
            for(int k=0; k!= size;k++)
            {
                for(Edge e : edges)
                {
                    if(e.getStartVert().getIdVertex()==i && e.getEndVert().getIdVertex()==k)
                        matrix[i][k]++;

                    else if(e.getStartVert().getIdVertex()==k && e.getEndVert().getIdVertex()==i)
                        matrix[i][k]++;
                }
            }
        }

        return matrix;
    }

    public void saveGraph(String name)
    {
        File file = new File(name+".graph");
        try(FileOutputStream fop = new FileOutputStream(file))
        {

            for(Edge e : this.edges)
            {
                String edge_to_write="";
                edge_to_write += (e.getStartVert().toString()+e.getEndVert().toString()+e.getWeight()+'\n');
                System.out.println(edge_to_write);
                fop.write(edge_to_write.getBytes());
            }
            System.out.println("sauvegarde");
        }
        catch(Exception e)
        {

        }
    }

    public static void main(String[] args)
    {
        Graph test = new Graph(false,false,false);
        test.addVertex(1);
        test.addVertex(2);
        test.addVertex(3);
        test.addVertex(4);
        test.addVertex(5);
        test.addVertex(6);
        test.addVertex(7);

        test.addEdge(1,2);
        test.addEdge(1,3);
        test.addEdge(2,3);
        test.addEdge(3,6);
        test.addEdge(4,6);
        test.addEdge(2,4);
        test.addEdge(2,7);
        test.addEdge(4,7);
        test.addEdge(4,5);
        test.addEdge(5,6);

        test.saveGraph("sauvegarde");


        //System.out.println(test.addEdge(1,1,6.));


        /*int[][] matrix = test.getAdjacencyMatrix();

        for(int i=0; i!= 5;i++)
        {
            for(int k=0; k!= 5;k++)
            {
                System.out.print(matrix[i][k]);
            }
            System.out.print('\n');
        }

        test.displayEdges();*/
    }

}
