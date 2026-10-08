package HashTable;




import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class HashScreen {

    private final Pane canvas = new Pane();
    BorderPane layout = new BorderPane();

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();

    public Scene create(Stage stage, Scene selectScene) {

        Scene scene = new Scene(layout, 1280, 700);
        scene.getStylesheets().add("style.css");

        Button createTree = new Button("Create Tree");
        setButtonSize(createTree);

        Button insertNode = new Button("Insert Node");
        setButtonSize(insertNode);

        Button deleteNode = new Button("Delete Node");
        setButtonSize(deleteNode);

        Button searchNode = new Button("Search Node");
        setButtonSize(searchNode);

        Button clear = new Button("Clear");
        setButtonSize(clear);
        clear.setOnAction(e -> {
            canvas.getChildren().clear();
        });

        Button selectScreen = new Button("Select Screen");
        setButtonSize(selectScreen);
        selectScreen.setOnAction(e -> {
            stage.setScene(selectScene);
        });

        TilePane menu = new TilePane();
        menu.setPrefColumns(5);
        menu.setTileAlignment(Pos.CENTER);
        menu.setAlignment(Pos.CENTER);
        menu.setPrefWidth(Double.MAX_VALUE);
        menu.setHgap(20);
        menu.setVgap(10);
        menu.setPadding(new Insets(20, 0, 30, 0));
        menu.getChildren().addAll(
                createTree,
                insertNode,
                deleteNode,
                searchNode,
                clear,
                selectScreen);
        layout.setBottom(menu);
        layout.setCenter(canvas);
        TilePane stackMenu = new TilePane();
        return scene; 
    }

    private void setButtonSize(Button button) {
        button.setMinSize(250, 75);
        button.setMaxSize(250, 75);
        button.setPrefSize(250, 75);

    }
}
