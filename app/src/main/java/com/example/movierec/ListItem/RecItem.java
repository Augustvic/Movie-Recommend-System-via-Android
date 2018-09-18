package com.example.movierec.ListItem;

/**
 * Created by August on 2018/5/4.
 */

public class RecItem {

    private int imageID;
    private String title;
    private int rankID;

    public RecItem(int imageID, String title, int rankID) {
        this.title = title;
        this.imageID = imageID;
        this.rankID = rankID;
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

    public int getRankID() {
        return rankID;
    }

    public void setRankID(int rankID) {
        this.rankID = rankID;
    }
}
