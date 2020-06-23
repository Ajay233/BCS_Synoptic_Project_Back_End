package quizManager.api.Quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizController {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizService quizService;

    // find by name

    @RequestMapping(value = "/quiz/findByName", method = RequestMethod.GET)
    private ResponseEntity<?> findQuizByName(@RequestParam String name){
        List<Quiz> quizzes = quizRepository.findByName(name);
        if(!quizzes.isEmpty()){
            return new ResponseEntity<List>(quizzes, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("No quizzes found by that name", HttpStatus.BAD_REQUEST);
        }
    }

    // create

    // update

    // delete

}
