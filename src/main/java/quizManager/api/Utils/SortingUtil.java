package quizManager.api.Utils;

import org.springframework.stereotype.Service;
import quizManager.api.Question.Question;

import java.util.List;

@Service
public class SortingUtil {

    public void questionsSelectSort(List<Question> questions, int listSize){
        int highestIndex = listSize -1;

        for(int i = 0; i < highestIndex; i++){

            int smallestVal = questions.get(i).getQuestionNumber();
            int indexOfSmallestVal = i;

            for(int x = i; x < highestIndex; x++){
                
            }

        }
    }

}
