package com.example.demo.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Collection {
    private String username;
    private String title;

    public Collection(String username,String title) {
        this.username = username;
        this.title = title;
    }
}
