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

public class NodeInsert {

    private static int value;
    private static int index;
    private static boolean submitted;

    public static int[] display(int listSize) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Insert Node");
        window.setMinWidth(400);
        window.setMinHeight(300);

        submitted = false;

        Label valueLabel = new Label("Node Value:");
        TextField valueInput = new TextField();
        valueInput.setPromptText("Enter an integer");

        Label indexLabel = new Label("Index:");
        TextField indexInput = new TextField();
        indexInput.setPromptText("0 - " + listSize);

        Button insert = new Button("Insert");

        insert.setOnAction(e -> {
            try {
                int enteredValue = Integer.parseInt(valueInput.getText());
                int enteredIndex = Integer.parseInt(indexInput.getText());

                if (enteredIndex < 0 || enteredIndex > listSize) {
                    indexInput.clear();
                    indexInput.setPromptText("Index must be 0 - " + listSize);
                    return;
                }

                value = enteredValue;
                index = enteredIndex;
                submitted = true;

                window.close();

            } catch (NumberFormatException ex) {
                valueInput.clear();
                indexInput.clear();

                valueInput.setPromptText("Enter an integer");
                indexInput.setPromptText("Enter a valid index");
            }
        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
            valueLabel,
            valueInput,
            indexLabel,
            indexInput,
            insert
        );

        Scene scene = new Scene(layout);
        window.setScene(scene);

        window.showAndWait();

        if (!submitted) {
            return null;
        }

        return new int[] {value, index};
    }
}