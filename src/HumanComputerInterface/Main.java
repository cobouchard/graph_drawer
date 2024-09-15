package HumanComputerInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application
{
    static File file;
    static FXMLLoader fxmlLoader;
    @Override
    public void start(Stage stage) throws Exception {

        String sceneFile = "/HumanComputerInterface/FXMLDocument.fxml";
        Parent root = null;
        URL url = null;


        try
        {
            fxmlLoader = new FXMLLoader();
            url  = getClass().getResource( sceneFile );
            root = fxmlLoader.load( url );
            System.out.println( "  fxmlResource = " + sceneFile );
        }
        catch ( Exception ex )
        {
            System.out.println( "Exception on FXMLLoader.load()" );
            System.out.println( "  * url: " + url );
            System.out.println( "  * " + ex );
            System.out.println( "    ----------------------------------------\n" );
            throw ex;
        }

        Scene scene = new Scene(root);
        //stage.setMaxHeight(750);
        //stage.setMaxWidth(750);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        if(file!=null)
        {
            FXMLDocumentController controller = fxmlLoader.getController();
            controller.currentFile=file;
            controller.openButton.fire();
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try
        {
            file = new File(args[0]);
        }
        catch(Exception e)
        {
            file=null;
        }

        launch(args);
    }
}
