package com.example.tripplanner.Models;

import java.io.Serializable;

/*
 *
 * @author Dennis Finch Imbaya S1903332
 *
 * */

public class EventObject implements Serializable {
    String title, description, date;

    public EventObject(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
