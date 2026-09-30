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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NodeScreen {

    private final Pane canvas = new Pane();

    /*
     * Head is the beginning of the actual linked list.
     */
    private IntNode head;

    /*
     * This ArrayList is only used to position
     * and draw the nodes.
     */
    private final List<IntNode> nodes =
            new ArrayList<>();

    public Scene create(
            Stage stage,
            Scene selectScene) {

        Button addNode =
                new Button("Add Node");

        setButtonSize(addNode);

        addNode.setOnAction(e -> {
            List<Integer> newValues =
                    NodeCreation.display();

            for (int value : newValues) {
                appendNode(value);
            }

            drawNodes();
        });

        Button removeNode =
                new Button("Remove Node");

        setButtonSize(removeNode);

        Button clearNode =
                new Button("Clear Canvas");

        setButtonSize(clearNode);

        clearNode.setOnAction(e -> {
            head = null;
            nodes.clear();
            canvas.getChildren().clear();
        });

        Button stepByStep =
                new Button("View Step By Step");

        setButtonSize(stepByStep);

        Button selectScreenButton =
                new Button("Select Screen");

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
        menu.setPadding(
            new Insets(20, 0, 30, 0)
        );

        menu.getChildren().addAll(
            addNode,
            removeNode,
            clearNode,
            stepByStep,
            selectScreenButton
        );

        BorderPane layout = new BorderPane();

        layout.setCenter(canvas);
        layout.setBottom(menu);

        Scene scene = new Scene(
            layout,
            1280,
            700
        );

        scene.getStylesheets().add(
            "style.css"
        );

        /*
         * Redraw the nodes when the window size changes.
         */
        canvas.widthProperty().addListener(
            (observable, oldWidth, newWidth) -> {
                if (head != null) {
                    drawNodes();
                }
            }
        );

        canvas.heightProperty().addListener(
            (observable, oldHeight, newHeight) -> {
                if (head != null) {
                    drawNodes();
                }
            }
        );

        return scene;
    }

    private void setButtonSize(Button button) {
        button.setMinSize(250, 75);
        button.setMaxSize(250, 75);
        button.setPrefSize(250, 75);
    }

    /*
     * Adds a new node to the end of the linked list.
     */
    private void appendNode(int value) {
        IntNode newNode = new IntNode(
            0,
            0,
            IntNode.DEFAULT_RADIUS,
            value,
            false
        );

        // The list is currently empty.
        if (head == null) {
            head = newNode;
            return;
        }

        // Find the final node.
        IntNode current = head;

        while (current.getNext() != null) {
            current = current.getNext();
        }

        // Connect the final node to the new node.
        current.setNext(newNode);
    }

    /*
     * Traverses the linked list from head
     * and draws every node.
     */
    private void drawNodes() {
        canvas.getChildren().clear();
        nodes.clear();

        IntNode current = head;

        while (current != null) {
            nodes.add(current);
            current = current.getNext();
        }

        if (nodes.isEmpty()) {
            return;
        }

        double canvasWidth =
                canvas.getWidth();

        double canvasHeight =
                canvas.getHeight();

        if (canvasWidth <= 0) {
            canvasWidth = 1280;
        }

        if (canvasHeight <= 0) {
            canvasHeight = 500;
        }

        double horizontalPadding = 80;
        double normalGap = 30;

        double normalWidth =
                nodes.size()
                * IntNode.DEFAULT_RADIUS
                * 2
                + (nodes.size() - 1)
                * normalGap;

        double availableWidth = Math.max(
            100,
            canvasWidth
            - horizontalPadding * 2
        );

        double scale = Math.min(
            1.0,
            availableWidth / normalWidth
        );

        double radius =
                IntNode.DEFAULT_RADIUS
                * scale;

        double gap =
                normalGap * scale;

        double totalWidth =
                nodes.size()
                * radius
                * 2
                + (nodes.size() - 1)
                * gap;

        double startX =
                (canvasWidth - totalWidth) / 2
                + radius;

        double y =
                canvasHeight / 2;

        // Resize and position every node.
        for (int i = 0;
                i < nodes.size();
                i++) {

            IntNode node =
                    nodes.get(i);

            double x =
                    startX
                    + i * (
                        radius * 2 + gap
                    );

            node.setScale(scale);
            node.setPosition(x, y);
        }

        /*
         * Draw the arrows first so they
         * appear behind the nodes.
         */
        for (int i = 0;
                i < nodes.size() - 1;
                i++) {

            drawArrow(
                nodes.get(i),
                nodes.get(i + 1)
            );
        }

        // Draw the nodes over the arrows.
        canvas.getChildren().addAll(nodes);

        drawHeadLabel();
        drawNullLabel();
    }

    /*
     * Draws an arrow from one node
     * to the following node.
     */
    private void drawArrow(
            IntNode firstNode,
            IntNode secondNode) {

        double startX =
                firstNode.getCenterX()
                + firstNode.getRadius();

        double endX =
                secondNode.getCenterX()
                - secondNode.getRadius();

        double y =
                firstNode.getCenterY();

        Line arrowLine = new Line(
            startX,
            y,
            endX,
            y
        );

        arrowLine.setStroke(Color.BLACK);
        arrowLine.setStrokeWidth(2);

        double arrowSize = Math.min(
            8,
            firstNode.getRadius() / 3
        );

        Polygon arrowHead = new Polygon(
            endX, y,
            endX - arrowSize,
            y - arrowSize / 2,
            endX - arrowSize,
            y + arrowSize / 2
        );

        arrowHead.setFill(Color.BLACK);

        canvas.getChildren().addAll(
            arrowLine,
            arrowHead
        );
    }

    /*
     * Displays HEAD above the first node.
     */
    private void drawHeadLabel() {
        if (nodes.isEmpty()) {
            return;
        }

        IntNode firstNode =
                nodes.get(0);

        Text headText =
                new Text("HEAD");

        headText.setFont(
            new Font("Consolas", 18)
        );

        headText.setFill(Color.BLACK);

        headText.setX(
            firstNode.getCenterX()
            - headText
                .getLayoutBounds()
                .getWidth() / 2
        );

        headText.setY(
            firstNode.getCenterY()
            - firstNode.getRadius()
            - 25
        );

        canvas.getChildren().add(
            headText
        );
    }

    /*
     * Displays null after the final node.
     */
    private void drawNullLabel() {
        if (nodes.isEmpty()) {
            return;
        }

        IntNode lastNode =
                nodes.get(nodes.size() - 1);

        Text nullText =
                new Text("null");

        nullText.setFont(
            new Font("Consolas", 18)
        );

        nullText.setFill(Color.BLACK);

        nullText.setX(
            lastNode.getCenterX()
            + lastNode.getRadius()
            + 20
        );

        nullText.setY(
            lastNode.getCenterY() + 5
        );

        canvas.getChildren().add(
            nullText
        );
    }
}