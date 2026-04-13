package com.deploy6.deploy6.controller;


import com.deploy6.deploy6.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

        import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    // LOGIN
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        // username = admin, password = NIM kamu (ganti sesuai NIM)
        if (username.equals("admin") && password.equals("12345678")) {
            session.setAttribute("loggedIn", true);
            if (session.getAttribute("userList") == null) {
                session.setAttribute("userList", new ArrayList<User>());
            }
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Username atau password salah!");
            return "login";
        }
    }

    // HOME
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/";
        }
        List<User> userList = (List<User>) session.getAttribute("userList");
        model.addAttribute("userList", userList);
        return "home";
    }

    // FORM
    @GetMapping("/form")
    public String formPage(HttpSession session) {
        if (session.getAttribute("loggedIn") == null) {
            return "redirect:/";
        }
        return "form";
    }

    @PostMapping("/form")
    public String submitForm(@RequestParam String nama,
                             @RequestParam String nim,
                             @RequestParam String jenisKelamin,
                             HttpSession session) {
        List<User> userList = (List<User>) session.getAttribute("userList");
        if (userList == null) {
            userList = new ArrayList<>();
        }
        userList.add(new User(nama, nim, jenisKelamin));
        session.setAttribute("userList", userList);
        return "redirect:/home";
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}