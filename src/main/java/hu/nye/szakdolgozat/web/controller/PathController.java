package hu.nye.szakdolgozat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PathController {
    @GetMapping()
    public String home() {
        return "index";
    }

    @GetMapping("/playGame")
    public String play() {
        return "playGame";
    }
}