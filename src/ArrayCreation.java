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
import java.util.Random;

//Operates the linked list window
@SuppressWarnings("unused")
public class ArrayCreation{
    public static List<Integer> display()
    {
        List<Integer> values = new ArrayList<>();

        Stage window = new Stage(); 
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Array Creation");
        window.setMinWidth(400);
        window.setMinHeight(400);
        TextField input = new TextField(); 
        input.setPromptText("Please enter a valid integer");
        TextField input2 = new TextField(); 
        Label currentArray = new Label(); 
        currentArray.setText("Current Array: \n" + values.toString());
        currentArray.setFont(new Font("Verdana", 40));
        Button createButton = new Button("Create"); 
        createButton.setPrefSize(50, 25);
        createButton.setOnAction(e -> {
           try{
            int value = Integer.parseInt(input.getText().trim());
            System.out.println(value);
            values.add(value);
            currentArray.setText("Current Array: \n" + values.toString());
            input.clear();
           }catch (NumberFormatException ex) {
                input.clear();
                input.setPromptText("Please enter a valid integer");
        }});
                Button randomizeButton = new Button("Randomize");

        randomizeButton.setOnAction(e -> {
            try {
                int length = Integer.parseInt(input2.getText().trim());

                values.clear();

                Random random = new Random();

                for (int i = 0; i < length; i++) {
                    values.add(random.nextInt(100) + 1);
                }

                window.close();

            } catch (NumberFormatException ex) {
                input2.clear();
                input2.setPromptText("Enter a valid array length");
            }
        });
        Button closeButton = new Button("Finish"); 
        closeButton.setPrefSize(50, 25);
        closeButton.setOnAction(e -> {
            window.close();
        });
        
        
        
        VBox menu = new VBox(); 
        menu.getChildren().addAll(input,input2, createButton, randomizeButton, closeButton,currentArray);
        GridPane layout = new GridPane(); 

        layout.getChildren().add(menu);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return values; 

    }
}