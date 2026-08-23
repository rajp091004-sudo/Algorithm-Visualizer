import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ArrayScreen {

    private final Pane canvas = new Pane();
    BorderPane layout = new BorderPane();

    private final List<Integer> values = new ArrayList<>();
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();

    public Scene create(Stage stage, Scene selectScene) {

        Button createArray = new Button("Create Array");
        setButtonSize(createArray);

        createArray.setOnAction(e -> {
            values.clear();
            canvas.getChildren().clear();
            values.addAll(ArrayCreation.display());
            plotValues(values, values.size() + 1, values.size() + 2);
        });

        Button sort = new Button("Sort Array");
        setButtonSize(sort);

        Button randomize = new Button("Randomize Array");
        setButtonSize(randomize);
        randomize.setOnAction(e -> {
            Collections.shuffle(values);
            plotValues(values, values.size() + 1, values.size() + 2);
        });

        Button clearArray = new Button("Clear Canvas");
        setButtonSize(clearArray);

        clearArray.setOnAction(e -> {
            values.clear();
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
                clearArray,
                selectScreenButton);
        TilePane sortMenu = new TilePane();
        sortMenu.setPrefColumns(5);

        setButtonSize(bubbleSort);
        bubbleSort.setOnAction(e -> {
            bubbleAlgo(values);
        });

        setButtonSize(selectionSort);
        selectionSort.setOnAction(e -> {
            selectionAlgo(values);
        });

        setButtonSize(insertionSort);
        insertionSort.setOnAction(e -> {
            insertionAlgo(values);
        });
        setButtonSize(mergeSort);
        mergeSort.setOnAction(e -> {
            mergeAlgo(values);
        });
        setButtonSize(quickSort);
        setButtonSize(heapSort);
        setButtonSize(shellSort);
        setButtonSize(countingSort);
        setButtonSize(radixSort);
        setButtonSize(bucketSort);
        setButtonSize(backButton);

        backButton.setOnAction(e -> {
            layout.setBottom(menu);
        });

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
                backButton);

        layout.setCenter(canvas);
        layout.setBottom(menu);

        Scene scene = new Scene(layout, 1280, 700);
        scene.getStylesheets().add("style.css");
        sort.setOnAction(e -> {
            layout.setBottom(sortMenu);
        });

        return scene;
    }

    private void setButtonSize(Button button) {
        button.setMinSize(250, 75);
        button.setMaxSize(250, 75);
        button.setPrefSize(250, 75);
    }

    private void plotValues(
            List<Integer> values,
            int index1,
            int index2) {

        Map<Integer, Color> highlights = new HashMap<>();

        if (index2 >= 0 && index2 < values.size()) {
            highlights.put(index2, Color.NAVY);
        }

        if (index1 >= 0 && index1 < values.size()) {
            highlights.put(index1, Color.ROYALBLUE);
        }

        plotValues(values, highlights);
    }

    private void plotValues(
            List<Integer> values,
            Map<Integer, Color> highlights) {

        canvas.getChildren().clear();

        if (values.isEmpty()) {
            return;
        }

        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        if (canvasWidth <= 0) {
            canvasWidth = 1280;
        }

        if (canvasHeight <= 0) {
            canvasHeight = 550;
        }

        double horizontalPadding = 80;
        double topPadding = 40;
        double bottomPadding = 50;

        double chartWidth = Math.max(
                100,
                canvasWidth - horizontalPadding * 2);

        double chartHeight = Math.max(
                100,
                canvasHeight - topPadding - bottomPadding);

        double bottomY = canvasHeight - bottomPadding;

        double startX = (canvasWidth - chartWidth) / 2;

        int maximum = 1;

        for (Integer value : values) {
            if (value != null && value > maximum) {
                maximum = value;
            }
        }

        double barWidth = chartWidth / values.size();

        double heightScale = chartHeight / maximum;

        Line baseline = new Line(
                startX,
                bottomY,
                startX + chartWidth,
                bottomY);

        baseline.setStroke(Color.BLACK);
        baseline.setStrokeWidth(2);

        canvas.getChildren().add(baseline);

        for (int i = 0; i < values.size(); i++) {
            Integer value = values.get(i);

            // A null value creates an empty space.
            if (value == null) {
                continue;
            }

            double barHeight = value * heightScale;

            double x = startX + i * barWidth;

            double y = bottomY - barHeight;

            Rectangle bar = new Rectangle(
                    x,
                    y,
                    barWidth,
                    barHeight);

            bar.setFill(
                    highlights.getOrDefault(
                            i,
                            Color.DODGERBLUE));

            bar.setStroke(Color.BLACK);

            Text valueText = new Text(
                    String.valueOf(value));

            valueText.setFont(
                    new Font("Consolas", 16));

            valueText.setX(
                    x + barWidth / 2
                            - valueText
                                    .getLayoutBounds()
                                    .getWidth() / 2);

            valueText.setY(y - 5);

            Text indexText = new Text(
                    String.valueOf(i));

            indexText.setFont(
                    new Font("Consolas", 14));

            indexText.setX(
                    x + barWidth / 2
                            - indexText
                                    .getLayoutBounds()
                                    .getWidth() / 2);

            indexText.setY(bottomY + 20);

            canvas.getChildren().addAll(
                    bar,
                    valueText,
                    indexText);
        }
    }

    private void plotTwoGraphs(
            List<Integer> firstValues,
            List<Integer> secondValues) {

        canvas.getChildren().clear();

        if (firstValues.isEmpty() && secondValues.isEmpty()) {
            return;
        }

        int separatorSize = 2;

        List<Integer> combinedValues = new ArrayList<>();

        combinedValues.addAll(firstValues);

        for (int i = 0; i < separatorSize; i++) {
            combinedValues.add(null);
        }

        combinedValues.addAll(secondValues);

        double chartWidth = 1000;
        double chartHeight = 400;
        double bottomY = 500;

        int maximum = 1;

        for (Integer value : combinedValues) {
            if (value != null && value > maximum) {
                maximum = value;
            }
        }

        double barWidth = chartWidth / combinedValues.size();
        double heightScale = chartHeight / maximum;

        double canvasWidth = canvas.getWidth();

        if (canvasWidth == 0) {
            canvasWidth = 1280;
        }

        double startX = (canvasWidth - chartWidth) / 2;

        Line baseline = new Line(
                startX,
                bottomY,
                startX + chartWidth,
                bottomY);

        baseline.setStroke(Color.BLACK);
        baseline.setStrokeWidth(2);

        canvas.getChildren().add(baseline);

        for (int i = 0; i < combinedValues.size(); i++) {
            Integer value = combinedValues.get(i);

            // Leave an empty space for null values.
            if (value == null) {
                continue;
            }

            double barHeight = value * heightScale;
            double x = startX + i * barWidth;
            double y = bottomY - barHeight;

            Rectangle bar = new Rectangle(
                    x,
                    y,
                    barWidth,
                    barHeight);

            int secondGraphStart = firstValues.size() + separatorSize;

            if (i < firstValues.size()) {
                bar.setFill(Color.web("#3B82F6"));
            } else {
                bar.setFill(Color.web("#1D4ED8"));
            }

            bar.setStroke(Color.BLACK);

            Text valueText = new Text(String.valueOf(value));
            valueText.setFont(new Font("Consolas", 16));

            valueText.setX(
                    x + barWidth / 2
                            - valueText.getLayoutBounds().getWidth() / 2);

            valueText.setY(y - 5);

            int displayedIndex;

            if (i < firstValues.size()) {
                displayedIndex = i;
            } else {
                displayedIndex = i - secondGraphStart;
            }

            Text indexText = new Text(
                    String.valueOf(displayedIndex));

            indexText.setFont(new Font("Consolas", 14));

            indexText.setX(
                    x + barWidth / 2
                            - indexText.getLayoutBounds().getWidth() / 2);

            indexText.setY(bottomY + 20);

            canvas.getChildren().addAll(
                    bar,
                    valueText,
                    indexText);
        }
    }

    private void bubbleAlgo(List<Integer> values) {
        if (values.size() < 2) {
            return;
        }

        Timeline timeline = new Timeline();

        double elapsedTime = 0;
        double delay = 500; // Half a second

        for (int i = 0; i < values.size() - 1; i++) {
            for (int j = 0; j < values.size() - i - 1; j++) {

                final int index1 = j;
                final int index2 = j + 1;

                // Highlight the two values being compared.
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(elapsedTime), e -> {
                            plotValues(values, index1, index2);
                        }));

                elapsedTime += delay;

                // Swap them if they are out of order.
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(elapsedTime), e -> {
                            if (values.get(index1) > values.get(index2)) {
                                Collections.swap(values, index1, index2);
                            }

                            plotValues(values, index1, index2);
                        }));

                elapsedTime += delay;
            }
        }
        Map<Integer, Color> greenBars = new HashMap<>();

        for (int i = 0; i < values.size(); i++) {
            greenBars.put(i, Color.LIGHTGREEN);

            final Map<Integer, Color> currentColors = new HashMap<>(greenBars);

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(elapsedTime), e -> {
                        plotValues(
                                values,
                                currentColors);
                    }));

            elapsedTime += delay / 2;
        }
        timeline.play();
    }

    private void selectionAlgo(List<Integer> values) {
        if (values.size() < 2) {
            return;
        }

        Timeline timeline = new Timeline();

        double elapsedTime = 0;
        double delay = 500;

        for (int i = 0; i < values.size() - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < values.size(); j++) {
                final int currentJ = j;

                if (values.get(j) < values.get(minIndex)) {
                    minIndex = j;
                }

                final int currentMinIndex = minIndex;

                final List<Integer> currentValues = new ArrayList<>(values);

                final Map<Integer, Color> currentColors = new HashMap<>();

                // Keep previously sorted positions green.
                for (int sortedIndex = 0; sortedIndex < i; sortedIndex++) {

                    currentColors.put(
                            sortedIndex,
                            Color.FORESTGREEN);
                }

                // Highlight the current comparison.
                currentColors.put(
                        currentJ,
                        Color.NAVY);

                currentColors.put(
                        currentMinIndex,
                        Color.ROYALBLUE);

                timeline.getKeyFrames().add(
                        new KeyFrame(
                                Duration.millis(elapsedTime),
                                e -> {
                                    plotValues(
                                            currentValues,
                                            currentColors);
                                }));

                elapsedTime += delay;
            }

            if (i != minIndex) {
                Collections.swap(
                        values,
                        i,
                        minIndex);
            }

            final List<Integer> swappedValues = new ArrayList<>(values);

            final Map<Integer, Color> sortedColors = new HashMap<>();

            // Include the newly sorted position.
            for (int sortedIndex = 0; sortedIndex <= i; sortedIndex++) {

                sortedColors.put(
                        sortedIndex,
                        Color.FORESTGREEN);
            }

            timeline.getKeyFrames().add(
                    new KeyFrame(
                            Duration.millis(elapsedTime),
                            e -> {
                                plotValues(
                                        swappedValues,
                                        sortedColors);
                            }));

            elapsedTime += delay;
        }

        final List<Integer> finalValues = new ArrayList<>(values);

        final Map<Integer, Color> allSortedColors = new HashMap<>();

        for (int i = 0; i < values.size(); i++) {
            allSortedColors.put(
                    i,
                    Color.FORESTGREEN);
        }

        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(elapsedTime),
                        e -> {
                            plotValues(
                                    finalValues,
                                    allSortedColors);
                        }));

        timeline.play();
    }

    private void insertionAlgo(List<Integer> values) {
        if (values.size() < 2) {
            return;
        }
        Timeline timeline = new Timeline();
        double elapsedTime = 0;
        double delay = 500;

        for (int i = 1; i < values.size(); i++) {
            final int currentI = i;
            int key = values.get(i);
            int j = i - 1;

            while (j >= 0 && values.get(j) > key) {
                values.set(j + 1, values.get(j));
                final int currentJ = j;
                final int shiftFrom = j;
                final int shiftTo = j + 1;
                final List<Integer> currentValues = new ArrayList<>(values);

                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(elapsedTime), e -> {
                            plotValues(
                                    currentValues,
                                    shiftFrom,
                                    shiftTo);
                        }));

                elapsedTime += delay;
                j = j - 1;
            }
            values.set(j + 1, key);
            final int insertIndex = j + 1;
            final int originalIndex = i;

            final List<Integer> insertedValues = new ArrayList<>(values);

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(elapsedTime), e -> {
                        plotValues(
                                insertedValues,
                                insertIndex,
                                originalIndex);
                    }));

            elapsedTime += delay;
        }
        timeline.play();
    }

    private void mergeAlgo(List<Integer> values) {
        if (values.size() < 2) {
            return;
        }

        Timeline timeline = new Timeline();

        double elapsedTime = 0;
        double delay = 750;

        List<List<Integer>> groups = new ArrayList<>();
        groups.add(new ArrayList<>(values));

        addGroupFrame(timeline, groups, elapsedTime);
        elapsedTime += delay;

        // Split the array into smaller groups.
        while (hasSplittableGroup(groups)) {
            List<List<Integer>> nextGroups = new ArrayList<>();

            for (List<Integer> group : groups) {
                if (group.size() < 2) {
                    nextGroups.add(new ArrayList<>(group));
                    continue;
                }

                int middle = group.size() / 2;

                List<Integer> left = new ArrayList<>(
                        group.subList(0, middle));

                List<Integer> right = new ArrayList<>(
                        group.subList(middle, group.size()));

                nextGroups.add(left);
                nextGroups.add(right);
            }

            groups = nextGroups;

            addGroupFrame(
                    timeline,
                    groups,
                    elapsedTime);

            elapsedTime += delay;
        }

        // Merge neighboring groups back together.
        while (groups.size() > 1) {
            List<List<Integer>> nextGroups = new ArrayList<>();

            for (int i = 0; i < groups.size(); i += 2) {
                if (i + 1 < groups.size()) {
                    List<Integer> merged = mergeTwoGroups(
                            groups.get(i),
                            groups.get(i + 1));

                    nextGroups.add(merged);
                } else {
                    nextGroups.add(
                            new ArrayList<>(groups.get(i)));
                }
            }

            groups = nextGroups;

            addGroupFrame(
                    timeline,
                    groups,
                    elapsedTime);

            elapsedTime += delay;
        }

        final List<Integer> sortedValues = new ArrayList<>(groups.get(0));

        timeline.setOnFinished(e -> {
            values.clear();
            values.addAll(sortedValues);
        });

        timeline.play();
    }

    private boolean hasSplittableGroup(
            List<List<Integer>> groups) {

        for (List<Integer> group : groups) {
            if (group.size() > 1) {
                return true;
            }
        }

        return false;
    }

    private List<Integer> mergeTwoGroups(
            List<Integer> left,
            List<Integer> right) {

        List<Integer> merged = new ArrayList<>();

        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size()
                && rightIndex < right.size()) {

            if (left.get(leftIndex) <= right.get(rightIndex)) {

                merged.add(left.get(leftIndex));
                leftIndex++;
            } else {
                merged.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        while (leftIndex < left.size()) {
            merged.add(left.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < right.size()) {
            merged.add(right.get(rightIndex));
            rightIndex++;
        }

        return merged;
    }

    private void addGroupFrame(
            Timeline timeline,
            List<List<Integer>> groups,
            double elapsedTime) {

        final List<List<Integer>> snapshot = copyGroups(groups);

        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(elapsedTime),
                        e -> plotGroups(snapshot)));
    }

    private List<List<Integer>> copyGroups(
            List<List<Integer>> groups) {

        List<List<Integer>> copy = new ArrayList<>();

        for (List<Integer> group : groups) {
            copy.add(new ArrayList<>(group));
        }

        return copy;
    }

    private void plotGroups(
            List<List<Integer>> groups) {

        canvas.getChildren().clear();

        if (groups.isEmpty()) {
            return;
        }

        double chartWidth = 1000;
        double chartHeight = 400;
        double bottomY = 500;

        int maximum = 1;
        int totalBars = 0;

        for (List<Integer> group : groups) {
            totalBars += group.size();

            for (int value : group) {
                if (value > maximum) {
                    maximum = value;
                }
            }
        }

        double gapSize = 1.0;

        double totalUnits = totalBars
                + (groups.size() - 1) * gapSize;

        double barWidth = chartWidth / totalUnits;
        double gapWidth = barWidth * gapSize;
        double heightScale = chartHeight / maximum;

        double canvasWidth = canvas.getWidth();

        if (canvasWidth == 0) {
            canvasWidth = 1280;
        }

        double currentX = (canvasWidth - chartWidth) / 2;

        Color[] groupColors = {
                Color.web("#1D4ED8"),
                Color.web("#1E40AF"),
                Color.web("#1E3A8A"),
                Color.web("#172554")
        };

        for (int groupIndex = 0; groupIndex < groups.size(); groupIndex++) {

            List<Integer> group = groups.get(groupIndex);

            double groupStartX = currentX;
            double groupWidth = group.size() * barWidth;

            Line baseline = new Line(
                    groupStartX,
                    bottomY,
                    groupStartX + groupWidth,
                    bottomY);

            baseline.setStroke(Color.BLACK);
            baseline.setStrokeWidth(2);

            canvas.getChildren().add(baseline);

            for (int i = 0; i < group.size(); i++) {
                int value = group.get(i);

                double barHeight = value * heightScale;

                double x = groupStartX + i * barWidth;

                double y = bottomY - barHeight;

                Rectangle bar = new Rectangle(
                        x,
                        y,
                        barWidth,
                        barHeight);

                bar.setFill(
                        groupColors[groupIndex % groupColors.length]);

                bar.setStroke(Color.BLACK);

                Text valueText = new Text(
                        String.valueOf(value));

                valueText.setFont(
                        new Font("Consolas", 16));

                valueText.setX(
                        x + barWidth / 2
                                - valueText
                                        .getLayoutBounds()
                                        .getWidth() / 2);

                valueText.setY(y - 5);

                Text indexText = new Text(
                        String.valueOf(i));

                indexText.setFont(
                        new Font("Consolas", 14));

                indexText.setX(
                        x + barWidth / 2
                                - indexText
                                        .getLayoutBounds()
                                        .getWidth() / 2);

                indexText.setY(bottomY + 20);

                canvas.getChildren().addAll(
                        bar,
                        valueText,
                        indexText);
            }

            currentX += groupWidth + gapWidth;
        }
    }
}