package com.example.movierec.ListItem;

/**
 * Created by August on 2018/5/8.
 */

public class NormalItem {
    private int imageID;
    private String title;
    private int arrowID;

    public NormalItem(int imageID, String title, int arrowID) {
        this.title = title;
        this.imageID = imageID;
        this.arrowID = arrowID;
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

    public int getArrowID() {
        return arrowID;
    }

    public void setArrowID(int arrowID) {
        this.arrowID = arrowID;
    }
}
