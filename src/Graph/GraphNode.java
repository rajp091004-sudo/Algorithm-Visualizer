package Graph;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GraphNode extends Pane {

    /*
     * =============================================
     * VALUE
     * =============================================
     */

    private final int value;

    /*
     * =============================================
     * EDGES
     * =============================================
     */

    private final List<GraphEdge> edges;

    /*
     * =============================================
     * VISUAL COMPONENTS
     * =============================================
     */

    private final Circle circle;

    private final Text text;

    /*
     * =============================================
     * CONSTRUCTOR
     * =============================================
     */

    public GraphNode(
            int value,
            double x,
            double y) {

        this.value =
                value;

        edges =
                new ArrayList<>();

        /*
         * =========================================
         * CIRCLE
         * =========================================
         */

        circle =
                new Circle(
                        30
                );

        circle.setFill(
                Color.WHITE
        );

        circle.setStroke(
                Color.BLACK
        );

        circle.setStrokeWidth(
                2
        );

        /*
         * =========================================
         * TEXT
         * =========================================
         */

        text =
                new Text(
                        String.valueOf(
                                value
                        )
                );

        text.setFont(
                Font.font(
                        "Arial",
                        18
                )
        );

        text.setX(
                -text
                        .getLayoutBounds()
                        .getWidth()
                        / 2
        );

        text.setY(
                text
                        .getLayoutBounds()
                        .getHeight()
                        / 4
        );

        /*
         * =========================================
         * POSITION
         * =========================================
         */

        setLayoutX(
                x
        );

        setLayoutY(
                y
        );

        /*
         * =========================================
         * ADD VISUALS
         * =========================================
         */

        getChildren().addAll(
                circle,
                text
        );
    }

    /*
     * =============================================
     * ADD EDGE
     * =============================================
     */

    public void addEdge(
            GraphNode destination,
            int weight) {

        /*
         * Prevent duplicate edges.
         */
        if (isConnectedTo(
                destination)) {

            return;
        }

        GraphEdge edge =
                new GraphEdge(
                        destination,
                        weight
                );

        edges.add(
                edge
        );
    }

    /*
     * =============================================
     * IS CONNECTED
     * =============================================
     */

    public boolean isConnectedTo(
            GraphNode node) {

        for (GraphEdge edge : edges) {

            if (edge.getDestination()
                    == node) {

                return true;
            }
        }

        return false;
    }

    /*
     * =============================================
     * GET EDGES
     * =============================================
     */

    public List<GraphEdge> getEdges() {

        return edges;
    }

    /*
     * =============================================
     * GET VALUE
     * =============================================
     */

    public int getValue() {

        return value;
    }

    /*
     * =============================================
     * SELECTED
     * =============================================
     */

    public void setSelected(
            boolean selected) {

        if (selected) {

            circle.setStroke(
                    Color.BLUE
            );

            circle.setStrokeWidth(
                    4
            );
        }
        else {

            circle.setStroke(
                    Color.BLACK
            );

            circle.setStrokeWidth(
                    2
            );
        }
    }

    /*
     * =============================================
     * VISITED
     * =============================================
     */

    public void setVisited(
            boolean visited) {

        if (visited) {

            circle.setFill(
                    Color.LIGHTGREEN
            );
        }
        else {

            circle.setFill(
                    Color.WHITE
            );
        }
    }
}