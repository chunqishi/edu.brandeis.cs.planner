package edu.brandeis.cs.planner.utils;


import edu.brandeis.cs.planner.service.ServiceInfo;
import junit.framework.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestConfigXML {

    @Test
    public void test(){
        String servicemanager = ConfigXML.config().getString("grids/grid/service_manager");
        Assert.assertEquals("http://eldrad.cs-i.brandeis.edu:8080/service_manager/", servicemanager);
    }
}