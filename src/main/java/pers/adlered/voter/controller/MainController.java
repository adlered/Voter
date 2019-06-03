package pers.adlered.voter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pers.adlered.voter.tool.GetDate;

@Controller
public class MainController {
    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("YEAR", GetDate.year());
        return modelAndView;
    }

    @RequestMapping("/public/en")
    public ModelAndView pubEn() {
        ModelAndView modelAndView = new ModelAndView("/public/public_en");
        modelAndView.addObject("YEAR", GetDate.year());
        return modelAndView;
    }

    @RequestMapping("/public/cn")
    public ModelAndView pubCn() {
        ModelAndView modelAndView = new ModelAndView("/public/public_cn");
        modelAndView.addObject("YEAR", GetDate.year());
        return modelAndView;
    }
}
