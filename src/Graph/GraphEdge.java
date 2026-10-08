package Graph;

public class GraphEdge {

    /*
     * Vertex this edge connects to.
     */
    private final GraphNode destination;

    /*
     * Cost / weight of the edge.
     */
    private final int weight;

    public GraphEdge(
            GraphNode destination,
            int weight) {

        this.destination =
                destination;

        this.weight =
                weight;
    }

    /*
     * =============================================
     * GET DESTINATION
     * =============================================
     */

    public GraphNode getDestination() {

        return destination;
    }

    /*
     * =============================================
     * GET WEIGHT
     * =============================================
     */

    public int getWeight() {

        return weight;
    }
}