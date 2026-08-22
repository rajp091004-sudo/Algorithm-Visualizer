import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.List;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import java.util.Collections;

public class ArrayScreen {

    private final Pane canvas = new Pane();
    private final List<IntNode> nodes = new ArrayList<>();      
    private final List<Integer> values = new ArrayList<>();
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    

    public Scene create(Stage stage, Scene selectScene) {

        Button createArray = new Button("Create Array");
        setButtonSize(createArray);

        createArray.setOnAction(e -> {
            values.clear();
            nodes.clear();
            canvas.getChildren().clear();
            values.addAll(ArrayCreation.display());
            plotValues(values);
        });


        Button sort = new Button("Sort Array");
        setButtonSize(sort);

        Button randomize = new Button("Randomize Array");
        setButtonSize(randomize);

        randomize.setOnAction(e -> {
            Collections.shuffle(values);
            plotValues(values);
        });
        
        Button clearNode = new Button("Clear Canvas");
        setButtonSize(clearNode);

        clearNode.setOnAction(e -> {
            values.clear();
            nodes.clear();
            canvas.getChildren().clear();
        });

        Button bubbleSort = new Button("Bubble Sort"),
                selectionSort = new Button("Selection Sort"),
                insertionSort = new Button("Insertion Sort"),
                mergeSort = new Button("Merge Sort"),
                quickSort = new Button("Quick Sort"),
                heapSort = new Button("Heap Sort"),
                shellSort = new Button("Shell Sort"),
                countingSort = new Button("Counting Sort"),
                radixSort = new Button("Radix Sort"),
                bucketSort = new Button("Bucket Sort"),
                backButton = new Button("Back");


        Button stepByStep = new Button("View Step By Step");
        setButtonSize(stepByStep);

        Button selectScreenButton = new Button("Select Screen");
        setButtonSize(selectScreenButton);

        selectScreenButton.setOnAction(e -> {
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
                createArray,
                randomize,
                sort,
                stepByStep,
                clearNode,
                selectScreenButton
        );
        TilePane sortMenu = new TilePane();
        sortMenu.setPrefColumns(5);
        setButtonSize(bubbleSort);
        setButtonSize(selectionSort);
        setButtonSize(insertionSort);
        setButtonSize(mergeSort);
        setButtonSize(quickSort);
        setButtonSize(heapSort);
        setButtonSize(shellSort);
        setButtonSize(countingSort);
        setButtonSize(radixSort);
        setButtonSize(bucketSort);
        setButtonSize(backButton);

        sortMenu.getChildren().addAll(
                bubbleSort,
                selectionSort,
                insertionSort,
                mergeSort,
                quickSort,
                heapSort,
                shellSort,
                countingSort,
                radixSort,
                bucketSort,
                backButton
        );
        
        BorderPane layout5 = new BorderPane();

        layout5.setCenter(canvas);
        layout5.setBottom(menu);

        Scene scene = new Scene(layout5, 1280, 700);
        scene.getStylesheets().add("style.css");
        sort.setOnAction(e -> {
            layout5.setBottom(sortMenu);
        });
        backButton.setOnAction(e -> {
            layout5.setBottom(menu);
        });
        return scene;
    }

    private void setButtonSize(Button button) {
        button.setMinSize(250, 75);
        button.setMaxSize(250, 75);
        button.setPrefSize(250, 75);
    }

 
    private void plotValues(List<Integer> values) {
    canvas.getChildren().clear();
    
    if (values.isEmpty()) {
        return;
    }

    double chartWidth = 1000;
    double chartHeight = 400;
    double bottomY = 500;

    // Find the largest value.
    int maximum = values.get(0);

    for (int value : values) {
        if (value > maximum) {
            maximum = value;
        }
    }

    // Prevent division by zero.
    if (maximum == 0) {
        maximum = 1;
    }

    double barWidth = chartWidth / values.size();

    double heightScale = chartHeight / maximum;
    double canvasWidth = canvas.getWidth();

    if (canvasWidth == 0) {
        canvasWidth = 1280;
    }

double startX = (canvasWidth - chartWidth) / 2;
    // Draw the horizontal baseline.
    Line baseline = new Line(
            startX,
            bottomY,
            startX + chartWidth,
            bottomY
    );

    baseline.setStroke(Color.BLACK);
    baseline.setStrokeWidth(2);

    canvas.getChildren().add(baseline);

    for (int i = 0; i < values.size(); i++) {
        int value = values.get(i);

        double barHeight = value * heightScale;
        double x = startX + i * (barWidth);
        double y = bottomY - barHeight;

        Rectangle bar = new Rectangle(
                x,
                y,
                barWidth,
                barHeight
        );

        bar.setFill(Color.DODGERBLUE);
        bar.setStroke(Color.BLACK);

        // Value displayed above the bar.
        Text valueText = new Text(String.valueOf(value));

        valueText.setFont(new Font("Consolas", 16));

        valueText.setX(
                x + barWidth / 2
                - valueText.getLayoutBounds().getWidth() / 2
        );

        valueText.setY(y - 5);

        // Array index displayed below the bar.
        Text indexText = new Text(
                String.valueOf(i)
        );

        indexText.setFont(new Font("Consolas", 14));

        indexText.setX(
                x + barWidth / 2
                - indexText.getLayoutBounds().getWidth() / 2
        );

        indexText.setY(bottomY + 20);

        canvas.getChildren().addAll(
                bar,
                valueText,
                indexText
        );
    }
}
}