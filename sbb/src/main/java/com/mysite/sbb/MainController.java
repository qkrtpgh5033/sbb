package com.mysite.sbb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {


    @Autowired
    ObjectMapper mapper;
    private static int num = 0;
    ArrayList<ArticleDto> articleList = new ArrayList<>(
            Arrays.asList(
                    new ArticleDto("제목", "내용"),
                    new ArticleDto("제목", "내용"))
    );

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

    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String mbti(@PathVariable String name) {
        String mbti = switch (name) {
            case "홍길동" -> "INFP";
            default -> "모름";
        };

        return """
                <h1>%s</h1>
                """.formatted(mbti);
    }

    @GetMapping("/saveSessionAge/{age}")
    @ResponseBody
    public void showSession(@PathVariable int age, HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("age", age);
    }

    @GetMapping("/getSessionAge")
    @ResponseBody
    public String getSession(HttpSession session) {
        return """
                <h1>%d</h1>
                """.formatted(session.getAttribute("age"));

    }

    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(@RequestParam("title") String title, @RequestParam("body") String body) {
        ArticleDto articleDto = new ArticleDto(title, body);
        articleList.add(articleDto);
        return """
                <h1>%d번 글이 등록되었습니다.</h1>
                """.formatted(articleDto.getId());
    }


    @GetMapping("/article/{id}")
    @ResponseBody
    public String showArticle(@PathVariable Integer id) throws JsonProcessingException {

        ArticleDto findArticle = null;
        for (ArticleDto articleDto : articleList) {
            if (articleDto.getId() == id)
                findArticle = articleDto;
        }
        if (findArticle == null) {
            return """
                    <h1>내용 없음</h1> 
                    """;
        }


        return """
                %s
                """.formatted(mapper.writeValueAsString(findArticle));
    }

    @GetMapping("/modifyArticle")
    @ResponseBody
    public String modifyArticle(@RequestParam("id") long id, @RequestParam("title") String title, @RequestParam("body") String body) {

        ArticleDto findArticle = articleList
                .stream()
                .filter(a -> a.getId() == id) // 1번
                .findFirst()
                .orElse(null);


        if (findArticle == null)
            return """
                    <h1>해당 게시물은 존재하지 않습니다.</h1>
                    """;

        findArticle.setTitle(title);
        findArticle.setBody(body);

        return """
                %d번 게시물이 수정되었습니다.
                """.formatted(id);
    }

    @GetMapping("/deleteArticle")
    @ResponseBody
    public String deleteArticle(@RequestParam("id") long id) {

        ArticleDto findArticle = articleList
                .stream()
                .filter(a -> a.getId() == id) // 1번
                .findFirst()
                .orElse(null);


        if (findArticle == null)
            return """
                    <h1>해당 게시물은 존재하지 않습니다.</h1>
                    """;

        articleList.remove(findArticle);
        return """
                %d번 게시물이 삭제되었습니다.
                """.formatted(id);
    }

    @GetMapping("/addPerson")
    @ResponseBody
    public Person addPerson(Person person){
        return person;
    }

    @GetMapping("/addPerson/{id}")
    @ResponseBody
    public Person addPerson2(@PathVariable("id") int id, Person person){
        person.setId(id);
        return person;
    }

}
