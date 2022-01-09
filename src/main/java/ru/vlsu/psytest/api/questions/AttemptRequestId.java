package ru.vlsu.psytest.api.questions;

public class AttemptRequestId {
    private Long id;

    public AttemptRequestId() {
    }

    public AttemptRequestId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}