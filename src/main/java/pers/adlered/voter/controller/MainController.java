package pers.adlered.voter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MainController {
    @RequestMapping("/")
    public ModelAndView index() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("YEAR", simpleDateFormat.format(date));
        return modelAndView;
    }

    @RequestMapping("/public/en")
    public ModelAndView pubEn() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView("/public/public_en");
        modelAndView.addObject("YEAR", simpleDateFormat.format(date));
        return modelAndView;
    }

    @RequestMapping("/public/cn")
    public ModelAndView pubCn() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView("/public/public_cn");
        modelAndView.addObject("YEAR", simpleDateFormat.format(date));
        return modelAndView;
    }
}
