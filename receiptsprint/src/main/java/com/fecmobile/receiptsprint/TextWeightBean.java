package com.fecmobile.receiptsprint;

public class TextWeightBean {
    public TextWeightBean() {
    }

    public TextWeightBean(String element, float weight) {
        this.element = element;
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    private float weight;
    private String text;
    private String element;
}
