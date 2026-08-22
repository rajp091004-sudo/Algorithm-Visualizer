import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class App extends Application {

    Stage window;

    Scene scene1;
    Scene scene2;
    Scene scene3;
    Scene scene4;
    Scene scene5;
    Scene scene6;
    Scene scene7;
    Scene scene8;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Set Up The Window and the First Scene
        window = primaryStage;
        window.setTitle("Algorithm Visualizer");

        //Sets up Images that will be used
        Image image = new Image(
                getClass().getResourceAsStream(
                        "/images/image.png"
                )
        );

        Image imageArr = new Image(
                getClass().getResourceAsStream(
                        "/images/array.png"
                )
        );

        Image imageQS = new Image(
                getClass().getResourceAsStream(
                        "/images/sq.png"
                )
        );

        Image imageLL = new Image(
                getClass().getResourceAsStream(
                        "/images/LL.png"
                )
        );

        Image imageTr = new Image(
                getClass().getResourceAsStream(
                        "/images/tree.png"
                )
        );

        Image imageGr = new Image(
                getClass().getResourceAsStream(
                        "/images/graph.png"
                )
        );

        Image imageHT = new Image(
                getClass().getResourceAsStream(
                        "/images/hash-table.png"
                )
        );

        window.getIcons().add(image);

        //Title Screen
        Label label1 = new Label("Algorithm Visualizer");
        label1.setFont(new Font("Arial", 100));

        Button button = new Button("Start");
        button.setMinSize(10, 10);
        button.setMaxSize(500, 300);
        button.setPrefSize(300, 100);
        button.getStyleClass().add("custom-button");
        button.setOnAction(
                e -> window.setScene(scene2)
        );

        Button button2 = new Button("Exit");
        button2.setMinSize(10, 10);
        button2.setMaxSize(500, 300);
        button2.setPrefSize(300, 100);
        button2.getStyleClass().add("custom-button");
        button2.setOnAction(
                e -> closeProgram()
        );

        //Set Up Select Screen
        ImageView hoverImage = new ImageView();
        hoverImage.setFitWidth(600);
        hoverImage.setFitHeight(600);
        hoverImage.setPreserveRatio(true);
        hoverImage.setSmooth(true);

        //Array Sortings (Scene 3)
        Button button3 = new Button("Array Sorting");
        button3.setMinSize(200, 60);
        button3.setMaxSize(200, 60);
        button3.setPrefSize(200, 60);
        button3.getStyleClass().add("selection-button");

        button3.setOnMouseEntered(
                e -> hoverImage.setImage(imageArr)
        );

        button3.setOnMouseExited(
                e -> hoverImage.setImage(null)
        );

        button3.setOnAction(
                e -> window.setScene(scene3)
        );

        //Stack and Queue (Scene undetermined)
        Button button4 = new Button("Stack And Queue");
        button4.setMinSize(200, 60);
        button4.setMaxSize(200, 60);
        button4.setPrefSize(200, 60);
        button4.getStyleClass().add("custom-button");

        button4.setOnMouseEntered(
                e -> hoverImage.setImage(imageQS)
        );

        button4.setOnMouseExited(
                e -> hoverImage.setImage(null)
        );

        //Linked List (Scene 5)
        Button button5 = new Button("Linked List");
        button5.setMinSize(200, 60);
        button5.setMaxSize(200, 60);
        button5.setPrefSize(200, 60);
        button5.getStyleClass().add("custom-button");

        button5.setOnMouseEntered(
                e -> hoverImage.setImage(imageLL)
        );

        button5.setOnMouseExited(
                e -> hoverImage.setImage(null)
        );

        button5.setOnAction(
                e -> window.setScene(scene5)
        );

        //Tree (Scene undetermined)
        Button button6 = new Button("Tree");
        button6.setMinSize(200, 60);
        button6.setMaxSize(200, 60);
        button6.setPrefSize(200, 60);
        button6.getStyleClass().add("custom-button");

        button6.setOnMouseEntered(
                e -> hoverImage.setImage(imageTr)
        );

        button6.setOnMouseExited(
                e -> hoverImage.setImage(null)
        );

        //Graphs (Scene undetermined)
        Button button7 = new Button("Graphs");
        button7.setMinSize(200, 60);
        button7.setMaxSize(200, 60);
        button7.setPrefSize(200, 60);
        button7.getStyleClass().add("custom-button");

        button7.setOnMouseEntered(
                e -> hoverImage.setImage(imageGr)
        );

        button7.setOnMouseExited(
                e -> hoverImage.setImage(null)
        );

        //Hash Tables (Scene undetermined)
        Button button8 = new Button("Hash Tables");
        button8.setMinSize(200, 60);
        button8.setMaxSize(200, 60);
        button8.setPrefSize(200, 60);
        button8.getStyleClass().add("custom-button");

        button8.setOnMouseEntered(
                e -> hoverImage.setImage(imageHT)
        );

        button8.setOnMouseExited(
                e -> hoverImage.setImage(null)
        );

        //Return to Title Button (Scene 1)
        Button button9 = new Button("Go Back To Title");
        button9.setMinSize(200, 60);
        button9.setMaxSize(200, 60);
        button9.setPrefSize(200, 60);
        button9.getStyleClass().add("custom-button");

        button9.setOnAction(
                e -> window.setScene(scene1)
        );

        //Scene 1 (Title Screen)
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(
                button,
                button2
        );

        layout1.setAlignment(Pos.BOTTOM_CENTER);

        layout1.setPadding(
                new Insets(0, 0, 40, 0)
        );

        BorderPane root = new BorderPane();
        root.setBottom(layout1);
        root.setCenter(label1);

        scene1 = new Scene(root, 1280, 700);
        scene1.getStylesheets().add("style.css");

        //Layout 2 - Stack Pane With a singular button
        BorderPane root2 = new BorderPane();
        StackPane centerPane = new StackPane();

        Label label3 = new Label("Select A Option");
        label3.setFont(new Font("Arial", 25));

        centerPane.getChildren().addAll(
                hoverImage,
                label3
        );

        StackPane.setAlignment(
                label3,
                Pos.TOP_CENTER
        );

        StackPane.setAlignment(
                hoverImage,
                Pos.CENTER
        );

        //Scene 2
        VBox layout2 = new VBox(20);

        layout2.getChildren().addAll(
                button3,
                button4,
                button5,
                button6,
                button7,
                button8,
                button9
        );

        layout2.setAlignment(Pos.CENTER);

        root2.setLeft(layout2);
        root2.setCenter(centerPane);

        scene2 = new Scene(root2, 1280, 700);

        //Scene 3
        //This is currently empty until its screen is implemented.
        BorderPane layout3 = new BorderPane();

        scene3 = new Scene(layout3, 1280, 700);
        scene3.getStylesheets().add("style.css");

        //Scene 5
        //The linked-list screen is created in NodeScreen.java.
        NodeScreen nodeScreen = new NodeScreen();
        scene5 = nodeScreen.create(window, scene2);

        window.setScene(scene1);
        window.show();
    }

    private void closeProgram() {
        System.out.println(
                "File Has Been Properly Closed"
        );

        window.close();
    }
}