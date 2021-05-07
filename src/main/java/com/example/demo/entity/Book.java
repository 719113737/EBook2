package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private String title;
    private String mAbstract;
    private String imagePath;
    private String category;
    private String filePath;
    private String phases;
    private String author;

    public Book(String title, String mAbstract, String imagePath, String category, String filePath, String phases, String author) {
        this.title = title;
        this.mAbstract = mAbstract;
        this.imagePath = imagePath;
        this.category = category;
        this.filePath = filePath;
        this.phases = phases;
        this.author = author;
    }
}