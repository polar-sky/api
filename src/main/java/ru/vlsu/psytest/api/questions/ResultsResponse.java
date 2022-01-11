package ru.vlsu.psytest.api.questions;

import java.util.List;

public class ResultsResponse {

    private Results results;

    public ResultsResponse(Results results) {
        this.results = results;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }
}
