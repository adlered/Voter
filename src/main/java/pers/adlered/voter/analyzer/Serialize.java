package pers.adlered.voter.analyzer;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * Reverse method of "pers.adlered.voter.analyzer.Selection.analyze()";
 * Make List<Map<String, String>> to serial string.
 */

@Controller
public class Serialize {
    public static String makeSerial(List<Map<String, String>> mapList) {
        String serial = "";
        for (Map<String, String> map : mapList) {
            serial += "<%" + map.get("num") + "<%" + map.get("count") + "<%" + map.get("selectionText");
        }
        return serial;
    }
}
