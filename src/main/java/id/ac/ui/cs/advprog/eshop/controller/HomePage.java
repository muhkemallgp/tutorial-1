package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomePage {
    @GetMapping("/")
    public String createProductPage(Model model){
        return "homepage";
    }

}
