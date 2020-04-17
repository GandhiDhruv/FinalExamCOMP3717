package com.example.finalexam2;

public class Movie {

    private String name;
    private String description;
    private String link;

    public Movie() {}

    public Movie(String name, String description, String link) {

        this.name = name;
        this.description = description;
        this.link = link;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLink() {
        return this.link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
