package com.rehman.eorderingsystem.Model;

public class FoodModel
{
    String foodName,foodCategory,foodPrice,foodQuantity,foodDescription,
            foodKey,foodImage;

    public FoodModel(String foodName, String foodCategory, String foodPrice, String foodQuantity, String foodDescription, String foodKey, String foodImage) {
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodPrice = foodPrice;
        this.foodQuantity = foodQuantity;
        this.foodDescription = foodDescription;
        this.foodKey = foodKey;
        this.foodImage = foodImage;
    }

    public FoodModel() {
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(String foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getFoodKey() {
        return foodKey;
    }

    public void setFoodKey(String foodKey) {
        this.foodKey = foodKey;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }
}
