package quizManager.api.Utils;

import org.springframework.stereotype.Service;
import quizManager.api.Answer.Answer;
import quizManager.api.Question.Question;

import java.util.List;

@Service
public class SortingUtil {

    public void questionsSelectSort(List<Question> questions, int listSize){
        int highestIndex = listSize - 1;

        for(int i = 0; i < highestIndex; i++){

            int smallestVal = questions.get(i).getQuestionNumber();
            int indexOfSmallestVal = i;

            for(int x = i; x < highestIndex; x++){
                if(smallestVal > questions.get(x + 1).getQuestionNumber()){
                    smallestVal = questions.get(x + 1).getQuestionNumber();
                    indexOfSmallestVal = x + 1;
                }
            }

            Question startingVal = questions.get(i);
            Question valToSwap = questions.get(indexOfSmallestVal);
            questions.set(i, valToSwap);
            questions.set(indexOfSmallestVal, startingVal);

        }
    }

    public void answerSelectSort(List<Answer> answers, int listSize){
        int highestIndex = listSize - 1;

        for(int i = 0; i < highestIndex; i++){

            String smallestVal = answers.get(i).getAnswerIndex();
            int indexOfSmallestVal = i;

            System.out.println("---starting new run---");

            for(int x = i; x < highestIndex; x++){
                if(smallestVal.compareTo(answers.get(x + 1).getAnswerIndex()) > 0){
                    smallestVal = answers.get(x + 1).getAnswerIndex();
                    System.out.println("New smallest val: " + smallestVal);
                    indexOfSmallestVal = x + 1;
                }
            }

            Answer startingVal = answers.get(i);
            Answer valToSwap = answers.get(indexOfSmallestVal);
            answers.set(i, valToSwap);
            answers.set(indexOfSmallestVal, startingVal);

            System.out.println("starting val: " + startingVal);
            System.out.println("val to swap: " + valToSwap);
            System.out.println(answers);
            System.out.println("---end run---");
        }
    }

}
