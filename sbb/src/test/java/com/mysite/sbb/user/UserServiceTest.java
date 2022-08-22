package com.mysite.sbb.user;

import com.mysite.sbb.AnswerRepositoryTest;
import com.mysite.sbb.QuestionRepositoryTest;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    public static void createSampleData(UserService userService) {
        userService.create("admin", "admin@test.com", "1234");
        userService.create("user1", "user1@test.com", "1234");
    }

    private void createSampleData() {
        createSampleData(userService);
    }

    public static void clearData(UserRepository userRepository, AnswerRepository answerRepository, QuestionRepository questionRepository) {
        AnswerRepositoryTest.clearData(answerRepository, questionRepository);
        QuestionRepositoryTest.clearData(questionRepository);
        userRepository.deleteAll(); // DELETE FROM site_user;
        userRepository.truncateTable();
    }

    private void clearData() {
        clearData(userRepository, answerRepository, questionRepository);
    }

    @Test
    @DisplayName("회원가입이 가능하다.")
    public void t1() {
        userService.create("user2", "user2@email.com", "1234");
    }


}