package Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TreeScreen {

    /*
     * Root of the tree.
     */
    private TreeNode root;

    /*
     * Currently selected node.
     */
    private TreeNode selectedNode;

    /*
     * True while inserting.
     */
    private boolean inserting = false;

    /*
     * True while deleting.
     */
    private boolean deleting = false;

    /*
     * True while sorting.
     */
    private boolean sorting = false;

    /*
     * Maximum tree height.
     */
    private static final int MAX_HEIGHT = 6;

    /*
     * Horizontal padding.
     */
    private static final double SIDE_PADDING = 50;

    /*
     * Vertical space between levels.
     */
    private static final double LEVEL_GAP = 80;

    /*
     * Delay between each node
     * during the sort animation.
     */
    private static final double SORT_DELAY = 500;

    /*
     * Tree drawing area.
     */
    private final Pane canvas =
            new Pane();

    /*
     * Main layout.
     */
    private final BorderPane layout =
            new BorderPane();

    /*
     * =============================================
     * CREATE SCREEN
     * =============================================
     */

    public Scene create(
            Stage stage,
            Scene selectScene) {

        Scene scene =
                new Scene(
                        layout,
                        1280,
                        700
                );

        scene.getStylesheets().add(
                "style.css"
        );

        /*
         * =========================================
         * CREATE TREE
         * =========================================
         */

        Button createTree =
                new Button(
                        "Create Tree"
                );

        setButtonSize(
                createTree
        );

        createTree.setOnAction(e -> {

            if (sorting) {
                return;
            }

            root =
                    TreeCreation.display();

            inserting = false;
            deleting = false;
            selectedNode = null;

            if (root != null) {

                drawTree();
            }
        });

        /*
         * =========================================
         * INSERT NODE
         * =========================================
         */

        Button insertNode =
                new Button(
                        "Insert Node"
                );

        setButtonSize(
                insertNode
        );

        insertNode.setOnAction(e -> {

            if (sorting) {
                return;
            }

            if (root == null) {

                showMessage(
                        "Create a tree first."
                );

                return;
            }

            /*
             * Turn off deletion mode.
             */
            deleting = false;

            /*
             * Remove previous selection.
             */
            if (selectedNode != null) {

                selectedNode.setSelected(
                        false
                );
            }

            selectedNode = null;

            /*
             * Remove previous event handlers
             * and instructions.
             */
            drawTree();

            /*
             * Begin insertion mode.
             */
            inserting = true;

            enableInsertSelection(
                    root
            );

            showInsertInstructions();
        });

        /*
         * =========================================
         * DELETE NODE
         * =========================================
         */

        Button deleteNode =
                new Button(
                        "Delete Node"
                );

        setButtonSize(
                deleteNode
        );

        deleteNode.setOnAction(e -> {

            if (sorting) {
                return;
            }

            if (root == null) {

                showMessage(
                        "Create a tree first."
                );

                return;
            }

            /*
             * Turn off insertion mode.
             */
            inserting = false;

            /*
             * Remove previous selection.
             */
            if (selectedNode != null) {

                selectedNode.setSelected(
                        false
                );
            }

            selectedNode = null;

            /*
             * Remove previous handlers.
             */
            drawTree();

            /*
             * Begin deletion mode.
             */
            deleting = true;

            enableDeleteSelection(
                    root
            );

            showDeleteInstructions();
        });

        /*
         * =========================================
         * SORT TREE
         * =========================================
         */

        Button sortTree =
                new Button(
                        "Sort Tree"
                );

        setButtonSize(
                sortTree
        );

        sortTree.setOnAction(e -> {

            if (sorting) {
                return;
            }

            if (root == null) {

                showMessage(
                        "Create a tree first."
                );

                return;
            }

            /*
             * Cancel any current
             * insert/delete mode.
             */
            inserting = false;
            deleting = false;
            selectedNode = null;

            /*
             * Remove old instructions
             * and click handlers.
             */
            drawTree();

            /*
             * Start animated sort.
             */
            startSortAnimation();
        });

        /*
         * =========================================
         * CLEAR
         * =========================================
         */

        Button clear =
                new Button(
                        "Clear"
                );

        setButtonSize(
                clear
        );

        clear.setOnAction(e -> {

            if (sorting) {
                return;
            }

            root = null;

            selectedNode = null;

            inserting = false;
            deleting = false;

            canvas
                    .getChildren()
                    .clear();
        });

        /*
         * =========================================
         * SELECT SCREEN
         * =========================================
         */

        Button selectScreen =
                new Button(
                        "Select Screen"
                );

        setButtonSize(
                selectScreen
        );

        selectScreen.setOnAction(e -> {

            if (sorting) {
                return;
            }

            inserting = false;
            deleting = false;
            selectedNode = null;

            stage.setScene(
                    selectScene
            );
        });

        /*
         * =========================================
         * MENU
         * =========================================
         */

        TilePane menu =
                new TilePane();

        menu.setPrefColumns(
                5
        );

        menu.setTileAlignment(
                Pos.CENTER
        );

        menu.setAlignment(
                Pos.CENTER
        );

        menu.setPrefWidth(
                Double.MAX_VALUE
        );

        menu.setHgap(
                20
        );

        menu.setVgap(
                10
        );

        menu.setPadding(
                new Insets(
                        20,
                        0,
                        30,
                        0
                )
        );

        menu.getChildren().addAll(
                createTree,
                insertNode,
                deleteNode,
                sortTree,
                clear,
                selectScreen
        );

        /*
         * =========================================
         * CANVAS
         * =========================================
         */

        canvas.setPrefWidth(
                1280
        );

        canvas.setPrefHeight(
                500
        );

        canvas.setMaxSize(
                Double.MAX_VALUE,
                Double.MAX_VALUE
        );

        /*
         * =========================================
         * MAIN LAYOUT
         * =========================================
         */

        layout.setCenter(
                canvas
        );

        layout.setBottom(
                menu
        );

        /*
         * Redraw when the window
         * changes width.
         */
        canvas.widthProperty().addListener(
                (observable,
                 oldWidth,
                 newWidth) -> {

                    /*
                     * Don't redraw during the
                     * sorting animation.
                     */
                    if (sorting) {
                        return;
                    }

                    if (root != null) {

                        drawTree();

                        /*
                         * Restore insertion mode.
                         */
                        if (inserting) {

                            enableInsertSelection(
                                    root
                            );

                            showInsertInstructions();
                        }

                        /*
                         * Restore deletion mode.
                         */
                        else if (deleting) {

                            enableDeleteSelection(
                                    root
                            );

                            showDeleteInstructions();
                        }
                    }
                }
        );

        return scene;
    }

    /*
     * =============================================
     * SORT TREE
     * =============================================
     *
     * Traverses the current tree,
     * highlights every visited node,
     * collects the values,
     * sorts the values,
     * and rebuilds the tree as a
     * balanced binary search tree.
     */

    private void startSortAnimation() {

        if (root == null) {
            return;
        }

        sorting = true;

        /*
         * Nodes in traversal order.
         */
        List<TreeNode> traversal =
                new ArrayList<>();

        /*
         * We use preorder traversal:
         *
         * Root
         * Left
         * Right
         */
        collectPreorder(
                root,
                traversal
        );

        /*
         * Stores the values as the
         * animation visits each node.
         */
        List<Integer> values =
                new ArrayList<>();

        /*
         * Display algorithm information.
         */
        Text status =
                new Text(
                        "Collecting tree values..."
                );

        status.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        status.setLayoutX(
                25
        );

        status.setLayoutY(
                30
        );

        canvas
                .getChildren()
                .add(
                        status
                );

        /*
         * Timeline controls the
         * traversal animation.
         */
        Timeline timeline =
                new Timeline();

        /*
         * =========================================
         * VISIT EACH NODE
         * =========================================
         */

        for (int i = 0;
             i < traversal.size();
             i++) {

            final int index = i;

            /*
             * Highlight current node.
             */
            KeyFrame highlightFrame =
                    new KeyFrame(
                            Duration.millis(
                                    index
                                            * SORT_DELAY
                            ),
                            e -> {

                                TreeNode node =
                                        traversal.get(
                                                index
                                        );

                                /*
                                 * Highlight node.
                                 */
                                node.setSelected(
                                        true
                                );

                                /*
                                 * Collect its value.
                                 */
                                values.add(
                                        node.value
                                );

                                status.setText(
                                        "Collecting: "
                                                + node.value
                                );
                            }
                    );

            timeline
                    .getKeyFrames()
                    .add(
                            highlightFrame
                    );

            /*
             * Remove highlight before
             * moving to next node.
             */
            KeyFrame removeHighlight =
                    new KeyFrame(
                            Duration.millis(
                                    index
                                            * SORT_DELAY
                                            + SORT_DELAY
                                                    * 0.75
                            ),
                            e -> {

                                TreeNode node =
                                        traversal.get(
                                                index
                                        );

                                node.setSelected(
                                        false
                                );
                            }
                    );

            timeline
                    .getKeyFrames()
                    .add(
                            removeHighlight
                    );
        }

        /*
         * =========================================
         * SORT VALUES
         * =========================================
         */

        double sortTime =
                traversal.size()
                        * SORT_DELAY
                        + 300;

        KeyFrame sortFrame =
                new KeyFrame(
                        Duration.millis(
                                sortTime
                        ),
                        e -> {

                            /*
                             * Sort the collected values.
                             */
                            Collections.sort(
                                    values
                            );

                            status.setText(
                                    "Sorted Values: "
                                            + values
                            );
                        }
                );

        timeline
                .getKeyFrames()
                .add(
                        sortFrame
                );

        /*
         * =========================================
         * REBUILD TREE
         * =========================================
         */

        KeyFrame rebuildFrame =
                new KeyFrame(
                        Duration.millis(
                                sortTime
                                        + 1000
                        ),
                        e -> {

                            /*
                             * Rebuild as a balanced
                             * binary search tree.
                             */
                            root =
                                    buildBalancedTree(
                                            values,
                                            0,
                                            values.size()
                                                    - 1
                                    );

                            sorting = false;

                            /*
                             * Draw the new tree.
                             */
                            drawTree();
                        }
                );

        timeline
                .getKeyFrames()
                .add(
                        rebuildFrame
                );

        /*
         * Start animation.
         */
        timeline.play();
    }

    /*
     * =============================================
     * PREORDER TRAVERSAL
     * =============================================
     *
     * Root -> Left -> Right
     */

    private void collectPreorder(
            TreeNode node,
            List<TreeNode> nodes) {

        if (node == null) {

            return;
        }

        /*
         * Visit current node.
         */
        nodes.add(
                node
        );

        /*
         * Visit left subtree.
         */
        collectPreorder(
                node.left,
                nodes
        );

        /*
         * Visit right subtree.
         */
        collectPreorder(
                node.right,
                nodes
        );
    }

    /*
     * =============================================
     * BUILD BALANCED BST
     * =============================================
     *
     * Uses the middle element of the
     * sorted list as the root.
     */

    private TreeNode buildBalancedTree(
            List<Integer> values,
            int start,
            int end) {

        /*
         * No values remaining.
         */
        if (start > end) {

            return null;
        }

        /*
         * Find middle value.
         */
        int middle =
                (start + end)
                        / 2;

        /*
         * Create node from middle.
         */
        TreeNode node =
                new TreeNode(
                        values.get(
                                middle
                        )
                );

        /*
         * Values before the middle
         * become the left subtree.
         */
        node.left =
                buildBalancedTree(
                        values,
                        start,
                        middle - 1
                );

        /*
         * Values after the middle
         * become the right subtree.
         */
        node.right =
                buildBalancedTree(
                        values,
                        middle + 1,
                        end
                );

        return node;
    }

    /*
     * =============================================
     * INSERT INSTRUCTIONS
     * =============================================
     */

    private void showInsertInstructions() {

        Text instructions =
                new Text(
                        "Select a node to insert under"
                );

        instructions.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        instructions.setLayoutX(
                25
        );

        instructions.setLayoutY(
                30
        );

        canvas
                .getChildren()
                .add(
                        instructions
                );
    }

    /*
     * =============================================
     * DELETE INSTRUCTIONS
     * =============================================
     */

    private void showDeleteInstructions() {

        Text instructions =
                new Text(
                        "Select a node to delete"
                );

        instructions.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        instructions.setLayoutX(
                25
        );

        instructions.setLayoutY(
                30
        );

        canvas
                .getChildren()
                .add(
                        instructions
                );
    }

    /*
     * =============================================
     * ENABLE INSERT SELECTION
     * =============================================
     */

    private void enableInsertSelection(
            TreeNode node) {

        if (node == null) {

            return;
        }

        node.setOnMouseClicked(e -> {

            if (!inserting) {

                return;
            }

            /*
             * Remove previous selection.
             */
            if (selectedNode != null) {

                selectedNode.setSelected(
                        false
                );
            }

            /*
             * Select clicked node.
             */
            selectedNode =
                    node;

            selectedNode.setSelected(
                    true
            );

            /*
             * Find selected node depth.
             */
            int depth =
                    getDepth(
                            root,
                            selectedNode,
                            1
                    );

            /*
             * Prevent insertion past
             * level 6.
             */
            if (depth >= MAX_HEIGHT) {

                showMessage(
                        "Maximum tree height of "
                                + MAX_HEIGHT
                                + " reached."
                );

                return;
            }

            /*
             * Ask left or right.
             */
            showDirectionChoice();
        });

        enableInsertSelection(
                node.left
        );

        enableInsertSelection(
                node.right
        );
    }

    /*
     * =============================================
     * ENABLE DELETE SELECTION
     * =============================================
     */

    private void enableDeleteSelection(
            TreeNode node) {

        if (node == null) {

            return;
        }

        node.setOnMouseClicked(e -> {

            if (!deleting) {

                return;
            }

            /*
             * Remove previous selection.
             */
            if (selectedNode != null) {

                selectedNode.setSelected(
                        false
                );
            }

            /*
             * Select clicked node.
             */
            selectedNode =
                    node;

            selectedNode.setSelected(
                    true
            );

            /*
             * Ask for confirmation.
             */
            showDeleteConfirmation();
        });

        enableDeleteSelection(
                node.left
        );

        enableDeleteSelection(
                node.right
        );
    }

    /*
     * =============================================
     * DELETE CONFIRMATION
     * =============================================
     */

    private void showDeleteConfirmation() {

        Stage window =
                new Stage();

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        window.setTitle(
                "Delete Node"
        );

        /*
         * Title.
         */
        Text title =
                new Text(
                        "Delete Node"
                );

        title.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        /*
         * Confirmation message.
         */
        Text message =
                new Text(
                        "Delete node "
                                + selectedNode.value
                                + "?"
                );

        message.setFont(
                Font.font(
                        "Arial",
                        16
                )
        );

        /*
         * Subtree warning.
         */
        Text warning =
                new Text();

        if (selectedNode.left != null
                || selectedNode.right != null) {

            warning.setText(
                    "This will also delete its subtree."
            );
        }

        warning.setFont(
                Font.font(
                        "Arial",
                        14
                )
        );

        /*
         * Buttons.
         */
        Button confirmButton =
                new Button(
                        "Delete"
                );

        Button cancelButton =
                new Button(
                        "Cancel"
                );

        setSmallButtonSize(
                confirmButton
        );

        setSmallButtonSize(
                cancelButton
        );

        /*
         * Button row.
         */
        HBox buttons =
                new HBox(20);

        buttons.setAlignment(
                Pos.CENTER
        );

        buttons.getChildren().addAll(
                confirmButton,
                cancelButton
        );

        /*
         * Layout.
         */
        VBox deleteLayout =
                new VBox(20);

        deleteLayout.setAlignment(
                Pos.CENTER
        );

        deleteLayout.setPadding(
                new Insets(25)
        );

        deleteLayout.getChildren().addAll(
                title,
                message,
                warning,
                buttons
        );

        /*
         * =========================================
         * DELETE
         * =========================================
         */

        confirmButton.setOnAction(e -> {

            window.close();

            Platform.runLater(() -> {

                deleteSelectedNode();

            });
        });

        /*
         * =========================================
         * CANCEL
         * =========================================
         */

        cancelButton.setOnAction(e -> {

            window.close();

            cancelDeletion();
        });

        /*
         * Scene.
         */
        Scene deleteScene =
                new Scene(
                        deleteLayout,
                        450,
                        250
                );

        deleteScene
                .getStylesheets()
                .add(
                        "style.css"
                );

        window.setScene(
                deleteScene
        );

        window.showAndWait();
    }

    /*
     * =============================================
     * DELETE SELECTED NODE
     * =============================================
     */

    private void deleteSelectedNode() {

        if (selectedNode == null) {

            return;
        }

        /*
         * Deleting root removes
         * the entire tree.
         */
        if (selectedNode == root) {

            root = null;
        }

        /*
         * Otherwise remove the parent's
         * reference to this node.
         */
        else {

            removeChildReference(
                    root,
                    selectedNode
            );
        }

        selectedNode = null;

        deleting = false;

        drawTree();
    }

    /*
     * =============================================
     * REMOVE CHILD REFERENCE
     * =============================================
     */

    private boolean removeChildReference(
            TreeNode current,
            TreeNode target) {

        if (current == null) {

            return false;
        }

        /*
         * Target is left child.
         */
        if (current.left == target) {

            current.left = null;

            return true;
        }

        /*
         * Target is right child.
         */
        if (current.right == target) {

            current.right = null;

            return true;
        }

        /*
         * Search left.
         */
        if (removeChildReference(
                current.left,
                target)) {

            return true;
        }

        /*
         * Search right.
         */
        return removeChildReference(
                current.right,
                target
        );
    }

    /*
     * =============================================
     * CANCEL DELETE
     * =============================================
     */

    private void cancelDeletion() {

        deleting = false;

        if (selectedNode != null) {

            selectedNode.setSelected(
                    false
            );
        }

        selectedNode = null;

        drawTree();
    }

    /*
     * =============================================
     * DIRECTION CHOICE
     * =============================================
     */

    private void showDirectionChoice() {

        Stage window =
                new Stage();

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        window.setTitle(
                "Insert Node"
        );

        /*
         * Selected parent.
         */
        Text parentText =
                new Text(
                        "Selected Parent: "
                                + selectedNode.value
                );

        parentText.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        /*
         * Instructions.
         */
        Text instructions =
                new Text(
                        "Choose where to insert the new node"
                );

        instructions.setFont(
                Font.font(
                        "Arial",
                        16
                )
        );

        /*
         * Buttons.
         */
        Button leftButton =
                new Button(
                        "Left"
                );

        Button rightButton =
                new Button(
                        "Right"
                );

        Button cancelButton =
                new Button(
                        "Cancel"
                );

        setSmallButtonSize(
                leftButton
        );

        setSmallButtonSize(
                rightButton
        );

        setSmallButtonSize(
                cancelButton
        );

        /*
         * Direction buttons.
         */
        HBox directions =
                new HBox(20);

        directions.setAlignment(
                Pos.CENTER
        );

        directions.getChildren().addAll(
                leftButton,
                rightButton
        );

        /*
         * Layout.
         */
        VBox directionLayout =
                new VBox(20);

        directionLayout.setAlignment(
                Pos.CENTER
        );

        directionLayout.setPadding(
                new Insets(25)
        );

        directionLayout.getChildren().addAll(
                parentText,
                instructions,
                directions,
                cancelButton
        );

        /*
         * =========================================
         * LEFT
         * =========================================
         */

        leftButton.setOnAction(e -> {

            if (selectedNode.left != null) {

                showMessage(
                        "This node already has a left child."
                );

                return;
            }

            window.close();

            Platform.runLater(() -> {

                showValuePrompt(
                        true
                );

            });
        });

        /*
         * =========================================
         * RIGHT
         * =========================================
         */

        rightButton.setOnAction(e -> {

            if (selectedNode.right != null) {

                showMessage(
                        "This node already has a right child."
                );

                return;
            }

            window.close();

            Platform.runLater(() -> {

                showValuePrompt(
                        false
                );

            });
        });

        /*
         * =========================================
         * CANCEL
         * =========================================
         */

        cancelButton.setOnAction(e -> {

            window.close();

            cancelInsertion();
        });

        /*
         * Scene.
         */
        Scene directionScene =
                new Scene(
                        directionLayout,
                        450,
                        250
                );

        directionScene
                .getStylesheets()
                .add(
                        "style.css"
                );

        window.setScene(
                directionScene
        );

        window.showAndWait();
    }

    /*
     * =============================================
     * VALUE PROMPT
     * =============================================
     */

    private void showValuePrompt(
            boolean insertLeft) {

        Stage window =
                new Stage();

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        window.setTitle(
                "Enter Node Value"
        );

        /*
         * Title.
         */
        Text title =
                new Text(
                        "Enter Node Value"
                );

        title.setFont(
                Font.font(
                        "Arial",
                        20
                )
        );

        /*
         * Direction.
         */
        Text directionText =
                new Text(
                        insertLeft
                                ? "Insert Left"
                                : "Insert Right"
                );

        directionText.setFont(
                Font.font(
                        "Arial",
                        16
                )
        );

        /*
         * =========================================
         * TEXT FIELD
         * =========================================
         */

        TextField input =
                new TextField();

        input.setPromptText(
                "Enter Integer"
        );

        input.setPrefWidth(
                200
        );

        input.setMaxWidth(
                200
        );

        input.setStyle(
                "-fx-background-color: white;"
                        + "-fx-text-fill: black;"
                        + "-fx-prompt-text-fill: gray;"
                        + "-fx-font-size: 14px;"
                        + "-fx-border-color: gray;"
                        + "-fx-border-width: 1px;"
        );

        /*
         * Enter button.
         */
        Button enterButton =
                new Button(
                        "Enter"
                );

        setSmallButtonSize(
                enterButton
        );

        /*
         * Error message.
         */
        Text status =
                new Text();

        status.setFont(
                Font.font(
                        "Arial",
                        14
                )
        );

        /*
         * Layout.
         */
        VBox valueLayout =
                new VBox(15);

        valueLayout.setAlignment(
                Pos.CENTER
        );

        valueLayout.setPadding(
                new Insets(25)
        );

        valueLayout.getChildren().addAll(
                title,
                directionText,
                input,
                enterButton,
                status
        );

        /*
         * =========================================
         * ENTER
         * =========================================
         */

        enterButton.setOnAction(e -> {

            try {

                int value =
                        Integer.parseInt(
                                input
                                        .getText()
                                        .trim()
                        );

                /*
                 * Create new node.
                 */
                TreeNode newNode =
                        new TreeNode(
                                value
                        );

                /*
                 * Insert left.
                 */
                if (insertLeft) {

                    selectedNode.left =
                            newNode;
                }

                /*
                 * Insert right.
                 */
                else {

                    selectedNode.right =
                            newNode;
                }

                /*
                 * Finish insertion.
                 */
                inserting = false;

                selectedNode.setSelected(
                        false
                );

                selectedNode = null;

                window.close();

                drawTree();

            }
            catch (NumberFormatException exception) {

                input.clear();

                status.setText(
                        "Enter a valid integer."
                );

                input.requestFocus();
            }
        });

        /*
         * Keyboard Enter.
         */
        input.setOnAction(e -> {

            enterButton.fire();

        });

        /*
         * Scene.
         */
        Scene valueScene =
                new Scene(
                        valueLayout,
                        400,
                        280
                );

        valueScene
                .getStylesheets()
                .add(
                        "style.css"
                );

        window.setScene(
                valueScene
        );

        /*
         * Force JavaFX to finish
         * layout before focusing.
         */
        window.setOnShown(e -> {

            valueLayout.applyCss();

            valueLayout.layout();

            Platform.runLater(() -> {

                input.requestFocus();

            });
        });

        window.showAndWait();
    }

    /*
     * =============================================
     * CANCEL INSERTION
     * =============================================
     */

    private void cancelInsertion() {

        inserting = false;

        if (selectedNode != null) {

            selectedNode.setSelected(
                    false
            );
        }

        selectedNode = null;

        drawTree();
    }

    /*
     * =============================================
     * MESSAGE WINDOW
     * =============================================
     */

    private void showMessage(
            String message) {

        Stage window =
                new Stage();

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        window.setTitle(
                "Tree"
        );

        Text text =
                new Text(
                        message
                );

        text.setFont(
                Font.font(
                        "Arial",
                        16
                )
        );

        Button okButton =
                new Button(
                        "OK"
                );

        setSmallButtonSize(
                okButton
        );

        okButton.setOnAction(e -> {

            window.close();

        });

        VBox messageLayout =
                new VBox(20);

        messageLayout.setAlignment(
                Pos.CENTER
        );

        messageLayout.setPadding(
                new Insets(25)
        );

        messageLayout.getChildren().addAll(
                text,
                okButton
        );

        Scene messageScene =
                new Scene(
                        messageLayout,
                        450,
                        180
                );

        messageScene
                .getStylesheets()
                .add(
                        "style.css"
                );

        window.setScene(
                messageScene
        );

        window.showAndWait();
    }

    /*
     * =============================================
     * GET DEPTH
     * =============================================
     *
     * Root = level 1.
     */

    private int getDepth(
            TreeNode current,
            TreeNode target,
            int depth) {

        if (current == null) {

            return -1;
        }

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
     * =============================================
     * DRAW TREE
     * =============================================
     */

    private void drawTree() {

        canvas
                .getChildren()
                .clear();

        if (root == null) {

            return;
        }

        /*
         * Canvas width.
         */
        double width =
                canvas.getWidth();

        if (width <= 0) {

            width = 1280;
        }

        /*
         * Root position.
         */
        double rootX =
                width / 2;

        double rootY =
                50;

        /*
         * Horizontal room.
         */
        double usableHalfWidth =
                (width / 2)
                        - SIDE_PADDING;

        /*
         * Calculate horizontal offset.
         */
        double offsetSum = 0;

        for (int i = 0;
             i < MAX_HEIGHT - 1;
             i++) {

            offsetSum +=
                    1.0
                            / Math.pow(
                                    2,
                                    i
                            );
        }

        double startingOffset =
                usableHalfWidth
                        / offsetSum;

        /*
         * Draw tree.
         */
        drawTreeRecursive(
                root,
                rootX,
                rootY,
                startingOffset
        );
    }

    /*
     * =============================================
     * DRAW TREE RECURSIVELY
     * =============================================
     */

    private void drawTreeRecursive(
            TreeNode node,
            double x,
            double y,
            double xOffset) {

        if (node == null) {

            return;
        }

        /*
         * =========================================
         * LEFT CHILD
         * =========================================
         */

        if (node.left != null) {

            double childX =
                    x - xOffset;

            double childY =
                    y + LEVEL_GAP;

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

            canvas
                    .getChildren()
                    .add(
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
         * =========================================
         * RIGHT CHILD
         * =========================================
         */

        if (node.right != null) {

            double childX =
                    x + xOffset;

            double childY =
                    y + LEVEL_GAP;

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

            canvas
                    .getChildren()
                    .add(
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
         * =========================================
         * CURRENT NODE
         * =========================================
         */

        node.setLayoutX(
                x
        );

        node.setLayoutY(
                y
        );

        node.setScaleX(
                1
        );

        node.setScaleY(
                1
        );

        /*
         * Remove selection.
         */
        node.setSelected(
                false
        );

        /*
         * Remove old click handler.
         */
        node.setOnMouseClicked(
                null
        );

        /*
         * Add node to canvas.
         */
        canvas
                .getChildren()
                .add(
                        node
                );
    }

    /*
     * =============================================
     * MAIN BUTTON SIZE
     * =============================================
     */

    private void setButtonSize(
            Button button) {

        button.setMinSize(
                250,
                75
        );

        button.setMaxSize(
                250,
                75
        );

        button.setPrefSize(
                250,
                75
        );
    }

    /*
     * =============================================
     * SMALL BUTTON SIZE
     * =============================================
     */

    private void setSmallButtonSize(
            Button button) {

        button.setMinSize(
                120,
                45
        );

        button.setMaxSize(
                120,
                45
        );

        button.setPrefSize(
                120,
                45
        );
    }
}