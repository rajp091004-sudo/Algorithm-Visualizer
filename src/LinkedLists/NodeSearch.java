package LinkedLists;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NodeSearch {

    private static int value;
    private static boolean submitted;

    public static Integer display() {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Search Linked List");
        window.setMinWidth(400);
        window.setMinHeight(250);

        submitted = false;

        Label label = new Label("Search Value:");

        TextField input = new TextField();
        input.setPromptText("Enter an integer");

        Button search = new Button("Search");

        search.setOnAction(e -> {
            try {
                value = Integer.parseInt(input.getText());

                submitted = true;
                window.close();

            } catch (NumberFormatException ex) {
                input.clear();
                input.setPromptText("Enter a valid integer");
            }
        });

        VBox layout = new VBox(15);

        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
            label,
            input,
            search
        );

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        if (!submitted) {
            return null;
        }

        return value;
    }
}