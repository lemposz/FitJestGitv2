package com.example.fitjestgit;

public class User {

    public double weight;
    public double height;
    public String sex;
    public String type;
    public Integer age;
    public String ratio;




    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public User(double weight, double height, String sex, String type, Integer age, String ratio) {
        this.weight = weight;
        this.height = height;
        this.sex = sex;
        this.type= type;
        this.age= age;
        this.ratio=ratio;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public String getRatio() {
        return ratio;
    }

    public void setType(String type) {
        this.type = type;
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
                ", sex='" + sex + '\'' +
                ", type='" + type + '\'' +
                ", age=" + age +
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
