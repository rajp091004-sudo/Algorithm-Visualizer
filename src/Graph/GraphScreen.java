package Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GraphScreen {

    /*
     * =============================================
     * GRAPH DATA
     * =============================================
     */

    private List<GraphNode> nodes =
            new ArrayList<>();

    /*
     * =============================================
     * ALGORITHM STATE
     * =============================================
     */

    private boolean selectingBFS =
            false;

    private boolean selectingDFS =
            false;

    private boolean selectingDijkstraStart =
            false;

    private boolean selectingDijkstraEnd =
            false;

    private boolean runningAlgorithm =
            false;

    private GraphNode dijkstraStart =
            null;

    private static final double ALGORITHM_DELAY =
            700;

    /*
     * =============================================
     * STATUS
     * =============================================
     */

    private final Text statusText =
            new Text();

    /*
     * =============================================
     * CANVAS
     * =============================================
     */

    private final Pane canvas =
            new Pane();

    /*
     * =============================================
     * MAIN LAYOUT
     * =============================================
     */

    private final BorderPane layout =
            new BorderPane();

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
         * CREATE GRAPH
         * =========================================
         */

        Button createGraph =
                new Button(
                        "Create Graph"
                );

        setButtonSize(
                createGraph
        );

        createGraph.setOnAction(e -> {

            if (runningAlgorithm) {

                return;
            }

            cancelSelections();

            clearNodeHandlers();

            List<GraphNode> createdNodes =
                    GraphCreation.display();

            if (createdNodes != null) {

                nodes =
                        createdNodes;

                statusText.setText(
                        ""
                );

                drawGraph();
            }
        });

        /*
         * =========================================
         * BFS
         * =========================================
         */

        Button bfs =
                new Button(
                        "BFS"
                );

        setButtonSize(
                bfs
        );

        bfs.setOnAction(e -> {

            if (runningAlgorithm) {

                return;
            }

            if (nodes.isEmpty()) {

                showMessage(
                        "Create a graph first."
                );

                return;
            }

            cancelSelections();

            clearNodeHandlers();

            resetNodeColors();

            selectingBFS =
                    true;

            showBFSInstructions();

            enableBFSSelection();
        });

        /*
         * =========================================
         * DFS
         * =========================================
         */

        Button dfs =
                new Button(
                        "DFS"
                );

        setButtonSize(
                dfs
        );

        dfs.setOnAction(e -> {

            if (runningAlgorithm) {

                return;
            }

            if (nodes.isEmpty()) {

                showMessage(
                        "Create a graph first."
                );

                return;
            }

            cancelSelections();

            clearNodeHandlers();

            resetNodeColors();

            selectingDFS =
                    true;

            showDFSInstructions();

            enableDFSSelection();
        });

        /*
         * =========================================
         * DIJKSTRA
         * =========================================
         */

        Button dijkstra =
                new Button(
                        "Dijkstra"
                );

        setButtonSize(
                dijkstra
        );

        dijkstra.setOnAction(e -> {

            if (runningAlgorithm) {

                return;
            }

            if (nodes.isEmpty()) {

                showMessage(
                        "Create a graph first."
                );

                return;
            }

            /*
             * Cancel other selections.
             */
            cancelSelections();

            clearNodeHandlers();

            resetNodeColors();

            /*
             * Start Dijkstra selection.
             */
            selectingDijkstraStart =
                    true;

            statusText.setText(
                    "Select the starting vertex for Dijkstra"
            );

            enableDijkstraStartSelection();
        });

        /*
         * =========================================
         * CLEAR
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

            cancelSelections();

            clearNodeHandlers();

            nodes.clear();

            canvas
                    .getChildren()
                    .clear();

            statusText.setText(
                    ""
            );
        });

        /*
         * =========================================
         * SELECT SCREEN
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

            cancelSelections();

            clearNodeHandlers();

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
                createGraph,
                bfs,
                dfs,
                dijkstra,
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

        statusArea.setPadding(
                new Insets(
                        10
                )
        );

        statusArea.setMinHeight(
                50
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
         * GRAPH AREA
         * =========================================
         */

        VBox graphArea =
                new VBox();

        VBox.setVgrow(
                canvas,
                Priority.ALWAYS
        );

        graphArea.getChildren().addAll(
                statusArea,
                canvas
        );

        /*
         * =========================================
         * MAIN LAYOUT
         * =========================================
         */

        layout.setCenter(
                graphArea
        );

        layout.setBottom(
                menu
        );

        return scene;
    }

    /*
     * =============================================
     * BFS INSTRUCTIONS
     * =============================================
     */

    private void showBFSInstructions() {

        drawGraph();

        statusText.setText(
                "Select a starting vertex for BFS"
        );
    }

    /*
     * =============================================
     * ENABLE BFS SELECTION
     * =============================================
     */

    private void enableBFSSelection() {

        for (GraphNode node : nodes) {

            node.setOnMouseClicked(e -> {

                e.consume();

                if (!selectingBFS) {

                    return;
                }

                selectingBFS =
                        false;

                node.setSelected(
                        true
                );

                statusText.setText(
                        "Starting BFS from vertex "
                                + node.getValue()
                );

                clearNodeHandlers();

                startBFS(
                        node
                );
            });
        }
    }

    /*
     * =============================================
     * START BFS
     * =============================================
     */

    private void startBFS(
            GraphNode start) {

        runningAlgorithm =
                true;

        Queue<GraphNode> queue =
                new ArrayDeque<>();

        Set<GraphNode> visited =
                new HashSet<>();

        List<GraphNode> traversal =
                new ArrayList<>();

        queue.add(
                start
        );

        visited.add(
                start
        );

        while (!queue.isEmpty()) {

            GraphNode current =
                    queue.remove();

            traversal.add(
                    current
            );

            for (GraphEdge edge :
                    current.getEdges()) {

                GraphNode neighbor =
                        edge.getDestination();

                if (!visited.contains(
                        neighbor)) {

                    visited.add(
                            neighbor
                    );

                    queue.add(
                            neighbor
                    );
                }
            }
        }

        animateBFS(
                traversal
        );
    }

    /*
     * =============================================
     * ANIMATE BFS
     * =============================================
     */

    private void animateBFS(
            List<GraphNode> traversal) {

        resetNodeColors();

        statusText.setText(
                "BFS Traversal: "
        );

        StringBuilder traversalText =
                new StringBuilder();

        Timeline timeline =
                new Timeline();

        for (int i = 0;
             i < traversal.size();
             i++) {

            final int index =
                    i;

            KeyFrame frame =
                    new KeyFrame(
                            Duration.millis(
                                    index
                                            * ALGORITHM_DELAY
                            ),
                            e -> {

                                GraphNode current =
                                        traversal.get(
                                                index
                                        );

                                current.setVisited(
                                        true
                                );

                                if (traversalText.length()
                                        > 0) {

                                    traversalText.append(
                                            " → "
                                    );
                                }

                                traversalText.append(
                                        current.getValue()
                                );

                                statusText.setText(
                                        "BFS Traversal: "
                                                + traversalText
                                );
                            }
                    );

            timeline
                    .getKeyFrames()
                    .add(
                            frame
                    );
        }

        KeyFrame finishFrame =
                new KeyFrame(
                        Duration.millis(
                                traversal.size()
                                        * ALGORITHM_DELAY
                                        + 300
                        ),
                        e -> {

                            runningAlgorithm =
                                    false;

                            statusText.setText(
                                    "BFS Complete: "
                                            + traversalText
                            );
                        }
                );

        timeline
                .getKeyFrames()
                .add(
                        finishFrame
                );

        timeline.play();
    }

    /*
     * =============================================
     * DFS INSTRUCTIONS
     * =============================================
     */

    private void showDFSInstructions() {

        drawGraph();

        statusText.setText(
                "Select a starting vertex for DFS"
        );
    }

    /*
     * =============================================
     * ENABLE DFS SELECTION
     * =============================================
     */

    private void enableDFSSelection() {

        for (GraphNode node : nodes) {

            node.setOnMouseClicked(e -> {

                e.consume();

                if (!selectingDFS) {

                    return;
                }

                selectingDFS =
                        false;

                node.setSelected(
                        true
                );

                statusText.setText(
                        "Starting DFS from vertex "
                                + node.getValue()
                );

                clearNodeHandlers();

                startDFS(
                        node
                );
            });
        }
    }

    /*
     * =============================================
     * START DFS
     * =============================================
     */

    private void startDFS(
            GraphNode start) {

        runningAlgorithm =
                true;

        ArrayDeque<GraphNode> stack =
                new ArrayDeque<>();

        Set<GraphNode> visited =
                new HashSet<>();

        List<GraphNode> traversal =
                new ArrayList<>();

        stack.push(
                start
        );

        while (!stack.isEmpty()) {

            GraphNode current =
                    stack.pop();

            if (visited.contains(
                    current)) {

                continue;
            }

            visited.add(
                    current
            );

            traversal.add(
                    current
            );

            List<GraphEdge> edges =
                    current.getEdges();

            for (int i =
                         edges.size() - 1;
                 i >= 0;
                 i--) {

                GraphEdge edge =
                        edges.get(
                                i
                        );

                GraphNode neighbor =
                        edge.getDestination();

                if (!visited.contains(
                        neighbor)) {

                    stack.push(
                            neighbor
                    );
                }
            }
        }

        animateDFS(
                traversal
        );
    }

    /*
     * =============================================
     * ANIMATE DFS
     * =============================================
     */

    private void animateDFS(
            List<GraphNode> traversal) {

        resetNodeColors();

        statusText.setText(
                "DFS Traversal: "
        );

        StringBuilder traversalText =
                new StringBuilder();

        Timeline timeline =
                new Timeline();

        for (int i = 0;
             i < traversal.size();
             i++) {

            final int index =
                    i;

            KeyFrame frame =
                    new KeyFrame(
                            Duration.millis(
                                    index
                                            * ALGORITHM_DELAY
                            ),
                            e -> {

                                GraphNode current =
                                        traversal.get(
                                                index
                                        );

                                current.setVisited(
                                        true
                                );

                                if (traversalText.length()
                                        > 0) {

                                    traversalText.append(
                                            " → "
                                    );
                                }

                                traversalText.append(
                                        current.getValue()
                                );

                                statusText.setText(
                                        "DFS Traversal: "
                                                + traversalText
                                );
                            }
                    );

            timeline
                    .getKeyFrames()
                    .add(
                            frame
                    );
        }

        KeyFrame finishFrame =
                new KeyFrame(
                        Duration.millis(
                                traversal.size()
                                        * ALGORITHM_DELAY
                                        + 300
                        ),
                        e -> {

                            runningAlgorithm =
                                    false;

                            statusText.setText(
                                    "DFS Complete: "
                                            + traversalText
                            );
                        }
                );

        timeline
                .getKeyFrames()
                .add(
                        finishFrame
                );

        timeline.play();
    }

    /*
     * =============================================
     * DIJKSTRA START SELECTION
     * =============================================
     */

    private void enableDijkstraStartSelection() {

        for (GraphNode node : nodes) {

            node.setOnMouseClicked(e -> {

                e.consume();

                if (!selectingDijkstraStart) {

                    return;
                }

                /*
                 * Save starting vertex.
                 */
                dijkstraStart =
                        node;

                selectingDijkstraStart =
                        false;

                /*
                 * Highlight start.
                 */
                node.setSelected(
                        true
                );

                /*
                 * Move to destination selection.
                 */
                selectingDijkstraEnd =
                        true;

                statusText.setText(
                        "Start: "
                                + node.getValue()
                                + " | Select the destination vertex"
                );

                enableDijkstraEndSelection();
            });
        }
    }

    /*
     * =============================================
     * DIJKSTRA END SELECTION
     * =============================================
     */

    private void enableDijkstraEndSelection() {

        for (GraphNode node : nodes) {

            node.setOnMouseClicked(e -> {

                e.consume();

                if (!selectingDijkstraEnd) {

                    return;
                }

                /*
                 * Do not allow the same vertex
                 * for both start and destination.
                 */
                if (node == dijkstraStart) {

                    statusText.setText(
                            "Select a different destination vertex."
                    );

                    return;
                }

                GraphNode destination =
                        node;

                selectingDijkstraEnd =
                        false;

                destination.setSelected(
                        true
                );

                clearNodeHandlers();

                startDijkstra(
                        dijkstraStart,
                        destination
                );
            });
        }
    }

    /*
     * =============================================
     * START DIJKSTRA
     * =============================================
     */

    private void startDijkstra(
            GraphNode start,
            GraphNode destination) {

        runningAlgorithm =
                true;

        /*
         * =========================================
         * DISTANCE MAP
         * =========================================
         *
         * Stores the shortest known distance
         * from the start to each vertex.
         */

        Map<GraphNode, Integer> distances =
                new HashMap<>();

        /*
         * =========================================
         * PREVIOUS MAP
         * =========================================
         *
         * Used to rebuild the final
         * shortest path.
         */

        Map<GraphNode, GraphNode> previous =
                new HashMap<>();

        /*
         * =========================================
         * VISITED
         * =========================================
         */

        Set<GraphNode> visited =
                new HashSet<>();

        /*
         * =========================================
         * VISIT ORDER
         * =========================================
         *
         * Used for the animation.
         */

        List<GraphNode> visitOrder =
                new ArrayList<>();

        /*
         * =========================================
         * PRIORITY QUEUE
         * =========================================
         */

        PriorityQueue<DijkstraEntry> queue =
                new PriorityQueue<>(
                        (first, second) ->
                                Integer.compare(
                                        first.distance,
                                        second.distance
                                )
                );

        /*
         * =========================================
         * INITIAL DISTANCES
         * =========================================
         */

        for (GraphNode node : nodes) {

            distances.put(
                    node,
                    Integer.MAX_VALUE
            );
        }

        /*
         * Starting vertex has
         * distance zero.
         */
        distances.put(
                start,
                0
        );

        queue.add(
                new DijkstraEntry(
                        start,
                        0
                )
        );

        /*
         * =========================================
         * RUN DIJKSTRA
         * =========================================
         */

        while (!queue.isEmpty()) {

            DijkstraEntry entry =
                    queue.poll();

            GraphNode current =
                    entry.node;

            /*
             * Ignore outdated queue entries.
             */
            if (entry.distance
                    != distances.get(
                            current
                    )) {

                continue;
            }

            /*
             * Ignore already finalized vertices.
             */
            if (visited.contains(
                    current)) {

                continue;
            }

            /*
             * Finalize current vertex.
             */
            visited.add(
                    current
            );

            visitOrder.add(
                    current
            );

            /*
             * Once the destination is removed
             * from the priority queue, its
             * shortest distance is finalized.
             */
            if (current
                    == destination) {

                break;
            }

            /*
             * =====================================
             * CHECK NEIGHBORS
             * =====================================
             */

            for (GraphEdge edge :
                    current.getEdges()) {

                GraphNode neighbor =
                        edge.getDestination();

                /*
                 * Skip finalized vertices.
                 */
                if (visited.contains(
                        neighbor)) {

                    continue;
                }

                /*
                 * Current shortest distance.
                 */
                int currentDistance =
                        distances.get(
                                current
                        );

                /*
                 * Calculate distance through
                 * the current vertex.
                 */
                long possibleDistance =
                        (long) currentDistance
                                + edge.getWeight();

                /*
                 * =================================
                 * RELAX EDGE
                 * =================================
                 */

                if (possibleDistance
                        < distances.get(
                                neighbor
                        )) {

                    int newDistance =
                            (int) possibleDistance;

                    /*
                     * Update shortest distance.
                     */
                    distances.put(
                            neighbor,
                            newDistance
                    );

                    /*
                     * Remember how we reached
                     * this vertex.
                     */
                    previous.put(
                            neighbor,
                            current
                    );

                    /*
                     * Add updated distance
                     * to priority queue.
                     */
                    queue.add(
                            new DijkstraEntry(
                                    neighbor,
                                    newDistance
                            )
                    );
                }
            }
        }

        /*
         * =========================================
         * NO PATH
         * =========================================
         */

        if (distances.get(
                destination)
                == Integer.MAX_VALUE) {

            animateNoPath(
                    visitOrder,
                    start,
                    destination
            );

            return;
        }

        /*
         * =========================================
         * BUILD SHORTEST PATH
         * =========================================
         */

        List<GraphNode> path =
                new ArrayList<>();

        GraphNode current =
                destination;

        while (current != null) {

            /*
             * Insert at beginning.
             */
            path.add(
                    0,
                    current
            );

            if (current == start) {

                break;
            }

            current =
                    previous.get(
                            current
                    );
        }

        /*
         * Animate algorithm.
         */
        animateDijkstra(
                visitOrder,
                path,
                distances.get(
                        destination
                )
        );
    }

    /*
     * =============================================
     * ANIMATE DIJKSTRA
     * =============================================
     */

    private void animateDijkstra(
            List<GraphNode> visitOrder,
            List<GraphNode> path,
            int totalDistance) {

        /*
         * Reset graph before animation.
         */
        resetNodeColors();

        Timeline timeline =
                new Timeline();

        /*
         * =========================================
         * VISIT VERTICES
         * =========================================
         */

        for (int i = 0;
             i < visitOrder.size();
             i++) {

            final int index =
                    i;

            KeyFrame frame =
                    new KeyFrame(
                            Duration.millis(
                                    index
                                            * ALGORITHM_DELAY
                            ),
                            e -> {

                                GraphNode current =
                                        visitOrder.get(
                                                index
                                        );

                                /*
                                 * Highlight each finalized
                                 * vertex while Dijkstra runs.
                                 */
                                current.setVisited(
                                        true
                                );

                                statusText.setText(
                                        "Dijkstra checking vertex "
                                                + current.getValue()
                                );
                            }
                    );

            timeline
                    .getKeyFrames()
                    .add(
                            frame
                    );
        }

        /*
         * =========================================
         * SHOW FINAL PATH
         * =========================================
         */

        double pathStartTime =
                visitOrder.size()
                        * ALGORITHM_DELAY;

        KeyFrame resetFrame =
                new KeyFrame(
                        Duration.millis(
                                pathStartTime
                        ),
                        e -> {

                            resetNodeColors();

                            statusText.setText(
                                    "Shortest path found."
                            );
                        }
                );

        timeline
                .getKeyFrames()
                .add(
                        resetFrame
                );

        /*
         * =========================================
         * PATH STRING
         * =========================================
         */

        StringBuilder pathText =
                new StringBuilder();

        /*
         * Animate the final path.
         */
        for (int i = 0;
             i < path.size();
             i++) {

            final int index =
                    i;

            KeyFrame frame =
                    new KeyFrame(
                            Duration.millis(
                                    pathStartTime
                                            + 300
                                            + index
                                            * ALGORITHM_DELAY
                            ),
                            e -> {

                                GraphNode current =
                                        path.get(
                                                index
                                        );

                                current.setVisited(
                                        true
                                );

                                if (pathText.length()
                                        > 0) {

                                    pathText.append(
                                            " → "
                                    );
                                }

                                pathText.append(
                                        current.getValue()
                                );

                                statusText.setText(
                                        "Shortest Path: "
                                                + pathText
                                );
                            }
                    );

            timeline
                    .getKeyFrames()
                    .add(
                            frame
                    );
        }

        /*
         * =========================================
         * FINISH
         * =========================================
         */

        KeyFrame finishFrame =
                new KeyFrame(
                        Duration.millis(
                                pathStartTime
                                        + 300
                                        + path.size()
                                        * ALGORITHM_DELAY
                        ),
                        e -> {

                            runningAlgorithm =
                                    false;

                            statusText.setText(
                                    "Shortest Path: "
                                            + pathText
                                            + " | Distance: "
                                            + totalDistance
                            );
                        }
                );

        timeline
                .getKeyFrames()
                .add(
                        finishFrame
                );

        timeline.play();
    }

    /*
     * =============================================
     * DIJKSTRA - NO PATH
     * =============================================
     */

    private void animateNoPath(
            List<GraphNode> visitOrder,
            GraphNode start,
            GraphNode destination) {

        resetNodeColors();

        Timeline timeline =
                new Timeline();

        /*
         * Show every reachable vertex
         * Dijkstra checked.
         */
        for (int i = 0;
             i < visitOrder.size();
             i++) {

            final int index =
                    i;

            KeyFrame frame =
                    new KeyFrame(
                            Duration.millis(
                                    index
                                            * ALGORITHM_DELAY
                            ),
                            e -> {

                                GraphNode current =
                                        visitOrder.get(
                                                index
                                        );

                                current.setVisited(
                                        true
                                );

                                statusText.setText(
                                        "Dijkstra checking vertex "
                                                + current.getValue()
                                );
                            }
                    );

            timeline
                    .getKeyFrames()
                    .add(
                            frame
                    );
        }

        /*
         * No route was found.
         */
        KeyFrame finishFrame =
                new KeyFrame(
                        Duration.millis(
                                visitOrder.size()
                                        * ALGORITHM_DELAY
                                        + 300
                        ),
                        e -> {

                            runningAlgorithm =
                                    false;

                            statusText.setText(
                                    "No path exists from "
                                            + start.getValue()
                                            + " to "
                                            + destination.getValue()
                                            + "."
                            );
                        }
                );

        timeline
                .getKeyFrames()
                .add(
                        finishFrame
                );

        timeline.play();
    }

    /*
     * =============================================
     * DIJKSTRA ENTRY
     * =============================================
     *
     * Stores a vertex and its current
     * distance inside the priority queue.
     */

    private static class DijkstraEntry {

        private final GraphNode node;

        private final int distance;

        public DijkstraEntry(
                GraphNode node,
                int distance) {

            this.node =
                    node;

            this.distance =
                    distance;
        }
    }

    /*
     * =============================================
     * CANCEL SELECTIONS
     * =============================================
     */

    private void cancelSelections() {

        selectingBFS =
                false;

        selectingDFS =
                false;

        selectingDijkstraStart =
                false;

        selectingDijkstraEnd =
                false;

        dijkstraStart =
                null;
    }

    /*
     * =============================================
     * RESET NODE COLORS
     * =============================================
     */

    private void resetNodeColors() {

        for (GraphNode node : nodes) {

            node.setSelected(
                    false
            );

            node.setVisited(
                    false
            );
        }
    }

    /*
     * =============================================
     * CLEAR NODE HANDLERS
     * =============================================
     */

    private void clearNodeHandlers() {

        for (GraphNode node : nodes) {

            node.setOnMouseClicked(
                    null
            );
        }
    }

    /*
     * =============================================
     * MESSAGE
     * =============================================
     */

    private void showMessage(
            String message) {

        drawGraph();

        statusText.setText(
                message
        );
    }

    /*
     * =============================================
     * DRAW GRAPH
     * =============================================
     */

    private void drawGraph() {

        canvas
                .getChildren()
                .clear();

        if (nodes == null
                || nodes.isEmpty()) {

            return;
        }

        /*
         * Draw edges before vertices.
         */
        drawEdges();

        /*
         * Draw vertices.
         */
        for (GraphNode node : nodes) {

            node.setSelected(
                    false
            );

            node.setVisited(
                    false
            );

            node.setOnMouseClicked(
                    null
            );

            canvas
                    .getChildren()
                    .add(
                            node
                    );
        }
    }

    /*
     * =============================================
     * DRAW WEIGHTED EDGES
     * =============================================
     */

    private void drawEdges() {

        /*
         * Since the graph is undirected,
         * every edge exists twice internally.
         */
        Set<String> drawnEdges =
                new HashSet<>();

        for (GraphNode node : nodes) {

            for (GraphEdge graphEdge :
                    node.getEdges()) {

                GraphNode neighbor =
                        graphEdge.getDestination();

                /*
                 * =================================
                 * EDGE ID
                 * =================================
                 */

                int smaller =
                        Math.min(
                                node.getValue(),
                                neighbor.getValue()
                        );

                int larger =
                        Math.max(
                                node.getValue(),
                                neighbor.getValue()
                        );

                String edgeID =
                        smaller
                                + "-"
                                + larger;

                /*
                 * Do not draw an undirected
                 * edge twice.
                 */
                if (drawnEdges.contains(
                        edgeID)) {

                    continue;
                }

                drawnEdges.add(
                        edgeID
                );

                /*
                 * =================================
                 * LINE
                 * =================================
                 */

                Line line =
                        new Line();

                line.startXProperty()
                        .bind(
                                node.layoutXProperty()
                        );

                line.startYProperty()
                        .bind(
                                node.layoutYProperty()
                        );

                line.endXProperty()
                        .bind(
                                neighbor.layoutXProperty()
                        );

                line.endYProperty()
                        .bind(
                                neighbor.layoutYProperty()
                        );

                line.setStrokeWidth(
                        2
                );

                /*
                 * =================================
                 * WEIGHT
                 * =================================
                 */

                Text weightText =
                        new Text(
                                String.valueOf(
                                        graphEdge.getWeight()
                                )
                        );

                weightText.setFont(
                        Font.font(
                                "Arial",
                                16
                        )
                );

                /*
                 * Horizontal center.
                 */
                weightText.xProperty()
                        .bind(
                                node.layoutXProperty()
                                        .add(
                                                neighbor.layoutXProperty()
                                        )
                                        .divide(
                                                2
                                        )
                        );

                /*
                 * Vertical center with a
                 * small upward offset.
                 */
                weightText.yProperty()
                        .bind(
                                node.layoutYProperty()
                                        .add(
                                                neighbor.layoutYProperty()
                                        )
                                        .divide(
                                                2
                                        )
                                        .subtract(
                                                8
                                        )
                        );

                /*
                 * Add line first.
                 */
                canvas
                        .getChildren()
                        .add(
                                line
                        );

                /*
                 * Then weight.
                 */
                canvas
                        .getChildren()
                        .add(
                                weightText
                        );
            }
        }
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