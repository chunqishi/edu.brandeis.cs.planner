package edu.brandeis.cs.planner.deduction;

import edu.brandeis.cs.planner.db.ServiceEntity;
import edu.brandeis.cs.planner.db.ServiceManagerDB;
import edu.brandeis.cs.planner.service.Metadata;
import edu.brandeis.cs.planner.service.ServiceInfo;
import edu.brandeis.cs.planner.service.WsdlInfo;
import edu.brandeis.cs.planner.utils.JsonReader;
import edu.brandeis.cs.planner.utils.WsdlClient;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by 310201833 on 2016/5/6.
 */
public class Facts {

    public static enum Categories {
        splitter, tokenizer, tagger, parser, dependencyParser, chunking, ner, coreference
    }

    public static List<String> CategoryNames = new ArrayList<>();


    public boolean isServiceId(String text) {
        text = text.trim();
        return facts.contains(text);
    }

    public boolean isCategory(String text) {
        text = text.trim().toLowerCase();
        return CategoryNames.contains(text);
    }

//
//    public String getCategory(String text) {
//        text = text.trim().toLowerCase();
//        if (facts.contains(text)) {
//            // service id;
//
//        } else if (CategoryNames.contains(text)) {
//            return text;
//        }
//    }

    public List<String> getFacts() {
        return facts;
    }

    public Map<String, Object[]> getByIds() {
        return byIds;
    }

    Map<String, Object[]> byIds = new HashMap<>();

    private List<String> facts = new ArrayList<String>();

    protected String genFact(ServiceEntity entity, ServiceInfo info, String wsdl, String metajson) {
        StringBuilder fact = new StringBuilder();
        String id = entity.getServiceid();
        JsonReader reader = new JsonReader(metajson);
        String classname = reader.read("payload.name");
        String description = reader.read("payload.description");
        String text = id + classname + description;
        text = text.toLowerCase();
        for (Categories cat : Categories.values()) {
            String catString = cat.name();
            if (text.contains(catString)) {
                fact.append(catString);
                break;
            }
        }
        // special case.
        if (text.contains("namedentity")) {
            fact.append("ner");
        }

        fact.append("('").append(id.toLowerCase()).append("').");
        return fact.toString();
    }

//    public String filter(String id) {
//
//    }
//    private Map<String,String> filterMap = new HashMap<>();
//    private Map<String,String> unfilterMap = new HashMap<>();

    public Facts(List<ServiceEntity> entities) throws WsdlClient.WSDLClientException, MalformedURLException, RemoteException {
        for (Categories cat : Categories.values()) {
            CategoryNames.add(cat.name());
        }
        WsdlInfo info = new WsdlInfo(entities);
        List<List<ServiceInfo>> services = info.getGrid_services();
        List<String> wsdls = info.getWsdls();
        Metadata meta = new Metadata(wsdls);
        List<String> metajsons = meta.getMetadataJsons();
        for (int i = 0; i < entities.size(); i++) {
            byIds.put(entities.get(i).getServiceid(), new Object[]{
                    entities.get(i),
                    services.get(0).get(i),
                    wsdls.get(i),
                    metajsons.get(i)
            });
            String fact = genFact(entities.get(i), services.get(0).get(i), wsdls.get(i), metajsons.get(i));
            facts.add(fact);
        }
    }
}
