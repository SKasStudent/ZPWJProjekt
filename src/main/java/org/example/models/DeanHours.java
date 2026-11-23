package org.example.models;

import java.time.LocalDate;

public class DeanHours {
    LocalDate date;
    String title;

    public DeanHours(String title, LocalDate date){
        this.title = title;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Nazwa art: " + title + "Data: " + date;
    }
}
