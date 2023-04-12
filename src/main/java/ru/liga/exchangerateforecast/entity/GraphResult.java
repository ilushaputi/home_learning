package ru.liga.exchangerateforecast.entity;

import java.awt.image.BufferedImage;

public class GraphResult {
    private String name;
    private BufferedImage image;

    public GraphResult(String name, BufferedImage image){
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
