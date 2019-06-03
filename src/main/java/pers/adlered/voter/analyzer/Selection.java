package pers.adlered.voter.analyzer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Selection {
    public static List<Map<String, String>> analyze(String words) {
        List<Map<String, String>> selectionList = new ArrayList<Map<String, String>>();
        words = words.replaceAll("\n", "");
        String[] mark = words.split("<%");

        //If i start with 0, the space will be include because of split() method.
        float countVotes = 0;
        for (int i = 1; i < mark.length; i++) {
            Map<String, String> selectMap = new HashMap<String, String>();
            selectMap.put("num", mark[i]);
            ++i;
            selectMap.put("count", mark[i]);
            countVotes = countVotes + Integer.parseInt(mark[i]);
            ++i;
            selectMap.put("selectionText", mark[i]);
            selectionList.add(selectMap);
        }

        //Write
        for (Map<String, String> map : selectionList) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            //To avoid 0/0=NaN
            if (countVotes == 0) {
                countVotes = 100;
            }
            float percent = Integer.parseInt(map.get("count")) / countVotes;
            percent = Float.parseFloat(decimalFormat.format(percent));
            //Too low or too high will wrong when browser display.
            if (percent > 0.85F) {
                percent = 0.85F;
            }
            if (percent < 0.05F) {
                percent = 0.05F;
            }
            String percentStr = String.valueOf(percent);
            percentStr = percentStr.split("\\.")[1];
            //Bug fix: e.g. 0.20 will be replaced as 2 not 20.
            if (percentStr.length() != 2) {
                int add = 2 - percentStr.length();
                for (int i = 0; i < add; ++i) {
                    percentStr += "0";
                }
            }
            map.put("percent", percentStr);
        }
        return selectionList;
    }
}
