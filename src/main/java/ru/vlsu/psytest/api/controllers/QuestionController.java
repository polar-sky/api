package ru.vlsu.psytest.api.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.psytest.api.questions.*;
import ru.vlsu.psytest.api.users.User;
import ru.vlsu.psytest.api.users.UserRepository;
import ru.vlsu.psytest.api.users.response.MessageResponse;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

//здесь обрабатываются запросы для restful web сервисов
@RestController
@RequestMapping("/api/testing")
public class QuestionController {

    @Autowired
    private QuestionService service;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/questions")
    @ApiOperation("Возвращает список всех вопросов")
    public List<Question> list(){
        return service.listAll();
    }

    @GetMapping("/question/{id}")
    @ApiOperation("Возвращает один из вопросов по Id")
    public ResponseEntity<Question> get(@PathVariable Integer id){
        try{
            Question question = service.getQuestion(id);
            return  new ResponseEntity<Question>(question, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/questions")
    @ApiOperation("Добавляет вопрос в базу данных")
    public void add (Question question) {
        service.save(question);
    }

    @PutMapping("/questions/{id}")
    @ApiOperation("Редактирование вопроса")
    public ResponseEntity<?> update(@RequestBody Question question, @PathVariable Integer id ){

        try{
            Question existQuestion = service.getQuestion(id);
            service.save(question);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/question/{id}")
    @ApiOperation("Удаление вопроса по id")
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }

    @GetMapping("/results")
    @ApiOperation("Получает данные о пройденном тесте и возвращает результат прохождения теста")
    public ResponseEntity<?> results(@RequestParam Integer IE, @RequestParam Integer SN, @RequestParam Integer TF, @RequestParam Integer JP, @RequestParam Long id){
     //return service.getResult(IE,SN,TF,JP,id).toString();
        if (service.getResult(IE,SN,TF,JP,id) == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Conflict. Attempt is not finished"));
        }
        return ResponseEntity.ok(new ResultsResponse(service.getResult(IE,SN,TF,JP,id)));
    }

    @GetMapping("/attempts")
    @ApiOperation("Возвращает список всех popit ок пользователя. Результаты прилагаются, где это возможно")
    public ResponseEntity<AttemptResponse>  attempts(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        return ResponseEntity.ok(new AttemptResponse(service.getUserAttempts(user)));
    }

    @PostMapping("/startAttempt")
    @ApiOperation("Создает у пользователя новую попытку. Если предыдущая не завершена то удаляет ее")
    public ResponseEntity<MessageResponse>  startAttempt(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if(service.startTest(user))
            return ResponseEntity.ok(new MessageResponse("Test started!"));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Something went wrong("));
    }

    @PostMapping("/finishAttempt")
    @ApiOperation("Завершает попытку, если ответы есть на все вопросы, возвращая id это попытки")
    public ResponseEntity<?>  finishAttempt(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        Long result = service.finishTest(user);
        if(result!=null)
            return ResponseEntity.ok(new AttemptRequestId(result));
        else
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Not all questions have answers( or maybe you have no attempt to finish"));
    }
}
