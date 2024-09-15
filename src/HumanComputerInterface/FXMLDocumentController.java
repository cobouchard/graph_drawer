package HumanComputerInterface;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import HardCode.*;
import Save.Saver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class FXMLDocumentController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private HBox hb;

    @FXML
    private TextField selectedVertexName;
    @FXML
    private TextField selectedEdgeWeight;
    @FXML
    private TextField strokeWidth;

    @FXML
    private Text selectedEdgeVertex1;
    @FXML
    private Text selectedEdgeVertex2;
    @FXML
    private Text selectedVertexId;

    @FXML
    private CheckBox edgeCreation;

    @FXML
    private MenuItem saveButton;
    @FXML
    private MenuItem saveAsButton;
    @FXML
    public MenuItem openButton;


    private AbstractGraph graph;
    private ArrayList<GraphicEdge> graphicEdges;
    private ArrayList<GraphicVertex> graphicVertices;

    private GraphicVertex selectedVertex;
    private GraphicEdge selectedEdge;
    private boolean selectedIsEdge = false;

    private MasterRegion mr;

    public File currentFile;

    private Integer idCount = 1;

    private boolean keyDeleteBlock = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if(true)
            graph = new OrientedGraph(false,true);
        else
            graph = new UnorientedGraph(false,true);

        graphicEdges = new ArrayList<>();
        graphicVertices = new ArrayList<>();

        mr = new MasterRegion();
        HBox.setHgrow(mr, Priority.ALWAYS);
        hb.getChildren().add(mr);

        VBox.setVgrow(mr,Priority.ALWAYS);

        strokeWidth.setOnAction(event -> {

            double newWidth = Double.parseDouble(strokeWidth.getText());

            GraphicEdge.strokeWidth=newWidth;

            for(GraphicEdge ge : graphicEdges)
                ge.quad.setStrokeWidth(newWidth);
        });

        selectedEdgeWeight.setOnAction(event -> {
            if(selectedEdge!=null)
            {
                double newWeight = Double.parseDouble(selectedEdgeWeight.getText());
                selectedEdge.edge.setWeight(newWeight);
            }

        });



        selectedVertexName.setOnAction(event -> {
            if(selectedVertex!=null)
            {
                selectedVertex.text.setText(selectedVertexName.getText());
            }

        });



        mr.addEventHandler(MouseEvent.MOUSE_PRESSED,
                event -> {

                    if(event.getButton().toString().equals("PRIMARY"))
                    {
                        ActionEvent empty = new ActionEvent();
                        selectedEdgeWeight.getOnAction().handle(empty);
                        selectedVertexName.getOnAction().handle(empty);
                        strokeWidth.getOnAction().handle(empty);

                        mr.requestFocus();

                        resetSelectedVertex(null);
                        resetSelectedEdge(null);

                        mr.x_translation=event.getX();
                        mr.y_translation=event.getY();
                    }

                    else if(event.getButton().toString().equals("SECONDARY"))
                        addGraphicVertex(event.getX(),event.getY());
                });

        mr.setOnScroll((ScrollEvent event) -> {

            float multiplier=Zoom.getMultiplier(event.getDeltaY());

            if(multiplier!=0)
            {
                GraphicVertex.textWidth *= multiplier;

                for(GraphicVertex gv : graphicVertices)
                    gv.changeSize(gv.circle.getRadius()*multiplier);


                for(GraphicEdge ge : graphicEdges)
                {
                    double new_size =ge.quad.getStrokeWidth()*multiplier;
                    ge.changeSize(new_size);
                    strokeWidth.setText(String.valueOf(new_size));
                }
            }

        });

        mr.setOnMouseDragged(event -> {
            if(event.isPrimaryButtonDown())
            {
                double x_movement = event.getX()-mr.x_translation;
                double y_movement = event.getY()-mr.y_translation;
                mr.x_translation=event.getX();
                mr.y_translation=event.getY();

                boolean block=false;

                for(GraphicVertex gv : graphicVertices)
                {
                    if(block)
                        continue;

                    double x=gv.circle.getCenterX()+x_movement;
                    double y=gv.circle.getCenterY()+y_movement;

                    if(!circle_movement_test(x,y,gv))
                        block=true;

                }

                if(!block)
                {
                    for(GraphicVertex gv : graphicVertices)
                    {
                        double x=gv.circle.getCenterX()+x_movement;
                        double y=gv.circle.getCenterY()+y_movement;
                        circle_movement(x,y,gv);
                    }

                    for(GraphicEdge ge : graphicEdges)
                    {
                        ge.mouseDragged(ge.quad.getControlX()+x_movement);
                    }
                }

            }
        });

        saveButton.setOnAction(event -> {
            if(currentFile!=null)
                Saver.save(graphicEdges,graphicVertices,currentFile);
            else
                saveAsButton.fire();

        });

        saveAsButton.setOnAction(event -> {
            File file = Saver.getFile(root.getScene().getWindow());

            if(file != null)
            {
                Saver.save(graphicEdges,graphicVertices,file);
                currentFile=file;
            }
        });

        openButton.setOnAction(event -> {
            File file = Saver.getFile(root.getScene().getWindow());

            if(file != null)
            {
                clearGraph();
                Saver.open(file, this);
                currentFile=file;
            }
        });

        Platform.runLater(this::initDeleteKey);

    }

    private void initDeleteKey()
    {
        Scene sc = mr.getScene();

        sc.setOnKeyPressed(key -> {
            if(!keyDeleteBlock && key.getCode().equals(KeyCode.DELETE))
            {
                keyDeleteBlock = true;

                if(selectedIsEdge)
                {
                    deleteGraphicEdge(selectedEdge);
                }
                else
                {
                    if(selectedVertex!=null)
                    {
                        deleteGraphicVertex(selectedVertex);
                    }
                }
            }

        });

        sc.setOnKeyReleased(key -> {
            if(key.getCode().equals(KeyCode.DELETE))
                keyDeleteBlock = false;

        });

    }

    private void deleteGraphicVertex(GraphicVertex gv)
    {
        if(selectedVertex==gv)
            resetSelectedVertex(null);

        if(!graphicEdges.isEmpty())
        {
            HashSet<GraphicEdge> graphicEdgesToRemove = new HashSet<>();
            for(GraphicEdge ge : graphicEdges)
            {
                if(ge.end.equals(gv) || ge.start.equals(gv))
                {
                    graphicEdgesToRemove.add(ge);
                }
            }

            for(GraphicEdge ge : graphicEdgesToRemove)
            {
                deleteGraphicEdge(ge);
            }

        }

        gv.delete(mr);
        graph.deleteVertex(gv.vertex);
        graphicVertices.remove(gv);
    }

    private void deleteGraphicEdge(GraphicEdge ge)
    {
        if(selectedEdge==ge)
        {
            resetSelectedEdge(null);
        }

        ge.delete(mr);
        graph.deleteEdge(ge.edge);
        graphicEdges.remove(ge);
    }

    private void resetSelectedVertex(GraphicVertex gv)
    {
        if(selectedVertex!=null)
        {
            selectedVertex.circle.setFill(Color.BLUE);
        }

        selectedVertex=gv;

        if(selectedVertex!=null)
        {
            gv.circle.setFill(Color.GREEN);
            selectedVertexName.setText(selectedVertex.text.getText());
            selectedVertexId.setText(Integer.toString(gv.vertex.getIdVertex()));
        }
        else
        {
            selectedVertexName.setText("");
            selectedVertexId.setText("");
        }

    }

    private void resetSelectedEdge(GraphicEdge ge)
    {
        if(selectedEdge!=null)
            selectedEdge.quad.setStroke(Color.BLACK);

        selectedEdge=ge;

        if(selectedEdge!=null)
        {
            ge.quad.setStroke(Color.RED);
            selectedEdgeVertex1.setText(ge.start.text.getText());
            selectedEdgeVertex2.setText(ge.end.text.getText());
            selectedEdgeWeight.setText(Double.toString(ge.edge.getWeight()));
            selectedIsEdge=true;
        }

        else
        {
            selectedEdgeVertex1.setText("");
            selectedEdgeVertex2.setText("");
            selectedEdgeWeight.setText("");
            selectedIsEdge=false;
        }

    }

    private boolean circle_movement_test(double x, double y, GraphicVertex gv)
    {
        boolean test =false;
        if(x-gv.circle.getRadius()>0 && y-gv.circle.getRadius()>0)
        {
            test=true;
        }
        return test;
    }

    private void circle_movement(double x, double y, GraphicVertex gv)
    {
        for(GraphicEdge ge : graphicEdges)
        {
            if(gv.equals(ge.start) || gv.equals(ge.end))
            {
                ge.resetBisection();
            }
        }
        gv.mouseDragged(x,y);
    }

    private GraphicVertex addGraphicVertex(double x, double y)
    {
        Vertex v = graph.addVertex(idCount);
        GraphicVertex gv = new GraphicVertex(v,x,y,idCount.toString());

        gv.circle.setOnMousePressed(event -> {

            if(event.isSecondaryButtonDown())
            {
                deleteGraphicVertex(gv);
            }

            else{
                if(!selectedIsEdge && selectedVertex!= null && selectedVertex!= gv && edgeCreation.selectedProperty().get())
                {
                    addGraphicEdge(selectedVertex,gv);
                }

                resetSelectedVertex(gv);

                selectedIsEdge=false;

            }

            event.consume();
        });

        gv.circle.setOnMouseDragged(event -> {
            double x_=event.getX(); double y_ =event.getY();

            if(circle_movement_test(x_,y_,gv))
                circle_movement(event.getX(),event.getY(), gv);

            event.consume();

        });

        gv.circle.setOnMouseReleased(event -> {

        });

        gv.graphicPrint(mr);
        graphicVertices.add(gv);

        idCount++;

        return gv;
    }

    public void addGraphicVertex(double x, double y, String text, int id)
    {
        //this is only used when you open a graph
        GraphicVertex gv = addGraphicVertex(x,y);
        gv.text.setText(text);
        gv.vertex.setIdVertex(id);
    }

    private GraphicEdge addGraphicEdge(GraphicVertex start, GraphicVertex end)
    {
        Edge e = graph.addEdge(start.vertex,end.vertex);

        GraphicEdge ge = new GraphicEdge(e,start,end);

        ge.quad.setOnMouseDragged(event -> {
            ge.mouseDragged(event.getX());
            event.consume();
        });

        ge.quad.setOnMousePressed(event -> {

            if(selectedEdge==ge)
            {
                resetSelectedEdge(null);
            }

            else{
                if(selectedEdge!=null)
                    selectedEdge.quad.setStroke(Color.BLACK);

                resetSelectedEdge(ge);
            }

            event.consume();

        });

        ge.graphicPrint(mr);

        ge.quad.toBack();

        graphicEdges.add(ge);

        return ge;

    }

    public void addGraphicEdge(int start, int end, double weight, double x, double y)
    {
        //this is only used when you open a graph
        GraphicEdge ge = addGraphicEdge(findGraphicVertexWithId(start),findGraphicVertexWithId(end));
        ge.quad.setControlX(x);
        ge.quad.setControlY(y);
        ge.edge.setWeight(weight);
    }

    private GraphicVertex findGraphicVertexWithId(int id)
    {
        GraphicVertex value = null;

        for(GraphicVertex gv : graphicVertices)
            if(gv.vertex.getIdVertex()==id)
                value=gv;


        return value;
    }

    private void clearGraph()
    {
        for(int i = graphicVertices.size()-1; i!= -1 ; i--)
        {
            deleteGraphicVertex(graphicVertices.get(i));
        }
    }
}
