package quizManager.api.Answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    AnswerService answerService;


    // find by question id
    @RequestMapping(value = "/answer/findByQuestionId", method = RequestMethod.GET)
    private ResponseEntity<?> findAnswerByQuestionId(@RequestParam Long questionId){
        List<Answer> answers = answerRepository.findByQuestionId(questionId);
        if(!answers.isEmpty()){
            return new ResponseEntity<List>(answers, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("This question currently has no answers", HttpStatus.NO_CONTENT);
        }
    }

    // create

    // update

    // delete

}
