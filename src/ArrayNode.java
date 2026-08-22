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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

//Operates the Array Window 
public class ArrayNode extends Pane{
    public static final double SIDE_LENGTH = 30; 
    private Rectangle square = new Rectangle(SIDE_LENGTH, SIDE_LENGTH);
    private Text number;
    private int integer; 
    private IntNode next; 
    
    public ArrayNode(double x, double y, int sideLength, int value, boolean selected){
    this.integer = value;
    this.next = null;
    square = new Rectangle(x, y, sideLength, sideLength);
    square.setStroke(Color.BLACK);
    square.setFill(Color.WHITE); 
    if(selected){
        square.setStroke(Color.RED);
    }

    number = new Text(String.valueOf(value));
    number.setFont(new Font(18));
    number.setX(x - number.getLayoutBounds().getWidth() / 2);
    number.setY(y - number.getLayoutBounds().getHeight() / 2);

    getChildren().addAll(square, number);
    }

    public void setNext(IntNode var2)
    {
        this.next = var2;
    }

    public IntNode getNext(){
        return this.next; 
    }

    public double getCenterX(){
        return square.getX() + square.getWidth() / 2;
    }
    public double getCenterY(){
        return square.getY() + square.getHeight() / 2;
    }
    public int getValue(){
        return integer; 
    }
    public void setScale(double scale){
        square.setWidth(SIDE_LENGTH * scale);
        square.setHeight(SIDE_LENGTH * scale);
        number.setFont(new Font(18 * scale));
        number.setX(square.getX() + square.getWidth() / 2 - number.getLayoutBounds().getWidth() / 2);
        number.setY(square.getY() + square.getHeight() / 2 + number.getLayoutBounds().getHeight() / 4);
    }
    public void setSelected(boolean selected){
        if(selected){
            square.setStroke(Color.RED);
        } else {
            square.setStroke(Color.BLACK);
        }
    }
}


