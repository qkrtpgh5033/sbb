package com.mysite.sbb;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@ToString
public class ArticleDto {
    private static long lastId = 0;
    private String title;
    private String body;
    private long id;

    public ArticleDto(String title, String body) {
        id = ++lastId;
        this.title = title;
        this.body = body;
    }
}
