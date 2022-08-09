package com.mysite.sbb;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class Person {
    private int id;
    @Setter
    private int age;
    @Setter
    private String name;

    public void setId(int id) {
        this.id = id;
    }


}
