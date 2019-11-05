package com.example.fitjestgit;

public class EatingPlans {
    private String ratio;
    private String describe;

    public EatingPlans(){}
    public EatingPlans(String ratio, String describe) {
        this.ratio = ratio;
        this.describe = describe;
    }
    public String getDescribe() {
        return describe;
    }



    public void setDescribe(String describe) {
        this.describe = describe;
    }


    @Override
    public String toString() {
        return describe ;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}
