package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    static int num = 0;
    @RequestMapping("/sbb")
    // 아래 함수의 리턴값을 그대로 브라우저에 표시
    // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답의 바디에 담음.
    @ResponseBody
    public String index() {
        System.out.println("test");
        return "안녕!!!!!~~";
    }

    @GetMapping("/problem1")
    @ResponseBody
    public String showProblem1() {
        return """
                <form method="POST" action="/plus">
                    <input type="number" name="a" placeholder= "나이" />
                    <input type="number" name="b" placeholder= "나이" />
                    <input type="submit" value="page2로 POST 방식으로 이동" />
                </form>
                """;
    }

    @GetMapping("/plus")
    @ResponseBody
    public String plus(@RequestParam("a") int a, @RequestParam("b") int b) {
        return """
                  <h1>Get</h1>
                %d + %d = %d
                """.formatted(a, b, (a + b));

    }

    @PostMapping("/plus")
    @ResponseBody
    public String plusPost(@RequestParam("a") int a, @RequestParam("b") int b) {
        return """
                <h1>Post</h1>
                %d + %d = %d
                """.formatted(a, b, (a + b));

    }

    @GetMapping("/minus")
    @ResponseBody
    public String minus(@RequestParam("a") int a, @RequestParam("b") int b) {
        return """
                  <h1>Get</h1>
                %d - %d = %d
                """.formatted(a, b, (a - b));

    }

    @PostMapping("/minus")
    @ResponseBody
    public String minusPost(@RequestParam("a") int a, @RequestParam("b") int b) {
        return """
                <h1>Post</h1>
                %d - %d = %d
                """.formatted(a, b, (a - b));

    }

    @GetMapping("/increase")
    @ResponseBody
    public String increase(){
        return """
                <h1>increase</h1>
                <h1>%d</h1>
                """.formatted(num++);
    }
}
