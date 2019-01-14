package sample.entity;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String name;
    private int recipeId;
    private List<ComponentOfRecipe> components;
    private String recipeText;
    private double recipePrice;
    private double proteinsFor100;
    private double carbohydratesFor100;
    private double fatsFor100;

    public Recipe(ArrayList<ComponentOfRecipe> components, String recipeText, String name, int recipeId) {
        this.components = components;
        this.recipeText = recipeText;
        this.name = name;
        this.recipeId = recipeId;
        for (ComponentOfRecipe c : components) {
            recipePrice += c.getPrice();
            proteinsFor100 += c.getIngredient().getProteins();
            carbohydratesFor100 += c.getIngredient().getCarbohydrates();
            fatsFor100 += c.getIngredient().getCarbohydrates();
        }
    }
    public Recipe(List<ComponentOfRecipe> components, String recipeText, String name) {
        this.components = components;
        this.recipeText = recipeText;
        this.name = name;
        for(ComponentOfRecipe c : components) {
            recipePrice +=  c.getPrice();
            proteinsFor100 += c.getIngredient().getProteins();
            carbohydratesFor100 += c.getIngredient().getCarbohydrates();
            fatsFor100 += c.getIngredient().getCarbohydrates();
        }

    }
    public Recipe() {}

    public double getProteinsFor100() {
        return proteinsFor100;
    }

    public void setProteinsFor100(double proteinsFor100) {
        this.proteinsFor100 = proteinsFor100;
    }

    public double getCarbohydratesFor100() {
        return carbohydratesFor100;
    }

    public void setCarbohydratesFor100(double carbohydratesFor100) {
        this.carbohydratesFor100 = carbohydratesFor100;
    }

    public double getFatsFor100() {
        return fatsFor100;
    }

    public void setFatsFor100(double fatsFor100) {
        this.fatsFor100 = fatsFor100;
    }

    public List<ComponentOfRecipe> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<ComponentOfRecipe> components) {
        this.components = components;
    }

    public String getRecipeText() {
        return recipeText;
    }

    public void setRecipeText(String recipeText) {
        this.recipeText = recipeText;
    }

    public double getRecipePrice() {
        return recipePrice;
    }

    public void setRecipePrice(double recipePrice) {
        this.recipePrice = recipePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
