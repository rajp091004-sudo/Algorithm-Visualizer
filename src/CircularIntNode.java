public class CircularIntNode extends IntNode {

    public CircularIntNode(
            double x,
            double y,
            double radius,
            int value,
            boolean selected) {

        super(
            x,
            y,
            radius,
            value,
            selected
        );
    }

    @Override
    public CircularIntNode getNext() {
        return (CircularIntNode) super.getNext();
    }

    public void setNext(CircularIntNode next) {
        super.setNext(next);
    }
}