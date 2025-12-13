package com.shima.chiebukuro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shima.chiebukuro.service.UserService;

/**
 * 認証関連のコントローラー
 */
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * ログイン画面を表示
     * @param error エラーパラメータ
     * @param logout ログアウトパラメータ
     * @param model モデル
     * @return テンプレート名
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        if (error != null) {
            model.addAttribute("error", "ユーザー名またはパスワードが正しくありません");
        }
        if (logout != null) {
            model.addAttribute("message", "ログアウトしました");
        }
        return "login";
    }

    /**
     * サインアップ画面を表示
     * @return テンプレート名
     */
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    /**
     * ユーザー登録処理
     * @param username ユーザー名
     * @param password パスワード
     * @param passwordConfirm パスワード確認
     * @param model モデル
     * @return リダイレクト先またはテンプレート名
     */
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("passwordConfirm") String passwordConfirm,
                          Model model) {
        
        if (username.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "ユーザー名とパスワードを入力してください");
            return "signup";
        }

        if (!password.equals(passwordConfirm)) {
            model.addAttribute("error", "パスワードが一致しません");
            return "signup";
        }

        if (userService.existsByUsername(username)) {
            model.addAttribute("error", "このユーザー名は既に使用されています");
            return "signup";
        }

        try {
            userService.registerUser(username, password);
            model.addAttribute("message", "登録が完了しました。ログインしてください。");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "登録に失敗しました");
            return "signup";
        }
    }
}
