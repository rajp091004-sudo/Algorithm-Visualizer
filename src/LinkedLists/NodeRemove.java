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

public class NodeRemove {

    private static int index;
    private static boolean submitted;

    public static Integer display(int listSize) {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Remove Node");
        window.setMinWidth(400);
        window.setMinHeight(250);

        submitted = false;

        Label indexLabel = new Label("Index:");

        TextField indexInput = new TextField();
        indexInput.setPromptText("0 - " + (listSize - 1));

        Button remove = new Button("Remove");

        remove.setOnAction(e -> {
            try {
                int enteredIndex =
                    Integer.parseInt(indexInput.getText());

                if (
                    enteredIndex < 0 ||
                    enteredIndex >= listSize
                ) {
                    indexInput.clear();
                    indexInput.setPromptText(
                        "Index must be 0 - " + (listSize - 1)
                    );

                    return;
                }

                index = enteredIndex;
                submitted = true;

                window.close();

            } catch (NumberFormatException ex) {
                indexInput.clear();
                indexInput.setPromptText("Enter a valid index");
            }
        });

        VBox layout = new VBox(15);

        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
            indexLabel,
            indexInput,
            remove
        );

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        if (!submitted) {
            return null;
        }

        return index;
    }
}