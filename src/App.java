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
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class App extends Application{
    private Pane canvas = new Pane();
    private List<IntNode> nodes = new ArrayList<>();
    Stage window; 
    Scene scene1, scene2, scene3, scene4, scene5, scene6, scene7, scene8;
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Set Up The Window and the First Scene
        window = primaryStage; 
        window.setTitle("Algorithm Visualizer");

        Image image = new Image(getClass().getResourceAsStream("/images/image.png"));
        Image imageArr = new Image(getClass().getResourceAsStream("/images/array.png"));
        Image imageQS = new Image(getClass().getResourceAsStream("/images/sq.png"));
        Image imageLL = new Image(getClass().getResourceAsStream("/images/LL.png"));
        Image imageTr = new Image(getClass().getResourceAsStream("/images/tree.png"));
        Image imageGr = new Image(getClass().getResourceAsStream("/images/graph.png"));
        Image imageHT = new Image(getClass().getResourceAsStream("/images/hash-table.png"));
         

        window.getIcons().add(image);

        Label label1 = new Label("Algorithm Visualizer"); 
        label1.setFont(new Font("Arial", 100));

        Button button = new Button("Start"); 
        button.setMinSize(10, 10);
        button.setMaxSize(500, 300);
        button.setPrefSize(300, 100);
        button.getStyleClass().add("custom-button");
        button.setOnAction(e -> window.setScene(scene2));

        Button button2 = new Button("Exit");
        button2.setMinSize(10, 10);
        button2.setMaxSize(500, 300);
        button2.setPrefSize(300, 100);
        button2.getStyleClass().add("custom-button");
        button2.setOnAction(e -> closeProgram());

        //Set Up Scene2

        ImageView hoverImage = new ImageView();
        hoverImage.setFitWidth(600);
        hoverImage.setFitHeight(600);
        hoverImage.setPreserveRatio(true);
        hoverImage.setSmooth(true);
        
        Button button3 = new Button("Array Sorting");
        button3.setMinSize(200, 60);
        button3.setMaxSize(200, 60);
        button3.setPrefSize(200, 60);
        button3.getStyleClass().add("selection-button");
        button3.setOnMouseEntered(e -> hoverImage.setImage(imageArr));
        button3.setOnMouseExited(e -> hoverImage.setImage(null));
        button3.setOnAction(e -> window.setScene(scene3));


        Button button4 = new Button("Stack And Queue");
        button4.setMinSize(200, 60);
        button4.setMaxSize(200, 60);
        button4.setPrefSize(200, 60);
        button4.getStyleClass().add("custom-button");
        button4.setOnMouseEntered(e -> hoverImage.setImage(imageQS));
        button4.setOnMouseExited(e -> hoverImage.setImage(null));

        Button button5 = new Button("Linked List");
        button5.setMinSize(200, 60);
        button5.setMaxSize(200, 60);
        button5.setPrefSize(200, 60);
        button5.getStyleClass().add("custom-button");
        button5.setOnMouseEntered(e -> hoverImage.setImage(imageLL));
        button5.setOnMouseExited(e -> hoverImage.setImage(null));
        button5.setOnAction(e -> window.setScene(scene5));

        Button button6 = new Button("Tree");
        button6.setMinSize(200, 60);
        button6.setMaxSize(200, 60);
        button6.setPrefSize(200, 60);
        button6.getStyleClass().add("custom-button");
        button6.setOnMouseEntered(e -> hoverImage.setImage(imageTr));
        button6.setOnMouseExited(e -> hoverImage.setImage(null));

        Button button7 = new Button("Graphs");
        button7.setMinSize(200, 60);
        button7.setMaxSize(200, 60);
        button7.setPrefSize(200, 60);
        button7.getStyleClass().add("custom-button");
        button7.setOnMouseEntered(e -> hoverImage.setImage(imageGr));
        button7.setOnMouseExited(e -> hoverImage.setImage(null));

        Button button8 = new Button("Hash Tables");
        button8.setMinSize(200, 60);
        button8.setMaxSize(200, 60);
        button8.setPrefSize(200, 60);
        button8.getStyleClass().add("custom-button");
        button8.setOnMouseEntered(e -> hoverImage.setImage(imageHT));
        button8.setOnMouseExited(e -> hoverImage.setImage(null));

        Button button9 = new Button("Go Back To Title");
        button9.setMinSize(200, 60);
        button9.setMaxSize(200, 60);
        button9.setPrefSize(200, 60);
        button9.getStyleClass().add("custom-button");
        button9.setOnAction(e -> window.setScene(scene1));
        
        
        //Scene 1

        VBox layout1 = new VBox(20); 
        layout1.getChildren().addAll(button, button2);
        layout1.setAlignment(Pos.BOTTOM_CENTER);
        layout1.setPadding(new Insets(0, 0, 40, 0)); // 40px bottom padding

        
        BorderPane root = new BorderPane(); 
        root.setBottom(layout1);
        root.setCenter(label1);
        

        scene1 = new Scene(root, 1280, 700);
        scene1.getStylesheets().add("style.css");

        //Layout 2 - Stack Plane With a singluar button
        BorderPane root2 = new BorderPane(); 
        StackPane centerPane = new StackPane(); 

        Label label3 = new Label("Select A Option"); 
        label3.setFont(new Font("Arial", 25));

        centerPane.getChildren().addAll(hoverImage,label3);
        StackPane.setAlignment(label3, Pos.TOP_CENTER);
        StackPane.setAlignment(hoverImage, Pos.CENTER);

        //Scene 2
        VBox layout2 = new VBox(20);
        
        layout2.getChildren().addAll(button3,button4,button5,button6,button7,button8,button9);
        layout2.setAlignment(Pos.CENTER);

        root2.setLeft(layout2);
        root2.setCenter(centerPane); 
        scene2 = new Scene(root2, 1280, 700);

        List<Integer> values = new ArrayList<>(); 
        Button addNode = new Button("Add Node"); 
        addNode.setMinSize(250, 75);
        addNode.setMaxSize(250, 75);
        addNode.setPrefSize(250, 75);
        addNode.setOnAction(e -> {
            values.addAll(NodeCreation.display());
            createNodes(values);
        });

        Button removeNode = new Button("Remove Node"); 
        removeNode.setMinSize(250, 75);
        removeNode.setMaxSize(250, 75);
        removeNode.setPrefSize(250, 75);
        Button clearNode = new Button("Clear Canvas");
        clearNode.setMinSize(250, 75);
        clearNode.setMaxSize(250, 75);
        clearNode.setPrefSize(250, 75); 
        clearNode.setOnAction(e -> {
            values.clear();
            nodes.clear();
            canvas.getChildren().clear();
        });

        Button stepByStep = new Button("View Step By Step"); 
        stepByStep.setMinSize(250, 75);
        stepByStep.setMaxSize(250, 75);
        stepByStep.setPrefSize(250, 75);
        Button selectScreen = new Button("Select Screen");
        selectScreen.setMinSize(250, 75);
        selectScreen.setMaxSize(250, 75);
        selectScreen.setPrefSize(250, 75);
        selectScreen.setOnAction(e -> window.setScene(scene2));
        

        TilePane menu = new TilePane();
        menu.setPrefColumns(5);           
        menu.setTileAlignment(Pos.CENTER);
        menu.setAlignment(Pos.CENTER);
        menu.setPrefWidth(Double.MAX_VALUE); 
        menu.setHgap(20);
        menu.setVgap(10);

        menu.getChildren().addAll(addNode, removeNode, clearNode, stepByStep, selectScreen);

        TilePane menu2 = new TilePane(); 
        menu2.setPrefColumns(5);
        menu2.setTileAlignment(Pos.CENTER);
        menu2.setAlignment(Pos.CENTER);
        menu2.setPrefWidth(Double.MAX_VALUE);
        menu2.setHgap(20);
        menu2.setVgap(10);

        Button SwapNodes = new Button("Swap Nodes"); 
        SwapNodes.setMinSize(250, 75);
        SwapNodes.setMaxSize(250, 75);
        SwapNodes.setPrefSize(250, 75);

        
        //Scene 3 
        BorderPane layout3 = new BorderPane(); 
        layout3.setBottom(menu);
        layout3.setCenter(canvas);
        

        //Scene 5
        BorderPane layout5 = new BorderPane(); 
        layout5.setBottom(menu);
        layout5.setCenter(canvas);
        menu.setPadding(new Insets(20, 0, 30, 0));

        scene5 = new Scene(layout5, 1280, 700);
        window.setScene(scene1);
        window.show();
        
    }

    private void closeProgram(){
        System.out.println("File Has Been Properly Closed");
        window.close();
    }
    private void resizeNodesIfNeeded() {
        if (nodes.isEmpty()) return;

        double canvasWidth = canvas.getWidth();
        if (canvasWidth == 0) canvasWidth = 1200; // fallback if not yet sized

        double totalNeededWidth = nodes.size() * (2 * IntNode.DEFAULT_RADIUS + 20);
        double scale = 1.0;

        if (totalNeededWidth > canvasWidth) {
            scale = canvasWidth / totalNeededWidth;
        }

        for (IntNode node : nodes) {
            node.setScale(scale);
        }
}
    private void createNodes(List<Integer> inputs){
        // Clear previous nodes from canvas and list
        canvas.getChildren().clear();
        nodes.clear();

        double spacing = 2 * IntNode.DEFAULT_RADIUS + 20;
        double totalWidth = inputs.size() * spacing;
        double canvasWidth = canvas.getWidth();
        if (canvasWidth == 0) canvasWidth = 1280; // fallback if not yet sized
        double startX = (canvasWidth - totalWidth) / 2 + IntNode.DEFAULT_RADIUS;
        double y = 120;

        for (int i = 0; i < inputs.size(); i++) {
            double x = startX + i * spacing;
            IntNode node = new IntNode(x, y, IntNode.DEFAULT_RADIUS, inputs.get(i), false);
            nodes.add(node);
            canvas.getChildren().add(node);
        }
        resizeNodesIfNeeded();

        // Set next references for linked list behavior
        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).setNext(nodes.get(i + 1));
        }

        // Draw arrows only where next reference exists
        for (IntNode from : nodes) {
            IntNode to = from.getNext();
            if (to != null) {
                double startXArrow = from.getCenterX() + IntNode.DEFAULT_RADIUS;
                double startYArrow = from.getCenterY();
                double endXArrow = to.getCenterX() - IntNode.DEFAULT_RADIUS;
                double endYArrow = to.getCenterY();

                javafx.scene.shape.Line line = new javafx.scene.shape.Line(startXArrow, startYArrow, endXArrow, endYArrow);
                line.setStrokeWidth(3);
                line.setStroke(javafx.scene.paint.Color.DARKGRAY);

                // Arrowhead
                double arrowLength = 15;
                double arrowWidth = 8;
                double angle = Math.atan2(endYArrow - startYArrow, endXArrow - startXArrow);
                double sin = Math.sin(angle);
                double cos = Math.cos(angle);

                double x1 = endXArrow - arrowLength * cos + arrowWidth * sin;
                double y1 = endYArrow - arrowLength * sin - arrowWidth * cos;
                double x2 = endXArrow - arrowLength * cos - arrowWidth * sin;
                double y2 = endYArrow - arrowLength * sin + arrowWidth * cos;

                javafx.scene.shape.Polygon arrowHead = new javafx.scene.shape.Polygon();
                arrowHead.getPoints().addAll(
                    endXArrow, endYArrow,
                    x1, y1,
                    x2, y2
                );
                arrowHead.setFill(javafx.scene.paint.Color.DARKGRAY);

                canvas.getChildren().addAll(line, arrowHead);
            }
        }
    }
    // Display an array of integers with each index in a box
    private void createArrayBoxes(List<Integer> array) {
        canvas.getChildren().clear();
        double boxWidth = 70;
        double boxHeight = 70;
        double spacing = 20;
        double startX = (canvas.getWidth() == 0 ? 1280 : canvas.getWidth()) / 2 - (array.size() * (boxWidth + spacing) - spacing) / 2;
        double y = 120;

        for (int i = 0; i < array.size(); i++) {
            double x = startX + i * (boxWidth + spacing);
            javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(x, y, boxWidth, boxHeight);
            rect.setFill(javafx.scene.paint.Color.LIGHTYELLOW);
            rect.setStroke(javafx.scene.paint.Color.DARKGRAY);
            rect.setStrokeWidth(2);

            javafx.scene.text.Text valueText = new javafx.scene.text.Text(String.valueOf(array.get(i)));
            valueText.setFont(new Font(24));
            valueText.setX(x + boxWidth / 2 - valueText.getLayoutBounds().getWidth() / 2);
            valueText.setY(y + boxHeight / 2 + valueText.getLayoutBounds().getHeight() / 4);

            javafx.scene.text.Text indexText = new javafx.scene.text.Text(String.valueOf(i));
            indexText.setFont(new Font(16));
            indexText.setFill(javafx.scene.paint.Color.DARKBLUE);
            indexText.setX(x + boxWidth / 2 - indexText.getLayoutBounds().getWidth() / 2);
            indexText.setY(y + boxHeight + 20);

            canvas.getChildren().addAll(rect, valueText, indexText);
        }
    }
    
}
