package com.freshdesk.freshjuice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping("/")
    public String health() {
        return "You are getting the reponse from the Git pipline!";
    }

    @GetMapping("/payment")
    public String paymentCheckOut() {
        return "Payment checkout Done!";
    }

    @GetMapping("/blog")
    public String blog() {
        return "Blog viewer..!";
    }


}
