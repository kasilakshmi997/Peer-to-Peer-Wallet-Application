

package com.example.demo;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Webcontroller {

    @GetMapping("/")
    public String index() {
        // This looks for src/main/resources/static/index.html
        return "index2.html";
    }
}
