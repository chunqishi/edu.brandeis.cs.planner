package edu.brandeis.cs.planner;


import edu.brandeis.cs.planner.db.ServiceEntity;
import edu.brandeis.cs.planner.db.ServiceManagerDB;
import edu.brandeis.cs.planner.deduction.Facts;
import edu.brandeis.cs.planner.deduction.JIPrologEngine;
import edu.brandeis.cs.planner.deduction.Rules;
import edu.brandeis.cs.planner.utils.WsdlClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class Planner implements IPlanner {
    final static Logger logger = LoggerFactory.getLogger(Planner.class);

    public static void main(String[] args) {
        System.out.print("Planner");
    }

    List<String> factList = null;
    List<String> ruleList;
    Facts facts;

    public Planner() {
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
        String[] res = pipelines(start, end);
        if (res.length > 0)
            return res[0];
        return null;
    }

    @Override
    public String[] pipelines(String start, String end) {
        start = start.trim();
        end = end.trim();
        StringBuilder sb = new StringBuilder();
        String from = null;
        String to = null;
        if (facts.isCategory(start)) {
            sb.append(start.toLowerCase()).append("(From),");
            from = "From";
        } else if (facts.isServiceId(start)) {
            from = start;
        }
        if (facts.isCategory(end)) {
            sb.append(start.toLowerCase()).append("(To),");
            to = "To";
        } else if (facts.isServiceId(end)) {
            to = end;
        }
        sb.append("workflow(").append(from).append(",").append(to).append(", Pipeline).");
        logger.debug("Goal: {}", sb);
        List<Map<String, String>> res = JIPrologEngine.queryFactsWithGoal(factList, ruleList, sb.toString());
        String[] solutions = new String[res.size()];
        for (int i = 0; i < res.size(); i++) {
            Map<String, String> map = res.get(i);
            solutions[i] = map.get("Pipeline");
        }
        return solutions;
    }
}

