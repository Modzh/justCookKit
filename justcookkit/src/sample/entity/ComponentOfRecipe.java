package sample.entity;

public class ComponentOfRecipe {

    private Ingredient ingredient;
    private Double weight;
    private Double price;
    private String name;

    public ComponentOfRecipe(Ingredient ingredient, Double weight) {
        this.ingredient = ingredient;
        this.weight = weight;
        this.price = ingredient.getPrice100()*weight;
        this.name = ingredient.getName();
    }

    @Override
    public String toString() {
        return name + " " + weight + "g" ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        price = price/this.weight;
        this.weight = weight;
        price = price*weight;
    }

    public Double getPrice() {
        return price;
    }

//    public void setPrice(Double price) {
//        this.price = price;
//    }
}
