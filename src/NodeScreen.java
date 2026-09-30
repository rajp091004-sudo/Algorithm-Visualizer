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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
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
     * Stores the type of linked list currently
     * being displayed.
     */
    private LinkedListType listType;

    /*
     * This ArrayList is only used to position
     * and draw the nodes.
     */
    private final List<IntNode> nodes =
            new ArrayList<>();

    public Scene create(
            Stage stage,
            Scene selectScene) {

        // ========================================
        // ADD NODE / CREATE LIST
        // ========================================

        Button addNode =
                new Button("Add Node");

        setButtonSize(addNode);

        addNode.setOnAction(e -> {

            LinkedListData data =
                    NodeCreation.display();

            if (data == null ||
                    data.getType() == null ||
                    data.getValues().isEmpty()) {

                return;
            }

            /*
             * Creating a new list replaces
             * the current linked list.
             */
            head = null;
            listType = null;

            nodes.clear();
            canvas.getChildren().clear();

            listType = data.getType();

            for (int value : data.getValues()) {
                appendNode(value);
            }

            drawNodes();
        });

        // ========================================
        // REMOVE NODE
        // ========================================

        Button removeNode =
                new Button("Remove Node");

        setButtonSize(removeNode);

        // ========================================
        // CLEAR CANVAS
        // ========================================

        Button clearNode =
                new Button("Clear Canvas");

        setButtonSize(clearNode);

        clearNode.setOnAction(e -> {

            head = null;
            listType = null;

            nodes.clear();

            canvas.getChildren().clear();
        });

        // ========================================
        // STEP BY STEP
        // ========================================

        Button stepByStep =
                new Button("View Step By Step");

        setButtonSize(stepByStep);

        // ========================================
        // SELECT SCREEN
        // ========================================

        Button selectScreenButton =
                new Button("Select Screen");

        setButtonSize(selectScreenButton);

        selectScreenButton.setOnAction(e -> {

            stage.setScene(selectScene);

        });

        // ========================================
        // MENU
        // ========================================

        TilePane menu =
                new TilePane();

        menu.setPrefColumns(5);

        menu.setTileAlignment(
                Pos.CENTER);

        menu.setAlignment(
                Pos.CENTER);

        menu.setPrefWidth(
                Double.MAX_VALUE);

        menu.setHgap(20);
        menu.setVgap(10);

        menu.setPadding(
                new Insets(
                        20,
                        0,
                        30,
                        0));

        menu.getChildren().addAll(
                addNode,
                removeNode,
                clearNode,
                stepByStep,
                selectScreenButton);

        // ========================================
        // LAYOUT
        // ========================================

        BorderPane layout =
                new BorderPane();

        layout.setCenter(canvas);
        layout.setBottom(menu);

        Scene scene =
                new Scene(
                        layout,
                        1280,
                        700);

        scene.getStylesheets().add(
                "style.css");

        /*
         * Redraw the nodes when the window
         * size changes.
         */
        canvas.widthProperty().addListener(
                (observable,
                 oldWidth,
                 newWidth) -> {

                    if (head != null) {
                        drawNodes();
                    }

                });

        canvas.heightProperty().addListener(
                (observable,
                 oldHeight,
                 newHeight) -> {

                    if (head != null) {
                        drawNodes();
                    }

                });

        return scene;
    }

    // ========================================
    // BUTTON SIZE
    // ========================================

    private void setButtonSize(
            Button button) {

        button.setMinSize(
                250,
                75);

        button.setMaxSize(
                250,
                75);

        button.setPrefSize(
                250,
                75);
    }

    // ========================================
    // APPEND NODE
    // ========================================

    /*
     * Adds a new node to the end of
     * the linked list.
     */
    private void appendNode(
            int value) {

        IntNode newNode;

        // ========================================
        // CREATE CORRECT NODE TYPE
        // ========================================

        switch (listType) {

            case SINGLY:

                newNode =
                        new IntNode(
                                0,
                                0,
                                IntNode.DEFAULT_RADIUS,
                                value,
                                false);

                break;

            case DOUBLY:

                newNode =
                        new DoublyIntNode(
                                0,
                                0,
                                IntNode.DEFAULT_RADIUS,
                                value,
                                false);

                break;

            case CIRCULAR_SINGLY:

                newNode =
                        new CircularIntNode(
                                0,
                                0,
                                IntNode.DEFAULT_RADIUS,
                                value,
                                false);

                break;

            case CIRCULAR_DOUBLY:

                newNode =
                        new CircularDoublyIntNode(
                                0,
                                0,
                                IntNode.DEFAULT_RADIUS,
                                value,
                                false);

                break;

            default:
                return;
        }

        // ========================================
        // EMPTY LIST
        // ========================================

        if (head == null) {

            head = newNode;

            /*
             * A circular singly linked list
             * with one node points to itself.
             */
            if (listType ==
                    LinkedListType.CIRCULAR_SINGLY) {

                newNode.setNext(
                        newNode);
            }

            /*
             * A circular doubly linked list
             * with one node has both pointers
             * pointing to itself.
             */
            else if (listType ==
                    LinkedListType.CIRCULAR_DOUBLY) {

                CircularDoublyIntNode node =
                        (CircularDoublyIntNode)
                                newNode;

                node.setNext(node);
                node.setPrevious(node);
            }

            return;
        }

        // ========================================
        // SINGLY LINKED LIST
        // ========================================

        if (listType ==
                LinkedListType.SINGLY) {

            IntNode current =
                    head;

            while (current.getNext()
                    != null) {

                current =
                        current.getNext();
            }

            current.setNext(
                    newNode);
        }

        // ========================================
        // DOUBLY LINKED LIST
        // ========================================

        else if (listType ==
                LinkedListType.DOUBLY) {

            DoublyIntNode current =
                    (DoublyIntNode)
                            head;

            while (current.getNext()
                    != null) {

                current =
                        current.getNext();
            }

            DoublyIntNode node =
                    (DoublyIntNode)
                            newNode;

            current.setNext(
                    node);

            node.setPrevious(
                    current);
        }

        // ========================================
        // CIRCULAR SINGLY LINKED LIST
        // ========================================

        else if (listType ==
                LinkedListType.CIRCULAR_SINGLY) {

            CircularIntNode current =
                    (CircularIntNode)
                            head;

            /*
             * Stop when the next node
             * would take us back to HEAD.
             */
            while (current.getNext()
                    != head) {

                current =
                        current.getNext();
            }

            CircularIntNode node =
                    (CircularIntNode)
                            newNode;

            current.setNext(
                    node);

            node.setNext(
                    (CircularIntNode)
                            head);
        }

        // ========================================
        // CIRCULAR DOUBLY LINKED LIST
        // ========================================

        else if (listType ==
                LinkedListType.CIRCULAR_DOUBLY) {

            CircularDoublyIntNode first =
                    (CircularDoublyIntNode)
                            head;

            /*
             * In a circular doubly linked list,
             * head.previous is always the tail.
             */
            CircularDoublyIntNode tail =
                    first.getPrevious();

            CircularDoublyIntNode node =
                    (CircularDoublyIntNode)
                            newNode;

            // Old tail -> new node
            tail.setNext(
                    node);

            // New node -> old tail
            node.setPrevious(
                    tail);

            // New node -> head
            node.setNext(
                    first);

            // Head -> new tail
            first.setPrevious(
                    node);
        }
    }

    // ========================================
    // DRAW NODES
    // ========================================

    /*
     * Traverses the linked list from HEAD
     * and draws every node.
     */
    private void drawNodes() {

        canvas.getChildren().clear();
        nodes.clear();

        if (head == null) {
            return;
        }

        IntNode current =
                head;

        // ========================================
        // BUILD DRAWING LIST
        // ========================================

        if (listType ==
                    LinkedListType.CIRCULAR_SINGLY ||
                listType ==
                    LinkedListType.CIRCULAR_DOUBLY) {

            do {

                nodes.add(
                        current);

                current =
                        current.getNext();

            } while (current != head);

        } else {

            while (current != null) {

                nodes.add(
                        current);

                current =
                        current.getNext();
            }
        }

        if (nodes.isEmpty()) {
            return;
        }

        // ========================================
        // CANVAS SIZE
        // ========================================

        double canvasWidth =
                canvas.getWidth();

        double canvasHeight =
                canvas.getHeight();

        if (canvasWidth <= 0) {
            canvasWidth =
                    1280;
        }

        if (canvasHeight <= 0) {
            canvasHeight =
                    500;
        }

        // ========================================
        // NODE SCALING
        // ========================================

        double horizontalPadding =
                80;

        double normalGap =
                30;

        double normalWidth =
                nodes.size()
                        * IntNode.DEFAULT_RADIUS
                        * 2
                        + (nodes.size() - 1)
                        * normalGap;

        double availableWidth =
                Math.max(
                        100,
                        canvasWidth
                                - horizontalPadding
                                * 2);

        double scale =
                Math.min(
                        1.0,
                        availableWidth
                                / normalWidth);

        double radius =
                IntNode.DEFAULT_RADIUS
                        * scale;

        double gap =
                normalGap
                        * scale;

        double totalWidth =
                nodes.size()
                        * radius
                        * 2
                        + (nodes.size() - 1)
                        * gap;

        double startX =
                (canvasWidth
                        - totalWidth)
                        / 2
                        + radius;

        double y =
                canvasHeight
                        / 2;

        // ========================================
        // POSITION NODES
        // ========================================

        for (int i = 0;
                i < nodes.size();
                i++) {

            IntNode node =
                    nodes.get(i);

            double x =
                    startX
                            + i
                            * (radius
                            * 2
                            + gap);

            node.setScale(
                    scale);

            node.setPosition(
                    x,
                    y);
        }

        // ========================================
        // DRAW CONNECTIONS
        // ========================================

        /*
         * Draw arrows first so that
         * the nodes appear above them.
         */
        for (int i = 0;
                i < nodes.size() - 1;
                i++) {

            IntNode firstNode =
                    nodes.get(i);

            IntNode secondNode =
                    nodes.get(i + 1);

            /*
             * Singly linked lists have
             * one-directional arrows.
             */
            if (listType ==
                        LinkedListType.SINGLY ||
                    listType ==
                        LinkedListType.CIRCULAR_SINGLY) {

                drawArrow(
                        firstNode,
                        secondNode);
            }

            /*
             * Doubly linked lists have
             * arrows in both directions.
             */
            else {

                drawDoubleArrow(
                        firstNode,
                        secondNode);
            }
        }

        // ========================================
        // DRAW NODES
        // ========================================

        canvas.getChildren().addAll(
                nodes);

        // ========================================
        // HEAD
        // ========================================

        drawHeadLabel();

        // ========================================
        // END OF LIST
        // ========================================

        if (listType ==
                    LinkedListType.SINGLY ||
                listType ==
                    LinkedListType.DOUBLY) {

            drawNullLabel();

        } else {

            /*
             * Tail -> Head
             */
            drawCircularArrow();

            /*
             * For a circular doubly list,
             * Head -> Tail also exists.
             */
            if (listType ==
                    LinkedListType.CIRCULAR_DOUBLY) {

                drawReverseCircularArrow();
            }
        }
    }

    // ========================================
    // SINGLE ARROW
    // ========================================

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

        Line arrowLine =
                new Line(
                        startX,
                        y,
                        endX,
                        y);

        arrowLine.setStroke(
                Color.BLACK);

        arrowLine.setStrokeWidth(
                2);

        double arrowSize =
                Math.min(
                        8,
                        firstNode.getRadius()
                                / 3);

        Polygon arrowHead =
                new Polygon(
                        endX,
                        y,

                        endX - arrowSize,
                        y - arrowSize / 2,

                        endX - arrowSize,
                        y + arrowSize / 2);

        arrowHead.setFill(
                Color.BLACK);

        canvas.getChildren().addAll(
                arrowLine,
                arrowHead);
    }

    // ========================================
    // DOUBLE ARROW
    // ========================================

    private void drawDoubleArrow(
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

        Line arrowLine =
                new Line(
                        startX,
                        y,
                        endX,
                        y);

        arrowLine.setStroke(
                Color.BLACK);

        arrowLine.setStrokeWidth(
                2);

        double arrowSize =
                Math.min(
                        8,
                        firstNode.getRadius()
                                / 3);

        // Arrow pointing right
        Polygon rightArrow =
                new Polygon(
                        endX,
                        y,

                        endX - arrowSize,
                        y - arrowSize / 2,

                        endX - arrowSize,
                        y + arrowSize / 2);

        rightArrow.setFill(
                Color.BLACK);

        // Arrow pointing left
        Polygon leftArrow =
                new Polygon(
                        startX,
                        y,

                        startX + arrowSize,
                        y - arrowSize / 2,

                        startX + arrowSize,
                        y + arrowSize / 2);

        leftArrow.setFill(
                Color.BLACK);

        canvas.getChildren().addAll(
                arrowLine,
                rightArrow,
                leftArrow);
    }

    // ========================================
    // CIRCULAR ARROW
    // ========================================

    /*
     * Draws the connection from the
     * tail back to HEAD.
     */
    private void drawCircularArrow() {

        if (nodes.isEmpty()) {
            return;
        }

        IntNode firstNode =
                nodes.get(0);

        IntNode lastNode =
                nodes.get(
                        nodes.size() - 1);

        double firstX =
                firstNode.getCenterX();

        double lastX =
                lastNode.getCenterX();

        double y =
                firstNode.getCenterY();

        double radius =
                firstNode.getRadius();

        double bottomY =
                y
                        + radius
                        + 50;

        Path path =
                new Path();

        // Start at bottom of tail.
        path.getElements().add(
                new MoveTo(
                        lastX,
                        y + radius));

        // Move downward.
        path.getElements().add(
                new LineTo(
                        lastX,
                        bottomY));

        // Move underneath list.
        path.getElements().add(
                new LineTo(
                        firstX,
                        bottomY));

        // Move upward toward HEAD.
        path.getElements().add(
                new LineTo(
                        firstX,
                        y + radius));

        path.setStroke(
                Color.BLACK);

        path.setStrokeWidth(
                2);

        path.setFill(
                null);

        double arrowSize =
                Math.min(
                        8,
                        radius / 3);

        /*
         * Arrow pointing upward
         * into HEAD.
         */
        Polygon arrowHead =
                new Polygon(
                        firstX,
                        y + radius,

                        firstX - arrowSize / 2,
                        y + radius + arrowSize,

                        firstX + arrowSize / 2,
                        y + radius + arrowSize);

        arrowHead.setFill(
                Color.BLACK);

        canvas.getChildren().addAll(
                path,
                arrowHead);
    }

    // ========================================
    // REVERSE CIRCULAR ARROW
    // ========================================

    /*
     * Used only for circular doubly
     * linked lists.
     *
     * Draws HEAD.previous -> tail.
     */
    private void drawReverseCircularArrow() {

        if (nodes.isEmpty()) {
            return;
        }

        IntNode firstNode =
                nodes.get(0);

        IntNode lastNode =
                nodes.get(
                        nodes.size() - 1);

        double firstX =
                firstNode.getCenterX();

        double lastX =
                lastNode.getCenterX();

        double y =
                firstNode.getCenterY();

        double radius =
                firstNode.getRadius();

        double topY =
                y
                        - radius
                        - 50;

        Path path =
                new Path();

        // Start above HEAD.
        path.getElements().add(
                new MoveTo(
                        firstX,
                        y - radius));

        // Move upward.
        path.getElements().add(
                new LineTo(
                        firstX,
                        topY));

        // Move across toward tail.
        path.getElements().add(
                new LineTo(
                        lastX,
                        topY));

        // Move downward toward tail.
        path.getElements().add(
                new LineTo(
                        lastX,
                        y - radius));

        path.setStroke(
                Color.BLACK);

        path.setStrokeWidth(
                2);

        path.setFill(
                null);

        double arrowSize =
                Math.min(
                        8,
                        radius / 3);

        /*
         * Arrow pointing downward
         * into the tail.
         */
        Polygon arrowHead =
                new Polygon(
                        lastX,
                        y - radius,

                        lastX - arrowSize / 2,
                        y - radius - arrowSize,

                        lastX + arrowSize / 2,
                        y - radius - arrowSize);

        arrowHead.setFill(
                Color.BLACK);

        canvas.getChildren().addAll(
                path,
                arrowHead);
    }

    // ========================================
    // HEAD LABEL
    // ========================================

    private void drawHeadLabel() {

        if (nodes.isEmpty()) {
            return;
        }

        IntNode firstNode =
                nodes.get(0);

        Text headText =
                new Text("HEAD");

        headText.setFont(
                new Font(
                        "Consolas",
                        18));

        headText.setFill(
                Color.BLACK);

        headText.setX(
                firstNode.getCenterX()
                        - headText
                        .getLayoutBounds()
                        .getWidth()
                        / 2);

        headText.setY(
                firstNode.getCenterY()
                        - firstNode.getRadius()
                        - 25);

        canvas.getChildren().add(
                headText);
    }

    // ========================================
    // NULL LABEL
    // ========================================

    private void drawNullLabel() {

        if (nodes.isEmpty()) {
            return;
        }

        IntNode lastNode =
                nodes.get(
                        nodes.size() - 1);

        Text nullText =
                new Text("null");

        nullText.setFont(
                new Font(
                        "Consolas",
                        18));

        nullText.setFill(
                Color.BLACK);

        nullText.setX(
                lastNode.getCenterX()
                        + lastNode.getRadius()
                        + 20);

        nullText.setY(
                lastNode.getCenterY()
                        + 5);

        canvas.getChildren().add(
                nullText);
    }
}