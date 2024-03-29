package com.iutbm.example.iutbm.couchot.healthybody.utils;


public class DrawerItem {
    private String name;
    private String navigationIcon;

    public DrawerItem(String name, String navigationIcon) {
        this.name = name;
        this.navigationIcon = navigationIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNavigationIcon() {
        return navigationIcon;
    }

    public void setNavigationIcon(String navigationIcon) {
        this.navigationIcon = navigationIcon;
    }


}
