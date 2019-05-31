package pers.adlered.voter.controller;

import com.google.common.util.concurrent.RateLimiter;
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
import pers.adlered.voter.limit.IpUtil;
import pers.adlered.voter.limit.LoadingCacheServiceImpl;
import pers.adlered.voter.mapper.VoteMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
public class VoteController {
    @Autowired
    VoteMapper voteMapper;
    @Resource
    LoadingCacheServiceImpl loadingCacheService;

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

    @RequestMapping("/voterSubmit")
    @ResponseBody
    public String voterSubmit(String title, String describe, String selection, String type, String limit) {
        return "";
    }

    @RequestMapping("/submitVote")
    @ResponseBody
    public String submitVote(HttpServletRequest request, Integer VID, String selected) {
        String ipAddr = IpUtil.getIpAddr(request);
        try {
            RateLimiter limiter = loadingCacheService.getRateLimiter(ipAddr);
            boolean localAccess = false;
            //Localhost is in the WhiteList
            if (ipAddr.equals("0:0:0:0:0:0:0:1")) {
                localAccess = true;
            }
            if(limiter.tryAcquire() || localAccess){
                //PASS
                //Get VID info
                Vote vote = voteMapper.getVote(VID);
                String selectionSerial = vote.getSelection();
                //Package and readout
                List<Map<String, String>> selects = Selection.analyze(selectionSerial);
                //Split selection
                String selectedList[] = selected.split(",");
                //Convert to Integer
                Integer selList[] = new Integer[selectedList.length];
                Integer i = 0;
                for (String sel : selectedList) {
                    selList[i] = Integer.parseInt(sel);
                    ++i;
                }
                //Write changes
                //Generate new List
                List<Map<String, String>> newList = new ArrayList<Map<String, String>>();
                //Change one by one
                for (int voteFor = 0; voteFor < selects.size(); ++voteFor) {
                    Map<String, String> stringMap = selects.get(voteFor);
                    boolean flag = false;
                    //Submit
                    for (Integer sel : selList) {
                        if (Integer.parseInt(stringMap.get("num")) == sel) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        //Change
                        Integer before = Integer.parseInt(stringMap.get("count"));
                        Integer after = before + 1;
                        stringMap.put("count", String.valueOf(after));
                    }
                    newList.add(stringMap);
                }
                //To serial
                String serial = Serialize.makeSerial(newList);
                //Update to database
                voteMapper.vote(serial, VID);
                return "1";
            }else {
                //DENIED
                return "0";
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "0";
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
