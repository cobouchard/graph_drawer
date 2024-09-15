package HardCode;

public class Edge implements java.lang.Comparable<Edge>{
    //this class should only be used by Graph

    private Vertex startVert; //no difference between these attributes if the Graph is unoriented
    private Vertex endVert;

    private Double weight;

    Edge(Vertex start, Vertex end)
    {
        startVert = start;
        endVert = end;
        weight=1.;
    }

    Edge(Vertex start, Vertex end, double weight_)
    {
        startVert = start;
        endVert = end;
        weight=weight_;
    }

    public Vertex getEndVert() {
        return endVert;
    }

    public Vertex getStartVert() {
        return startVert;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight_){weight=weight_;}

    @Override
    public int compareTo(Edge o) {
        return weight.compareTo(o.getWeight());
    }

    @Override
    public String toString() {
        return '('+startVert.toString()+','+endVert.toString()+')';
    }
}
