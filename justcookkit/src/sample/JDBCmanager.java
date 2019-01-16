package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.entity.ComponentOfRecipe;
import sample.entity.Ingredient;
import sample.entity.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCmanager {

//    private static final String FILEPATH =  System.getProperty("user.dir");
//    private static final String file1 = System.getProperty("user.dir") + "/justcookkit/out/artifacts/justcookkit_jar";
//    private static final String dbURL = "jdbc:sqlite:" + file1 + "/" +  "justCookKit.jar/src/db/";

    private Connection connect (String nameOfDb) {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e ){
            System.out.println(e.getMessage());
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite::resource:" + "db/"+nameOfDb);

            System.out.println("Connection to " + nameOfDb + " has been established");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    protected ArrayList<Ingredient> getIngredientFromDb(int id) {
        String sql = "SELECT id, name, proteins100, carbo100, fats100, price100 from ingredients ";
        if (id != 0) {sql +=  "WHERE id = " + id;}
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try (Connection conn = connect("ingredients");
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                ingredients.add(new Ingredient(rs.getString("name"),
                        rs.getDouble("proteins100"),
                        rs.getDouble("carbo100"),
                        rs.getDouble("fats100"),
                        rs.getDouble("price100"),
                        rs.getInt("id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ingredients;
    }

    protected ObservableList<Ingredient> getAllIngredients() {
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();

        return ingredients;
    }


    protected ArrayList<ComponentOfRecipe> getComponentsFromDb(int recipe_id) {
        ArrayList<ComponentOfRecipe> components = new ArrayList<>();
        String sql = "SELECT ingredient_id, weight FROM components WHERE recipe_id = " + recipe_id;

        try (Connection conn = connect("components");
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
             while (rs.next()) {
                 Ingredient ingredient = getIngredientFromDb(rs.getInt("ingredient_id")).get(0);

                 ComponentOfRecipe componentOfRecipe = new ComponentOfRecipe(ingredient,
                                                                            rs.getDouble("weight"));
                 components.add(componentOfRecipe);
             }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return components;
    }

    protected ArrayList<Recipe> getRecipeFromDb(int recipe_id) {
        String sql = "SELECT recipe_id, recipe_name, recipe_text FROM recipes";
        if (recipe_id!=0) sql += " WHERE recipe_id = " + recipe_id;
        ArrayList<Recipe> recipes = new ArrayList<>();

        try (Connection connection = connect("recipes");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()) {
                int id = rs.getInt("recipe_id");
                String name = rs.getString("recipe_name");
                recipes.add(new Recipe(getComponentsFromDb(id), rs.getString("recipe_text"), name, id));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return recipes;
    }

    private void addComponentsToRecipe(int recipe_id, List<ComponentOfRecipe> components) {
        for (ComponentOfRecipe c : components) {
            String sql = "INSERT INTO components (recipe_id, ingredient_id, weight) VALUES(?,?,?)";
            try (Connection connection = connect("components");
                 PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, recipe_id);
                stmt.setInt(2, c.getIngredient().getIngredient_id());
                stmt.setDouble(3,c.getWeight());
                stmt.executeUpdate();
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int getRecipeIdByName(String recipeName) {
        String sql = "SELECT recipe_id FROM recipes WHERE recipe_name = \"" + recipeName + "\" ";
        int id = 0;
        try (Connection connection = connect("recipes");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
                id = rs.getInt("recipe_id");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    protected void saveRecipeToDB(Recipe recipe) {
        String sql = "INSERT INTO recipes (recipe_name, recipe_text) VALUES(?,?)";
        try (Connection connection = connect("recipes");
             PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, recipe.getName());
                stmt.setString(2, recipe.getRecipeText());
                stmt.executeUpdate();
                addComponentsToRecipe(getRecipeIdByName(recipe.getName()),recipe.getComponents());
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void deleteFromDb(String recipe_name, String db) {
        int recipe_id = getRecipeIdByName(recipe_name);
        String sql = "DELETE FROM " + db +" WHERE recipe_id = " + recipe_id;
        try (Connection connection = connect(db);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteAllComponentsOfRecipe(int recipe_id){
        String sql = "DELETE FROM components WHERE recipe_id = " + recipe_id;
    }

    void deleteComponentFromDb(int recipe_id, int ingredient_id) {
        String sql = "DELETE FROM components WHERE recipe_id = " + recipe_id + "AND ingredient_id = " + ingredient_id;
    }

}
