package com.shima.chiebukuro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shima.chiebukuro.model.AnswerForm;
import com.shima.chiebukuro.model.QuestionForm;
import com.shima.chiebukuro.service.AnswerService;
import com.shima.chiebukuro.service.QuestionService;

@Controller
public class AhooController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    public AhooController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    // redirect処理
    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String index(QuestionForm questionForm, Model model) {
        model.addAttribute("titles", questionService.findByQuestions());
        return "home.html";
    }

    @RequestMapping("/question/{id}")
    public String getQuestion(AnswerForm answerForm, @PathVariable("id") String id, Model model) {
        model.addAttribute("question", questionService.findByQuestionContent(id));
        model.addAttribute("answers", answerService.findByAnswerContent(id));
        return "question.html";
    }

    @RequestMapping("/form")
    public String form(QuestionForm questionForm) {
        return "form.html";
    }

    @PostMapping("/insertQuestion")
    public String insert(@Validated QuestionForm questionForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "form.html";
        }
        questionService.insertQuestion(questionForm);
        return "redirect:/home";
    }

    @PostMapping("/question/insertAnswer")
    public String insertAnswer(@Validated AnswerForm answerForm, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            answerService.insertAnswer(answerForm);
        }
        String redirectPath = String.format("redirect:/question/%s", answerForm.getQuestionId());
        return redirectPath;
    }
}
