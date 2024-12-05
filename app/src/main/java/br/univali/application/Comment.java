package br.univali.application;

public class Comment {
    private final String name;
    private final float rating;
    private final String text;

    public Comment(String name, float rating, String text) {
        this.name = name;
        this.rating = rating;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }
}
