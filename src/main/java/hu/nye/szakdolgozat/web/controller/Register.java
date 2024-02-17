package hu.nye.szakdolgozat.web.controller;

import hu.nye.szakdolgozat.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/aaaaaaaaaaaaaaaaaaaa")
public class Register {
    private final Session session;
    @Autowired
    public Register(Session session) {
        this.session = session;
    }


    @GetMapping
    public String getRegisterPage(Model model) {
        model.addAttribute("user", session.getUser());
        return "register";
    }

    @PostMapping
    public String register(Model model, User user) {
        User newUser = session.userService.save(user);
        model.addAttribute("user", user);
        return "register";
    }
}
