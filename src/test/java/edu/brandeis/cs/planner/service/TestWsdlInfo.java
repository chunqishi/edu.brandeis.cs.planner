package edu.brandeis.cs.planner.service;


import edu.brandeis.cs.planner.db.ServiceEntity;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestWsdlInfo {

    @Test
    public void test() {
        System.out.println("TEST..." + this.getClass());
        List<ServiceEntity> entities = new ArrayList<ServiceEntity>();
        ServiceEntity en = new ServiceEntity();
        en.setServiceid("uima.dkpro.opennlp.parser_0.0.1");
        en.setGridid("brandeis_eldrad_grid_1");
        entities.add(en);
        WsdlInfo wi = new WsdlInfo(entities);
        Assert.assertTrue(wi.getGrid_services().size() > 0);
        System.out.println(wi.getWsdls());
    }
}