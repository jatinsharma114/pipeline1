package com.freshdesk.freshjuice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping("/home")
    public String home() {
        return "This application is running fine!! version 30-5-2025";
    }


}
