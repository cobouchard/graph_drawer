package Save;

public class GraphicEdgeDataContainer {

    int start;
    int end;
    double weight;

    double controlx;
    double controly;

    public GraphicEdgeDataContainer(int start, int end, double weight, double controlx, double controly) {
        this.start = start;
        this.end = end;
        this.weight = weight;
        this.controlx = controlx;
        this.controly = controly;
    }
}
