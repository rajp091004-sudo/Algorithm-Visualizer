import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArrayCreation {

    public static List<Integer> display() {

        List<Integer> values = new ArrayList<>();

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Array Creation");

        window.setMinWidth(500);
        window.setMinHeight(400);

        // Main menu
        VBox menu = new VBox(20);

        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));

        // Title
        Label title = new Label("Create Array");
        title.setFont(new Font("Verdana", 30));

        Label instructions = new Label(
                "How would you like to create your array?");

        instructions.setFont(new Font("Verdana", 16));

        // Buttons
        Button customButton = new Button("Custom Array");
        Button randomButton = new Button("Random Array");

        customButton.setPrefSize(150, 50);
        randomButton.setPrefSize(150, 50);

        HBox selectionButtons = new HBox(20);

        selectionButtons.setAlignment(Pos.CENTER);

        selectionButtons.getChildren().addAll(
                customButton,
                randomButton);

        menu.getChildren().addAll(
                title,
                instructions,
                selectionButtons);

        // ========================================
        // CUSTOM ARRAY
        // ========================================

        customButton.setOnAction(e -> {

            menu.getChildren().clear();

            Label customTitle =
                    new Label("Custom Array");

            customTitle.setFont(
                    new Font("Verdana", 30));

            Label customInstructions =
                    new Label(
                            "Enter a number to add it to the array.");

            TextField input =
                    new TextField();

            input.setPromptText(
                    "Enter an integer");

            input.setMaxWidth(250);

            Label currentArray =
                    new Label(
                            "Current Array:\n" +
                            values.toString());

            currentArray.setFont(
                    new Font("Verdana", 20));

            Button addButton =
                    new Button("Add");

            Button finishButton =
                    new Button("Finish");

            Button backButton =
                    new Button("Back");

            addButton.setPrefSize(100, 35);
            finishButton.setPrefSize(100, 35);
            backButton.setPrefSize(100, 35);

            // Add number
            addButton.setOnAction(event -> {

                try {

                    int value =
                            Integer.parseInt(
                                    input.getText().trim());

                    values.add(value);

                    currentArray.setText(
                            "Current Array:\n" +
                            values.toString());

                    input.clear();

                    input.setPromptText(
                            "Enter an integer");

                } catch (NumberFormatException ex) {

                    input.clear();

                    input.setPromptText(
                            "Please enter a valid integer");
                }
            });

            // Finish custom array
            finishButton.setOnAction(event -> {

                if (!values.isEmpty()) {
                    window.close();
                }

            });

            // Go back
            backButton.setOnAction(event -> {

                values.clear();

                menu.getChildren().clear();

                menu.getChildren().addAll(
                        title,
                        instructions,
                        selectionButtons);
            });

            HBox buttons =
                    new HBox(10);

            buttons.setAlignment(Pos.CENTER);

            buttons.getChildren().addAll(
                    addButton,
                    finishButton,
                    backButton);

            menu.getChildren().addAll(
                    customTitle,
                    customInstructions,
                    input,
                    currentArray,
                    buttons);
        });

        // ========================================
        // RANDOM ARRAY
        // ========================================

        randomButton.setOnAction(e -> {

            menu.getChildren().clear();

            Label randomTitle =
                    new Label("Random Array");

            randomTitle.setFont(
                    new Font("Verdana", 30));

            Label randomInstructions =
                    new Label(
                            "Enter the size of the array.");

            TextField input =
                    new TextField();

            input.setPromptText(
                    "Array size");

            input.setMaxWidth(250);

            Button createButton =
                    new Button("Create");

            Button backButton =
                    new Button("Back");

            createButton.setPrefSize(100, 35);
            backButton.setPrefSize(100, 35);

            // Generate random array
            createButton.setOnAction(event -> {

                try {

                    int length =
                            Integer.parseInt(
                                    input.getText().trim());

                    // Don't allow invalid sizes
                    if (length <= 0) {

                        input.clear();

                        input.setPromptText(
                                "Size must be greater than 0");

                        return;
                    }

                    values.clear();

                    Random random =
                            new Random();

                    for (int i = 0;
                         i < length;
                         i++) {

                        values.add(
                                random.nextInt(100) + 1);
                    }

                    window.close();

                } catch (NumberFormatException ex) {

                    input.clear();

                    input.setPromptText(
                            "Please enter a valid size");
                }

            });

            // Go back
            backButton.setOnAction(event -> {

                menu.getChildren().clear();

                menu.getChildren().addAll(
                        title,
                        instructions,
                        selectionButtons);
            });

            HBox buttons =
                    new HBox(10);

            buttons.setAlignment(Pos.CENTER);

            buttons.getChildren().addAll(
                    createButton,
                    backButton);

            menu.getChildren().addAll(
                    randomTitle,
                    randomInstructions,
                    input,
                    buttons);
        });

        // ========================================
        // WINDOW
        // ========================================

        Scene scene =
                new Scene(menu, 500, 400);

        window.setScene(scene);

        window.showAndWait();

        return values;
    }
}