package ru.vlsu.psytest.api.questions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt,Long> {
    @Query("select a from Attempt a where a.finished=false and  a.user.id=:id")
    Attempt getCurrentAttempt(@Param("id") long id);

    @Query("select a from Attempt a where a.user.id=:id")
    List<Attempt> getAllUserAttempts(@Param("id") long id);

    @Query("select a from Attempt a where a.finished=false")
    List<Attempt> getAllNotFinishedAttempts();

    @Query("select a from Attempt a where a.finished=true")
    List<Attempt> getAllFinishedAttempts();
}
