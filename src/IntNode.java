import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class IntNode extends Pane {

    public static final double DEFAULT_RADIUS = 30;

    private final Circle circle;
    private final Text number;
    private final int integer;

    private IntNode next;

    public IntNode(
            double x,
            double y,
            double radius,
            int value,
            boolean selected) {

        integer = value;
        next = null;

        circle = new Circle(
            x,
            y,
            radius
        );

        circle.setFill(Color.WHITE);
        circle.setStroke(
            selected
                ? Color.RED
                : Color.BLACK
        );

        circle.setStrokeWidth(2);

        number = new Text(
            String.valueOf(value)
        );

        number.setFont(
            new Font("Consolas", 18)
        );

        setPosition(x, y);

        getChildren().addAll(
            circle,
            number
        );
    }

    public void setNext(IntNode next) {
        this.next = next;
    }

    public IntNode getNext() {
        return next;
    }

    public int getValue() {
        return integer;
    }

    public double getCenterX() {
        return circle.getCenterX();
    }

    public double getCenterY() {
        return circle.getCenterY();
    }

    public double getRadius() {
        return circle.getRadius();
    }

    public void setPosition(
            double x,
            double y) {

        circle.setCenterX(x);
        circle.setCenterY(y);

        number.setX(
            x
            - number.getLayoutBounds()
                    .getWidth() / 2
        );

        number.setY(
            y
            + number.getLayoutBounds()
                    .getHeight() / 4
        );
    }

    public void setScale(double scale) {
        circle.setRadius(
            DEFAULT_RADIUS * scale
        );

        number.setFont(
            new Font(
                "Consolas",
                18 * scale
            )
        );

        setPosition(
            circle.getCenterX(),
            circle.getCenterY()
        );
    }

    public void setSelected(boolean selected) {
        circle.setStroke(
            selected
                ? Color.RED
                : Color.BLACK
        );
    }
}   