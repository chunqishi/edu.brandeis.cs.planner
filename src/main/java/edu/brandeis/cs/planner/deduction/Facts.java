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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310201833 on 2016/5/6.
 */
public class Facts {

    public static enum Categories {
        text, splitter, tokenizer, tagger, parser, dependencyParser, chunking, ner, coreference
    }

    public List<String> facts = new ArrayList<String>();

    public boolean isCategory(String name, Categories cat) {
        return false;
    }

    protected String genFact(ServiceEntity entity, ServiceInfo info, String wsdl, String metajson) {
        StringBuilder fact = new StringBuilder();
        String id = entity.getServiceid();
        JsonReader reader = new JsonReader(metajson);
        String classname = reader.read("$payload.name");
        String description = reader.read("$payload.description");
        String text = id + classname + description;
        text = text.toLowerCase();
        for (Categories cat : Categories.values()) {
            String catString = cat.name();
            if (text.contains(catString)) {
                fact.append(catString);
                break;
            }
        }
        fact.append("(").append(id.toLowerCase()).append(")");
        return fact.toString();
    }

    public Facts() throws WsdlClient.WSDLClientException, MalformedURLException, RemoteException {
        ServiceManagerDB sm = new ServiceManagerDB();
        List<ServiceEntity> entities = sm.listServices();
        WsdlInfo info = new WsdlInfo(entities);
        List<List<ServiceInfo>> services = info.getGrid_services();
        List<String> wsdls = info.getWsdls();
        Metadata meta = new Metadata(wsdls);
        List<String> metajsons = meta.getMetadataJsons();
        for (int i = 0; i < entities.size(); i++) {
            String fact = genFact(entities.get(i), services.get(0).get(i), wsdls.get(i), metajsons.get(i));
            facts.add(fact);
        }
    }
}
