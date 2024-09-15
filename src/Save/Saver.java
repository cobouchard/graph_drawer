package Save;

import HumanComputerInterface.FXMLDocumentController;
import HumanComputerInterface.GraphicEdge;
import HumanComputerInterface.GraphicVertex;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;

public abstract class Saver {

    private static FileOutputStream fos;
    private static DataOutputStream dos;

    private static final int NAME_LENGTH = 10;

    public static File getFile(Window wd)
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Graph files (*.graph)", "*.graph");
        fileChooser.getExtensionFilters().add(extFilter);

        return fileChooser.showOpenDialog(wd);
    }

    public static void save(ArrayList<GraphicEdge> ge_list, ArrayList<GraphicVertex> gv_list, File file)
    {
        //save all vertex then all edges

        try
        {
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);

            //numbers of vertices (used to open)
            dos.writeInt(gv_list.size());

            for(GraphicVertex gv : gv_list)
            {
                //hardcode information
                dos.writeInt(gv.vertex.getIdVertex());

                //graphic information
                dos.writeDouble(gv.circle.getCenterX());
                dos.writeDouble(gv.circle.getCenterY());

                //we have to make sure the string is always the same length
                String temp = gv.text.getText();

                int shift=NAME_LENGTH-temp.length();


                if(shift<0)
                {
                    temp=temp.substring(0,NAME_LENGTH-1);
                }
                else if(shift>0)
                {
                    for(int i=0; i!= shift; i++)
                        temp=temp.concat(" ");
                }


                dos.writeChars(temp);
            }

            dos.writeInt(ge_list.size());

            for(GraphicEdge ge : ge_list)
            {
                //hardcore information
                dos.writeInt(ge.start.vertex.getIdVertex());
                dos.writeInt(ge.end.vertex.getIdVertex());
                dos.writeDouble(ge.edge.getWeight());
                //graphic information
                dos.writeDouble(ge.quad.getControlX());
                dos.writeDouble(ge.quad.getControlY());
            }

            dos.flush(); dos.close();
            fos.flush(); fos.close();
        }
        catch(Exception e)   {e.printStackTrace();}

    }

    public static void open(File file, FXMLDocumentController controller)
    {
        ArrayList<GraphicVertexDataContainer> gv_list = new ArrayList<>();
        ArrayList<GraphicEdgeDataContainer> ge_list = new ArrayList<>();
        //retrieve information from file
        try
        {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);

            int nb_vertices = dis.readInt();
            for(int i=0; i!= nb_vertices; i++)
            {
                int id = dis.readInt();
                double centerx = dis.readDouble();
                double centery =dis.readDouble();

                String empty ="          ";
                char name_temp[] = empty.toCharArray();

                for(int c=0; c!= 10; c++)
                {
                    name_temp[c]=dis.readChar();
                }

                String name = String.valueOf(name_temp);
                gv_list.add(new GraphicVertexDataContainer(id,centerx,centery,name));

            }

            int nb_edges = dis.readInt();
            for(int i=0; i!= nb_edges; i++)
            {
                int start = dis.readInt();
                int end = dis.readInt();
                double weight = dis.readDouble();
                double controlx = dis.readDouble();
                double controly = dis.readDouble();

                ge_list.add(new GraphicEdgeDataContainer(start,end,weight,controlx,controly));
            }

            fis.close();
            dis.close();

        }
        catch(IOException e){e.printStackTrace();}

        //Recreate the graph
        for(GraphicVertexDataContainer gv : gv_list)
        {
            controller.addGraphicVertex(gv.centerx,gv.centery,gv.name,gv.id);
        }

        for(GraphicEdgeDataContainer ge : ge_list)
        {
            controller.addGraphicEdge(ge.start,ge.end,ge.weight,ge.controlx,ge.controly);
        }

    }
}
