package edu.brandeis.cs.planner.utils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310201833 on 2016/5/6.
 * https://github.com/jayway/JsonPath
 */
public class JsonReader {

    Configuration conf;
    DocumentContext context;

    public JsonReader(String jsonContent) {
        conf = Configuration.defaultConfiguration();
        context = JsonPath.using(conf).parse(jsonContent);
    }

    public String read(String jsonpath) {
        return context.read(jsonpath);
    }


    public static List<String> flatJson(String jsonObj) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject obj = (JSONObject) new JSONParser(JSONParser.MODE_JSON_SIMPLE).parse(jsonObj);
            flatJson(obj, null, list);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void flatJson(JSONObject jsonObj, String key, List<String> list) {
        for (Object keyObj : jsonObj.keySet()) {
            String keyStr = (String) keyObj;
            Object keyvalue = jsonObj.get(keyStr);
            StringBuilder sb = new StringBuilder();
            String subkey;
            if (key != null && key.length() > 1) {
                subkey = key + "." + keyStr;
            } else {
                subkey = keyStr;
            }
            sb.append(subkey);
            if (keyvalue instanceof JSONObject)
                flatJson((JSONObject) keyvalue, subkey, list);
            else {
                sb.append("=").append(keyvalue);
            }
            list.add(sb.toString());
        }
    }
}
