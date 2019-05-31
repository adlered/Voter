package pers.adlered.voter.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pers.adlered.voter.analyzer.Selection;
import pers.adlered.voter.analyzer.Serialize;
import pers.adlered.voter.dao.Select;
import pers.adlered.voter.dao.Vote;
import pers.adlered.voter.mapper.VoteMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VoteController {
    @Autowired
    VoteMapper voteMapper;

    @RequestMapping("/vote/{VID}")
    public ModelAndView showVote(@PathVariable Integer VID) {
        Vote vote = voteMapper.getVote(VID);
        ModelAndView modelAndView = new ModelAndView("/vote/index_en");
        try {
            modelAndView.addObject("VoteID", vote.getVID());
            modelAndView.addObject("Title", vote.getTitle());
            modelAndView.addObject("Describe", vote.getDescribe());
            modelAndView.addObject("Type", vote.getType());
            modelAndView.addObject("Limit", vote.getLimit());
        } catch (NullPointerException NPE) {

        }
        //Selection process
        List<Map<String, String>> selects = Selection.analyze(vote.getSelection());
        modelAndView.addObject("Selection", selects);
        return modelAndView;
    }

    @RequestMapping("/checkVoteID/{VID}")
    @ResponseBody
    public Integer checkVoteID(@PathVariable Integer VID) {
        try {
            voteMapper.checkVote(VID).getVID();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @RequestMapping("/serialize")
    @ResponseBody
    public String serialize(Integer VID) {
        Vote vote = voteMapper.getVote(VID);
        List<Map<String, String>> selects = Selection.analyze(vote.getSelection());
        System.out.println(Serialize.makeSerial(selects));
        return Serialize.makeSerial(selects);
    }
}
