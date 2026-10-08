package Tree;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TreeNode extends Pane {

    int value;

    TreeNode left;
    TreeNode right;

    private final Circle circle;
    private final Text text;

    public TreeNode(int value) {

        this.value = value;

        left = null;
        right = null;

        circle = new Circle(30);

        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);

        text = new Text(
                String.valueOf(value)
        );

        text.setFont(
                Font.font("Arial", 18)
        );

        text.setX(
                -text.getLayoutBounds().getWidth() / 2
        );

        text.setY(
                text.getLayoutBounds().getHeight() / 4
        );

        getChildren().addAll(
                circle,
                text
        );
    }

    public void setSelected(boolean selected) {

        if (selected) {

            circle.setStroke(Color.BLUE);
            circle.setStrokeWidth(4);

        }
        else {

            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);
        }
    }
}