package com.mysite.sbb;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Getter
@Setter
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@ToString
public class Session {
    private static final long serialVersionUID = 1L;

    static private int age;

    static void setAge(int age){
        Session.age = age;
    }

    static int getAge(){
        return Session.age;
    }
}
