import javax.swing.text.LabelView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class NodeCreation{
    public static List<Integer> display()
    {
        List<Integer> values = new ArrayList<>();

        Stage window = new Stage(); 
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Node Creation");
        window.setMinWidth(250);
        window.setMinHeight(400);
        TextField input = new TextField(); 
        input.setPromptText("Please enter a valid integer");


        Button addButton = new Button("Add"); 
        addButton.setMinSize(50, 25);
        addButton.setMaxSize(50, 25);
        addButton.setPrefSize(50, 25);
        addButton.setOnAction(e -> {
           try{
            int value = Integer.parseInt(input.getText().trim());
            System.out.println(value);
            values.add(value);
            input.clear();
           }catch (NumberFormatException ex) {
                input.clear();
                input.setPromptText("Please enter a valid integer");
        }});
        Button closeButton = new Button("Finish"); 
        closeButton.setMinSize(50, 25);
        closeButton.setMaxSize(50, 25);
        closeButton.setPrefSize(50, 25);
        closeButton.setOnAction(e -> {
            window.close();
        });
        
        
        
        VBox menu = new VBox(); 
        menu.getChildren().addAll(input,addButton,closeButton);
        GridPane layout = new GridPane(); 

        layout.getChildren().add(menu);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return values; 

    }
}