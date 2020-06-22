package quizManager.api.Answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer>findByQuestionId(Long questionId);

    @Transactional
    @Modifying
    @Query(value = "truncate table answers", nativeQuery = true)
    void truncateTable();

}
