package LinkedLists;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class NodeScreen {

    private final Pane canvas = new Pane();

    private final TextField searchResult = new TextField();

    private IntNode head;
    private LinkedListType listType;

    /*
     * This ArrayList is only used to position
     * and draw the nodes.
     */
    private final List<IntNode> nodes = new ArrayList<>();

    public Scene create(Stage stage, Scene selectScene) {

        // SEARCH RESULT
        searchResult.setEditable(false);
        searchResult.setPrefWidth(150);
        searchResult.setMaxWidth(150);
        searchResult.setPromptText("Search Result");
        searchResult.setVisible(false);
        searchResult.setManaged(false);

        // MODIFY
        Button modify = new Button("Modify");
        setButtonSize(modify);
        modify.setDisable(true);

        // SELECT SCREEN
        Button selectScreenButton = new Button("Select Screen");
        setButtonSize(selectScreenButton);

        selectScreenButton.setOnAction(e -> {
            stage.setScene(selectScene);
        });

        // CREATE LIST
        Button createList = new Button("Create List");
        setButtonSize(createList);

        createList.setOnAction(e -> {
            LinkedListData data = NodeCreation.display();

            if (data == null ||
                data.getType() == null ||
                data.getValues().isEmpty()) {

                return;
            }

            head = null;
            listType = null;

            nodes.clear();
            canvas.getChildren().clear();

            searchResult.clear();
            searchResult.setVisible(false);
            searchResult.setManaged(false);

            listType = data.getType();

            for (int value : data.getValues()) {
                appendNode(value);
            }

            drawNodes();
            modify.setDisable(false);
        });

        // CLEAR CANVAS
        Button clearNode = new Button("Clear Canvas");
        setButtonSize(clearNode);

        clearNode.setOnAction(e -> {
            head = null;
            listType = null;

            nodes.clear();
            canvas.getChildren().clear();

            searchResult.clear();
            searchResult.setVisible(false);
            searchResult.setManaged(false);

            modify.setDisable(true);
        });

        // MAIN MENU
        TilePane menu = new TilePane();

        menu.setPrefColumns(5);
        menu.setTileAlignment(Pos.CENTER);
        menu.setAlignment(Pos.CENTER);
        menu.setPrefWidth(Double.MAX_VALUE);
        menu.setHgap(20);
        menu.setVgap(10);
        menu.setPadding(new Insets(20, 0, 30, 0));

        menu.getChildren().addAll(
            createList,
            clearNode,
            modify,
            selectScreenButton
        );

        // LAYOUT
        BorderPane layout = new BorderPane();

        layout.setCenter(canvas);
        layout.setBottom(menu);

        // BACK
        Button back = new Button("Back");
        setButtonSize(back);

        back.setOnAction(e -> {
            layout.setBottom(menu);
        });

        // INSERT
        Button insert = new Button("Insert");
        setButtonSize(insert);

        insert.setOnAction(e -> {
            if (head == null) {
                return;
            }

            int[] result = NodeInsert.display(nodes.size());

            if (result == null) {
                return;
            }

            int value = result[0];
            int index = result[1];

            animateInsert(value, index);
        });

        // REMOVE
        Button remove = new Button("Remove");
        setButtonSize(remove);

        remove.setOnAction(e -> {
            if (head == null) {
                return;
            }

            Integer index =
                NodeRemove.display(nodes.size());

            if (index == null) {
                return;
            }

            animateRemove(index);
        });

        // SEARCH
        Button search = new Button("Search");
        setButtonSize(search);

        search.setOnAction(e -> {

            if (head == null) {
                return;
            }

            Integer value = NodeSearch.display();

            if (value == null) {
                return;
            }

            searchResult.clear();
            searchResult.setVisible(false);
            searchResult.setManaged(false);

            animateSearch(value);
        });

        // REVERSE
        Button reverse = new Button("Reverse");
        setButtonSize(reverse);

        reverse.setOnAction(e -> {
            if (head == null) {
                return;
            }

            animateReverse();
        });

        // ALGORITHM MENU
        TilePane algorithms = new TilePane();

        algorithms.setPrefColumns(5);
        algorithms.setTileAlignment(Pos.CENTER);
        algorithms.setAlignment(Pos.CENTER);
        algorithms.setPrefWidth(Double.MAX_VALUE);
        algorithms.setHgap(20);
        algorithms.setVgap(10);
        algorithms.setPadding(new Insets(20, 0, 30, 0));

        algorithms.getChildren().addAll(
            insert,
            remove,
            search,
            reverse,
            back
        );

        VBox algorithmArea = new VBox(10);
        algorithmArea.setAlignment(Pos.CENTER);
        algorithmArea.getChildren().addAll(
            searchResult,
            algorithms
        );

        // SCENE
        Scene scene = new Scene(layout, 1280, 700);
        scene.getStylesheets().add("style.css");

        modify.setOnAction(e -> {
            layout.setBottom(algorithmArea);
        });

        /*
         * Redraw the nodes when the window
         * size changes.
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

    // BUTTON SIZE
    private void setButtonSize(Button button) {
        button.setMinSize(250, 75);
        button.setMaxSize(250, 75);
        button.setPrefSize(250, 75);
    }

    // APPEND NODE
    private void appendNode(int value) {

        IntNode newNode;

        switch (listType) {

            case SINGLY:
                newNode = new IntNode(
                    0,
                    0,
                    IntNode.DEFAULT_RADIUS,
                    value,
                    false
                );
                break;

            case DOUBLY:
                newNode = new DoublyIntNode(
                    0,
                    0,
                    IntNode.DEFAULT_RADIUS,
                    value,
                    false
                );
                break;

            case CIRCULAR_SINGLY:
                newNode = new CircularIntNode(
                    0,
                    0,
                    IntNode.DEFAULT_RADIUS,
                    value,
                    false
                );
                break;

            case CIRCULAR_DOUBLY:
                newNode = new CircularDoublyIntNode(
                    0,
                    0,
                    IntNode.DEFAULT_RADIUS,
                    value,
                    false
                );
                break;

            default:
                return;
        }

        // EMPTY LIST
        if (head == null) {

            head = newNode;

            if (listType == LinkedListType.CIRCULAR_SINGLY) {
                newNode.setNext(newNode);
            }

            else if (listType == LinkedListType.CIRCULAR_DOUBLY) {

                CircularDoublyIntNode node =
                    (CircularDoublyIntNode) newNode;

                node.setNext(node);
                node.setPrevious(node);
            }

            return;
        }

        // SINGLY
        if (listType == LinkedListType.SINGLY) {

            IntNode current = head;

            while (current.getNext() != null) {
                current = current.getNext();
            }

            current.setNext(newNode);
        }

        // DOUBLY
        else if (listType == LinkedListType.DOUBLY) {

            DoublyIntNode current =
                (DoublyIntNode) head;

            while (current.getNext() != null) {
                current = current.getNext();
            }

            DoublyIntNode node =
                (DoublyIntNode) newNode;

            current.setNext(node);
            node.setPrevious(current);
        }

        // CIRCULAR SINGLY
        else if (
            listType ==
            LinkedListType.CIRCULAR_SINGLY
        ) {

            CircularIntNode current =
                (CircularIntNode) head;

            while (current.getNext() != head) {
                current = current.getNext();
            }

            CircularIntNode node =
                (CircularIntNode) newNode;

            current.setNext(node);
            node.setNext((CircularIntNode) head);
        }

        // CIRCULAR DOUBLY
        else if (
            listType ==
            LinkedListType.CIRCULAR_DOUBLY
        ) {

            CircularDoublyIntNode first =
                (CircularDoublyIntNode) head;

            CircularDoublyIntNode tail =
                first.getPrevious();

            CircularDoublyIntNode node =
                (CircularDoublyIntNode) newNode;

            tail.setNext(node);
            node.setPrevious(tail);

            node.setNext(first);
            first.setPrevious(node);
        }
    }
        // DRAW NODES
    private void drawNodes() {

        canvas.getChildren().clear();
        nodes.clear();

        if (head == null) {
            return;
        }

        IntNode current = head;

        // BUILD DRAWING LIST
        if (
            listType == LinkedListType.CIRCULAR_SINGLY ||
            listType == LinkedListType.CIRCULAR_DOUBLY
        ) {

            do {
                nodes.add(current);
                current = current.getNext();
            }
            while (current != head);

        } else {

            while (current != null) {
                nodes.add(current);
                current = current.getNext();
            }
        }

        if (nodes.isEmpty()) {
            return;
        }

        // CANVAS SIZE
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        if (canvasWidth <= 0) {
            canvasWidth = 1280;
        }

        if (canvasHeight <= 0) {
            canvasHeight = 500;
        }

        // NODE SCALING
        double horizontalPadding = 80;
        double normalGap = 30;

        double normalWidth =
            nodes.size() * IntNode.DEFAULT_RADIUS * 2
            + (nodes.size() - 1) * normalGap;

        double availableWidth =
            Math.max(
                100,
                canvasWidth - horizontalPadding * 2
            );

        double scale =
            Math.min(
                1.0,
                availableWidth / normalWidth
            );

        double radius =
            IntNode.DEFAULT_RADIUS * scale;

        double gap =
            normalGap * scale;

        double totalWidth =
            nodes.size() * radius * 2
            + (nodes.size() - 1) * gap;

        double startX =
            (canvasWidth - totalWidth) / 2
            + radius;

        double y =
            canvasHeight / 2;

        // POSITION NODES
        for (int i = 0; i < nodes.size(); i++) {

            IntNode node =
                nodes.get(i);

            double x =
                startX
                + i * (radius * 2 + gap);

            node.setScale(scale);
            node.setPosition(x, y);
        }

        // DRAW CONNECTIONS
        for (
            int i = 0;
            i < nodes.size() - 1;
            i++
        ) {

            IntNode firstNode =
                nodes.get(i);

            IntNode secondNode =
                nodes.get(i + 1);

            if (
                listType ==
                LinkedListType.SINGLY ||
                listType ==
                LinkedListType.CIRCULAR_SINGLY
            ) {

                drawArrow(
                    firstNode,
                    secondNode
                );
            }

            else {

                drawDoubleArrow(
                    firstNode,
                    secondNode
                );
            }
        }

        // DRAW NODES
        canvas.getChildren().addAll(nodes);

        // HEAD
        drawHeadLabel();

        // END OF LIST
        if (
            listType == LinkedListType.SINGLY ||
            listType == LinkedListType.DOUBLY
        ) {

            drawNullLabel();

        } else {

            drawCircularArrow();

            if (
                listType ==
                LinkedListType.CIRCULAR_DOUBLY
            ) {

                drawReverseCircularArrow();
            }
        }
    }

    // SINGLE ARROW
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
                y
            );

        arrowLine.setStroke(Color.BLACK);
        arrowLine.setStrokeWidth(2);

        double arrowSize =
            Math.min(
                8,
                firstNode.getRadius() / 3
            );

        Polygon arrowHead =
            new Polygon(
                endX,
                y,

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

    // DOUBLE ARROW
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
                y
            );

        arrowLine.setStroke(Color.BLACK);
        arrowLine.setStrokeWidth(2);

        double arrowSize =
            Math.min(
                8,
                firstNode.getRadius() / 3
            );

        Polygon rightArrow =
            new Polygon(
                endX,
                y,

                endX - arrowSize,
                y - arrowSize / 2,

                endX - arrowSize,
                y + arrowSize / 2
            );

        rightArrow.setFill(Color.BLACK);

        Polygon leftArrow =
            new Polygon(
                startX,
                y,

                startX + arrowSize,
                y - arrowSize / 2,

                startX + arrowSize,
                y + arrowSize / 2
            );

        leftArrow.setFill(Color.BLACK);

        canvas.getChildren().addAll(
            arrowLine,
            rightArrow,
            leftArrow
        );
    }

    // CIRCULAR ARROW
    private void drawCircularArrow() {

        if (nodes.isEmpty()) {
            return;
        }

        IntNode firstNode =
            nodes.get(0);

        IntNode lastNode =
            nodes.get(nodes.size() - 1);

        double firstX =
            firstNode.getCenterX();

        double lastX =
            lastNode.getCenterX();

        double y =
            firstNode.getCenterY();

        double radius =
            firstNode.getRadius();

        double bottomY =
            y + radius + 50;

        Path path =
            new Path();

        path.getElements().add(
            new MoveTo(
                lastX,
                y + radius
            )
        );

        path.getElements().add(
            new LineTo(
                lastX,
                bottomY
            )
        );

        path.getElements().add(
            new LineTo(
                firstX,
                bottomY
            )
        );

        path.getElements().add(
            new LineTo(
                firstX,
                y + radius
            )
        );

        path.setStroke(Color.BLACK);
        path.setStrokeWidth(2);
        path.setFill(null);

        double arrowSize =
            Math.min(
                8,
                radius / 3
            );

        Polygon arrowHead =
            new Polygon(
                firstX,
                y + radius,

                firstX - arrowSize / 2,
                y + radius + arrowSize,

                firstX + arrowSize / 2,
                y + radius + arrowSize
            );

        arrowHead.setFill(Color.BLACK);

        canvas.getChildren().addAll(
            path,
            arrowHead
        );
    }

    // REVERSE CIRCULAR ARROW
    private void drawReverseCircularArrow() {

        if (nodes.isEmpty()) {
            return;
        }

        IntNode firstNode =
            nodes.get(0);

        IntNode lastNode =
            nodes.get(nodes.size() - 1);

        double firstX =
            firstNode.getCenterX();

        double lastX =
            lastNode.getCenterX();

        double y =
            firstNode.getCenterY();

        double radius =
            firstNode.getRadius();

        double topY =
            y - radius - 50;

        Path path =
            new Path();

        path.getElements().add(
            new MoveTo(
                firstX,
                y - radius
            )
        );

        path.getElements().add(
            new LineTo(
                firstX,
                topY
            )
        );

        path.getElements().add(
            new LineTo(
                lastX,
                topY
            )
        );

        path.getElements().add(
            new LineTo(
                lastX,
                y - radius
            )
        );

        path.setStroke(Color.BLACK);
        path.setStrokeWidth(2);
        path.setFill(null);

        double arrowSize =
            Math.min(
                8,
                radius / 3
            );

        Polygon arrowHead =
            new Polygon(
                lastX,
                y - radius,

                lastX - arrowSize / 2,
                y - radius - arrowSize,

                lastX + arrowSize / 2,
                y - radius - arrowSize
            );

        arrowHead.setFill(Color.BLACK);

        canvas.getChildren().addAll(
            path,
            arrowHead
        );
    }

    // HEAD LABEL
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
                18
            )
        );

        headText.setFill(Color.BLACK);

        headText.setX(
            firstNode.getCenterX()
            - headText.getLayoutBounds()
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

    // NULL LABEL
    private void drawNullLabel() {

        if (nodes.isEmpty()) {
            return;
        }

        IntNode lastNode =
            nodes.get(
                nodes.size() - 1
            );

        Text nullText =
            new Text("null");

        nullText.setFont(
            new Font(
                "Consolas",
                18
            )
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
    // INSERT NODE
    private void insertNode(
            int value,
            int index) {

        // INSERT AT END
        if (index == nodes.size()) {
            appendNode(value);
            return;
        }

        IntNode newNode =
            createVisualNode(value);

        if (newNode == null) {
            return;
        }

        // INSERT AT HEAD
        if (index == 0) {

            // SINGLY
            if (
                listType ==
                LinkedListType.SINGLY
            ) {

                newNode.setNext(head);
                head = newNode;
            }

            // DOUBLY
            else if (
                listType ==
                LinkedListType.DOUBLY
            ) {

                DoublyIntNode oldHead =
                    (DoublyIntNode) head;

                DoublyIntNode node =
                    (DoublyIntNode) newNode;

                node.setNext(oldHead);
                oldHead.setPrevious(node);

                head = node;
            }

            // CIRCULAR SINGLY
            else if (
                listType ==
                LinkedListType.CIRCULAR_SINGLY
            ) {

                CircularIntNode oldHead =
                    (CircularIntNode) head;

                CircularIntNode node =
                    (CircularIntNode) newNode;

                CircularIntNode tail =
                    oldHead;

                while (
                    tail.getNext()
                    != oldHead
                ) {

                    tail =
                        tail.getNext();
                }

                node.setNext(oldHead);
                tail.setNext(node);

                head = node;
            }

            // CIRCULAR DOUBLY
            else if (
                listType ==
                LinkedListType.CIRCULAR_DOUBLY
            ) {

                CircularDoublyIntNode oldHead =
                    (CircularDoublyIntNode) head;

                CircularDoublyIntNode tail =
                    oldHead.getPrevious();

                CircularDoublyIntNode node =
                    (CircularDoublyIntNode) newNode;

                node.setNext(oldHead);
                node.setPrevious(tail);

                tail.setNext(node);
                oldHead.setPrevious(node);

                head = node;
            }

            return;
        }

        // FIND NODE BEFORE INSERTION
        IntNode previous = head;

        for (
            int i = 0;
            i < index - 1;
            i++
        ) {

            previous =
                previous.getNext();
        }

        // SINGLY / CIRCULAR SINGLY
        if (
            listType == LinkedListType.SINGLY ||
            listType == LinkedListType.CIRCULAR_SINGLY
        ) {

            newNode.setNext(
                previous.getNext()
            );

            previous.setNext(
                newNode
            );
        }

        // DOUBLY
        else if (
            listType ==
            LinkedListType.DOUBLY
        ) {

            DoublyIntNode previousNode =
                (DoublyIntNode) previous;

            DoublyIntNode nextNode =
                previousNode.getNext();

            DoublyIntNode node =
                (DoublyIntNode) newNode;

            node.setPrevious(
                previousNode
            );

            node.setNext(
                nextNode
            );

            previousNode.setNext(
                node
            );

            if (nextNode != null) {
                nextNode.setPrevious(node);
            }
        }

        // CIRCULAR DOUBLY
        else if (
            listType ==
            LinkedListType.CIRCULAR_DOUBLY
        ) {

            CircularDoublyIntNode previousNode =
                (CircularDoublyIntNode) previous;

            CircularDoublyIntNode nextNode =
                previousNode.getNext();

            CircularDoublyIntNode node =
                (CircularDoublyIntNode) newNode;

            node.setPrevious(
                previousNode
            );

            node.setNext(
                nextNode
            );

            previousNode.setNext(
                node
            );

            nextNode.setPrevious(
                node
            );
        }
    }

    // CREATE TEMPORARY VISUAL NODE
    private IntNode createVisualNode(
            int value) {

        switch (listType) {

            case SINGLY:
                return new IntNode(
                    0,
                    0,
                    IntNode.DEFAULT_RADIUS,
                    value,
                    false
                );

            case DOUBLY:
                return new DoublyIntNode(
                    0,
                    0,
                    IntNode.DEFAULT_RADIUS,
                    value,
                    false
                );

            case CIRCULAR_SINGLY:
                return new CircularIntNode(
                    0,
                    0,
                    IntNode.DEFAULT_RADIUS,
                    value,
                    false
                );

            case CIRCULAR_DOUBLY:
                return new CircularDoublyIntNode(
                    0,
                    0,
                    IntNode.DEFAULT_RADIUS,
                    value,
                    false
                );

            default:
                return null;
        }
    }

    // INSERT ANIMATION
    private void animateInsert(int value, int index) {

        Timeline timeline = new Timeline();

        IntNode newNode = createVisualNode(value);

        if (newNode == null || nodes.isEmpty()) {
            return;
        }

        // Position the node being added underneath the list
        double x;

        if (index == 0) {
            x = nodes.get(0).getCenterX() - 80;
        }

        else if (index >= nodes.size()) {
            x = nodes.get(nodes.size() - 1).getCenterX() + 100;
        }

        else {
            x = (
                nodes.get(index - 1).getCenterX()
                + nodes.get(index).getCenterX()
            ) / 2;
        }

        double y = nodes.get(0).getCenterY() + 100;

        newNode.setPosition(x, y);
        newNode.setSelected(true);

        // Show immediately
        canvas.getChildren().add(newNode);

        double delay = 0;

        // TRAVERSAL
        for (int i = 0; i < index && i < nodes.size(); i++) {

            final int currentIndex = i;

            timeline.getKeyFrames().add(
                new KeyFrame(
                    Duration.millis(delay),
                    e -> {
                        if (currentIndex > 0) {
                            nodes.get(currentIndex - 1).setSelected(false);
                        }

                        nodes.get(currentIndex).setSelected(true);
                    }
                )
            );

            delay += 500;
        }

        // REMOVE TRAVERSAL HIGHLIGHT
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.millis(delay),
                e -> {
                    for (IntNode node : nodes) {
                        node.setSelected(false);
                    }

                    // Keep the new node highlighted
                    newNode.setSelected(true);
                }
            )
        );

        delay += 300;

        // SHOW POINTER CHANGES
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.millis(delay),
                e -> {

                    // INSERT AT HEAD
                    if (index == 0) {

                        IntNode next = nodes.get(0);

                        drawTemporaryArrow(
                            newNode.getCenterX() + newNode.getRadius(),
                            newNode.getCenterY(),
                            next.getCenterX(),
                            next.getCenterY() + next.getRadius()
                        );

                        if (
                            listType == LinkedListType.DOUBLY ||
                            listType == LinkedListType.CIRCULAR_DOUBLY
                        ) {

                            drawTemporaryArrow(
                                next.getCenterX() - next.getRadius(),
                                next.getCenterY(),
                                newNode.getCenterX(),
                                newNode.getCenterY() - newNode.getRadius()
                            );
                        }
                    }

                    // INSERT AT TAIL
                    else if (index >= nodes.size()) {

                        IntNode previous = nodes.get(nodes.size() - 1);

                        drawTemporaryArrow(
                            previous.getCenterX(),
                            previous.getCenterY() + previous.getRadius(),
                            newNode.getCenterX() - newNode.getRadius(),
                            newNode.getCenterY()
                        );

                        if (
                            listType == LinkedListType.DOUBLY ||
                            listType == LinkedListType.CIRCULAR_DOUBLY
                        ) {

                            drawTemporaryArrow(
                                newNode.getCenterX(),
                                newNode.getCenterY() - newNode.getRadius(),
                                previous.getCenterX() + previous.getRadius(),
                                previous.getCenterY()
                            );
                        }
                    }

                    // INSERT IN MIDDLE
                    else {

                        IntNode previous = nodes.get(index - 1);
                        IntNode next = nodes.get(index);

                        // previous -> newNode
                        drawTemporaryArrow(
                            previous.getCenterX(),
                            previous.getCenterY() + previous.getRadius(),
                            newNode.getCenterX() - newNode.getRadius(),
                            newNode.getCenterY()
                        );

                        // newNode -> next
                        drawTemporaryArrow(
                            newNode.getCenterX() + newNode.getRadius(),
                            newNode.getCenterY(),
                            next.getCenterX(),
                            next.getCenterY() + next.getRadius()
                        );

                        // Reverse pointers for doubly linked lists
                        if (
                            listType == LinkedListType.DOUBLY ||
                            listType == LinkedListType.CIRCULAR_DOUBLY
                        ) {

                            // newNode -> previous
                            drawTemporaryArrow(
                                newNode.getCenterX() - newNode.getRadius(),
                                newNode.getCenterY(),
                                previous.getCenterX(),
                                previous.getCenterY() + previous.getRadius()
                            );

                            // next -> newNode
                            drawTemporaryArrow(
                                next.getCenterX(),
                                next.getCenterY() + next.getRadius(),
                                newNode.getCenterX() + newNode.getRadius(),
                                newNode.getCenterY()
                            );
                        }
                    }
                }
            )
        );

        delay += 1000;

        // ACTUAL INSERTION
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.millis(delay),
                e -> {
                    insertNode(value, index);

                    /*
                     * drawNodes() clears the temporary node
                     * and temporary arrows, then draws the
                     * real updated list.
                     */
                    drawNodes();

                    // Highlight the inserted node
                    if (index < nodes.size()) {
                        nodes.get(index).setSelected(true);
                    }
                }
            )
        );

        delay += 700;

        // FINISH
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.millis(delay),
                e -> {
                    if (index < nodes.size()) {
                        nodes.get(index).setSelected(false);
                    }
                }
            )
        );

        timeline.play();
    }
        // TEMPORARY ANIMATION ARROW
    private void drawTemporaryArrow(
            double startX,
            double startY,
            double endX,
            double endY) {

        Line line =
            new Line(
                startX,
                startY,
                endX,
                endY
            );

        line.setStroke(Color.RED);
        line.setStrokeWidth(2);

        double angle =
            Math.atan2(
                endY - startY,
                endX - startX
            );

        double arrowSize = 8;

        double x1 =
            endX
            - arrowSize
            * Math.cos(
                angle - Math.PI / 6
            );

        double y1 =
            endY
            - arrowSize
            * Math.sin(
                angle - Math.PI / 6
            );

        double x2 =
            endX
            - arrowSize
            * Math.cos(
                angle + Math.PI / 6
            );

        double y2 =
            endY
            - arrowSize
            * Math.sin(
                angle + Math.PI / 6
            );

        Polygon arrowHead =
            new Polygon(
                endX,
                endY,
                x1,
                y1,
                x2,
                y2
            );

        arrowHead.setFill(Color.RED);

        canvas.getChildren().addAll(
            line,
            arrowHead
        );
    }

    // REMOVE NODE
    private void removeNode(int index) {

        if (head == null) {
            return;
        }

        // ONLY NODE IN THE LIST
        if (nodes.size() == 1) {
            head = null;
            return;
        }

        // REMOVE HEAD
        if (index == 0) {

            // SINGLY
            if (listType == LinkedListType.SINGLY) {
                head = head.getNext();
            }

            // DOUBLY
            else if (listType == LinkedListType.DOUBLY) {

                DoublyIntNode newHead =
                    (DoublyIntNode) head.getNext();

                newHead.setPrevious(null);
                head = newHead;
            }

            // CIRCULAR SINGLY
            else if (listType == LinkedListType.CIRCULAR_SINGLY) {

                CircularIntNode oldHead =
                    (CircularIntNode) head;

                CircularIntNode tail = oldHead;

                while (tail.getNext() != oldHead) {
                    tail = tail.getNext();
                }

                CircularIntNode newHead =
                    oldHead.getNext();

                tail.setNext(newHead);
                head = newHead;
            }

            // CIRCULAR DOUBLY
            else if (listType == LinkedListType.CIRCULAR_DOUBLY) {

                CircularDoublyIntNode oldHead =
                    (CircularDoublyIntNode) head;

                CircularDoublyIntNode newHead =
                    oldHead.getNext();

                CircularDoublyIntNode tail =
                    oldHead.getPrevious();

                tail.setNext(newHead);
                newHead.setPrevious(tail);

                head = newHead;
            }

            return;
        }

        // FIND NODE BEFORE THE ONE BEING REMOVED
        IntNode previous = head;

        for (int i = 0; i < index - 1; i++) {
            previous = previous.getNext();
        }

        IntNode removedNode =
            previous.getNext();

        // SINGLY
        if (listType == LinkedListType.SINGLY) {

            previous.setNext(
                removedNode.getNext()
            );
        }

        // CIRCULAR SINGLY
        else if (listType == LinkedListType.CIRCULAR_SINGLY) {

            previous.setNext(
                removedNode.getNext()
            );
        }

        // DOUBLY
        else if (listType == LinkedListType.DOUBLY) {

            DoublyIntNode previousNode =
                (DoublyIntNode) previous;

            DoublyIntNode removed =
                (DoublyIntNode) removedNode;

            DoublyIntNode nextNode =
                removed.getNext();

            previousNode.setNext(nextNode);

            if (nextNode != null) {
                nextNode.setPrevious(previousNode);
            }
        }

        // CIRCULAR DOUBLY
        else if (listType == LinkedListType.CIRCULAR_DOUBLY) {

            CircularDoublyIntNode previousNode =
                (CircularDoublyIntNode) previous;

            CircularDoublyIntNode removed =
                (CircularDoublyIntNode) removedNode;

            CircularDoublyIntNode nextNode =
                removed.getNext();

            previousNode.setNext(nextNode);
            nextNode.setPrevious(previousNode);
        }
    }

    // REMOVE ANIMATION
    private void animateRemove(int index) {

        if (head == null || nodes.isEmpty()) {
            return;
        }

        Timeline timeline = new Timeline();
        double delay = 0;

        /*
         * Traverse from HEAD to the node
         * that we want to remove.
         *
         * Even doubly linked lists must start
         * from HEAD because NodeScreen does not
         * currently store a tail pointer.
         */
        for (int i = 0; i <= index; i++) {

            final int currentIndex = i;

            timeline.getKeyFrames().add(
                new KeyFrame(
                    Duration.millis(delay),
                    e -> {

                        /*
                         * Turn the previous traversal
                         * node back to normal.
                         */
                        if (currentIndex > 0) {
                            nodes.get(currentIndex - 1)
                                .setSelected(false);
                        }

                        /*
                         * Highlight the node we are
                         * currently visiting.
                         */
                        nodes.get(currentIndex)
                            .setSelected(true);
                    }
                )
            );

            delay += 500;
        }

        /*
         * Keep only the node being removed
         * highlighted.
         */
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.millis(delay),
                e -> {

                    for (int i = 0; i < nodes.size(); i++) {
                        nodes.get(i).setSelected(i == index);
                    }
                }
            )
        );

        delay += 500;

        /*
         * Show how the pointers will change
         * before actually removing the node.
         */
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.millis(delay),
                e -> {

                    /*
                     * If this is the only node,
                     * there are no nodes to reconnect.
                     */
                    if (nodes.size() == 1) {
                        return;
                    }

                    /*
                     * =========================
                     * REMOVE HEAD
                     * =========================
                     */
                    if (index == 0) {

                        IntNode nextNode = nodes.get(1);

                        /*
                         * For normal singly and doubly
                         * linked lists, HEAD will simply
                         * move to nextNode.
                         *
                         * There is no bypass arrow
                         * needed here.
                         */

                        /*
                         * Circular lists must reconnect
                         * the tail to the new HEAD.
                         */
                        if (
                            listType == LinkedListType.CIRCULAR_SINGLY ||
                            listType == LinkedListType.CIRCULAR_DOUBLY
                        ) {

                            IntNode tail =
                                nodes.get(nodes.size() - 1);

                            /*
                             * tail.next = newHead
                             */
                            drawTemporaryArrow(
                                tail.getCenterX(),
                                tail.getCenterY() + tail.getRadius(),

                                nextNode.getCenterX(),
                                nextNode.getCenterY() + nextNode.getRadius()
                            );

                            /*
                             * Circular doubly also needs:
                             *
                             * newHead.previous = tail
                             */
                            if (
                                listType ==
                                LinkedListType.CIRCULAR_DOUBLY
                            ) {

                                drawTemporaryArrow(
                                    nextNode.getCenterX(),
                                    nextNode.getCenterY() - nextNode.getRadius(),

                                    tail.getCenterX(),
                                    tail.getCenterY() - tail.getRadius()
                                );
                            }
                        }

                        return;
                    }

                    /*
                     * The node immediately before
                     * the node being removed.
                     */
                    IntNode previousNode =
                        nodes.get(index - 1);

                    /*
                     * =========================
                     * REMOVE TAIL
                     * =========================
                     */
                    if (index == nodes.size() - 1) {

                        /*
                         * For normal singly and doubly
                         * linked lists, previous.next
                         * becomes null.
                         *
                         * No replacement node exists,
                         * so there is no bypass arrow.
                         */
                        if (
                            listType == LinkedListType.SINGLY ||
                            listType == LinkedListType.DOUBLY
                        ) {
                            return;
                        }

                        /*
                         * Circular lists reconnect the
                         * new tail to HEAD.
                         */
                        IntNode firstNode =
                            nodes.get(0);

                        /*
                         * newTail.next = HEAD
                         */
                        drawTemporaryArrow(
                            previousNode.getCenterX(),
                            previousNode.getCenterY()
                                + previousNode.getRadius(),

                            firstNode.getCenterX(),
                            firstNode.getCenterY()
                                + firstNode.getRadius()
                        );

                        /*
                         * Circular doubly also needs:
                         *
                         * HEAD.previous = newTail
                         */
                        if (
                            listType ==
                            LinkedListType.CIRCULAR_DOUBLY
                        ) {

                            drawTemporaryArrow(
                                firstNode.getCenterX(),
                                firstNode.getCenterY()
                                    - firstNode.getRadius(),

                                previousNode.getCenterX(),
                                previousNode.getCenterY()
                                    - previousNode.getRadius()
                            );
                        }

                        return;
                    }

                    /*
                     * =========================
                     * REMOVE MIDDLE NODE
                     * =========================
                     */

                    IntNode nextNode =
                        nodes.get(index + 1);

                    /*
                     * Skip over the node being removed.
                     *
                     * previous.next = next
                     */
                    drawTemporaryArrow(
                        previousNode.getCenterX(),
                        previousNode.getCenterY()
                            + previousNode.getRadius(),

                        nextNode.getCenterX(),
                        nextNode.getCenterY()
                            + nextNode.getRadius()
                    );

                    /*
                     * Doubly linked lists also reconnect
                     * the previous pointer:
                     *
                     * next.previous = previous
                     */
                    if (
                        listType == LinkedListType.DOUBLY ||
                        listType == LinkedListType.CIRCULAR_DOUBLY
                    ) {

                        drawTemporaryArrow(
                            nextNode.getCenterX(),
                            nextNode.getCenterY()
                                - nextNode.getRadius(),

                            previousNode.getCenterX(),
                            previousNode.getCenterY()
                                - previousNode.getRadius()
                        );
                    }
                }
            )
        );

        /*
         * Leave the pointer change visible
         * for one second.
         */
        delay += 1000;

        /*
         * Actually modify the linked list.
         */
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.millis(delay),
                e -> {

                    removeNode(index);

                    /*
                     * Redraw the updated linked list.
                     *
                     * This also clears the temporary
                     * red arrows.
                     */
                    drawNodes();
                }
            )
        );

        timeline.play();
    }
        // SEARCH ANIMATION
    private void animateSearch(int value) {

        if (head == null || nodes.isEmpty()) {
            return;
        }

        Timeline timeline = new Timeline();
        double delay = 0;

        boolean found = false;

        for (int i = 0; i < nodes.size(); i++) {

            final int currentIndex = i;

            // Highlight the node currently being checked
            timeline.getKeyFrames().add(
                new KeyFrame(
                    Duration.millis(delay),
                    e -> {

                        if (currentIndex > 0) {
                            nodes.get(currentIndex - 1)
                                .setSelected(false);
                        }

                        nodes.get(currentIndex)
                            .setSelected(true);
                    }
                )
            );

            delay += 500;

            // VALUE FOUND
            if (nodes.get(i).getValue() == value) {

                final int foundIndex = i;

                timeline.getKeyFrames().add(
                    new KeyFrame(
                        Duration.millis(delay),
                        e -> {

                            nodes.get(foundIndex)
                                .setSelected(false);

                            nodes.get(foundIndex)
                                .setFound(true);
                        }
                    )
                );

                delay += 1000;

                timeline.getKeyFrames().add(
                    new KeyFrame(
                        Duration.millis(delay),
                        e -> {

                            nodes.get(foundIndex)
                                .setFound(false);

                            /*
                             * Display the index after
                             * the search animation finishes.
                             */
                            searchResult.setText(
                                "Index: " + foundIndex
                            );

                            searchResult.setVisible(true);
                            searchResult.setManaged(true);
                        }
                    )
                );

                found = true;
                break;
            }
        }

        // VALUE WAS NOT FOUND
        if (!found) {

            final int lastIndex =
                nodes.size() - 1;

            timeline.getKeyFrames().add(
                new KeyFrame(
                    Duration.millis(delay),
                    e -> {

                        nodes.get(lastIndex)
                            .setSelected(false);

                        /*
                         * The entire list was searched,
                         * so display Not Found.
                         */
                        searchResult.setText(
                            "Not Found"
                        );

                        searchResult.setVisible(true);
                        searchResult.setManaged(true);
                    }
                )
            );
        }

        timeline.play();
    }

    // REVERSE LIST
    private void reverseList() {

        if (head == null || head.getNext() == null) {
            return;
        }

        // SINGLY
        if (listType == LinkedListType.SINGLY) {

            IntNode previous = null;
            IntNode current = head;

            while (current != null) {

                IntNode next =
                    current.getNext();

                current.setNext(previous);

                previous = current;
                current = next;
            }

            head = previous;
        }

        // DOUBLY
        else if (listType == LinkedListType.DOUBLY) {

            DoublyIntNode current =
                (DoublyIntNode) head;

            DoublyIntNode newHead = null;

            while (current != null) {

                DoublyIntNode next =
                    current.getNext();

                DoublyIntNode previous =
                    current.getPrevious();

                current.setNext(previous);
                current.setPrevious(next);

                newHead = current;
                current = next;
            }

            head = newHead;
        }

        // CIRCULAR SINGLY
        else if (
            listType ==
            LinkedListType.CIRCULAR_SINGLY
        ) {

            CircularIntNode oldHead =
                (CircularIntNode) head;

            CircularIntNode previous =
                oldHead;

            CircularIntNode current =
                oldHead.getNext();

            while (current != oldHead) {

                CircularIntNode next =
                    current.getNext();

                current.setNext(previous);

                previous = current;
                current = next;
            }

            oldHead.setNext(previous);

            head = previous;
        }

        // CIRCULAR DOUBLY
        else if (
            listType ==
            LinkedListType.CIRCULAR_DOUBLY
        ) {

            CircularDoublyIntNode oldHead =
                (CircularDoublyIntNode) head;

            CircularDoublyIntNode current =
                oldHead;

            do {

                CircularDoublyIntNode oldNext =
                    current.getNext();

                CircularDoublyIntNode oldPrevious =
                    current.getPrevious();

                current.setNext(oldPrevious);
                current.setPrevious(oldNext);

                current = oldNext;

            } while (current != oldHead);

            /*
             * The old tail becomes the new HEAD.
             *
             * After swapping next/previous,
             * oldHead.getNext() is the old tail.
             */
            head = oldHead.getNext();
        }
    }

    // REVERSE ANIMATION
    
    private void animateReverse() {

        if (head == null || nodes.isEmpty()) {
            return;
        }

        /*
         * Nothing meaningful to reverse
         * with only one node.
         */
        if (nodes.size() == 1) {

            nodes.get(0).setSelected(true);

            Timeline singleNodeTimeline = new Timeline(
                new KeyFrame(
                    Duration.millis(700),
                    e -> nodes.get(0).setSelected(false)
                )
            );

            singleNodeTimeline.play();
            return;
        }

        Timeline timeline = new Timeline();
        double delay = 0;

        /*
         * Visit every node from HEAD forward.
         */
        for (int i = 0; i < nodes.size(); i++) {

            final int currentIndex = i;

            timeline.getKeyFrames().add(
                new KeyFrame(
                    Duration.millis(delay),
                    e -> {

                        if (currentIndex > 0) {
                            nodes.get(currentIndex - 1)
                                .setSelected(false);
                        }

                        nodes.get(currentIndex)
                            .setSelected(true);
                    }
                )
            );

            delay += 500;
        }

        /*
         * Leave the final node highlighted
         * briefly before displaying the result.
         */
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.millis(delay),
                e -> {

                    nodes.get(nodes.size() - 1)
                        .setSelected(true);
                }
            )
        );

        delay += 500;

        /*
         * Reverse the actual linked list and
         * redraw it in its new order.
         */
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.millis(delay),
                e -> {

                    reverseList();
                    drawNodes();

                    /*
                     * The old tail is now HEAD,
                     * so highlight the new HEAD.
                     */
                    if (!nodes.isEmpty()) {
                        nodes.get(0)
                            .setSelected(true);
                    }
                }
            )
        );

        delay += 700;

        /*
         * Return the new HEAD to normal.
         */
        timeline.getKeyFrames().add(
            new KeyFrame(
                Duration.millis(delay),
                e -> {

                    if (!nodes.isEmpty()) {
                        nodes.get(0)
                            .setSelected(false);
                    }
                }
            )
        );

        timeline.play();
    }
}