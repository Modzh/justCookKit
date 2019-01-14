package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.entity.ComponentOfRecipe;
import sample.entity.Ingredient;
import sample.entity.Recipe;

import java.util.ArrayList;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("JavaFX Welcome");

        Scene scene;

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
//		grid.setGridLinesVisible(true);

        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        grid.add(sceneTitle, 1, 0, 1, 1);


        Button showAllButton = new Button("Show all recipes");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(showAllButton);
        grid.add(hbBtn, 1, 4);

        Button addNewRecipeButton = new Button("add new recipe");
        HBox hBtn1 = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hBtn1.getChildren().add(addNewRecipeButton);
        grid.add(hBtn1, 1, 5);

        Button delBtn = new Button("delete recipe");
        HBox hBtn2 = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hBtn1.getChildren().add(delBtn);
        grid.add(hBtn2, 0, 5);

        ObservableList<ComponentOfRecipe> componentOfRecipes = FXCollections.observableArrayList();
        ListView<ComponentOfRecipe> componentOfRecipeListView = new ListView<>(componentOfRecipes);
        componentOfRecipeListView.setMaxHeight(150);
        grid.add(componentOfRecipeListView, 4, 0, 1, 1);
        componentOfRecipeListView.setVisible(false);

        Label recipeText = new Label();
        recipeText.setWrapText(true);
        recipeText.autosize();
        grid.add(recipeText, 1, 1, 4, 1);

        ObservableList<Recipe> recipeList = FXCollections.observableArrayList();

        ListView recipeListView = new ListView(recipeList);

        recipeListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        recipeListView.getSelectionModel().selectedItemProperty()
                .addListener((ChangeListener<Recipe>) (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        componentOfRecipes.setAll(newValue.getComponents());
                        componentOfRecipeListView.setVisible(true);
                        recipeText.setText(newValue.getRecipeText());
                        sceneTitle.setText(newValue.getName());
                    }
                });

        grid.add(recipeListView, 0, 0, 1, 2);

        showAllButton.setOnAction(e -> {
            recipeList.setAll(Controller.getAllRecipes());

        });

        scene = new Scene(grid, 700, 475);

        addNewRecipeButton.setOnAction(e -> {
            grid.setVisible(false);
            GridPane iniSaveGridPane = iniSaveGridPane(grid, primaryStage, scene);
            Scene sceneAddRecipe = new Scene(iniSaveGridPane, 700, 475);
            primaryStage.setScene(sceneAddRecipe);
        });

        delBtn.setOnAction(e -> {
            Recipe deletedRecipe = (Recipe) recipeListView.getSelectionModel().getSelectedItem();
            Controller.deleteRecipe(deletedRecipe);
            showAllButton.fire();
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    private GridPane iniSaveGridPane(GridPane oldGridPane, Stage primaryStage, Scene oldScene) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label nameLabel = new Label("name of recipe");
        nameLabel.setWrapText(true);
        grid.add(nameLabel, 0, 1);

        TextArea nameTextArea = new TextArea();
        nameTextArea.setMaxSize(70, 20);
        grid.add(nameTextArea, 1, 1);


        Label textLabel = new Label("text of recipe");
        textLabel.setWrapText(true);
        grid.add(textLabel, 0, 3);

        TextArea recipeTextArea = new TextArea();
        recipeTextArea.setMinHeight(100);
        recipeTextArea.setWrapText(true);
        grid.add(recipeTextArea, 1, 3, 4, 1);


        Label allIngredientsLabel = new Label("All ingredients: ");

        grid.add(allIngredientsLabel, 3, 0);
        ObservableList<Ingredient> ingredients = Controller.getAllIngredients();
        ListView<Ingredient> allIngredientsView = new ListView<>(ingredients);
        grid.add(allIngredientsView, 3, 1);

        Label componentsLabel = new Label("Components of recipe: ");
        grid.add(componentsLabel, 2, 0);
        ObservableList<ComponentOfRecipe> componentsInRecipe = FXCollections.observableArrayList();
        ListView<ComponentOfRecipe> recipeIngredients = new ListView<>(componentsInRecipe);
        recipeIngredients.setMaxHeight(100);
        grid.add(recipeIngredients, 2, 1);

        allIngredientsView.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        Ingredient currentItemSelected = allIngredientsView.getSelectionModel().getSelectedItem();
                        TextInputDialog chooseWeight = new TextInputDialog();
                        Double weight;
                        Optional<String> result;
                        chooseWeight.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                            if (!newValue.matches("[0-99]*")) {
                                chooseWeight.getEditor().setText(oldValue);
                            }
                        });
                        chooseWeight.setTitle("Choose weight");
                        result = chooseWeight.showAndWait();
                        weight = Double.parseDouble(result.orElse("0"));
                        if (weight > 0) {
                            componentsInRecipe.add(new ComponentOfRecipe(currentItemSelected, weight));
                        }
                    }
                }

        );

        Button saveRecipeBtn = new Button("save recipe");
        saveRecipeBtn.setOnAction(e -> {
            Recipe recipe = new Recipe(componentsInRecipe, recipeTextArea.getText(), nameTextArea.getText());
            Controller.saveRecipe(recipe);

            oldScene.getRoot().setVisible(true);
            primaryStage.setScene(oldScene);

            int s = oldScene.getRoot().getChildrenUnmodifiable().size();

            ((Button) ((HBox) oldScene.getRoot().getChildrenUnmodifiable().get(1)).getChildren().get(0)).fire();
            ((ListView<Recipe>)oldScene.getRoot().getChildrenUnmodifiable().get(s - 1)).getSelectionModel().selectLast();
        });

        grid.add(saveRecipeBtn, 3, 4);

        grid.setGridLinesVisible(true);
        return grid;
    }
}
