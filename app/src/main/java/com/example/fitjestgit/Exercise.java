package com.example.fitjestgit;

import androidx.annotation.NonNull;

public class Exercise {
    private String name;
    private Integer calories;

    @Override
    public String toString() {
            return name + ": "+calories+ " kalorii";

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

    public Exercise(String name, Integer calories) {
        this.name = name;
        this.calories = calories;
    }
}
