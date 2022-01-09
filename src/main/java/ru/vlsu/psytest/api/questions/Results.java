package ru.vlsu.psytest.api.questions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Results {

    //INDUS SOSAT
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id_result;
    private String type;
    private String description;

    public Results() {
    }

    public Results(Integer id, String type, String description) {
        this.id_result = id;
        this.type = type;
        this.description = description;
    }

    public Integer getId() {
        return id_result;
    }

    public void setId(Integer id) {
        this.id_result = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
