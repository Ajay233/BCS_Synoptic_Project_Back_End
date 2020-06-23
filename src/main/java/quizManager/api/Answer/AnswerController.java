package quizManager.api.Answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quizManager.api.Utils.SortingUtil;

import java.util.List;

@RestController
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    AnswerService answerService;

    @Autowired
    SortingUtil sortingUtil;


    // find by question id
    @RequestMapping(value = "/answer/findByQuestionId", method = RequestMethod.GET)
    private ResponseEntity<?> findAnswersByQuestionId(@RequestParam Long questionId){
        List<Answer> answers = answerRepository.findByQuestionId(questionId);
        if(!answers.isEmpty()){
            sortingUtil.answerSelectSort(answers, answers.size());
            return new ResponseEntity<List>(answers, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("This question currently has no answers", HttpStatus.NO_CONTENT);
        }
    }

    // create
    @RequestMapping(value = "/answer/create", method = RequestMethod.POST)
    private ResponseEntity<?> createAnswers(@RequestBody List<Answer> answers){
        if(answerService.answerFieldsValid(answers)){
            List<Answer> savedAnswers = answerRepository.saveAll(answers);
            return new ResponseEntity<List>(savedAnswers, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Invalid values provided - Please check and try again", HttpStatus.BAD_REQUEST);
        }
    }

    // update
    @RequestMapping(value = "/answer/update", method = RequestMethod.PUT)
    private ResponseEntity<?> updateAnswers(@RequestBody List<Answer> answers){
        if(answerService.allExist(answers)) {
            if (answerService.answerFieldsValid(answers)) {
                List<Answer> updatedAnswers = answerRepository.saveAll(answers);
                return new ResponseEntity<List>(updatedAnswers, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Invalid values provided - Please check and try again", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<String>("Answers not found", HttpStatus.NO_CONTENT);
        }
    }

    // delete
    @RequestMapping(value = "/answer/delete", method = RequestMethod.DELETE)
    private ResponseEntity<String> deleteAnswers(@RequestBody List<Answer> answers){
        if(answerService.allExist(answers)){
            answerRepository.deleteAll(answers);
            return new ResponseEntity<String>("Answer deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Answers not found", HttpStatus.NO_CONTENT);
        }
    }

}
