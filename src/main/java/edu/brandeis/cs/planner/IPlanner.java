package edu.brandeis.cs.planner;

import org.lappsgrid.api.WebService;

public interface IPlanner extends WebService {
    String pipeline(String start, String end);

    String[] pipelines(String start, String end, int topN);

    String [] listServices();

    String[][] listMetadata();
}
