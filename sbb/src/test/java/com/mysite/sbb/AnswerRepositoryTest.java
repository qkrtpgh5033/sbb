package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserRepository;
import com.mysite.sbb.user.UserService;
import com.mysite.sbb.user.UserServiceTest;
import groovy.transform.AutoImplement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AnswerRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

//    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    public static void clearData(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        QuestionRepositoryTest.clearData(questionRepository);

        answerRepository.deleteAll(); // DELETE FROM question;
        answerRepository.truncateTable();
    }

    private void clearData() {
        clearData(answerRepository, questionRepository);
    }

    private void createSampleData() {
        QuestionRepositoryTest.createSampleData(questionRepository);

        // 관련 답변이 하나없는 상태에서 쿼리 발생
        Question q = questionRepository.findById(1).get();

        Answer a1 = new Answer();
        a1.setContent("sbb는 질문답변 게시판 입니다.");
        a1.setAuthor(new SiteUser(1L));
        a1.setCreateDate(LocalDateTime.now());
        q.addAnswer(a1);

        Answer a2 = new Answer();
        a2.setContent("sbb에서는 주로 스프링부트관련 내용을 다룹니다.");
        a2.setAuthor(new SiteUser(2L));
        a2.setCreateDate(LocalDateTime.now());
        q.addAnswer(a2);

        questionRepository.save(q);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void 저장() {
        Question q = questionRepository.findById(2).get();

        Answer a1 = new Answer();
        a1.setContent("네 자동으로 생성됩니다.");
        a1.setAuthor(new SiteUser(1L));
        a1.setCreateDate(LocalDateTime.now());
        q.addAnswer(a1);

        Answer a2 = new Answer();
        a2.setContent("네네~ 맞아요!");
        a2.setAuthor(new SiteUser(2L));
        a2.setCreateDate(LocalDateTime.now());
        q.addAnswer(a2);

        questionRepository.save(q);
    }

    @Test
    void 조회() {
        Answer a = this.answerRepository.findById(1).get();
        assertThat(a.getContent()).isEqualTo("sbb는 질문답변 게시판 입니다.");
    }

    @Test
    void 관련된_question_조회() {
        Answer a = this.answerRepository.findById(1).get();
        Question q = a.getQuestion();

        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback(false)
    void question으로부터_관련된_질문들_조회() {
        // SELECT * FROM question WHERE id = 1
        Question q = questionRepository.findById(1).get();
        // DB 연결이 끊김

        // SELECT * FROM answer WHERE question_id = 1
        List<Answer> answerList = q.getAnswerList();

        assertThat(answerList.size()).isEqualTo(2);
        assertThat(answerList.get(0).getContent()).isEqualTo("sbb는 질문답변 게시판 입니다.");
    }
}