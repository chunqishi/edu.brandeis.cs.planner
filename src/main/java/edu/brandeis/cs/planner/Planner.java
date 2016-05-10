package edu.brandeis.cs.planner;


import edu.brandeis.cs.planner.db.ServiceEntity;
import edu.brandeis.cs.planner.db.ServiceManagerDB;
import edu.brandeis.cs.planner.deduction.Facts;
import edu.brandeis.cs.planner.deduction.JIPrologEngine;
import edu.brandeis.cs.planner.deduction.Rules;
import edu.brandeis.cs.planner.utils.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class Planner implements IPlanner {
    final static Logger logger = LoggerFactory.getLogger(Planner.class);

    public static void main(String[] args) {
        System.out.print("Planner");
    }

    static List<String> factList = null;
    static List<String> ruleList;
    static Facts facts;

    static {
        try {
            ServiceManagerDB sm = new ServiceManagerDB();
            List<ServiceEntity> entities = sm.listServices();
            facts = new Facts(entities);
            factList = facts.getFacts();
            ruleList = new Rules().getRules();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Planner() {
    }

    @Override
    public String execute(String input) {
        return null;
    }

    @Override
    public String getMetadata() {
        return null;
    }

    @Override
    public String pipeline(String start, String end) {
        String goal = prepareGoal(start, end);
        List<Map<String, String>> res = JIPrologEngine.queryFactsWithGoal(factList, ruleList, goal, 0);
        String[] solutions = new String[res.size()];
        for (int i = 0; i < res.size(); i++) {
            Map<String, String> map = res.get(i);
            solutions[i] = map.get("Pipeline");
        }
        return solutions[0];
    }

    protected String prepareGoal(String start, String end) {
        start = start.trim();
        end = end.trim();
        StringBuilder sb = new StringBuilder();
        String from = null;
        String to = null;
        if (start.equals("text")) {
            from = "text";
        } else if (facts.isCategory(start)) {
            sb.append(start.toLowerCase()).append("(From),");
            from = "From";
        } else if (facts.isServiceId(start)) {
            from = "'" + start + "'";
        }
        if (facts.isCategory(end)) {
            sb.append(end.toLowerCase()).append("(To),");
            to = "To";
        } else if (facts.isServiceId(end)) {
            to = "'" + end + "'";
        }
        sb.append("workflow(").append(from).append(",").append(to).append(", Pipeline).");
        logger.debug("Goal: {}", sb);
        return sb.toString();
    }

    @Override
    public String[] pipelines(String start, String end, int topN) {
        String goal = prepareGoal(start, end);
        List<Map<String, String>> res = JIPrologEngine.queryFactsWithGoal(factList, ruleList, goal, topN);
        String[] solutions = new String[res.size()];
        for (int i = 0; i < res.size(); i++) {
            Map<String, String> map = res.get(i);
            solutions[i] = map.get("Pipeline");
        }
        return solutions;
    }

    @Override
    public String[] listServices() {
        Map<String, Object[]> byids = facts.getByIds();
        return byids.keySet().toArray(new String[byids.size()]);
    }

    @Override
    public String[][] listMetadata() {
        Map<String, Object[]> byids = facts.getByIds();
        String[][] metadata = new String[byids.size()][];
        int i = 0;
        for (String id : byids.keySet()) {
            metadata[i] = new String[]{id, byids.get(id)[3].toString()};
            i++;
        }
        return metadata;
    }

    @Override
    public String[][] listMetadataAsFlat() {
        Map<String, Object[]> byids = facts.getByIds();
        String[][] metadata = new String[byids.size()][];
        int i = 0;
        for (String id : byids.keySet()) {
            String json = byids.get(id)[3].toString();
            JsonReader reader = new JsonReader(json);
            metadata[i] = new String[]{id,
                    reader.read("payload.produces.annotations"),
                    reader.read("payload.produces.format"),
                    reader.read("payload.requires.annotations"),
                    reader.read("payload.requires.format")};
            i++;
        }
        return metadata;
    }

//    @Override
//    public String[][] listMetadataAsFlat() {
//        Map<String, Object[]> byids = facts.getByIds();
//        String[][] metadata = new String[byids.size()][];
//        int i = 0;
//        for (String id : byids.keySet()) {
//            String json = byids.get(id)[3].toString();
//            List<String> kv = JsonReader.flatJson(json);
//            metadata[i] = new String[kv.size() + 1];
//            metadata[i][0] = id;
//            for (int j = 0; j < kv.size(); j++) {
//                metadata[i][j + 1] = kv.get(j);
//            }
//            i++;
//        }
//        return metadata;
//    }
}

