package com.shima.chiebukuro;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shima.chiebukuro.service.QuestionService;

@Controller
public class AhooController {

    private final QuestionService questionService;

    public AhooController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // redirect処理
    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String index(QuestionForm questionForm, Model model) {
        model.addAttribute("titles", questionService.findQuestionTitles());
        return "home.html";
    }

    @RequestMapping("/content")
    public String content(Model model) {
        return "question.html";
    }

    @RequestMapping("/form")
    public String form(QuestionForm questionForm) {
        return "form.html";
    }

    @PostMapping("/insert")
    public String insert(@Validated QuestionForm questionForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "form.html";
        }
        questionService.insertQuestion(questionForm);
        return "redirect:/home";
    }
}
