package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.answer.QuestionForm;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor // 생성자 주입
@RequestMapping("/question")
public class QuestionController {
    // @Autowired // 필드 주입
    private final QuestionService questionService;
    private final UserService userService;

    @RequestMapping("/list")
    // 이 자리에 @ResponseBody가 없으면 resources/question_list/question_list.html 파일을 뷰로 삼는다.
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
//        List<Question> questionList = questionService.getList();

//         미래에 실행된 question_list.html 에서
//         questionList 라는 이름으로 questionList 변수를 사용할 수 있다.
//        model.addAttribute("questionList", questionList);

        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @RequestMapping("/list2")
    @ResponseBody
    public String list2() {
        return "하하111777";
    }

    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser user = userService.getUser(principal.getName());
        questionService.create(questionForm.getSubject(), questionForm.getContent(), user);
        return "redirect:/question/list";

    }


}