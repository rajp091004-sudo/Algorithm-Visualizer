package Tree;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TreeCreation {

    /*
     * Maximum number of levels allowed.
     */
    private static final int MAX_HEIGHT = 6;

    private static TreeNode root;

    /*
     * Node that the next node will
     * be inserted underneath.
     */
    private static TreeNode selectedNode;

    private static final Pane canvas =
            new Pane();

    private static TextField input;

    private static Text status;

    private static Text selectedText;

    private static VBox rootCreationMenu;

    private static VBox insertMenu;

    public static TreeNode display() {

        /*
         * Reset everything whenever
         * TreeCreation opens.
         */
        root = null;
        selectedNode = null;

        canvas.getChildren().clear();

        /*
         * ============================
         * WINDOW
         * ============================
         */

        Stage window = new Stage();

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        window.setTitle(
                "Tree Creation"
        );

        window.setMinWidth(1000);
        window.setMinHeight(700);

        /*
         * ============================
         * MAIN LAYOUT
         * ============================
         */

        BorderPane layout =
                new BorderPane();

        /*
         * ============================
         * CANVAS
         * ============================
         */

        canvas.setPrefSize(
                1000,
                450
        );

        layout.setCenter(
                canvas
        );

        /*
         * ============================
         * ROOT CREATION
         * ============================
         */

        Text rootTitle =
                new Text("Create Root");

        rootTitle.setFont(
                Font.font(
                        "Arial",
                        25
                )
        );

        TextField rootInput =
                new TextField();

        rootInput.setPromptText(
                "Enter Root Value"
        );

        rootInput.setMaxWidth(
                250
        );

        Button createRoot =
                new Button("Create Root");

        setButtonSize(
                createRoot
        );

        rootCreationMenu =
                new VBox(15);

        rootCreationMenu.setAlignment(
                Pos.CENTER
        );

        rootCreationMenu.setPadding(
                new Insets(20)
        );

        rootCreationMenu.getChildren().addAll(
                rootTitle,
                rootInput,
                createRoot
        );

        /*
         * ============================
         * INSERT MENU
         * ============================
         */

        Text insertTitle =
                new Text("Insert Node");

        insertTitle.setFont(
                Font.font(
                        "Arial",
                        25
                )
        );

        selectedText =
                new Text(
                        "Selected Parent: None"
                );

        selectedText.setFont(
                Font.font(
                        "Arial",
                        18
                )
        );

        input = new TextField();

        input.setPromptText(
                "Enter Node Value"
        );

        input.setMaxWidth(
                250
        );

        Button leftButton =
                new Button("Insert Left");

        Button rightButton =
                new Button("Insert Right");

        Button finishButton =
                new Button("Finish");

        setButtonSize(
                leftButton
        );

        setButtonSize(
                rightButton
        );

        setButtonSize(
                finishButton
        );

        /*
         * Left / Right buttons
         */
        HBox directionButtons =
                new HBox(15);

        directionButtons.setAlignment(
                Pos.CENTER
        );

        directionButtons.getChildren().addAll(
                leftButton,
                rightButton
        );

        /*
         * Status message
         */
        status =
                new Text(
                        "Select where the next node should go."
                );

        status.setFont(
                Font.font(
                        "Arial",
                        16
                )
        );

        /*
         * Insert menu
         */
        insertMenu =
                new VBox(15);

        insertMenu.setAlignment(
                Pos.CENTER
        );

        insertMenu.setPadding(
                new Insets(20)
        );

        insertMenu.getChildren().addAll(
                insertTitle,
                selectedText,
                input,
                directionButtons,
                status,
                finishButton
        );

        /*
         * Hide insert controls until
         * root exists.
         */
        insertMenu.setVisible(
                false
        );

        insertMenu.setManaged(
                false
        );

        /*
         * ============================
         * BOTTOM MENU
         * ============================
         */

        VBox bottomMenu =
                new VBox();

        bottomMenu.setAlignment(
                Pos.CENTER
        );

        bottomMenu.getChildren().addAll(
                rootCreationMenu,
                insertMenu
        );

        layout.setBottom(
                bottomMenu
        );

        /*
         * ============================
         * CREATE ROOT ACTION
         * ============================
         */

        createRoot.setOnAction(e -> {

            try {

                int value =
                        Integer.parseInt(
                                rootInput.getText()
                        );

                /*
                 * Create root.
                 */
                root =
                        new TreeNode(value);

                /*
                 * Root automatically
                 * becomes selected.
                 */
                selectedNode =
                        root;

                selectedNode.setSelected(
                        true
                );

                /*
                 * Hide root menu.
                 */
                rootCreationMenu.setVisible(
                        false
                );

                rootCreationMenu.setManaged(
                        false
                );

                /*
                 * Show insertion menu.
                 */
                insertMenu.setVisible(
                        true
                );

                insertMenu.setManaged(
                        true
                );

                selectedText.setText(
                        "Selected Parent: "
                                + selectedNode.value
                                + " | Level: 1"
                );

                status.setText(
                        "Root created."
                );

                drawTree();

            }
            catch (NumberFormatException exception) {

                rootInput.clear();

                rootInput.setPromptText(
                        "Enter a valid integer"
                );
            }
        });

        /*
         * Pressing Enter creates root.
         */
        rootInput.setOnAction(e -> {

            createRoot.fire();

        });

        /*
         * ============================
         * INSERT LEFT
         * ============================
         */

        leftButton.setOnAction(e -> {

            if (selectedNode == null) {

                status.setText(
                        "Select a parent node first."
                );

                return;
            }

            /*
             * Check current node's level.
             */
            int depth =
                    getDepth(
                            root,
                            selectedNode,
                            1
                    );

            /*
             * Nodes on level 6 cannot
             * have children.
             */
            if (depth >= MAX_HEIGHT) {

                status.setText(
                        "Maximum tree height of "
                                + MAX_HEIGHT
                                + " reached."
                );

                return;
            }

            try {

                int value =
                        Integer.parseInt(
                                input.getText()
                        );

                /*
                 * Don't overwrite an
                 * existing child.
                 */
                if (selectedNode.left != null) {

                    status.setText(
                            "This node already has a left child."
                    );

                    return;
                }

                /*
                 * Remember parent before
                 * changing selectedNode.
                 */
                TreeNode parent =
                        selectedNode;

                TreeNode newNode =
                        new TreeNode(value);

                /*
                 * Connect child.
                 */
                parent.left =
                        newNode;

                /*
                 * Newly created node becomes
                 * selected automatically.
                 */
                selectNode(
                        newNode
                );

                input.clear();

                status.setText(
                        value
                                + " inserted to the left of "
                                + parent.value
                );

                drawTree();

            }
            catch (NumberFormatException exception) {

                input.clear();

                status.setText(
                        "Enter a valid integer."
                );
            }
        });

        /*
         * ============================
         * INSERT RIGHT
         * ============================
         */

        rightButton.setOnAction(e -> {

            if (selectedNode == null) {

                status.setText(
                        "Select a parent node first."
                );

                return;
            }

            int depth =
                    getDepth(
                            root,
                            selectedNode,
                            1
                    );

            if (depth >= MAX_HEIGHT) {

                status.setText(
                        "Maximum tree height of "
                                + MAX_HEIGHT
                                + " reached."
                );

                return;
            }

            try {

                int value =
                        Integer.parseInt(
                                input.getText()
                        );

                /*
                 * Don't overwrite an
                 * existing right child.
                 */
                if (selectedNode.right != null) {

                    status.setText(
                            "This node already has a right child."
                    );

                    return;
                }

                TreeNode parent =
                        selectedNode;

                TreeNode newNode =
                        new TreeNode(value);

                /*
                 * Connect child.
                 */
                parent.right =
                        newNode;

                /*
                 * Newly created node becomes
                 * selected automatically.
                 */
                selectNode(
                        newNode
                );

                input.clear();

                status.setText(
                        value
                                + " inserted to the right of "
                                + parent.value
                );

                drawTree();

            }
            catch (NumberFormatException exception) {

                input.clear();

                status.setText(
                        "Enter a valid integer."
                );
            }
        });

        /*
         * ============================
         * FINISH
         * ============================
         */

        finishButton.setOnAction(e -> {

            window.close();

        });

        /*
         * ============================
         * SCENE
         * ============================
         */

        Scene scene =
                new Scene(
                        layout,
                        1000,
                        700
                );

        scene.getStylesheets().add(
                "style.css"
        );

        window.setScene(
                scene
        );

        window.showAndWait();

        /*
         * Return finished tree.
         */
        return root;
    }

    /*
     * ==================================================
     * SELECT NODE
     * ==================================================
     */

    private static void selectNode(
            TreeNode node) {

        /*
         * Remove previous selection.
         */
        if (selectedNode != null) {

            selectedNode.setSelected(
                    false
            );
        }

        /*
         * Change selection.
         */
        selectedNode =
                node;

        /*
         * Highlight selected node.
         */
        selectedNode.setSelected(
                true
        );

        /*
         * Find selected node's level.
         */
        int depth =
                getDepth(
                        root,
                        selectedNode,
                        1
                );

        /*
         * Update selected parent text.
         */
        selectedText.setText(
                "Selected Parent: "
                        + selectedNode.value
                        + " | Level: "
                        + depth
        );
    }

    /*
     * ==================================================
     * GET DEPTH
     * ==================================================
     *
     * Finds what level a particular
     * TreeNode is located on.
     */

    private static int getDepth(
            TreeNode current,
            TreeNode target,
            int depth) {

        if (current == null) {

            return -1;

        }

        /*
         * Found the target.
         */
        if (current == target) {

            return depth;

        }

        /*
         * Search left.
         */
        int leftDepth =
                getDepth(
                        current.left,
                        target,
                        depth + 1
                );

        if (leftDepth != -1) {

            return leftDepth;

        }

        /*
         * Search right.
         */
        return getDepth(
                current.right,
                target,
                depth + 1
        );
    }

    /*
     * ==================================================
     * DRAW TREE
     * ==================================================
     */

    private static void drawTree() {

        canvas.getChildren().clear();

        if (root == null) {

            return;

        }

        /*
         * Root stays in the center.
         */
        double startX =
                canvas.getWidth() / 2;

        /*
         * JavaFX may not have calculated
         * width yet.
         */
        if (startX <= 0) {

            startX = 500;

        }

        /*
         * Because the tree is limited
         * to six levels, we can use
         * predictable spacing.
         */
        double startingOffset =
                220;

        drawTreeRecursive(
                root,
                startX,
                50,
                startingOffset
        );
    }

    /*
     * ==================================================
     * DRAW TREE RECURSIVELY
     * ==================================================
     */

    private static void drawTreeRecursive(
            TreeNode node,
            double x,
            double y,
            double xOffset) {

        if (node == null) {

            return;

        }

        /*
         * ============================
         * LEFT CHILD
         * ============================
         */

        if (node.left != null) {

            double childX =
                    x - xOffset;

            double childY =
                    y + 70;

            Line line =
                    new Line(
                            x,
                            y,
                            childX,
                            childY
                    );

            line.setStrokeWidth(
                    2
            );

            canvas.getChildren().add(
                    line
            );

            drawTreeRecursive(
                    node.left,
                    childX,
                    childY,
                    xOffset / 2
            );
        }

        /*
         * ============================
         * RIGHT CHILD
         * ============================
         */

        if (node.right != null) {

            double childX =
                    x + xOffset;

            double childY =
                    y + 70;

            Line line =
                    new Line(
                            x,
                            y,
                            childX,
                            childY
                    );

            line.setStrokeWidth(
                    2
            );

            canvas.getChildren().add(
                    line
            );

            drawTreeRecursive(
                    node.right,
                    childX,
                    childY,
                    xOffset / 2
            );
        }

        /*
         * ============================
         * CURRENT NODE
         * ============================
         */

        node.setLayoutX(
                x
        );

        node.setLayoutY(
                y
        );

        /*
         * Keep nodes at normal size.
         */
        node.setScaleX(
                1
        );

        node.setScaleY(
                1
        );

        /*
         * Make node clickable.
         */
        node.setOnMouseClicked(e -> {

            selectNode(
                    node
            );

            int depth =
                    getDepth(
                            root,
                            node,
                            1
                    );

            if (depth >= MAX_HEIGHT) {

                status.setText(
                        node.value
                                + " selected. Maximum level reached."
                );

            }
            else {

                status.setText(
                        node.value
                                + " selected as parent."
                );
            }
        });

        canvas.getChildren().add(
                node
        );
    }

    /*
     * ==================================================
     * BUTTON SIZE
     * ==================================================
     */

    private static void setButtonSize(
            Button button) {

        button.setMinSize(
                180,
                50
        );

        button.setMaxSize(
                180,
                50
        );

        button.setPrefSize(
                180,
                50
        );
    }
}