package Graph;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GraphCreation {

    /*
     * =============================================
     * GRAPH DATA
     * =============================================
     */

    private static List<GraphNode> nodes;

    private static Pane canvas;

    /*
     * =============================================
     * NODE PLACEMENT
     * =============================================
     */

    private static boolean placingNode;

    private static int pendingValue;

    /*
     * =============================================
     * EDGE CREATION
     * =============================================
     */

    private static boolean addingEdge;

    private static GraphNode firstEdgeNode;

    private static GraphNode secondEdgeNode;

    /*
     * =============================================
     * STATUS
     * =============================================
     */

    private static Text status;

    /*
     * =============================================
     * DISPLAY
     * =============================================
     */

    public static List<GraphNode> display() {

        /*
         * =========================================
         * RESET DATA
         * =========================================
         */

        nodes =
                new ArrayList<>();

        canvas =
                new Pane();

        placingNode =
                false;

        addingEdge =
                false;

        firstEdgeNode =
                null;

        secondEdgeNode =
                null;

        /*
         * =========================================
         * WINDOW
         * =========================================
         */

        Stage window =
                new Stage();

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        window.setTitle(
                "Graph Creation"
        );

        window.setMinWidth(
                1000
        );

        window.setMinHeight(
                700
        );

        /*
         * =========================================
         * MAIN LAYOUT
         * =========================================
         */

        BorderPane layout =
                new BorderPane();

        /*
         * =========================================
         * STATUS
         * =========================================
         */

        status =
                new Text(
                        "Add vertices to create your graph."
                );

        status.setFont(
                Font.font(
                        "Arial",
                        18
                )
        );

        HBox statusArea =
                new HBox();

        statusArea.setAlignment(
                Pos.CENTER
        );

        statusArea.setPadding(
                new Insets(
                        15
                )
        );

        statusArea.getChildren().add(
                status
        );

        layout.setTop(
                statusArea
        );

        /*
         * =========================================
         * CANVAS
         * =========================================
         */

        canvas.setPrefSize(
                900,
                500
        );

        canvas.setStyle(
                "-fx-background-color: white;"
                        + "-fx-border-color: black;"
                        + "-fx-border-width: 2px;"
        );

        layout.setCenter(
                canvas
        );

        /*
         * =========================================
         * ADD VERTEX
         * =========================================
         */

        Button addVertex =
                new Button(
                        "Add Vertex"
                );

        setButtonSize(
                addVertex
        );

        addVertex.setOnAction(e -> {

            /*
             * Cancel edge creation if
             * the user switches modes.
             */
            cancelEdgeSelection();

            /*
             * Do not allow another vertex
             * prompt while waiting for placement.
             */
            if (placingNode) {

                status.setText(
                        "Place the current vertex first."
                );

                return;
            }

            showVertexPrompt();
        });

        /*
         * =========================================
         * ADD EDGE
         * =========================================
         */

        Button addEdge =
                new Button(
                        "Add Edge"
                );

        setButtonSize(
                addEdge
        );

        addEdge.setOnAction(e -> {

            /*
             * A pending vertex must be
             * placed before creating an edge.
             */
            if (placingNode) {

                status.setText(
                        "Place the current vertex first."
                );

                return;
            }

            /*
             * Need at least two vertices.
             */
            if (nodes.size() < 2) {

                status.setText(
                        "Create at least two vertices first."
                );

                return;
            }

            /*
             * Reset any previous edge selection.
             */
            cancelEdgeSelection();

            /*
             * Start edge selection mode.
             */
            addingEdge =
                    true;

            firstEdgeNode =
                    null;

            secondEdgeNode =
                    null;

            status.setText(
                    "Select the first vertex."
            );

            enableEdgeSelection();
        });

        /*
         * =========================================
         * FINISH
         * =========================================
         */

        Button finish =
                new Button(
                        "Finish"
                );

        setButtonSize(
                finish
        );

        finish.setOnAction(e -> {

            /*
             * Do not finish while waiting
             * for a vertex to be placed.
             */
            if (placingNode) {

                status.setText(
                        "Place the current vertex first."
                );

                return;
            }

            /*
             * Cancel any unfinished edge.
             */
            cancelEdgeSelection();

            window.close();
        });

        /*
         * =========================================
         * BUTTON MENU
         * =========================================
         */

        HBox buttons =
                new HBox(
                        20
                );

        buttons.setAlignment(
                Pos.CENTER
        );

        buttons.setPadding(
                new Insets(
                        20
                )
        );

        buttons.getChildren().addAll(
                addVertex,
                addEdge,
                finish
        );

        layout.setBottom(
                buttons
        );

        /*
         * =========================================
         * CANVAS CLICK
         * =========================================
         */

        canvas.setOnMouseClicked(e -> {

            /*
             * Only place a vertex when
             * placement mode is active.
             */
            if (!placingNode) {

                return;
            }

            double x =
                    e.getX();

            double y =
                    e.getY();

            /*
             * =====================================
             * CANVAS BOUNDARY CHECK
             * =====================================
             *
             * Radius is 30, so keep the center
             * at least 30 pixels from the edge.
             */

            if (x < 30
                    || y < 30
                    || x > canvas.getWidth() - 30
                    || y > canvas.getHeight() - 30) {

                status.setText(
                        "Vertex is too close to the edge of the canvas."
                );

                return;
            }

            /*
             * =====================================
             * DISTANCE CHECK
             * =====================================
             *
             * Prevent vertices from overlapping.
             */

            for (GraphNode node : nodes) {

                double dx =
                        node.getLayoutX()
                                - x;

                double dy =
                        node.getLayoutY()
                                - y;

                double distance =
                        Math.sqrt(
                                dx * dx
                                        + dy * dy
                        );

                if (distance < 75) {

                    status.setText(
                            "Vertex is too close to another vertex."
                    );

                    return;
                }
            }

            /*
             * =====================================
             * CREATE VERTEX
             * =====================================
             */

            GraphNode node =
                    new GraphNode(
                            pendingValue,
                            x,
                            y
                    );

            nodes.add(
                    node
            );

            canvas
                    .getChildren()
                    .add(
                            node
                    );

            placingNode =
                    false;

            status.setText(
                    "Vertex "
                            + pendingValue
                            + " added."
            );
        });

        /*
         * =========================================
         * SCENE
         * =========================================
         */

        Scene scene =
                new Scene(
                        layout,
                        1000,
                        700
                );

        scene
                .getStylesheets()
                .add(
                        "style.css"
                );

        window.setScene(
                scene
        );

        window.showAndWait();

        /*
         * =========================================
         * RETURN GRAPH
         * =========================================
         */

        return nodes;
    }

    /*
     * =============================================
     * VERTEX VALUE PROMPT
     * =============================================
     */

    private static void showVertexPrompt() {

        Stage window =
                new Stage();

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        window.setTitle(
                "Add Vertex"
        );

        /*
         * =========================================
         * TITLE
         * =========================================
         */

        Text title =
                new Text(
                        "Enter Vertex Value"
                );

        title.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        /*
         * =========================================
         * INPUT
         * =========================================
         */

        TextField input =
                new TextField();

        input.setPromptText(
                "Integer"
        );

        input.setPrefWidth(
                200
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

        /*
         * =========================================
         * ERROR
         * =========================================
         */

        Text error =
                new Text();

        error.setFont(
                Font.font(
                        "Arial",
                        14
                )
        );

        /*
         * =========================================
         * ENTER BUTTON
         * =========================================
         */

        Button enter =
                new Button(
                        "Enter"
                );

        setSmallButtonSize(
                enter
        );

        /*
         * =========================================
         * LAYOUT
         * =========================================
         */

        VBox layout =
                new VBox(
                        15
                );

        layout.setAlignment(
                Pos.CENTER
        );

        layout.setPadding(
                new Insets(
                        25
                )
        );

        layout.getChildren().addAll(
                title,
                input,
                enter,
                error
        );

        /*
         * =========================================
         * ENTER VALUE
         * =========================================
         */

        enter.setOnAction(e -> {

            try {

                int value =
                        Integer.parseInt(
                                input
                                        .getText()
                                        .trim()
                        );

                /*
                 * =================================
                 * DUPLICATE VALUE CHECK
                 * =================================
                 */

                for (GraphNode node : nodes) {

                    if (node.getValue()
                            == value) {

                        error.setText(
                                "That vertex already exists."
                        );

                        input.clear();

                        input.requestFocus();

                        return;
                    }
                }

                /*
                 * Save value until the user
                 * chooses a location.
                 */
                pendingValue =
                        value;

                placingNode =
                        true;

                status.setText(
                        "Click the graph to place vertex "
                                + value
                                + "."
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

        /*
         * Allow keyboard Enter.
         */
        input.setOnAction(e -> {

            enter.fire();

        });

        /*
         * =========================================
         * SCENE
         * =========================================
         */

        Scene scene =
                new Scene(
                        layout,
                        400,
                        300
                );

        scene
                .getStylesheets()
                .add(
                        "style.css"
                );

        window.setScene(
                scene
        );

        /*
         * JavaFX TextField rendering/focus fix.
         */
        window.setOnShown(e -> {

            layout.applyCss();

            layout.layout();

            Platform.runLater(() -> {

                input.requestFocus();

            });
        });

        window.showAndWait();
    }

    /*
     * =============================================
     * ENABLE EDGE SELECTION
     * =============================================
     */

    private static void enableEdgeSelection() {

        for (GraphNode node : nodes) {

            node.setOnMouseClicked(e -> {

                /*
                 * Do not let this click
                 * reach the canvas.
                 */
                e.consume();

                if (!addingEdge) {

                    return;
                }

                /*
                 * =================================
                 * FIRST VERTEX
                 * =================================
                 */

                if (firstEdgeNode == null) {

                    firstEdgeNode =
                            node;

                    firstEdgeNode.setSelected(
                            true
                    );

                    status.setText(
                            "Selected "
                                    + node.getValue()
                                    + ". Select the second vertex."
                    );

                    return;
                }

                /*
                 * =================================
                 * SAME VERTEX
                 * =================================
                 */

                if (firstEdgeNode == node) {

                    status.setText(
                            "A vertex cannot connect to itself. Select another vertex."
                    );

                    return;
                }

                /*
                 * =================================
                 * DUPLICATE EDGE
                 * =================================
                 */

                if (firstEdgeNode
                        .isConnectedTo(
                                node
                        )) {

                    status.setText(
                            "Those vertices are already connected."
                    );

                    firstEdgeNode.setSelected(
                            false
                    );

                    firstEdgeNode =
                            null;

                    secondEdgeNode =
                            null;

                    addingEdge =
                            false;

                    clearNodeClickHandlers();

                    return;
                }

                /*
                 * =================================
                 * SECOND VERTEX
                 * =================================
                 */

                secondEdgeNode =
                        node;

                secondEdgeNode.setSelected(
                        true
                );

                /*
                 * Stop accepting vertex clicks.
                 */
                addingEdge =
                        false;

                clearNodeClickHandlers();

                /*
                 * Ask for edge weight.
                 */
                showWeightPrompt();
            });
        }
    }

    /*
     * =============================================
     * EDGE WEIGHT PROMPT
     * =============================================
     */

    private static void showWeightPrompt() {

        Stage window =
                new Stage();

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        window.setTitle(
                "Edge Weight"
        );

        /*
         * =========================================
         * TITLE
         * =========================================
         */

        Text title =
                new Text(
                        "Enter Edge Weight"
                );

        title.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        /*
         * =========================================
         * CONNECTION
         * =========================================
         */

        Text connection =
                new Text(
                        firstEdgeNode.getValue()
                                + " ↔ "
                                + secondEdgeNode.getValue()
                );

        connection.setFont(
                Font.font(
                        "Arial",
                        16
                )
        );

        /*
         * =========================================
         * INPUT
         * =========================================
         */

        TextField input =
                new TextField();

        input.setPromptText(
                "Weight"
        );

        input.setPrefWidth(
                200
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

        /*
         * =========================================
         * ERROR
         * =========================================
         */

        Text error =
                new Text();

        error.setFont(
                Font.font(
                        "Arial",
                        14
                )
        );

        /*
         * =========================================
         * ENTER BUTTON
         * =========================================
         */

        Button enter =
                new Button(
                        "Enter"
                );

        setSmallButtonSize(
                enter
        );

        /*
         * =========================================
         * LAYOUT
         * =========================================
         */

        VBox layout =
                new VBox(
                        15
                );

        layout.setAlignment(
                Pos.CENTER
        );

        layout.setPadding(
                new Insets(
                        25
                )
        );

        layout.getChildren().addAll(
                title,
                connection,
                input,
                enter,
                error
        );

        /*
         * =========================================
         * ENTER WEIGHT
         * =========================================
         */

        enter.setOnAction(e -> {

            try {

                int weight =
                        Integer.parseInt(
                                input
                                        .getText()
                                        .trim()
                        );

                /*
                 * Dijkstra cannot use
                 * negative edge weights.
                 *
                 * We will require positive
                 * weights for this visualizer.
                 */
                if (weight <= 0) {

                    error.setText(
                            "Weight must be greater than 0."
                    );

                    input.clear();

                    input.requestFocus();

                    return;
                }

                /*
                 * Save values before
                 * resetting selection.
                 */
                int firstValue =
                        firstEdgeNode
                                .getValue();

                int secondValue =
                        secondEdgeNode
                                .getValue();

                /*
                 * =================================
                 * CREATE EDGE
                 * =================================
                 */

                createEdge(
                        firstEdgeNode,
                        secondEdgeNode,
                        weight
                );

                /*
                 * =================================
                 * REMOVE SELECTION
                 * =================================
                 */

                firstEdgeNode.setSelected(
                        false
                );

                secondEdgeNode.setSelected(
                        false
                );

                /*
                 * =================================
                 * RESET EDGE DATA
                 * =================================
                 */

                firstEdgeNode =
                        null;

                secondEdgeNode =
                        null;

                addingEdge =
                        false;

                /*
                 * =================================
                 * STATUS
                 * =================================
                 */

                status.setText(
                        "Edge added between "
                                + firstValue
                                + " and "
                                + secondValue
                                + " with weight "
                                + weight
                                + "."
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

        /*
         * Keyboard Enter.
         */
        input.setOnAction(e -> {

            enter.fire();

        });

        /*
         * =========================================
         * CLOSE WINDOW
         * =========================================
         *
         * If the user presses X instead of
         * entering a weight, cancel the edge.
         */

        window.setOnCloseRequest(e -> {

            cancelEdgeSelection();

            status.setText(
                    "Edge creation cancelled."
            );
        });

        /*
         * =========================================
         * SCENE
         * =========================================
         */

        Scene scene =
                new Scene(
                        layout,
                        400,
                        300
                );

        scene
                .getStylesheets()
                .add(
                        "style.css"
                );

        window.setScene(
                scene
        );

        /*
         * JavaFX TextField rendering/focus fix.
         */
        window.setOnShown(e -> {

            layout.applyCss();

            layout.layout();

            Platform.runLater(() -> {

                input.requestFocus();

            });
        });

        window.showAndWait();
    }

    /*
     * =============================================
     * CREATE WEIGHTED EDGE
     * =============================================
     */

    private static void createEdge(
            GraphNode first,
            GraphNode second,
            int weight) {

        /*
         * =========================================
         * DATA STRUCTURE
         * =========================================
         *
         * The graph is undirected.
         *
         * Therefore:
         *
         * first -> second
         *
         * AND
         *
         * second -> first
         *
         * both receive the same weight.
         */

        first.addEdge(
                second,
                weight
        );

        second.addEdge(
                first,
                weight
        );

        /*
         * =========================================
         * EDGE LINE
         * =========================================
         */

        Line edge =
                new Line();

        /*
         * First vertex.
         */
        edge.startXProperty()
                .bind(
                        first.layoutXProperty()
                );

        edge.startYProperty()
                .bind(
                        first.layoutYProperty()
                );

        /*
         * Second vertex.
         */
        edge.endXProperty()
                .bind(
                        second.layoutXProperty()
                );

        edge.endYProperty()
                .bind(
                        second.layoutYProperty()
                );

        edge.setStrokeWidth(
                2
        );

        /*
         * =========================================
         * WEIGHT TEXT
         * =========================================
         */

        Text weightText =
                new Text(
                        String.valueOf(
                                weight
                        )
                );

        weightText.setFont(
                Font.font(
                        "Arial",
                        16
                )
        );

        /*
         * Place the weight halfway
         * between the two vertices.
         */
        weightText.xProperty()
                .bind(
                        first.layoutXProperty()
                                .add(
                                        second.layoutXProperty()
                                )
                                .divide(
                                        2
                                )
                );

        /*
         * Move it slightly above
         * the edge line.
         */
        weightText.yProperty()
                .bind(
                        first.layoutYProperty()
                                .add(
                                        second.layoutYProperty()
                                )
                                .divide(
                                        2
                                )
                                .subtract(
                                        8
                                )
                );

        /*
         * =========================================
         * ADD TO CANVAS
         * =========================================
         */

        /*
         * Put line behind vertices.
         */
        canvas
                .getChildren()
                .add(
                        0,
                        edge
                );

        /*
         * Add weight.
         */
        canvas
                .getChildren()
                .add(
                        weightText
                );

        /*
         * Keep all vertices in front
         * of the lines and weight labels.
         */
        for (GraphNode node : nodes) {

            node.toFront();
        }
    }

    /*
     * =============================================
     * CANCEL EDGE SELECTION
     * =============================================
     */

    private static void cancelEdgeSelection() {

        /*
         * Remove highlight from
         * first selected vertex.
         */
        if (firstEdgeNode != null) {

            firstEdgeNode.setSelected(
                    false
            );
        }

        /*
         * Remove highlight from
         * second selected vertex.
         */
        if (secondEdgeNode != null) {

            secondEdgeNode.setSelected(
                    false
            );
        }

        /*
         * Reset edge selection data.
         */
        firstEdgeNode =
                null;

        secondEdgeNode =
                null;

        addingEdge =
                false;

        /*
         * Remove vertex click handlers.
         */
        clearNodeClickHandlers();
    }

    /*
     * =============================================
     * CLEAR NODE CLICK HANDLERS
     * =============================================
     */

    private static void clearNodeClickHandlers() {

        for (GraphNode node : nodes) {

            node.setOnMouseClicked(
                    null
            );
        }
    }

    /*
     * =============================================
     * MAIN BUTTON SIZE
     * =============================================
     */

    private static void setButtonSize(
            Button button) {

        button.setMinSize(
                180,
                50
        );

        button.setMaxSize(
                180,
                50
        );

        button.setPrefSize(
                180,
                50
        );
    }

    /*
     * =============================================
     * SMALL BUTTON SIZE
     * =============================================
     */

    private static void setSmallButtonSize(
            Button button) {

        button.setMinSize(
                120,
                45
        );

        button.setMaxSize(
                120,
                45
        );

        button.setPrefSize(
                120,
                45
        );
    }
}