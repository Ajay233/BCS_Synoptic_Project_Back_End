package quizManager.api.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/question/create", method = RequestMethod.POST)
    private ResponseEntity<?> createQuestions(@RequestBody List<Question> questions){
        if(questionService.questionFieldsValid(questions)){
            List<Question> savedQuestions = questionsRepository.saveAll(questions);
            return new ResponseEntity<List>(savedQuestions, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Invalid values provided - Please check and try again", HttpStatus.BAD_REQUEST);
        }
    }


    // update
    @RequestMapping(value = "/question/update", method = RequestMethod.PUT)
    private ResponseEntity<?> updateQuestions(@RequestBody List<Question> questions){
        if(questionService.allExist(questions)) {
            if (questionService.questionFieldsValid(questions)) {
                List<Question> updatedQuestions = questionsRepository.saveAll(questions);
                return new ResponseEntity<List>(updatedQuestions, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Invalid values provided - Please check and try again", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<String>("Questions not found", HttpStatus.NO_CONTENT);
        }
    }



    // delete

    @RequestMapping(value = "/question/delete", method = RequestMethod.DELETE)
    private ResponseEntity<String> deleteQuestions(@RequestBody List<Question> questions){
        if(questionService.allExist(questions)){
            questionService.deleteQuestions(questions);
            return new ResponseEntity<String>("Question deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Questions not found", HttpStatus.NO_CONTENT);
        }
    }

}
