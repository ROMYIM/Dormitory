package com.john_yim.dormitory.entity.view;

/**
 * Created by MSI-PC on 2018/2/12.
 */

public class GridItem {
    private int id;
    private String name;

    public GridItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
