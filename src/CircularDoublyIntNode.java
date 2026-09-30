public class CircularDoublyIntNode extends DoublyIntNode {

    public CircularDoublyIntNode(
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
    public CircularDoublyIntNode getNext() {
        return (CircularDoublyIntNode) super.getNext();
    }

    @Override
    public CircularDoublyIntNode getPrevious() {
        return (CircularDoublyIntNode) super.getPrevious();
    }

    public void setNext(CircularDoublyIntNode next) {
        super.setNext(next);
    }

    public void setPrevious(CircularDoublyIntNode previous) {
        super.setPrevious(previous);
    }
}