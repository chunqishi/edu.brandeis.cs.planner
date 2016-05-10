package edu.brandeis.cs.planner.service;


import edu.brandeis.cs.planner.db.ServiceEntity;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestMetadata {

    @Test
    public void test() {
        System.out.println("TEST..." + this.getClass());
        String wsdl = "http://eldrad.cs-i.brandeis.edu:8080/service_manager/wsdl/brandeis_eldrad_grid_1:uima.dkpro.opennlp.parser_0.0.1";
        List<String> wsdls = new ArrayList<>();
        wsdls.add(wsdl);
        Metadata mt = new Metadata(wsdls);
        
    }
}