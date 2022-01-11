package ru.vlsu.psytest.api.questions;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vlsu.psytest.api.questions.Results;

public interface ResultsRepository extends JpaRepository <Results, Integer> {
    Results findByType(String type);
}
