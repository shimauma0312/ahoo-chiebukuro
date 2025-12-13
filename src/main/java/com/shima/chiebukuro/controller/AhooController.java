package com.shima.chiebukuro.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shima.chiebukuro.model.AnswerForm;
import com.shima.chiebukuro.model.QuestionForm;
import com.shima.chiebukuro.model.User;
import com.shima.chiebukuro.service.AnswerService;
import com.shima.chiebukuro.service.QuestionService;
import com.shima.chiebukuro.service.UserService;

/**
 * AHOO知恵袋のメインコントローラー
 */
@Controller
public class AhooController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    public AhooController(QuestionService questionService, AnswerService answerService, UserService userService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.userService = userService;
    }

    /**
     * ルートアクセス時のリダイレクト
     * 
     * @return ホームへのリダイレクト
     */
    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }

    /**
     * ホーム画面を表示
     * 
     * @param questionForm 質問フォーム
     * @param model        モデル
     * @param userDetails  ログインユーザー情報
     * @return テンプレート名
     */
    @RequestMapping("/home")
    public String index(QuestionForm questionForm, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("titles", questionService.findByQuestions());
        model.addAttribute("popularQuestions", questionService.findPopularQuestions(5));
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        return "home.html";
    }

    /**
     * 質問詳細画面を表示
     * 
     * @param answerForm 回答フォーム
     * @param id         質問ID
     * @param model      モデル
     * @return テンプレート名
     */
    @RequestMapping("/question/{id}")
    public String getQuestion(AnswerForm answerForm, @PathVariable("id") String id, Model model) {
        model.addAttribute("question", questionService.findByQuestionContent(id));
        model.addAttribute("answers", answerService.findByAnswerContent(id));
        return "question.html";
    }

    /**
     * 質問フォーム画面を表示
     * 
     * @param questionForm 質問フォーム
     * @return テンプレート名
     */
    @RequestMapping("/form")
    public String form(QuestionForm questionForm) {
        return "form.html";
    }

    /**
     * 質問を登録
     * 
     * @param questionForm  質問フォームデータ
     * @param bindingResult バリデーション結果
     * @param model         モデル
     * @param userDetails   ログインユーザー情報
     * @return リダイレクト先
     */
    @PostMapping("/insertQuestion")
    public String insert(@Validated QuestionForm questionForm, BindingResult bindingResult,
            Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            return "form.html";
        }

        if (userDetails != null) {
            User currentUser = userService.getCurrentUser();
            if (currentUser != null) {
                questionForm.setUserId(currentUser.getId());
            }
        }

        questionService.insertQuestion(questionForm);
        return "redirect:/home";
    }

    /**
     * 回答を登録
     * 
     * @param answerForm    回答フォーム
     * @param bindingResult バリデーション結果
     * @param model         モデル
     * @param userDetails   ログインユーザー情報
     * @return リダイレクト先
     */
    @PostMapping("/question/insertAnswer")
    public String insertAnswer(@Validated AnswerForm answerForm, BindingResult bindingResult,
            Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (!bindingResult.hasErrors()) {
            if (userDetails != null) {
                User currentUser = userService.getCurrentUser();
                if (currentUser != null) {
                    answerForm.setUserId(currentUser.getId());
                    answerForm.setResponder(currentUser.getUsername());
                }
            }
            answerService.insertAnswer(answerForm);
        }
        String redirectPath = String.format("redirect:/question/%s", answerForm.getQuestionId());
        return redirectPath;
    }
    
    /**
     * 回答の共感数をインクリメント
     * 
     * @param answerId 回答ID
     * @return 成功メッセージ
     */
    @PostMapping("/answer/empathy/{id}")
    @ResponseBody
    public String incrementEmpathy(@PathVariable("id") int answerId) {
        answerService.incrementEmpathyCount(answerId);
        return "success";
    }
    
    /**
     * 質問の共感数をインクリメント
     * 
     * @param questionId 質問ID
     * @return 成功メッセージ
     */
    @PostMapping("/question/empathy/{id}")
    @ResponseBody
    public String incrementQuestionEmpathy(@PathVariable("id") int questionId) {
        questionService.incrementEmpathyCount(questionId);
        return "success";
    }
}
