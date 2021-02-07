package com.example.journal22.old;

public class Planet {

    private String planetName;
    private int distanceFromSun;
    private int gravity;
    private int diameter;

    private String title;
    private String content;

    public Planet(String title, String content, int gravity, int diameter) {
        this.title = title;
        this.content = content;
        this.gravity = gravity;
        this.diameter = diameter;
    }
    public String getPlanetName() {
        return planetName;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }
    public int getDistanceFromSun() {
        return distanceFromSun;
    }
    public void setDistanceFromSun(int distanceFromSun) {
        this.distanceFromSun = distanceFromSun;
    }
    public int getGravity() {
        return gravity;
    }
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
    public int getDiameter() {
        return diameter;
    }
    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }
}
