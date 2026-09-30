public class DoublyIntNode extends IntNode {

    private DoublyIntNode previous;

    public DoublyIntNode(
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

        previous = null;
    }

    public void setPrevious(DoublyIntNode previous) {
        this.previous = previous;
    }

    public DoublyIntNode getPrevious() {
        return previous;
    }

    @Override
    public DoublyIntNode getNext() {
        return (DoublyIntNode) super.getNext();
    }

    public void setNext(DoublyIntNode next) {
        super.setNext(next);
    }
}