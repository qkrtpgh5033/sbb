package com.mysite.sbb.user;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    public SiteUser(Long id) {
        this.id = id;
    }


    //    @OneToMany(mappedBy = "siteuser", cascade = {CascadeType.ALL})
//    private List<Question> questionList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "siteuser", cascade = {CascadeType.ALL})
//    private List<Answer> answerList = new ArrayList<>();

//    @OneToMany(mappedBy = "site_user", cascade = {CascadeType.ALL})
//    private List<Question> questionList = new ArrayList<>();

//    @OneToMany
}
