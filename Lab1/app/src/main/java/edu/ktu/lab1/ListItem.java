package edu.ktu.lab1;

import java.io.Serializable;

public class ListItem implements  Serializable {
    private String title;
    private int imageId;
    private String description;

    public ListItem(){

    }

    public ListItem(String title, int imageId, String description){
        this.title = title;
        this.imageId = imageId;
        this.description = description;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public int getImageId(){
        return imageId;
    }

    public void setImageId(int id){
        this.imageId = id;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String desc){
        this.description = desc;
    }
}
