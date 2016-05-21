package edu.brandeis.cs.planner;


import org.apache.commons.io.IOUtils;
import edu.brandeis.cs.planner.db.ServiceEntity;
import edu.brandeis.cs.planner.db.ServiceManagerDB;
import edu.brandeis.cs.planner.deduction.Facts;
import edu.brandeis.cs.planner.deduction.JIPrologEngine;
import edu.brandeis.cs.planner.deduction.Rules;
import edu.brandeis.cs.planner.utils.JsonReader;
import org.lappsgrid.discriminator.Discriminators;
import org.lappsgrid.serialization.Data;
import org.lappsgrid.serialization.Serializer;
import org.lappsgrid.serialization.json.JsonArr;
import org.lappsgrid.serialization.json.JsonObj;
import org.lappsgrid.serialization.json.WSJsonBuilder;
import org.lappsgrid.serialization.lif.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
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
    public String execute(String s) {
        WSJsonBuilder json = null;
        try {
            s = s.trim();
            if (s.startsWith("{") && s.endsWith("}")) {
                json = new WSJsonBuilder(s);
                if (Discriminators.Uri.ERROR.equals(json.getDiscriminator())) {
                    return json.toString();
                }
            } else {
                json = new WSJsonBuilder();
                json.setError("Only JSON imput is allowed!", "Unkown input: " + s);
                json.toString();
                System.err.println(json.toString());
                logger.error(json.toString());
                return s;
            }
            return execute(json);
        } catch (Throwable th) {
            StringWriter sw = new StringWriter();
            th.printStackTrace(new PrintWriter(sw));
            json.setError(th.toString(), sw.toString());
            System.err.println(sw.toString());
            logger.error(sw.toString());
            return s;
        }
    }

    public String execute(WSJsonBuilder json) throws Exception {
        String [] pls = this.pipelines(json.getInput(), json.getOutput(), json.getN());
//        String[] pls = new String[]{"[text,opennlp.splitter_2.0.1,opennlp.tokenizer_2.0.1]"};
        for (String pl : pls) {
            JsonArr components = json.newPipeline();
            String[] ids = pl.substring(1, pl.length() - 1).split(",");
//            JsonArr ids = new JsonArr(pl);
            for (int i = 0; i < ids.length; i++) {
                json.addComponent(components, ids[i]);
            }
        }
        return json.toString();
    }

    @Override
    public String getMetadata() {
        {
            // get caller name using reflection
            String name = this.getClass().getName();
            //
            String resName = "/metadata/" + name + ".json";
            System.out.println("load resources:" + resName);
            try {
                String meta = IOUtils.toString(this.getClass().getResourceAsStream(resName));
                JsonObj json = new JsonObj();
                json.put("discriminator", Discriminators.Uri.META);
                json.put("payload", new JsonObj(meta));
                System.out.println("---------------------META:-------------------\n" + json.toString());
                return json.toString();
            } catch (Throwable th) {
                JsonObj json = new JsonObj();
                json.put("discriminator", Discriminators.Uri.ERROR);
                JsonObj error = new JsonObj();
                error.put("class", name);
                error.put("error", "NOT EXIST: " + resName);
                error.put("message", th.getMessage());
                StringWriter sw = new StringWriter();
                th.printStackTrace(new PrintWriter(sw));
                System.err.println(sw.toString());
                error.put("stacktrace", sw.toString());
                json.put("payload", error);
                return json.toString();
            }
        }
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
    public String[][] listMetadataAsFlat(int hint) {
        Map<String, Object[]> byids = facts.getByIds();
        String[][] metadata = new String[byids.size()][];
        int i = 0;
        for (String id : byids.keySet()) {
            String json = byids.get(id)[3].toString();
            if (hint == 1) {
                JsonReader reader = new JsonReader(json);
                metadata[i] = new String[]{id,
                        reader.read("payload.produces.annotations"),
                        reader.read("payload.produces.format"),
                        reader.read("payload.requires.annotations"),
                        reader.read("payload.requires.format")};
            } else {
                List<String> kv = JsonReader.flatJson(json);
                metadata[i] = new String[kv.size() + 1];
                metadata[i][0] = id;
                for (int j = 0; j < kv.size(); j++) {
                    metadata[i][j + 1] = kv.get(j);
                }
            }
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

