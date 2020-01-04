package com.example.recipefinder;

class Recipe {
    public String title;
    public String href;
    public String ingredients;

    public Recipe(String title, String href, String ingredients) {
        this.title = title;
        this.href = href;
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", ingredients='" + ingredients + '\'' +
                '}';
    }
}
