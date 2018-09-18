package com.example.movierec.ListItem;

/**
 * Created by August on 2018/5/7.
 */

public class MenuItem {

    private String title;
    private int imageID;
    private int arrow;

    public MenuItem(String title, int imageID, int arrow) {
        this.title = title;
        this.imageID = imageID;
        this.arrow = arrow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getArrow() {
        return arrow;
    }

    public void setArrow(int arrow) {
        this.arrow = arrow;
    }
}
