package com.example.fitjestgit;

public class User {

    public double weight;
    public double height;
    public String sex;


    public User(double weight, double height, String sex) {
        this.weight = weight;
        this.height = height;
        this.sex = sex;
    }

    public User(){

    }
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "User{" +
                "weight=" + weight +
                ", height=" + height +
                ", sex=" + sex +
                '}';
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
