package quizManager.api.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    QuestionsRepository questionsRepository;

    @Autowired
    QuestionService questionService;


    // find by quiz id
    @RequestMapping(value = "/question/findByQuizId", method = RequestMethod.GET)
    private ResponseEntity<?> findQuestionByQuizId(@RequestParam Long quizId){
        List<Question> questions = questionsRepository.findByQuizId(quizId);
        if(!questions.isEmpty()){
            return new ResponseEntity<List>(questions, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("This quiz currently has no questions", HttpStatus.NO_CONTENT);
        }
    }


    // create


    // update


    // answer


}
