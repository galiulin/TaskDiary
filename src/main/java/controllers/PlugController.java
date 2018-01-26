package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PlugController {

    @RequestMapping(value = "/plug", method = RequestMethod.GET)
    public String mainPage() {
        return "plug";
    }
}
