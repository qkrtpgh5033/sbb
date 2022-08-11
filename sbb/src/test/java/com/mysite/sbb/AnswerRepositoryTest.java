package com.mysite.sbb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AnswerRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
//    @Test
    public void beforeEach(){

        clearData();
        addData();

    }

    public void clearData(){
        questionRepository.disableForeignKeyCHECKS();
        questionRepository.truncateQuestion();
        questionRepository.ableForeignKeyCHECKS();

        answerRepository.disableForeignKeyCHECKS();
        answerRepository.truncateAnswer();
        answerRepository.ableForeignKeyCHECKS();

    }

    public void addData(){
        Question q = new Question();
        q.setSubject("ssb");
        q.setContent("ssb에 대해서 알고 싶습니다.");
        q.setCreateDate(LocalDateTime.now());
//        questionRepository.save(q);  // 첫번째 질문 저장
        questionRepository.saveAndFlush(q);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.saveAndFlush(q2);  // 두번째 질문 저장

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.saveAndFlush(a);

        Answer a2 = new Answer();
        a2.setContent("네 자동으로 생성됩니다2.");
        a2.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a2.setCreateDate(LocalDateTime.now());
        this.answerRepository.saveAndFlush(a2);



    }

    @Test
    @Transactional(readOnly=true)
    @Rollback(value = false)
    public void test() {

        Question question = questionRepository.findById(1).orElse(null);
        System.out.println("question.getSubject() = " + question.getSubject());

//        List<Answer> answerList = question.getAnswerList();
        List<Answer> answerList = answerRepository.findByQuestion(question);

        for (Answer answer : answerList) {
            System.out.println("answer.getContent() = " + answer.getContent());
        }
        assertEquals(2, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }


}