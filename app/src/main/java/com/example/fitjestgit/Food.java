package com.example.fitjestgit;

public class Food {

    public String name;
    public Integer calories;

    public Food(){}

    public Food(String name, Integer calories){
this.name=name;
this.calories=calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    @Override
    public String toString(){
        return name + ": "+calories+ " kalorii";
    }

}
