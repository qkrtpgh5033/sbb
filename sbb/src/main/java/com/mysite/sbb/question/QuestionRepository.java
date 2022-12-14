package com.mysite.sbb.question;

import com.mysite.sbb.base.RepositoryUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer>, RepositoryUtil {

     List<Question> findBySubject(String subject);

     Optional<Question> findBySubjectAndContent(String subject, String content);

     List<Question> findBySubjectLike(String subject);


     @Transactional
     @Modifying
     @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
     void truncate(); // 이거 지우면 안됨, truncateTable 하면 자동으로 이게 실행됨

     Page<Question> findAll(Pageable pageable);

     Page<Question> findBySubjectContains(Pageable pageable, String kw);
     Page<Question> findBySubjectContainsOrContentContains(Pageable pageable, String kw, String kw_);
     Page<Question> findBySubjectContainsOrContentContainsOrAuthor_usernameContains(Pageable pageable, String kw, String kw_, String kw__);
     Page<Question> findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerListContentContains(Pageable pageable, String kw, String kw_, String kw__, String kw___);
     Page<Question> findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerListContentContainsOrAnswerList_author_username(Pageable pageable, String kw, String kw_, String kw__, String kw___, String kw____);


}
