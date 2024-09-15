package HardCode;

public class Vertex {
    //this class should only be used by Graph and Edge


    private Integer idVertex; //must be unique
    private char state; //used for DFS and BFS

    Vertex(int id)
    {
        idVertex=id;state='0';
    }

    @Override
    public int hashCode() {
        return idVertex.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Vertex temp = (Vertex)obj;
        return idVertex.equals(temp.idVertex);
    }

    @Override
    public String toString() {
        return idVertex.toString();
    }

    public int getIdVertex() {
        return idVertex;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public void setIdVertex(int id)
    {
        //use this only if you are sure the id is unique
        idVertex=id;
    }
}
