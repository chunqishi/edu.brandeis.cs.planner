package edu.brandeis.cs.planner.utils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

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
}
