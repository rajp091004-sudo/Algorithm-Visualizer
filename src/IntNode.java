import javax.swing.text.LabelView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

@SuppressWarnings("unused")
public class IntNode extends Pane {
    public static final double DEFAULT_RADIUS = 30; 
    
    private Circle circle = new Circle();
    private Text number;
    private int integer; 
    private IntNode next; 
    
    public IntNode(double x, double y, double radius, int value, boolean selected){
    this.integer = value;
    this.next = null;
    circle = new Circle(x, y, radius);
    circle.setStroke(Color.BLACK);
    circle.setFill(Color.WHITE); 
    if(selected){
        circle.setStroke(Color.RED);
    }

    number = new Text(String.valueOf(value));
    number.setFont(new Font(18));
    number.setX(x - number.getLayoutBounds().getWidth() / 2);
    number.setY(y - number.getLayoutBounds().getHeight() / 2);

    getChildren().addAll(circle, number);
    }

    public void setNext(IntNode var2)
    {
        this.next = var2;
    }

    public IntNode getNext(){
        return this.next; 
    }

    public double getCenterX(){
        return circle.getCenterX();
    }
    public double getCenterY(){
        return circle.getCenterY();
    }
    public int getValue(){
        return integer; 
    }
    public void setScale(double scale){
        circle.setRadius(DEFAULT_RADIUS * scale);
        number.setFont(new Font(18 * scale));
        number.setX(circle.getCenterX() - number.getLayoutBounds().getWidth() / 2);
        number.setY(circle.getCenterY() + number.getLayoutBounds().getHeight() / 4);
    }
    public void setSelected(boolean selected){
        if(selected){
            circle.setStroke(Color.RED);
        } else {
            circle.setStroke(Color.BLACK);
        }
    }
}
