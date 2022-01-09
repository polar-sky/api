package ru.vlsu.psytest.api.questions;

import javax.persistence.*;

@Entity
public class Question {

    @Id
    private Integer ID;
    private String text1;
    private String type;


    //конструктор

    public Question(Integer ID, String text1, String type) {
        this.ID = ID;
        this.text1 = text1;
        this.type = type;
    }

    public Question() {

    }

    //геттеры и сеттеры
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
