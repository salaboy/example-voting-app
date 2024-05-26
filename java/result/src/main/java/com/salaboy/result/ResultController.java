package com.salaboy.result;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultController {

    @GetMapping("/")
    String renderHTML() {
        return "index.html";
    }

}
