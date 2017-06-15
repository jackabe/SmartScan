package com.smartscan.app.smartscanapp.model;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

public class ObjectDrawerItem {

    private int icon;
    private String name;

    public ObjectDrawerItem(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}