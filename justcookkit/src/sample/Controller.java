package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import sample.entity.Ingredient;
import sample.entity.Recipe;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        scene = Main.;
//        Recipe getAllRecipes = iniRecipe();
//        displayRecipe(getAllRecipes);
    }

    static ObservableList<Recipe> getAllRecipes() {
        JDBCmanager jdbCmanager = new JDBCmanager();
        return FXCollections.observableArrayList(jdbCmanager.getRecipeFromDb(0));
    }

    static Recipe getRecipe(int id) {
        JDBCmanager jdbCmanager = new JDBCmanager();
        return jdbCmanager.getRecipeFromDb(id).get(0);
    }

    static void saveRecipe(Recipe recipe) {
        JDBCmanager jdbCmanager = new JDBCmanager();
        jdbCmanager.saveRecipeToDB(recipe);
    }

    static ObservableList<Ingredient> getAllIngredients() {
        JDBCmanager jdbCmanager = new JDBCmanager();
        return FXCollections.observableArrayList(jdbCmanager.getIngredientFromDb(0));
    }

    static void deleteRecipe(Recipe recipe) {
        JDBCmanager jdbCmanager = new JDBCmanager();
        jdbCmanager.deleteFromDb(recipe.getName(),"recipes");
        jdbCmanager.deleteFromDb(recipe.getName(),"components");
    }

}
