package LinkedLists;
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

public class NodeCreation {

    private static LinkedListType selectedType;

    public static LinkedListData display() {

        selectedType = null;

        List<Integer> values = new ArrayList<>();

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Linked List Creation");

        window.setMinWidth(600);
        window.setMinHeight(450);

        VBox menu = new VBox(20);

        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));

        // ========================================
        // MAIN MENU
        // ========================================

        showTypeSelection(
                menu,
                window,
                values
        );

        Scene scene =
                new Scene(menu, 600, 450);

        window.setScene(scene);

        window.showAndWait();

        return new LinkedListData(
                values,
                selectedType
        );
    }

    // ========================================
    // TYPE SELECTION
    // ========================================

    private static void showTypeSelection(
            VBox menu,
            Stage window,
            List<Integer> values) {

        menu.getChildren().clear();

        Label title =
                new Label("Create Linked List");

        title.setFont(
                new Font("Verdana", 30)
        );

        Label instructions =
                new Label(
                        "What type of linked list would you like to create?"
                );

        instructions.setFont(
                new Font("Verdana", 16)
        );

        Button singlyButton =
                new Button("Singly Linked List");

        Button doublyButton =
                new Button("Doubly Linked List");

        Button circularSinglyButton =
                new Button("Circular Singly List");

        Button circularDoublyButton =
                new Button("Circular Doubly List");

        singlyButton.setPrefSize(200, 60);
        doublyButton.setPrefSize(200, 60);

        circularSinglyButton.setPrefSize(200, 60);
        circularDoublyButton.setPrefSize(200, 60);

        HBox firstRow =
                new HBox(20);

        firstRow.setAlignment(
                Pos.CENTER
        );

        firstRow.getChildren().addAll(
                singlyButton,
                doublyButton
        );

        HBox secondRow =
                new HBox(20);

        secondRow.setAlignment(
                Pos.CENTER
        );

        secondRow.getChildren().addAll(
                circularSinglyButton,
                circularDoublyButton
        );

        menu.getChildren().addAll(
                title,
                instructions,
                firstRow,
                secondRow
        );

        // ========================================
        // SINGLY
        // ========================================

        singlyButton.setOnAction(e -> {

            selectedType =
                    LinkedListType.SINGLY;

            showCreationOptions(
                    menu,
                    window,
                    values,
                    "Singly Linked List"
            );
        });

        // ========================================
        // DOUBLY
        // ========================================

        doublyButton.setOnAction(e -> {

            selectedType =
                    LinkedListType.DOUBLY;

            showCreationOptions(
                    menu,
                    window,
                    values,
                    "Doubly Linked List"
            );
        });

        // ========================================
        // CIRCULAR SINGLY
        // ========================================

        circularSinglyButton.setOnAction(e -> {

            selectedType =
                    LinkedListType.CIRCULAR_SINGLY;

            showCreationOptions(
                    menu,
                    window,
                    values,
                    "Circular Singly Linked List"
            );
        });

        // ========================================
        // CIRCULAR DOUBLY
        // ========================================

        circularDoublyButton.setOnAction(e -> {

            selectedType =
                    LinkedListType.CIRCULAR_DOUBLY;

            showCreationOptions(
                    menu,
                    window,
                    values,
                    "Circular Doubly Linked List"
            );
        });
    }

    // ========================================
    // CREATION OPTIONS
    // ========================================

    private static void showCreationOptions(
            VBox menu,
            Stage window,
            List<Integer> values,
            String listType) {

        menu.getChildren().clear();

        Label title =
                new Label(listType);

        title.setFont(
                new Font("Verdana", 30)
        );

        Label instructions =
                new Label(
                        "How would you like to create your linked list?"
                );

        instructions.setFont(
                new Font("Verdana", 16)
        );

        Button customButton =
                new Button("Custom List");

        Button randomButton =
                new Button("Random List");

        Button backButton =
                new Button("Back");

        customButton.setPrefSize(150, 50);
        randomButton.setPrefSize(150, 50);
        backButton.setPrefSize(150, 50);

        HBox buttons =
                new HBox(20);

        buttons.setAlignment(
                Pos.CENTER
        );

        buttons.getChildren().addAll(
                customButton,
                randomButton
        );

        menu.getChildren().addAll(
                title,
                instructions,
                buttons,
                backButton
        );

        // ========================================
        // CUSTOM
        // ========================================

        customButton.setOnAction(e -> {

            showCustomCreation(
                    menu,
                    window,
                    values,
                    listType
            );
        });

        // ========================================
        // RANDOM
        // ========================================

        randomButton.setOnAction(e -> {

            showRandomCreation(
                    menu,
                    window,
                    values,
                    listType
            );
        });

        // ========================================
        // BACK
        // ========================================

        backButton.setOnAction(e -> {

            values.clear();

            selectedType = null;

            showTypeSelection(
                    menu,
                    window,
                    values
            );
        });
    }

    // ========================================
    // CUSTOM CREATION
    // ========================================

    private static void showCustomCreation(
            VBox menu,
            Stage window,
            List<Integer> values,
            String listType) {

        menu.getChildren().clear();

        Label title =
                new Label("Create " + listType);

        title.setFont(
                new Font("Verdana", 30)
        );

        Label instructions =
                new Label(
                        "Enter a number to create a node."
                );

        TextField input =
                new TextField();

        input.setPromptText(
                "Enter an integer"
        );

        input.setMaxWidth(250);

        Label currentList =
                new Label(
                        "Current List:\n"
                                + values.toString()
                );

        currentList.setFont(
                new Font("Verdana", 20)
        );

        Button addButton =
                new Button("Add");

        Button finishButton =
                new Button("Finish");

        Button backButton =
                new Button("Back");

        addButton.setPrefSize(100, 35);
        finishButton.setPrefSize(100, 35);
        backButton.setPrefSize(100, 35);

        // ========================================
        // ADD
        // ========================================

        addButton.setOnAction(e -> {

            try {

                int value =
                        Integer.parseInt(
                                input
                                        .getText()
                                        .trim()
                        );

                values.add(value);

                currentList.setText(
                        "Current List:\n"
                                + values.toString()
                );

                input.clear();

                input.setPromptText(
                        "Enter an integer"
                );

            } catch (NumberFormatException ex) {

                input.clear();

                input.setPromptText(
                        "Please enter a valid integer"
                );
            }
        });

        // ========================================
        // FINISH
        // ========================================

        finishButton.setOnAction(e -> {

            if (!values.isEmpty()) {

                window.close();

            }
        });

        // ========================================
        // BACK
        // ========================================

        backButton.setOnAction(e -> {

            values.clear();

            showCreationOptions(
                    menu,
                    window,
                    values,
                    listType
            );
        });

        HBox buttons =
                new HBox(10);

        buttons.setAlignment(
                Pos.CENTER
        );

        buttons.getChildren().addAll(
                addButton,
                finishButton,
                backButton
        );

        menu.getChildren().addAll(
                title,
                instructions,
                input,
                currentList,
                buttons
        );
    }

    // ========================================
    // RANDOM CREATION
    // ========================================

    private static void showRandomCreation(
            VBox menu,
            Stage window,
            List<Integer> values,
            String listType) {

        menu.getChildren().clear();

        Label title =
                new Label("Create " + listType);

        title.setFont(
                new Font("Verdana", 30)
        );

        Label instructions =
                new Label(
                        "Enter the number of nodes."
                );

        TextField input =
                new TextField();

        input.setPromptText(
                "Number of nodes"
        );

        input.setMaxWidth(250);

        Button createButton =
                new Button("Create");

        Button backButton =
                new Button("Back");

        createButton.setPrefSize(100, 35);
        backButton.setPrefSize(100, 35);

        // ========================================
        // CREATE
        // ========================================

        createButton.setOnAction(e -> {

            try {

                int length =
                        Integer.parseInt(
                                input
                                        .getText()
                                        .trim()
                        );

                if (length <= 0) {

                    input.clear();

                    input.setPromptText(
                            "Size must be greater than 0"
                    );

                    return;
                }

                values.clear();

                Random random =
                        new Random();

                for (int i = 0;
                     i < length;
                     i++) {

                    values.add(
                            random.nextInt(100) + 1
                    );
                }

                window.close();

            } catch (NumberFormatException ex) {

                input.clear();

                input.setPromptText(
                        "Please enter a valid size"
                );
            }
        });

        // ========================================
        // BACK
        // ========================================

        backButton.setOnAction(e -> {

            values.clear();

            showCreationOptions(
                    menu,
                    window,
                    values,
                    listType
            );
        });

        HBox buttons =
                new HBox(10);

        buttons.setAlignment(
                Pos.CENTER
        );

        buttons.getChildren().addAll(
                createButton,
                backButton
        );

        menu.getChildren().addAll(
                title,
                instructions,
                input,
                buttons
        );
    }
}