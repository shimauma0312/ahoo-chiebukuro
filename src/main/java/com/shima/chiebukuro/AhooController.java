package com.shima.chiebukuro;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("questions", questionService.findByQuestions());
        return "home.html";
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
        questionService.insert(questionForm);
        return "redirect:/home";
    }
}