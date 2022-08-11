package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

     List<Question> findBySubject(String subject);

     Optional<Question> findBySubjectAndContent(String subject, String content);

     List<Question> findBySubjectLike(String subject);


     @Transactional
     @Modifying
     @Query(value = "truncate question", nativeQuery = true)
     void truncateQuestion();

     @Transactional
     @Modifying
     @Query(value = "set foreign_key_checks = 0", nativeQuery = true)
     void disableForeignKeyCHECKS();

     @Transactional
     @Modifying
     @Query(value = "set foreign_key_checks = 1", nativeQuery = true)
     void ableForeignKeyCHECKS();

}
