package edu.brandeis.cs.planner.deduction;


import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Rules {

    private String scriptPath = "/planner.pl";


    private List<String> rules = new ArrayList<>();

    public List<String> getRules() {
        return rules;
    }

    public Rules() throws IOException {
        rules.add(getFileAsString(scriptPath));
    }

    public Rules(String scriptPath) throws IOException {
        rules.add(getFileAsString(scriptPath));
    }

    public static String getFileAsString(String path) throws IOException {
        return IOUtils.toString(new FileInputStream(new File(Rules.class.getResource(path).getFile())));
    }
}
