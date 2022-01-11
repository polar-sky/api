package ru.vlsu.psytest.api.questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vlsu.psytest.api.questions.Question;
import ru.vlsu.psytest.api.questions.QuestionRepository;
import ru.vlsu.psytest.api.users.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repo;

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private ResultsRepository resultsRepository;

    public List<Question> listAll(){
        return repo.findAll();
    }

    public void save (Question question){
        repo.save(question);
    }

    public Question getQuestion(Integer id){
        return repo.findById(id).get();
    }

    public void delete(Integer id){
        repo.deleteById(id);
    }

    public Results getResult(Integer IE, Integer SN, Integer TF, Integer JP, Long id){

        Attempt attempt = attemptRepository.getById(id);
        Results result;

        // инициализируем перменные под черты характера
        String introvertOrExtrovert;
        String sensorOrIntuitive;
        String thinkerOrFeeler;
        String judgerOrPerceiver;

        // Main algorithm. Stores the user's Test answers as corresponding Myers-Briggs letters.
        if (IE <= 20){
            introvertOrExtrovert = "E";
        }else{
            introvertOrExtrovert = "I";
        }

        if( SN <= 20){
            sensorOrIntuitive = "S";
        }else{
            sensorOrIntuitive = "N";
        }

        if(TF <= 20){
            thinkerOrFeeler = "T";
        }else{
            thinkerOrFeeler = "F";
        }

        if(JP <= 20){
            judgerOrPerceiver = "J";
        }else{
            judgerOrPerceiver = "P";
        }

        // конкатенируем строчку результата:
        String res = introvertOrExtrovert + sensorOrIntuitive + thinkerOrFeeler + judgerOrPerceiver;

        result = resultsRepository.findByType(res);
        attempt.setResult(result);

        return result;
    }

    @Transactional
    public boolean startTest(User user)  {
        Attempt attempt = attemptRepository.getCurrentAttempt(user.getId());
        if(attempt!=null) {
            attemptRepository.delete(attempt);
        }
        attempt = new Attempt();
        attempt.setFinished(false);
        attempt.setDate(new Date(System.currentTimeMillis()));
        attempt.setUser(user);
        try {
            attemptRepository.save(attempt);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    @Transactional
    public Long finishTest(User user)  {
        Attempt attempt = attemptRepository.getCurrentAttempt(user.getId());
            attempt.setFinished(true);
            attempt.setDate(new Date(Calendar.getInstance().getTime().getTime()));
            attemptRepository.save(attempt);
            return attempt.getId();
        }

    public List<Attempt> getUserAttempts(User user)  {
        return attemptRepository.getAllUserAttempts(user.getId());
    }
}
