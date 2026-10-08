package HashTable;

import java.util.Arrays;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HashScreen {

    /*
     * =============================================
     * HASH TABLE
     * =============================================
     */

    private Integer[][] hashTable;

    /*
     * The table begins with 5 collision slots.
     * More columns are automatically added
     * when necessary.
     */
    private static final int STARTING_COLLISION_SLOTS =
            5;

    /*
     * =============================================
     * VISUAL CELLS
     * =============================================
     *
     * Stores the Rectangle that represents
     * each position in the hash table.
     */

    private Rectangle[][] cells;

    /*
     * =============================================
     * VISUAL COMPONENTS
     * =============================================
     */

    private final Pane canvas =
            new Pane();

    private final Text statusText =
            new Text();

    /*
     * =============================================
     * MAIN LAYOUT
     * =============================================
     */

    private final BorderPane layout =
            new BorderPane();

    /*
     * =============================================
     * ANIMATION
     * =============================================
     */

    private static final double ANIMATION_DELAY =
            700;

    private boolean runningAlgorithm =
            false;

    /*
     * =============================================
     * CREATE SCREEN
     * =============================================
     */

    public Scene create(
            Stage stage,
            Scene selectScene) {

        /*
         * =========================================
         * SCENE
         * =========================================
         */

        Scene scene =
                new Scene(
                        layout,
                        1280,
                        700
                );

        scene.getStylesheets().add(
                "style.css"
        );

        /*
         * =========================================
         * CREATE TABLE BUTTON
         * =========================================
         */

        Button createTable =
                new Button(
                        "Create Table"
                );

        setButtonSize(
                createTable
        );

        createTable.setOnAction(e -> {

            if (runningAlgorithm) {

                return;
            }

            showCreateTableWindow();
        });

        /*
         * =========================================
         * INSERT BUTTON
         * =========================================
         */

        Button insert =
                new Button(
                        "Insert"
                );

        setButtonSize(
                insert
        );

        insert.setOnAction(e -> {

            if (runningAlgorithm) {

                return;
            }

            if (hashTable == null) {

                statusText.setText(
                        "Create a hash table first."
                );

                return;
            }

            showInsertWindow();
        });

        /*
         * =========================================
         * SEARCH BUTTON
         * =========================================
         */

        Button search =
                new Button(
                        "Search"
                );

        setButtonSize(
                search
        );

        search.setOnAction(e -> {

            if (runningAlgorithm) {

                return;
            }

            if (hashTable == null) {

                statusText.setText(
                        "Create a hash table first."
                );

                return;
            }

            showSearchWindow();
        });

        /*
         * =========================================
         * DELETE BUTTON
         * =========================================
         */

        Button delete =
                new Button(
                        "Delete"
                );

        setButtonSize(
                delete
        );

        delete.setOnAction(e -> {

            if (runningAlgorithm) {

                return;
            }

            if (hashTable == null) {

                statusText.setText(
                        "Create a hash table first."
                );

                return;
            }

            showDeleteWindow();
        });

        /*
         * =========================================
         * CLEAR BUTTON
         * =========================================
         */

        Button clear =
                new Button(
                        "Clear"
                );

        setButtonSize(
                clear
        );

        clear.setOnAction(e -> {

            if (runningAlgorithm) {

                return;
            }

            hashTable =
                    null;

            cells =
                    null;

            canvas
                    .getChildren()
                    .clear();

            statusText.setText(
                    ""
            );
        });

        /*
         * =========================================
         * SELECT SCREEN BUTTON
         * =========================================
         */

        Button selectScreen =
                new Button(
                        "Select Screen"
                );

        setButtonSize(
                selectScreen
        );

        selectScreen.setOnAction(e -> {

            if (runningAlgorithm) {

                return;
            }

            resetCellColors();

            statusText.setText(
                    ""
            );

            stage.setScene(
                    selectScene
            );
        });

        /*
         * =========================================
         * MENU
         * =========================================
         */

        TilePane menu =
                new TilePane();

        menu.setPrefColumns(
                5
        );

        menu.setTileAlignment(
                Pos.CENTER
        );

        menu.setAlignment(
                Pos.CENTER
        );

        menu.setPrefWidth(
                Double.MAX_VALUE
        );

        menu.setHgap(
                20
        );

        menu.setVgap(
                10
        );

        menu.setPadding(
                new Insets(
                        20,
                        0,
                        30,
                        0
                )
        );

        menu.getChildren().addAll(
                createTable,
                insert,
                search,
                delete,
                clear,
                selectScreen
        );

        /*
         * =========================================
         * STATUS AREA
         * =========================================
         */

        statusText.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        HBox statusArea =
                new HBox();

        statusArea.setAlignment(
                Pos.CENTER
        );

        statusArea.setMinHeight(
                50
        );

        statusArea.setPadding(
                new Insets(
                        10
                )
        );

        statusArea.getChildren().add(
                statusText
        );

        /*
         * =========================================
         * CANVAS
         * =========================================
         */

        canvas.setPrefWidth(
                1280
        );

        canvas.setPrefHeight(
                500
        );

        canvas.setMaxSize(
                Double.MAX_VALUE,
                Double.MAX_VALUE
        );

        /*
         * =========================================
         * TABLE AREA
         * =========================================
         */

        VBox tableArea =
                new VBox();

        tableArea.setAlignment(
                Pos.TOP_CENTER
        );

        VBox.setVgrow(
                canvas,
                Priority.ALWAYS
        );

        tableArea.getChildren().addAll(
                statusArea,
                canvas
        );

        /*
         * =========================================
         * MAIN LAYOUT
         * =========================================
         */

        layout.setCenter(
                tableArea
        );

        layout.setBottom(
                menu
        );

        return scene;
    }

    /*
     * =============================================
     * CREATE TABLE WINDOW
     * =============================================
     */

    private void showCreateTableWindow() {

        Stage window =
                new Stage();

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        window.setTitle(
                "Create Hash Table"
        );

        Text title =
                new Text(
                        "Number of Buckets"
                );

        title.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        TextField input =
                createInputField();

        input.setPromptText(
                "Example: 10"
        );

        Text error =
                new Text();

        error.setFont(
                Font.font(
                        "Arial",
                        14
                )
        );

        Button create =
                new Button(
                        "Create"
                );

        create.setPrefSize(
                120,
                45
        );

        /*
         * =========================================
         * CREATE ACTION
         * =========================================
         */

        create.setOnAction(e -> {

            try {

                int size =
                        Integer.parseInt(
                                input
                                        .getText()
                                        .trim()
                        );

                /*
                 * =================================
                 * SIZE CHECK
                 * =================================
                 */

                if (size <= 0) {

                    error.setText(
                            "Size must be greater than 0."
                    );

                    input.clear();

                    input.requestFocus();

                    return;
                }

                if (size > 10) {

                    error.setText(
                            "Maximum table size is 10."
                    );

                    input.clear();

                    input.requestFocus();

                    return;
                }

                /*
                 * =================================
                 * CREATE TABLE
                 * =================================
                 */

                hashTable =
                        new Integer[
                                size
                        ][STARTING_COLLISION_SLOTS];

                drawTable();

                statusText.setText(
                        "Hash table created with "
                                + size
                                + " buckets."
                );

                window.close();

            }
            catch (NumberFormatException exception) {

                error.setText(
                        "Enter a valid integer."
                );

                input.clear();

                input.requestFocus();
            }
        });

        input.setOnAction(e -> {

            create.fire();

        });

        VBox windowLayout =
                createWindowLayout(
                        title,
                        input,
                        create,
                        error
                );

        Scene scene =
                new Scene(
                        windowLayout,
                        400,
                        300
                );

        scene.getStylesheets().add(
                "style.css"
        );

        window.setScene(
                scene
        );

        window.showAndWait();
    }

    /*
     * =============================================
     * INSERT WINDOW
     * =============================================
     */

    private void showInsertWindow() {

        Stage window =
                new Stage();

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        window.setTitle(
                "Insert"
        );

        Text title =
                new Text(
                        "Enter Value"
                );

        title.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        TextField input =
                createInputField();

        Text error =
                new Text();

        error.setFont(
                Font.font(
                        "Arial",
                        14
                )
        );

        Button insert =
                new Button(
                        "Insert"
                );

        insert.setPrefSize(
                120,
                45
        );

        /*
         * =========================================
         * INSERT ACTION
         * =========================================
         */

        insert.setOnAction(e -> {

            try {

                int value =
                        Integer.parseInt(
                                input
                                        .getText()
                                        .trim()
                        );

                window.close();

                insertValue(
                        value
                );

            }
            catch (NumberFormatException exception) {

                error.setText(
                        "Enter a valid integer."
                );

                input.clear();

                input.requestFocus();
            }
        });

        input.setOnAction(e -> {

            insert.fire();

        });

        VBox windowLayout =
                createWindowLayout(
                        title,
                        input,
                        insert,
                        error
                );

        Scene scene =
                new Scene(
                        windowLayout,
                        400,
                        300
                );

        scene.getStylesheets().add(
                "style.css"
        );

        window.setScene(
                scene
        );

        window.showAndWait();
    }

    /*
     * =============================================
     * INSERT VALUE
     * =============================================
     */

    private void insertValue(
            int value) {

        /*
         * =========================================
         * HASH FUNCTION
         * =========================================
         */

        int row =
                Math.floorMod(
                        value,
                        hashTable.length
                );

        /*
         * Reset previous Search/Delete colors.
         */
        resetCellColors();

        /*
         * =========================================
         * CHECK FOR DUPLICATE
         * =========================================
         */

        for (int column = 0;
             column < hashTable[row].length;
             column++) {

            if (hashTable[row][column] != null
                    && hashTable[row][column] == value) {

                statusText.setText(
                        value
                                + " already exists in bucket "
                                + row
                                + "."
                );

                return;
            }
        }

        /*
         * =========================================
         * COUNT VALUES
         * =========================================
         */

        int valueCount =
                0;

        for (int column = 0;
             column < hashTable[row].length;
             column++) {

            if (hashTable[row][column] != null) {

                valueCount++;
            }
        }

        /*
         * =========================================
         * EXPAND TABLE
         * =========================================
         *
         * If this bucket has filled every
         * available collision slot, add another
         * column to the entire 2D array.
         */

        if (valueCount
                == hashTable[row].length) {

            expandTable();
        }

        /*
         * =========================================
         * INSERT VALUE
         * =========================================
         */

        hashTable[row][valueCount] =
                value;

        /*
         * =========================================
         * SORT BUCKET
         * =========================================
         */

        sortBucket(
                row
        );

        /*
         * Redraw because sorting may have
         * moved the inserted value.
         */
        drawTable();

        /*
         * =========================================
         * FIND INSERTED VALUE
         * =========================================
         */

        int finalColumn =
                -1;

        for (int column = 0;
             column < hashTable[row].length;
             column++) {

            if (hashTable[row][column] != null
                    && hashTable[row][column] == value) {

                finalColumn =
                        column;

                break;
            }
        }

        /*
         * =========================================
         * HIGHLIGHT INSERTED VALUE
         * =========================================
         */

        if (finalColumn != -1) {

            cells[row][finalColumn]
                    .setFill(
                            Color.LIGHTGREEN
                    );
        }

        /*
         * =========================================
         * STATUS
         * =========================================
         */

        statusText.setText(
                "floorMod("
                        + value
                        + ", "
                        + hashTable.length
                        + ") = "
                        + row
                        + " | Inserted and sorted bucket "
                        + row
                        + "."
        );
    }

    /*
     * =============================================
     * EXPAND TABLE
     * =============================================
     */

    private void expandTable() {

        /*
         * =========================================
         * OLD SIZE
         * =========================================
         */

        int rows =
                hashTable.length;

        int oldColumns =
                hashTable[0].length;

        int newColumns =
                oldColumns + 1;

        /*
         * =========================================
         * NEW TABLE
         * =========================================
         */

        Integer[][] newTable =
                new Integer[
                        rows
                ][newColumns];

        /*
         * =========================================
         * COPY VALUES
         * =========================================
         */

        for (int row = 0;
             row < rows;
             row++) {

            for (int column = 0;
                 column < oldColumns;
                 column++) {

                newTable[row][column] =
                        hashTable[row][column];
            }
        }

        /*
         * Replace old table.
         */
        hashTable =
                newTable;
    }

    /*
     * =============================================
     * SORT BUCKET
     * =============================================
     */

    private void sortBucket(
            int row) {

        /*
         * =========================================
         * COUNT VALUES
         * =========================================
         */

        int count =
                0;

        for (int column = 0;
             column < hashTable[row].length;
             column++) {

            if (hashTable[row][column] != null) {

                count++;
            }
        }

        /*
         * =========================================
         * COPY VALUES
         * =========================================
         */

        Integer[] values =
                new Integer[
                        count
                ];

        int index =
                0;

        for (int column = 0;
             column < hashTable[row].length;
             column++) {

            if (hashTable[row][column] != null) {

                values[index] =
                        hashTable[row][column];

                index++;
            }
        }

        /*
         * =========================================
         * SORT VALUES
         * =========================================
         */

        Arrays.sort(
                values
        );

        /*
         * =========================================
         * CLEAR BUCKET
         * =========================================
         */

        for (int column = 0;
             column < hashTable[row].length;
             column++) {

            hashTable[row][column] =
                    null;
        }

        /*
         * =========================================
         * COPY SORTED VALUES BACK
         * =========================================
         */

        for (int column = 0;
             column < values.length;
             column++) {

            hashTable[row][column] =
                    values[column];
        }
    }

    /*
     * =============================================
     * SEARCH WINDOW
     * =============================================
     */

    private void showSearchWindow() {

        Stage window =
                new Stage();

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        window.setTitle(
                "Search"
        );

        Text title =
                new Text(
                        "Enter Value"
                );

        title.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        TextField input =
                createInputField();

        Text error =
                new Text();

        error.setFont(
                Font.font(
                        "Arial",
                        14
                )
        );

        Button search =
                new Button(
                        "Search"
                );

        search.setPrefSize(
                120,
                45
        );

        search.setOnAction(e -> {

            try {

                int value =
                        Integer.parseInt(
                                input
                                        .getText()
                                        .trim()
                        );

                window.close();

                searchValue(
                        value
                );

            }
            catch (NumberFormatException exception) {

                error.setText(
                        "Enter a valid integer."
                );

                input.clear();

                input.requestFocus();
            }
        });

        input.setOnAction(e -> {

            search.fire();

        });

        VBox windowLayout =
                createWindowLayout(
                        title,
                        input,
                        search,
                        error
                );

        Scene scene =
                new Scene(
                        windowLayout,
                        400,
                        300
                );

        scene.getStylesheets().add(
                "style.css"
        );

        window.setScene(
                scene
        );

        window.showAndWait();
    }

    /*
     * =============================================
     * SEARCH VALUE
     * =============================================
     */

    private void searchValue(
            int value) {

        /*
         * =========================================
         * HASH FUNCTION
         * =========================================
         */

        int row =
                Math.floorMod(
                        value,
                        hashTable.length
                );

        resetCellColors();

        runningAlgorithm =
                true;

        Timeline timeline =
                new Timeline();

        double delay =
                0;

        int foundColumn =
                -1;

        /*
         * =========================================
         * SEARCH BUCKET
         * =========================================
         */

        for (int column = 0;
             column < hashTable[row].length;
             column++) {

            if (hashTable[row][column]
                    == null) {

                break;
            }

            int currentColumn =
                    column;

            Integer currentValue =
                    hashTable[row][column];

            /*
             * =====================================
             * CHECK CELL
             * =====================================
             */

            KeyFrame check =
                    new KeyFrame(
                            Duration.millis(
                                    delay
                            ),
                            e -> {

                                resetCellColors();

                                cells[row][currentColumn]
                                        .setFill(
                                                Color.LIGHTBLUE
                                        );

                                statusText.setText(
                                        "Checking bucket "
                                                + row
                                                + ", slot "
                                                + currentColumn
                                                + "..."
                                );
                            }
                    );

            timeline
                    .getKeyFrames()
                    .add(
                            check
                    );

            delay +=
                    ANIMATION_DELAY;

            /*
             * =====================================
             * FOUND
             * =====================================
             */

            if (currentValue == value) {

                foundColumn =
                        column;

                break;
            }

            /*
             * Since each bucket is sorted,
             * we can stop if we pass the value.
             */
            if (currentValue > value) {

                break;
            }
        }

        /*
         * =========================================
         * VALUE FOUND
         * =========================================
         */

        if (foundColumn != -1) {

            int finalColumn =
                    foundColumn;

            KeyFrame found =
                    new KeyFrame(
                            Duration.millis(
                                    delay
                            ),
                            e -> {

                                resetCellColors();

                                cells[row][finalColumn]
                                        .setFill(
                                                Color.LIGHTGREEN
                                        );

                                statusText.setText(
                                        value
                                                + " found in bucket "
                                                + row
                                                + ", slot "
                                                + finalColumn
                                                + "."
                                );

                                runningAlgorithm =
                                        false;
                            }
                    );

            timeline
                    .getKeyFrames()
                    .add(
                            found
                    );
        }

        /*
         * =========================================
         * VALUE NOT FOUND
         * =========================================
         */

        else {

            KeyFrame notFound =
                    new KeyFrame(
                            Duration.millis(
                                    delay
                            ),
                            e -> {

                                resetCellColors();

                                statusText.setText(
                                        value
                                                + " was not found in bucket "
                                                + row
                                                + "."
                                );

                                runningAlgorithm =
                                        false;
                            }
                    );

            timeline
                    .getKeyFrames()
                    .add(
                            notFound
                    );
        }

        timeline.play();
    }

    /*
     * =============================================
     * DELETE WINDOW
     * =============================================
     */

    private void showDeleteWindow() {

        Stage window =
                new Stage();

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        window.setTitle(
                "Delete"
        );

        Text title =
                new Text(
                        "Enter Value"
                );

        title.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        TextField input =
                createInputField();

        Text error =
                new Text();

        error.setFont(
                Font.font(
                        "Arial",
                        14
                )
        );

        Button delete =
                new Button(
                        "Delete"
                );

        delete.setPrefSize(
                120,
                45
        );

        delete.setOnAction(e -> {

            try {

                int value =
                        Integer.parseInt(
                                input
                                        .getText()
                                        .trim()
                        );

                window.close();

                deleteValue(
                        value
                );

            }
            catch (NumberFormatException exception) {

                error.setText(
                        "Enter a valid integer."
                );

                input.clear();

                input.requestFocus();
            }
        });

        input.setOnAction(e -> {

            delete.fire();

        });

        VBox windowLayout =
                createWindowLayout(
                        title,
                        input,
                        delete,
                        error
                );

        Scene scene =
                new Scene(
                        windowLayout,
                        400,
                        300
                );

        scene.getStylesheets().add(
                "style.css"
        );

        window.setScene(
                scene
        );

        window.showAndWait();
    }

    /*
     * =============================================
     * DELETE VALUE
     * =============================================
     */

    private void deleteValue(
            int value) {

        /*
         * =========================================
         * HASH FUNCTION
         * =========================================
         */

        int row =
                Math.floorMod(
                        value,
                        hashTable.length
                );

        resetCellColors();

        runningAlgorithm =
                true;

        Timeline timeline =
                new Timeline();

        double delay =
                0;

        int foundColumn =
                -1;

        /*
         * =========================================
         * SEARCH BUCKET
         * =========================================
         */

        for (int column = 0;
             column < hashTable[row].length;
             column++) {

            if (hashTable[row][column]
                    == null) {

                break;
            }

            int currentColumn =
                    column;

            Integer currentValue =
                    hashTable[row][column];

            /*
             * =====================================
             * CHECK CELL
             * =====================================
             */

            KeyFrame check =
                    new KeyFrame(
                            Duration.millis(
                                    delay
                            ),
                            e -> {

                                resetCellColors();

                                cells[row][currentColumn]
                                        .setFill(
                                                Color.LIGHTBLUE
                                        );

                                statusText.setText(
                                        "Checking bucket "
                                                + row
                                                + ", slot "
                                                + currentColumn
                                                + "..."
                                );
                            }
                    );

            timeline
                    .getKeyFrames()
                    .add(
                            check
                    );

            delay +=
                    ANIMATION_DELAY;

            /*
             * =====================================
             * FOUND
             * =====================================
             */

            if (currentValue == value) {

                foundColumn =
                        column;

                break;
            }

            /*
             * Bucket is sorted.
             */
            if (currentValue > value) {

                break;
            }
        }

        /*
         * =========================================
         * NOT FOUND
         * =========================================
         */

        if (foundColumn == -1) {

            KeyFrame notFound =
                    new KeyFrame(
                            Duration.millis(
                                    delay
                            ),
                            e -> {

                                resetCellColors();

                                statusText.setText(
                                        value
                                                + " was not found."
                                );

                                runningAlgorithm =
                                        false;
                            }
                    );

            timeline
                    .getKeyFrames()
                    .add(
                            notFound
                    );

            timeline.play();

            return;
        }

        /*
         * =========================================
         * FOUND VALUE
         * =========================================
         */

        int deleteColumn =
                foundColumn;

        KeyFrame found =
                new KeyFrame(
                        Duration.millis(
                                delay
                        ),
                        e -> {

                            resetCellColors();

                            cells[row][deleteColumn]
                                    .setFill(
                                            Color.LIGHTGREEN
                                    );

                            statusText.setText(
                                    value
                                            + " found. Deleting..."
                            );
                        }
                );

        timeline
                .getKeyFrames()
                .add(
                        found
                );

        delay +=
                ANIMATION_DELAY;

        /*
         * =========================================
         * DELETE AND SHIFT
         * =========================================
         */

        KeyFrame delete =
                new KeyFrame(
                        Duration.millis(
                                delay
                        ),
                        e -> {

                            /*
                             * Shift everything after
                             * the deleted value left.
                             */
                            for (int column =
                                         deleteColumn;
                                 column
                                         < hashTable[row].length
                                         - 1;
                                 column++) {

                                hashTable[row][column] =
                                        hashTable[row][column + 1];
                            }

                            /*
                             * Clear final position.
                             */
                            hashTable[row][
                                    hashTable[row].length
                                            - 1
                                    ] =
                                    null;

                            /*
                             * The row was already
                             * sorted, so shifting left
                             * keeps it sorted.
                             */
                            drawTable();

                            statusText.setText(
                                    value
                                            + " deleted from bucket "
                                            + row
                                            + "."
                            );

                            runningAlgorithm =
                                    false;
                        }
                );

        timeline
                .getKeyFrames()
                .add(
                        delete
                );

        timeline.play();
    }

    /*
     * =============================================
     * DRAW TABLE
     * =============================================
     */

    private void drawTable() {

        /*
         * =========================================
         * CLEAR OLD TABLE
         * =========================================
         */

        canvas
                .getChildren()
                .clear();

        if (hashTable == null) {

            cells =
                    null;

            return;
        }

        /*
         * =========================================
         * CREATE CELL ARRAY
         * =========================================
         */

        cells =
                new Rectangle[
                        hashTable.length
                ][hashTable[0].length];

        /*
         * =========================================
         * CELL SIZE
         * =========================================
         */

        double cellWidth =
                100;

        double cellHeight =
                50;

        /*
         * =========================================
         * TABLE SIZE
         * =========================================
         */

        double tableWidth =
                hashTable[0].length
                        * cellWidth;

        double tableHeight =
                hashTable.length
                        * cellHeight;

        canvas.setPrefHeight(
                tableHeight
                        + 100
        );

        /*
         * =========================================
         * CENTER TABLE
         * =========================================
         */

        double canvasWidth =
                canvas.getWidth();

        if (canvasWidth <= 0) {

            canvasWidth =
                    canvas.getPrefWidth();
        }

        double startX =
                (canvasWidth
                        - tableWidth)
                        / 2;

        double startY =
                40;

        /*
         * =========================================
         * COLUMN HEADERS
         * =========================================
         */

        for (int column = 0;
             column < hashTable[0].length;
             column++) {

            Text columnText =
                    new Text(
                            "Slot "
                                    + column
                    );

            columnText.setFont(
                    Font.font(
                            "Arial",
                            14
                    )
            );

            double textWidth =
                    columnText
                            .getLayoutBounds()
                            .getWidth();

            columnText.setX(
                    startX
                            + column
                            * cellWidth
                            + cellWidth
                            / 2
                            - textWidth
                            / 2
            );

            columnText.setY(
                    startY
                            - 10
            );

            canvas
                    .getChildren()
                    .add(
                            columnText
                    );
        }

        /*
         * =========================================
         * DRAW ROWS
         * =========================================
         */

        for (int row = 0;
             row < hashTable.length;
             row++) {

            /*
             * =====================================
             * BUCKET INDEX
             * =====================================
             */

            Text index =
                    new Text(
                            String.valueOf(
                                    row
                            )
                    );

            index.setFont(
                    Font.font(
                            "Arial",
                            18
                    )
            );

            index.setX(
                    startX
                            - 40
            );

            index.setY(
                    startY
                            + row
                            * cellHeight
                            + cellHeight
                            / 2
                            + 6
            );

            canvas
                    .getChildren()
                    .add(
                            index
                    );

            /*
             * =====================================
             * DRAW CELLS
             * =====================================
             */

            for (int column = 0;
                 column < hashTable[row].length;
                 column++) {

                Rectangle cell =
                        new Rectangle(
                                cellWidth,
                                cellHeight
                        );

                cell.setX(
                        startX
                                + column
                                * cellWidth
                );

                cell.setY(
                        startY
                                + row
                                * cellHeight
                );

                cell.setFill(
                        Color.WHITE
                );

                cell.setStroke(
                        Color.BLACK
                );

                cell.setStrokeWidth(
                        2
                );

                /*
                 * Store rectangle so algorithms
                 * can change its color later.
                 */
                cells[row][column] =
                        cell;

                canvas
                        .getChildren()
                        .add(
                                cell
                        );

                /*
                 * =================================
                 * VALUE
                 * =================================
                 */

                Integer value =
                        hashTable[row][column];

                if (value != null) {

                    Text valueText =
                            new Text(
                                    String.valueOf(
                                            value
                                    )
                            );

                    valueText.setFont(
                            Font.font(
                                    "Arial",
                                    18
                            )
                    );

                    double textWidth =
                            valueText
                                    .getLayoutBounds()
                                    .getWidth();

                    double textHeight =
                            valueText
                                    .getLayoutBounds()
                                    .getHeight();

                    valueText.setX(
                            startX
                                    + column
                                    * cellWidth
                                    + cellWidth
                                    / 2
                                    - textWidth
                                    / 2
                    );

                    valueText.setY(
                            startY
                                    + row
                                    * cellHeight
                                    + cellHeight
                                    / 2
                                    + textHeight
                                    / 4
                    );

                    canvas
                            .getChildren()
                            .add(
                                    valueText
                            );
                }
            }
        }
    }

    /*
     * =============================================
     * RESET CELL COLORS
     * =============================================
     */

    private void resetCellColors() {

        if (cells == null) {

            return;
        }

        for (int row = 0;
             row < cells.length;
             row++) {

            for (int column = 0;
                 column < cells[row].length;
                 column++) {

                if (cells[row][column]
                        != null) {

                    cells[row][column]
                            .setFill(
                                    Color.WHITE
                            );
                }
            }
        }
    }

    /*
     * =============================================
     * CREATE INPUT FIELD
     * =============================================
     */

    private TextField createInputField() {

        TextField input =
                new TextField();

        input.setPromptText(
                "Integer"
        );

        input.setMaxWidth(
                200
        );

        input.setStyle(
                "-fx-background-color: white;"
                        + "-fx-text-fill: black;"
                        + "-fx-prompt-text-fill: gray;"
                        + "-fx-font-size: 14px;"
                        + "-fx-border-color: gray;"
                        + "-fx-border-width: 1px;"
        );

        return input;
    }

    /*
     * =============================================
     * CREATE WINDOW LAYOUT
     * =============================================
     */

    private VBox createWindowLayout(
            Text title,
            TextField input,
            Button button,
            Text error) {

        VBox windowLayout =
                new VBox(
                        15
                );

        windowLayout.setAlignment(
                Pos.CENTER
        );

        windowLayout.setPadding(
                new Insets(
                        25
                )
        );

        windowLayout.getChildren().addAll(
                title,
                input,
                button,
                error
        );

        return windowLayout;
    }

    /*
     * =============================================
     * BUTTON SIZE
     * =============================================
     */

    private void setButtonSize(
            Button button) {

        button.setMinSize(
                250,
                75
        );

        button.setMaxSize(
                250,
                75
        );

        button.setPrefSize(
                250,
                75
        );
    }
}