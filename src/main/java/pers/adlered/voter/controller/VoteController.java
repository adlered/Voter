package pers.adlered.voter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pers.adlered.voter.dao.Vote;
import pers.adlered.voter.mapper.VoteMapper;


@Controller
public class VoteController {
    @Autowired
    VoteMapper voteMapper;

    @RequestMapping("/vote/{VID}")
    public ModelAndView showVote(@PathVariable Integer VID) {
        Vote vote = voteMapper.getVote(VID);
        ModelAndView modelAndView = new ModelAndView("/vote/index_en");
        modelAndView.addObject("VoteID", vote.getVID());
        modelAndView.addObject("Title", vote.getTitle());
        modelAndView.addObject("Describe", vote.getDescribe());
        modelAndView.addObject("Selection", vote.getSelection());
        return modelAndView;
    }
}
