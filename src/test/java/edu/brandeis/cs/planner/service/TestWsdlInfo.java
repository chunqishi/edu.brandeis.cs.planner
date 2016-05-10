package edu.brandeis.cs.planner.service;


import edu.brandeis.cs.planner.db.ServiceEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestWsdlInfo {

    @Test
    public void test(){
        System.out.println("TEST...");
        List<ServiceEntity> entities = new ArrayList<ServiceEntity>();
        WsdlInfo wi = new WsdlInfo(entities);
    }
}