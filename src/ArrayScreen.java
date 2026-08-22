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
import javafx.stage.Stage;

public class ArrayScreen {

    private final Pane canvas = new Pane();
    private final List<IntNode> nodes = new ArrayList<>();
    private final List<Integer> values = new ArrayList<>();

    public Scene create(Stage stage, Scene selectScene) {

        Button createArray = new Button("Create Array");
        setButtonSize(createArray);

        createArray.setOnAction(e -> {
            values.addAll(ArrayCreation.display());
            createNodes(values);
        });

        Button removeNode = new Button("Remove Node");
        setButtonSize(removeNode);

        Button clearNode = new Button("Clear Canvas");
        setButtonSize(clearNode);

        clearNode.setOnAction(e -> {
            values.clear();
            nodes.clear();
            canvas.getChildren().clear();
        });

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
                removeNode,
                clearNode,
                stepByStep,
                selectScreenButton
        );

        BorderPane layout5 = new BorderPane();

        layout5.setCenter(canvas);
        layout5.setBottom(menu);

        Scene scene = new Scene(layout5, 1280, 700);
        scene.getStylesheets().add("style.css");

        return scene;
    }

    private void setButtonSize(Button button) {
        button.setMinSize(250, 75);
        button.setMaxSize(250, 75);
        button.setPrefSize(250, 75);
    }

    private void resizeNodesIfNeeded() {
        if (nodes.isEmpty()) {
            return;
        }

        double canvasWidth = canvas.getWidth();

        if (canvasWidth == 0) {
            canvasWidth = 1200;
        }

        double totalNeededWidth =
                nodes.size() * (2 * IntNode.DEFAULT_RADIUS + 20);

        double scale = 1.0;

        if (totalNeededWidth > canvasWidth) {
            scale = canvasWidth / totalNeededWidth;
        }

        for (IntNode node : nodes) {
            node.setScale(scale);
        }
    }

    private void createNodes(List<Integer> inputs) {
        canvas.getChildren().clear();
        nodes.clear();

        double spacing = 2 * IntNode.DEFAULT_RADIUS + 20;
        double totalWidth = inputs.size() * spacing;

        double canvasWidth = canvas.getWidth();

        if (canvasWidth == 0) {
            canvasWidth = 1280;
        }

        double startX =
                (canvasWidth - totalWidth) / 2
                + IntNode.DEFAULT_RADIUS;

        double y = 120;

        // Create the nodes.
        for (int i = 0; i < inputs.size(); i++) {
            double x = startX + i * spacing;

            IntNode node = new IntNode(
                    x,
                    y,
                    IntNode.DEFAULT_RADIUS,
                    inputs.get(i),
                    false
            );

            nodes.add(node);
            canvas.getChildren().add(node);
        }

        resizeNodesIfNeeded();

        // Connect each node to the following node.
        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).setNext(nodes.get(i + 1));
        }

        // Draw arrows between connected nodes.
        for (IntNode from : nodes) {
            IntNode to = from.getNext();

            if (to != null) {
                drawArrow(from, to);
            }
        }
    }

    private void drawArrow(IntNode from, IntNode to) {
        double startX =
                from.getCenterX() + IntNode.DEFAULT_RADIUS;

        double startY = from.getCenterY();

        double endX =
                to.getCenterX() - IntNode.DEFAULT_RADIUS;

        double endY = to.getCenterY();

        Line line = new Line(
                startX,
                startY,
                endX,
                endY
        );

        line.setStrokeWidth(3);
        line.setStroke(Color.DARKGRAY);

        double arrowLength = 15;
        double arrowWidth = 8;

        double angle = Math.atan2(
                endY - startY,
                endX - startX
        );

        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        double x1 =
                endX - arrowLength * cos
                + arrowWidth * sin;

        double y1 =
                endY - arrowLength * sin
                - arrowWidth * cos;

        double x2 =
                endX - arrowLength * cos
                - arrowWidth * sin;

        double y2 =
                endY - arrowLength * sin
                + arrowWidth * cos;

        Polygon arrowHead = new Polygon();

        arrowHead.getPoints().addAll(
                endX, endY,
                x1, y1,
                x2, y2
        );

        arrowHead.setFill(Color.DARKGRAY);

        canvas.getChildren().addAll(line, arrowHead);
    }
}