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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

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
        quickSort.setOnAction(e -> {
            quickAlgo(values);
        });
        setButtonSize(heapSort);
        heapSort.setOnAction(e -> {
            heapAlgo(values);
        });

        setButtonSize(shellSort);
        shellSort.setOnAction(e -> {
            shellAlgo(values);
        });
        setButtonSize(countingSort);
        countingSort.setOnAction(e -> {
            countingAlgo(values);
        });
        setButtonSize(radixSort);
        radixSort.setOnAction(e -> {
            radixAlgo(values);
        });
        setButtonSize(bucketSort);
        bucketSort.setOnAction(e -> {
            bucketAlgo(values);
        });

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

    private void quickAlgo(List<Integer> values) {
        if (values.size() < 2) {
            return;
        }

        Timeline timeline = new Timeline();

        double delay = 500;
        double[] elapsedTime = { 0 };

        Set<Integer> sortedIndices = new HashSet<>();

        quickSortFrames(
                values,
                0,
                values.size() - 1,
                timeline,
                elapsedTime,
                delay,
                sortedIndices);

        timeline.play();
    }

    private void quickSortFrames(
            List<Integer> values,
            int low,
            int high,
            Timeline timeline,
            double[] elapsedTime,
            double delay,
            Set<Integer> sortedIndices) {

        if (low > high) {
            return;
        }

        // A section containing one value is already sorted.
        if (low == high) {
            sortedIndices.add(low);

            addQuickFrame(
                    values,
                    timeline,
                    elapsedTime,
                    delay,
                    sortedIndices,
                    -1,
                    -1,
                    -1);

            return;
        }

        int pivotIndex = partitionFrames(
                values,
                low,
                high,
                timeline,
                elapsedTime,
                delay,
                sortedIndices);

        quickSortFrames(
                values,
                low,
                pivotIndex - 1,
                timeline,
                elapsedTime,
                delay,
                sortedIndices);

        quickSortFrames(
                values,
                pivotIndex + 1,
                high,
                timeline,
                elapsedTime,
                delay,
                sortedIndices);
    }

    private int partitionFrames(
            List<Integer> values,
            int low,
            int high,
            Timeline timeline,
            double[] elapsedTime,
            double delay,
            Set<Integer> sortedIndices) {

        // Select a random pivot.
        int randomPivotIndex = ThreadLocalRandom.current()
                .nextInt(low, high + 1);

        // Display the randomly selected pivot in bright green.
        addQuickFrame(
                values,
                timeline,
                elapsedTime,
                delay,
                sortedIndices,
                randomPivotIndex,
                -1,
                -1);

        /*
         * Move the pivot to the end of the current section.
         * This makes the partitioning process easier.
         */
        Collections.swap(
                values,
                randomPivotIndex,
                high);

        int pivotValue = values.get(high);

        // Display the pivot in its temporary position.
        addQuickFrame(
                values,
                timeline,
                elapsedTime,
                delay,
                sortedIndices,
                high,
                randomPivotIndex,
                -1);

        int smallerIndex = low - 1;

        for (int currentIndex = low; currentIndex < high; currentIndex++) {

            /*
             * Show the value being compared with the pivot.
             *
             * Pivot: bright green
             * Current value: navy
             * Smaller-value boundary: royal blue
             */
            addQuickFrame(
                    values,
                    timeline,
                    elapsedTime,
                    delay,
                    sortedIndices,
                    high,
                    currentIndex,
                    smallerIndex);

            if (values.get(currentIndex) <= pivotValue) {
                smallerIndex++;

                if (smallerIndex != currentIndex) {
                    Collections.swap(
                            values,
                            smallerIndex,
                            currentIndex);
                }

                // Display the completed swap.
                addQuickFrame(
                        values,
                        timeline,
                        elapsedTime,
                        delay,
                        sortedIndices,
                        high,
                        currentIndex,
                        smallerIndex);
            }
        }

        int finalPivotIndex = smallerIndex + 1;

        Collections.swap(
                values,
                finalPivotIndex,
                high);

        // The pivot is now permanently sorted.
        sortedIndices.add(finalPivotIndex);

        addQuickFrame(
                values,
                timeline,
                elapsedTime,
                delay,
                sortedIndices,
                -1,
                -1,
                -1);

        return finalPivotIndex;
    }

    private void addQuickFrame(
            List<Integer> values,
            Timeline timeline,
            double[] elapsedTime,
            double delay,
            Set<Integer> sortedIndices,
            int pivotIndex,
            int comparisonIndex,
            int boundaryIndex) {

        final List<Integer> currentValues = new ArrayList<>(values);

        final Map<Integer, Color> currentColors = new HashMap<>();

        // Permanently sorted positions.
        for (int sortedIndex : sortedIndices) {
            currentColors.put(
                    sortedIndex,
                    Color.FORESTGREEN);
        }

        // Current value being compared.
        if (comparisonIndex >= 0) {
            currentColors.put(
                    comparisonIndex,
                    Color.NAVY);
        }

        // Boundary between smaller and larger values.
        if (boundaryIndex >= 0) {
            currentColors.put(
                    boundaryIndex,
                    Color.ROYALBLUE);
        }

        // The active pivot overrides the other colors.
        if (pivotIndex >= 0) {
            currentColors.put(
                    pivotIndex,
                    Color.LIMEGREEN);
        }

        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(elapsedTime[0]),
                        e -> {
                            plotValues(
                                    currentValues,
                                    currentColors);
                        }));

        elapsedTime[0] += delay;
    }

    private void shellAlgo(List<Integer> values) {
        if (values.size() < 2) {
            return;
        }

        Timeline timeline = new Timeline();

        double elapsedTime = 0;
        double delay = 500;

        // Used to calculate the sorting steps before animating them.
        List<Integer> workingValues = new ArrayList<>(values);

        for (int gap = workingValues.size() / 2; gap > 0; gap /= 2) {

            for (int i = gap; i < workingValues.size(); i++) {
                int temp = workingValues.get(i);
                int j = i;

                while (j >= gap) {
                    int leftIndex = j - gap;
                    int rightIndex = j;

                    List<Integer> comparisonState = new ArrayList<>(workingValues);

                    Map<Integer, Color> comparisonHighlights = new HashMap<>();

                    comparisonHighlights.put(leftIndex, Color.RED);
                    comparisonHighlights.put(rightIndex, Color.ORANGE);

                    double comparisonTime = elapsedTime;

                    timeline.getKeyFrames().add(
                            new KeyFrame(
                                    Duration.millis(comparisonTime),
                                    e -> {
                                        values.clear();
                                        values.addAll(comparisonState);

                                        plotValues(
                                                values,
                                                comparisonHighlights);
                                    }));

                    elapsedTime += delay;

                    if (workingValues.get(leftIndex) <= temp) {
                        break;
                    }

                    // Shift the larger value to the right.
                    workingValues.set(
                            rightIndex,
                            workingValues.get(leftIndex));

                    List<Integer> shiftedState = new ArrayList<>(workingValues);

                    Map<Integer, Color> shiftedHighlights = new HashMap<>();

                    shiftedHighlights.put(leftIndex, Color.RED);
                    shiftedHighlights.put(rightIndex, Color.ORANGE);

                    double shiftTime = elapsedTime;

                    timeline.getKeyFrames().add(
                            new KeyFrame(
                                    Duration.millis(shiftTime),
                                    e -> {
                                        values.clear();
                                        values.addAll(shiftedState);

                                        plotValues(
                                                values,
                                                shiftedHighlights);
                                    }));

                    elapsedTime += delay;
                    j -= gap;
                }

                // Insert the selected value into its new position.
                workingValues.set(j, temp);

                List<Integer> insertedState = new ArrayList<>(workingValues);

                Map<Integer, Color> insertedHighlights = new HashMap<>();

                /*
                 * During the final gap pass, the section from
                 * index 0 through i is sorted.
                 */
                if (gap == 1) {
                    for (int sortedIndex = 0; sortedIndex <= i; sortedIndex++) {

                        insertedHighlights.put(
                                sortedIndex,
                                Color.GREEN);
                    }
                } else {
                    insertedHighlights.put(j, Color.PURPLE);
                }

                double insertionTime = elapsedTime;

                timeline.getKeyFrames().add(
                        new KeyFrame(
                                Duration.millis(insertionTime),
                                e -> {
                                    values.clear();
                                    values.addAll(insertedState);

                                    plotValues(
                                            values,
                                            insertedHighlights);
                                }));

                elapsedTime += delay;
            }
        }

        timeline.play();
    }

    private void countingAlgo(List<Integer> values) {
        if (values.size() < 2) {
            return;
        }

        // Counting-array indices cannot represent negative numbers.
        for (int value : values) {
            if (value < 0) {
                throw new IllegalArgumentException(
                        "Counting sort requires non-negative integers.");
            }
        }

        Timeline timeline = new Timeline();

        double elapsedTime = 0;
        double delay = 500;

        int maximum = Collections.max(values);

        // Create [0, 0, 0, ...] from index 0 through maximum.
        List<Integer> counts = new ArrayList<>();

        for (int i = 0; i <= maximum; i++) {
            counts.add(0);
        }

        /*
         * Display the original array and the empty
         * counting array before counting begins.
         */
        final List<Integer> initialValues = new ArrayList<>(values);

        final List<Integer> initialCounts = new ArrayList<>(counts);

        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(elapsedTime),
                        e -> plotTwoGraphs(
                                initialValues,
                                initialCounts)));

        elapsedTime += delay;

        // Count each number in the original array.
        for (int value : values) {
            counts.set(
                    value,
                    counts.get(value) + 1);

            final List<Integer> currentValues = new ArrayList<>(values);

            final List<Integer> currentCounts = new ArrayList<>(counts);

            timeline.getKeyFrames().add(
                    new KeyFrame(
                            Duration.millis(elapsedTime),
                            e -> plotTwoGraphs(
                                    currentValues,
                                    currentCounts)));

            elapsedTime += delay;
        }

        /*
         * Reconstruct the sorted array.
         * Start with a copy so each frame can show
         * the sorted portion replacing the old values.
         */
        List<Integer> reconstructedValues = new ArrayList<>(values);

        int writeIndex = 0;

        for (int value = 0; value < counts.size(); value++) {

            int appearances = counts.get(value);

            for (int amountInserted = 0; amountInserted < appearances; amountInserted++) {

                reconstructedValues.set(
                        writeIndex,
                        value);

                final List<Integer> currentValues = new ArrayList<>(
                        reconstructedValues);

                final List<Integer> currentCounts = new ArrayList<>(counts);

                timeline.getKeyFrames().add(
                        new KeyFrame(
                                Duration.millis(elapsedTime),
                                e -> plotTwoGraphs(
                                        currentValues,
                                        currentCounts)));

                elapsedTime += delay;
                writeIndex++;
            }
        }

        final List<Integer> sortedValues = new ArrayList<>(
                reconstructedValues);

        timeline.setOnFinished(e -> {
            values.clear();
            values.addAll(sortedValues);
        });

        timeline.play();
    }

    private void radixAlgo(List<Integer> values) {
        if (values.size() < 2) {
            return;
        }

        // This version only supports non-negative integers.
        for (int value : values) {
            if (value < 0) {
                throw new IllegalArgumentException(
                        "Radix sort requires non-negative integers.");
            }
        }

        Timeline timeline = new Timeline();

        double elapsedTime = 0;
        double delay = 500;

        List<Integer> workingValues = new ArrayList<>(values);

        int maximum = Collections.max(workingValues);

        /*
         * place = 1 → ones digit
         * place = 10 → tens digit
         * place = 100 → hundreds digit
         */
        for (long place = 1; maximum / place > 0; place *= 10) {

            int currentPlace = (int) place;

            int[] counts = new int[10];

            // Count each digit from 0 through 9.
            for (int value : workingValues) {
                int digit = (value / currentPlace) % 10;

                counts[digit]++;
            }

            // Convert counts into output positions.
            for (int digit = 1; digit < counts.length; digit++) {

                counts[digit] += counts[digit - 1];
            }

            List<Integer> outputValues = new ArrayList<>(
                    Collections.nCopies(
                            workingValues.size(),
                            null));

            /*
             * Work backward to preserve the order
             * of values with matching digits.
             */
            for (int i = workingValues.size() - 1; i >= 0; i--) {

                int value = workingValues.get(i);

                int digit = (value / currentPlace) % 10;

                int outputIndex = counts[digit] - 1;

                outputValues.set(
                        outputIndex,
                        value);

                counts[digit]--;

                final List<Integer> currentInput = new ArrayList<>(
                        workingValues);

                final List<Integer> currentOutput = new ArrayList<>(
                        outputValues);

                final int displayedPlace = currentPlace;

                timeline.getKeyFrames().add(
                        new KeyFrame(
                                Duration.millis(elapsedTime),
                                e -> plotRadixGraphs(
                                        currentInput,
                                        currentOutput,
                                        displayedPlace)));

                elapsedTime += delay;
            }

            // The output becomes the input for the next digit.
            workingValues = new ArrayList<>(outputValues);
        }

        final List<Integer> sortedValues = new ArrayList<>(workingValues);

        final Map<Integer, Color> finishedColors = new HashMap<>();

        for (int i = 0; i < sortedValues.size(); i++) {
            finishedColors.put(
                    i,
                    Color.FORESTGREEN);
        }

        // Display the completed array in green.
        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(elapsedTime),
                        e -> plotValues(
                                sortedValues,
                                finishedColors)));

        timeline.setOnFinished(e -> {
            values.clear();
            values.addAll(sortedValues);
        });

        timeline.play();
    }

    private void bucketAlgo(List<Integer> values) {
        if (values.size() < 2) {
            return;
        }

        // Your current bar graph expects non-negative values.
        for (int value : values) {
            if (value < 0) {
                throw new IllegalArgumentException(
                        "Bucket sort requires non-negative integers.");
            }
        }

        Timeline timeline = new Timeline();

        double elapsedTime = 0;
        double delay = 500;

        int minimum = Collections.min(values);
        int maximum = Collections.max(values);

        /*
         * Use approximately the square root of the
         * number of values as the number of buckets.
         */
        int bucketCount = Math.max(
                1,
                (int) Math.ceil(
                        Math.sqrt(values.size())));

        double bucketWidth = Math.max(
                1.0,
                ((double) maximum - minimum + 1)
                        / bucketCount);

        List<List<Integer>> buckets = new ArrayList<>();

        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // Display the original array first.
        final List<Integer> initialValues = new ArrayList<>(values);

        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(elapsedTime),
                        e -> plotValues(
                                initialValues,
                                Collections.emptyMap())));

        elapsedTime += delay;

        // Place each value into its appropriate bucket.
        for (int value : values) {
            int bucketIndex = (int) ((value - minimum) / bucketWidth);

            // Protect against rounding at the maximum value.
            bucketIndex = Math.min(
                    bucketIndex,
                    bucketCount - 1);

            buckets.get(bucketIndex).add(value);

            addBucketFrame(
                    timeline,
                    buckets,
                    elapsedTime);

            elapsedTime += delay;
        }

        // Sort each individual bucket.
        for (List<Integer> bucket : buckets) {
            if (bucket.size() > 1) {
                Collections.sort(bucket);

                addBucketFrame(
                        timeline,
                        buckets,
                        elapsedTime);

                elapsedTime += delay;
            }
        }

        /*
         * Reconstruct the array by visiting the
         * buckets from left to right.
         */
        List<Integer> reconstructedValues = new ArrayList<>(values);

        int writeIndex = 0;

        for (List<Integer> bucket : buckets) {
            for (int value : bucket) {
                reconstructedValues.set(
                        writeIndex,
                        value);

                final List<Integer> currentValues = new ArrayList<>(
                        reconstructedValues);

                final Map<Integer, Color> sortedColors = new HashMap<>();

                // Every completed position stays green.
                for (int i = 0; i <= writeIndex; i++) {
                    sortedColors.put(
                            i,
                            Color.FORESTGREEN);
                }

                timeline.getKeyFrames().add(
                        new KeyFrame(
                                Duration.millis(elapsedTime),
                                e -> plotValues(
                                        currentValues,
                                        sortedColors)));

                elapsedTime += delay;
                writeIndex++;
            }
        }

        final List<Integer> sortedValues = new ArrayList<>(
                reconstructedValues);

        timeline.setOnFinished(e -> {
            values.clear();
            values.addAll(sortedValues);
        });

        timeline.play();
    }
private void heapAlgo(List<Integer> values) {
    if (values.size() < 2) {
        return;
    }

    Timeline timeline = new Timeline();

    double delay = 500;
    double[] elapsedTime = {0};

    List<Integer> workingValues =
            new ArrayList<>(values);

    Set<Integer> sortedIndices =
            new HashSet<>();

    int size = workingValues.size();

    // Build the initial max heap.
    for (int i = size / 2 - 1; i >= 0; i--) {
        heapifyFrames(
            workingValues,
            size,
            i,
            timeline,
            elapsedTime,
            delay,
            sortedIndices
        );
    }

    /*
     * Move the largest value at index 0
     * to the end of the unsorted section.
     */
    for (int end = size - 1; end > 0; end--) {
        addHeapFrame(
            workingValues,
            timeline,
            elapsedTime,
            delay,
            sortedIndices,
            0,
            end
        );

        Collections.swap(
            workingValues,
            0,
            end
        );

        // The value at end is now permanently sorted.
        sortedIndices.add(end);

        addHeapFrame(
            workingValues,
            timeline,
            elapsedTime,
            delay,
            sortedIndices,
            0,
            -1
        );

        // Repair the remaining heap.
        heapifyFrames(
            workingValues,
            end,
            0,
            timeline,
            elapsedTime,
            delay,
            sortedIndices
        );
    }

    // The final remaining value is also sorted.
    sortedIndices.add(0);

    addHeapFrame(
        workingValues,
        timeline,
        elapsedTime,
        delay,
        sortedIndices,
        -1,
        -1
    );

    final List<Integer> sortedValues =
            new ArrayList<>(workingValues);

    timeline.setOnFinished(e -> {
        values.clear();
        values.addAll(sortedValues);
    });

    timeline.play();
}
private void heapifyFrames(
        List<Integer> values,
        int heapSize,
        int rootIndex,
        Timeline timeline,
        double[] elapsedTime,
        double delay,
        Set<Integer> sortedIndices) {

    int currentRoot = rootIndex;

    while (true) {
        int largestIndex = currentRoot;

        int leftChild =
                2 * currentRoot + 1;

        int rightChild =
                2 * currentRoot + 2;

        // Compare the root with its left child.
        if (leftChild < heapSize) {
            addHeapFrame(
                values,
                timeline,
                elapsedTime,
                delay,
                sortedIndices,
                largestIndex,
                leftChild
            );

            if (values.get(leftChild)
                    > values.get(largestIndex)) {

                largestIndex = leftChild;
            }
        }

        // Compare the current largest with the right child.
        if (rightChild < heapSize) {
            addHeapFrame(
                values,
                timeline,
                elapsedTime,
                delay,
                sortedIndices,
                largestIndex,
                rightChild
            );

            if (values.get(rightChild)
                    > values.get(largestIndex)) {

                largestIndex = rightChild;
            }
        }

        // The root is already larger than both children.
        if (largestIndex == currentRoot) {
            break;
        }

        Collections.swap(
            values,
            currentRoot,
            largestIndex
        );

        // Display the completed swap.
        addHeapFrame(
            values,
            timeline,
            elapsedTime,
            delay,
            sortedIndices,
            currentRoot,
            largestIndex
        );

        /*
         * Continue repairing the heap from
         * the position where the root moved.
         */
        currentRoot = largestIndex;
    }
}
private void addHeapFrame(
        List<Integer> values,
        Timeline timeline,
        double[] elapsedTime,
        double delay,
        Set<Integer> sortedIndices,
        int firstIndex,
        int secondIndex) {

    final List<Integer> currentValues =
            new ArrayList<>(values);

    final Map<Integer, Color> currentColors =
            new HashMap<>();

    if (firstIndex >= 0) {
        currentColors.put(
            firstIndex,
            Color.ROYALBLUE
        );
    }

    if (secondIndex >= 0) {
        currentColors.put(
            secondIndex,
            Color.NAVY
        );
    }

    // Sorted positions override comparison colors.
    for (int sortedIndex : sortedIndices) {
        currentColors.put(
            sortedIndex,
            Color.FORESTGREEN
        );
    }

    timeline.getKeyFrames().add(
        new KeyFrame(
            Duration.millis(elapsedTime[0]),
            e -> plotValues(
                currentValues,
                currentColors
            )
        )
    );

    elapsedTime[0] += delay;
}
    private void addBucketFrame(
            Timeline timeline,
            List<List<Integer>> buckets,
            double elapsedTime) {

        final List<List<Integer>> currentBuckets = new ArrayList<>();

        // Make a deep copy so later changes don't affect this frame.
        for (List<Integer> bucket : buckets) {
            currentBuckets.add(
                    new ArrayList<>(bucket));
        }

        timeline.getKeyFrames().add(
                new KeyFrame(
                        Duration.millis(elapsedTime),
                        e -> plotGroups(currentBuckets)));
    }

    private void plotRadixGraphs(
            List<Integer> inputValues,
            List<Integer> outputValues,
            int place) {

        plotTwoGraphs(
                inputValues,
                outputValues);

        String digitName;

        if (place == 1) {
            digitName = "Ones";
        } else if (place == 10) {
            digitName = "Tens";
        } else if (place == 100) {
            digitName = "Hundreds";
        } else if (place == 1000) {
            digitName = "Thousands";
        } else {
            digitName = "Place " + place;
        }

        Text placeText = new Text(
                "Sorting by: " + digitName);

        placeText.setFont(
                new Font("Consolas", 20));

        placeText.setFill(Color.BLACK);
        placeText.setX(40);
        placeText.setY(40);

        canvas.getChildren().add(placeText);
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