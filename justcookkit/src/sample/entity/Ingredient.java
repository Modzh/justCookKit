package sample.entity;

public class Ingredient {
    /*
    * Variables for constant values;
    * */

    private String name;
    private int ingredient_id;
    private double price100;
    private double proteins;
    private double carbohydrates;
    private double fats;

    public Ingredient(String name,
                      double proteins, double carbohydrates, double fats,
                      double price100,
                      int ingredient_id) {
        this.name = name;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.price100 = price100;
        this.ingredient_id = ingredient_id;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice100() {
        return price100;
    }

    public void setPrice100(double priceFor100g) {
        this.price100 = priceFor100g;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }
}
