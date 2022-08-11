package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Transactional
    @Modifying
    @Query(value = "truncate answer", nativeQuery = true)
    void truncateAnswer();

    @Transactional
    @Modifying
    @Query(value = "set foreign_key_checks = 0", nativeQuery = true)
    void disableForeignKeyCHECKS();

    @Transactional
    @Modifying
    @Query(value = "set foreign_key_checks = 1", nativeQuery = true)
    void ableForeignKeyCHECKS();

    List<Answer> findByQuestion(Question question);
}
