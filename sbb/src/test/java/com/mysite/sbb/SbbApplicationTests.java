package com.mysite.sbb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
@RunWith(SpringRunner.class)
@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@BeforeEach
	public void beforeEach(){
		questionRepository.deleteAll();
		Question q = new Question();
		q.setSubject("ssb");
		q.setContent("ssb에 대해서 알고 싶습니다.");
		q.setLocalDateTime(LocalDateTime.now());
		this.questionRepository.save(q);  // 첫번째 질문 저장

//		Question q2 = new Question();
//		q2.setSubject("스프링부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setLocalDateTime(LocalDateTime.now());
//		this.questionRepository.save(q2);  // 두번째 질문 저장

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);

		Answer a2 = new Answer();
		a2.setContent("네 자동으로 생성됩니다2.");
		a2.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
		a2.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a2);


	}


	@Test
	public void find(){
		List<Question> all = questionRepository.findAll();
		assertEquals(1, all.size());

		Question question = all.get(0);
		assertEquals("ssb", question.getSubject());

		Question question1 = questionRepository.findById(1).get();
//		assertEquals(question, question1); 엔티티 동일성 보장은 안해줌 ( EntityManager가 필요 )
		assertEquals(question.getSubject(), question1.getSubject());
	}

	@Test
	public void find_subject(){
		List<Question> all = questionRepository.findAll();
		for (Question i : all) {
			System.out.println(i.getSubject());
		}
		List<Question> list = questionRepository.findBySubject("ssb");
		assertEquals(list.get(0).getSubject(), "ssb");

	}

	@Test
	public void find_subjectAndContent(){
		Question findQuestion = questionRepository.findBySubjectAndContent("ssb", "ssb에 대해서 알고 싶습니다.").orElseGet(null);
		assertEquals(findQuestion.getId(), 1);
	}

	@Test
	public void find_subjectLike(){
		List<Question> bySubjectLike = questionRepository.findBySubjectLike("ssb%");
		assertEquals(bySubjectLike.get(0).getSubject(), "ssb");
	}

	@Test
	public void modify_subject(){
		Optional<Question> byId = questionRepository.findById(1);
		assertTrue(byId.isPresent());

		Question question = byId.get();
		question.setSubject("수정된 제목");
		questionRepository.save(question);
	}

	@Test
	public void delete(){
		Question findQuestion = questionRepository.findById(1).orElse(null);
		questionRepository.delete(findQuestion);
		assertEquals(1, questionRepository.count());
	}

	@Test
	void testJpa() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		System.out.println("a.getContent() = " + a.getContent());
		assertEquals(1, a.getQuestion().getId());
	}

	@Transactional
	@Test
	void testJpa2() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		System.out.println("q.getContent() = " + q.getContent());
		List<Answer> answerList = q.getAnswerList();
		for (Answer answer : answerList) {
			System.out.println("answer.getContent() = " + answer.getContent());
		}
		System.out.println("test");
//		assertEquals(1, answerList.size());
//		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}

}
