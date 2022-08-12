package com.mysite.sbb.question;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.mysite.sbb.answer.Answer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@Entity
@Transactional

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Answer> answerList = new ArrayList<>();

    public void addAnswerList(Answer answer){
        if (answerList == null) {
            this.answerList = new ArrayList<>();
        }
        answerList.add(answer);
    }

}