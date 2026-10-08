package StackQueue;
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

public class sqScreen {

    private final Pane canvas = new Pane();
    BorderPane layout = new BorderPane();

    private final Stack<Integer> stack = new Stack<>();
    private final Deque<Integer> queue = new ArrayDeque<>();

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();

    public Scene create(Stage stage, Scene selectScene) {

        Scene scene = new Scene(layout, 1280, 700);
        scene.getStylesheets().add("style.css");

        Button createStack = new Button("Create Stack");
        setButtonSize(createStack);

        Button createQueue = new Button("Create Queue");
        setButtonSize(createQueue);

        Button clear = new Button("Clear");
        setButtonSize(clear);
        clear.setOnAction(e -> {
            stack.clear();
            queue.clear();
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
                createStack,
                createQueue,
                clear,
                selectScreen);
        layout.setBottom(menu);
        layout.setCenter(canvas);
        TilePane stackMenu = new TilePane();

        TextField stackInput = new TextField();
        stackInput.setPromptText("Enter a Value");
        stackInput.setPrefWidth(200);
        stackInput.setPrefHeight(50);

        Button push = new Button("Push");
        push.disableProperty().bind(stackInput.textProperty().isEmpty());
        setButtonSize(push);

        Button pop = new Button("Pop");
        setButtonSize(pop);

        pop.setOnAction(e -> {
            if (!stack.isEmpty()) {
                int value = stack.pop();

                drawStack();
                drawResult("Pop", value);
            }
        });

        Button peek = new Button("Peek");
        setButtonSize(peek);
        peek.setOnAction(e -> {
            if (!stack.isEmpty()) {
                int value = stack.peek();
                drawStack();
                drawResult("Peek", value);
            }
        });

        push.setOnAction(e -> {
            try {
                int value = Integer.parseInt(stackInput.getText());

                stack.push(value);
                stackInput.clear();

                drawStack();

            } catch (NumberFormatException ex) {
                stackInput.clear();
                stackInput.setPromptText("Enter an Integer");
            }
        });

        Button backStack = new Button("Back");
        setButtonSize(backStack);

        stackMenu.setPrefColumns(1);
        stackMenu.setTileAlignment(Pos.CENTER);
        stackMenu.setAlignment(Pos.CENTER);

        stackMenu.setVgap(20);
        stackMenu.setPadding(new Insets(20));
        stackMenu.getChildren().addAll(stackInput, push, pop, peek, backStack);

        createStack.setOnAction(e -> {
            stack.clear();
            canvas.getChildren().clear();

            layout.setLeft(stackMenu);
            layout.setBottom(null);

            drawStack();
        });
        backStack.setOnAction(e -> {
            stack.clear();
            canvas.getChildren().clear();

            layout.setLeft(null);
            layout.setBottom(menu);
        });
        TextField queueInput = new TextField();
        queueInput.setPromptText("Enter a Value");
        queueInput.setPrefWidth(200);
        queueInput.setPrefHeight(50);

        Button enqueue = new Button("Enqueue");
        enqueue.disableProperty().bind(queueInput.textProperty().isEmpty());
        setButtonSize(enqueue);
        enqueue.setOnAction(e -> {
            try {
                int value = Integer.parseInt(queueInput.getText());

                queue.offer(value);
                queueInput.clear();

                drawQueue();

            } catch (NumberFormatException ex) {
                queueInput.clear();
                queueInput.setPromptText("Enter an Integer");
            }
        });

        Button dequeue = new Button("Dequeue");
        setButtonSize(dequeue);
        dequeue.setOnAction(e -> {

            if (!queue.isEmpty()) {

                int value = queue.poll();

                drawQueue();
                drawResult("Dequeue", value);
            }
        });

        Button peekQueue = new Button("Peek");
        setButtonSize(peekQueue);
        peekQueue.setOnAction(e -> {

            if (!queue.isEmpty()) {

                int value = queue.peek();

                drawQueue();
                drawResult("Peek", value);
            }
        });

        Button backQueue = new Button("Back");
        setButtonSize(backQueue);

        TilePane queueMenu = new TilePane();
        queueMenu.setPrefColumns(5);
        queueMenu.setTileAlignment(Pos.CENTER);
        queueMenu.setAlignment(Pos.CENTER);
        queueMenu.setPrefWidth(Double.MAX_VALUE);
        queueMenu.setHgap(20);
        queueMenu.setVgap(10);
        queueMenu.setPadding(new Insets(20, 0, 30, 0));
        queueMenu.getChildren().addAll(queueInput, enqueue, dequeue, peekQueue, backQueue);

        createQueue.setOnAction(e -> {
            queue.clear();
            canvas.getChildren().clear();

            layout.setBottom(queueMenu);
            drawQueue();

        });

        backQueue.setOnAction(e -> {
            queue.clear();
            canvas.getChildren().clear();

            layout.setBottom(menu);
        });

        return scene;
    }

    private void setButtonSize(Button button) {
        button.setMinSize(250, 75);
        button.setMaxSize(250, 75);
        button.setPrefSize(250, 75);

    }

    private void drawStack() {

        canvas.getChildren().clear();

        double boxWidth = 150;
        double maxStackHeight = 500;
        double normalBoxHeight = 60;
        double minBoxHeight = 25;

        double boxHeight = normalBoxHeight;

        if (!stack.isEmpty()) {
            boxHeight = Math.min(
                    normalBoxHeight,
                    maxStackHeight / stack.size());

            boxHeight = Math.max(boxHeight, minBoxHeight);
        }

        double x = 400;
        double bottomY = 550;
        if (stack.isEmpty()) {
            Text emptyText = new Text("Stack Empty");

            emptyText.setFont(Font.font(30));

            emptyText.setX(400);
            emptyText.setY(300);

            canvas.getChildren().add(emptyText);

            return;
        }
        for (int i = 0; i < stack.size(); i++) {

            double y = bottomY - (i * boxHeight);

            Rectangle rectangle = new Rectangle(
                    x,
                    y,
                    boxWidth,
                    boxHeight);

            rectangle.setFill(Color.LIGHTBLUE);
            rectangle.setStroke(Color.BLACK);

            Text value = new Text(
                    String.valueOf(stack.get(i)));

            value.setFont(Font.font(20));

            // roughly center text inside rectangle
            value.setX(
                    x + boxWidth / 2
                            - value.getLayoutBounds().getWidth() / 2);

            value.setY(
                    y + boxHeight / 2 + 7);

            canvas.getChildren().addAll(
                    rectangle,
                    value);
        }
        // Draw TOP indicator
        if (!stack.isEmpty()) {

            int topIndex = stack.size() - 1;

            double topY = bottomY - (topIndex * boxHeight);

            Text topIndicator = new Text("TOP →");
            topIndicator.setFont(Font.font(20));

            topIndicator.setX(x - 75);
            topIndicator.setY(topY + boxHeight / 2 + 7);

            canvas.getChildren().add(topIndicator);
        }
    }

    private void drawResult(String operation, int value) {

        double x = 650;
        double y = 200;

        Rectangle resultBar = new Rectangle(
                x,
                y,
                200,
                60);

        resultBar.setFill(Color.LIGHTGRAY);
        resultBar.setStroke(Color.BLACK);

        Text resultText = new Text(
                operation + ": " + value);

        resultText.setFont(Font.font(20));

        resultText.setX(
                x + 100
                        - resultText.getLayoutBounds().getWidth() / 2);

        resultText.setY(y + 37);

        canvas.getChildren().addAll(
                resultBar,
                resultText);
    }


    private void drawQueue() {

        canvas.getChildren().clear();

        double normalBoxWidth = 100;
        double minBoxWidth = 40;
        double boxHeight = 100;

        double maxQueueWidth = 800;

        double startX = 100;
        double y = 250;

        // Empty queue
        if (queue.isEmpty()) {

            Text emptyText = new Text("Queue Empty");
            emptyText.setFont(Font.font(30));

            emptyText.setX(450);
            emptyText.setY(300);

            canvas.getChildren().add(emptyText);

            return;
        }

        // Resize boxes as queue gets larger
        double boxWidth = Math.min(
                normalBoxWidth,
                maxQueueWidth / queue.size());

        boxWidth = Math.max(
                boxWidth,
                minBoxWidth);

        int index = 0;

        for (int value : queue) {

            double x = startX + (index * boxWidth);

            Rectangle rectangle = new Rectangle(
                    x,
                    y,
                    boxWidth,
                    boxHeight);

            rectangle.setFill(Color.LIGHTBLUE);
            rectangle.setStroke(Color.BLACK);

            Text valueText = new Text(
                    String.valueOf(value));

            valueText.setFont(
                    Font.font(Math.min(20, boxWidth * 0.3)));

            valueText.setX(
                    x + boxWidth / 2
                            - valueText.getLayoutBounds().getWidth() / 2);

            valueText.setY(
                    y + boxHeight / 2 + 7);

            canvas.getChildren().addAll(
                    rectangle,
                    valueText);

            index++;
        }

        // FRONT
        Text front = new Text("FRONT");
        front.setFont(Font.font(20));
        front.setX(startX);
        front.setY(y - 25);

        // REAR
        double rearX = startX + ((queue.size() - 1) * boxWidth);

        Text rear = new Text("REAR");
        rear.setFont(Font.font(20));
        rear.setX(rearX);
        rear.setY(y + boxHeight + 35);

        canvas.getChildren().addAll(front, rear);
    }
}