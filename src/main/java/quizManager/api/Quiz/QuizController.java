package quizManager.api.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuizController {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizService quizService;


    @RequestMapping(value = "/quiz/findByName", method = RequestMethod.GET)
    private ResponseEntity<?> findQuizByName(@RequestParam String name){
        List<Quiz> quizzes = quizRepository.findByName(name);
        if(!quizzes.isEmpty()){
            return new ResponseEntity<List>(quizzes, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("No quizzes found by that name", HttpStatus.NO_CONTENT);
        }
    }


    @RequestMapping(value = "/quiz/create", method = RequestMethod.POST)
    private ResponseEntity<?> createQuiz(@RequestBody Quiz quiz){
        if(quizService.quizFieldValid(quiz)){
            Quiz savedQuiz = quizRepository.save(quiz);
            return new ResponseEntity<Quiz>(savedQuiz, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Invalid values provided - Please check and try again", HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/quiz/update", method = RequestMethod.PUT)
    private ResponseEntity<?> updateQuiz(@RequestBody Quiz quiz){
        if(quizRepository.existsById(quiz.getId())) {
            if(quizService.quizFieldValid(quiz)) {
                Quiz updatedQuiz = quizRepository.save(quiz);
                return new ResponseEntity<Quiz>(updatedQuiz, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Invalid values provided - Please check and try again", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<String>("Quiz not found", HttpStatus.NO_CONTENT);
        }
    }


    @RequestMapping(value = "/quiz/delete", method = RequestMethod.DELETE)
    private ResponseEntity<String> deleteQuiz(Quiz quiz){
        if(quizRepository.existsById(quiz.getId())){
            quizService.deleteQuiz(quiz);
            return new ResponseEntity<String>("Quiz deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Unable to delete - Quiz not found", HttpStatus.NO_CONTENT);
        }
    }

}
