package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @GetMapping("/plus2")
    @ResponseBody
    public void showPlus2(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));
        res.getWriter().append(a + b + "");
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
    public String increase() {
        return """
                <h1>increase</h1>
                <h1>%d</h1>
                """.formatted(num++);
    }

    @GetMapping("/problem4")
    @ResponseBody
    public String problem4() {
        return """
                   <form method="POST" action="/gugudan">
                    <input type="number" name="a" placeholder= "구구단을 입력해주세요." />
                    <input type="submit" value="gugudan POST 방식으로 이동" />
                </form>
                """;
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String gugudan(Integer dan, Integer limit) {
        if (dan == null)
            dan = 9;

        if (limit == null)
            limit = 9;

        Integer finalDan = dan;
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
                .collect(Collectors.joining("<br>\n"));

    }

    @GetMapping("/mbti")
    @ResponseBody
    public String mbti(String name) {
        String mbti = "";
        if (name.equals("홍길동"))
            mbti = "INFP";
        else if (name.equals("홍길순")) {
            mbti = "ENFP";
        } else if (name.equals("임꺽정")) {
            mbti = "INFJ";
        } else if (name.equals("박범서")) {
            mbti = "ISFP";
        }
        return """
                <h1>%s</h1>
                """.formatted(mbti);
    }
}
