package HumanComputerInterface;



import javafx.scene.layout.Pane;

public class MasterRegion extends Pane  {

    public double x_translation;
    public double y_translation;

    MasterRegion() {

        //this.setWidth(3000);
        //this.setHeight(3000);
        x_translation=0;
        y_translation=0;
        setStyle("-fx-background-color: #FFFFFF");
        this.setFocused(true);

    }

}
