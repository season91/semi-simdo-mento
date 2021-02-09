package com.kh.simdo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // /index로 하면 안들어가짐.!!
    @GetMapping(value = "/")
    public String index(Model model){
        model.addAttribute("username","SIMDO");
        return "index";
    }

}

