package edu.brandeis.cs.planner.utils;


import edu.brandeis.cs.planner.service.ServiceInfo;
import org.junit.Test;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestWsdlClient {

    @Test
    public void test() throws MalformedURLException, RemoteException {
        System.out.println("TEST..." + this.getClass());
        WsdlClient client = new WsdlClient();
        client.setProxy(ConfigXML.config().getString("connection/proxies/http_proxy"));
        ServiceInfo si = new ServiceInfo();
        si.setService_manager("http://eldrad.cs-i.brandeis.edu:8080/service_manager/");
        si.setGrid_id("brandeis_eldrad_grid_1");
        si.setService_id("uima.dkpro.opennlp.parser_0.0.1");
        si.init();
        String wsldString = si.getWsdl();
        System.out.println("WSDL=" + wsldString);
        try {
            client.init(new StringReader(si.getWsdlContent()));
            client.authorize("eldrad", "eldrad1234");
            Object ret = client.callService("", "execute", "How are you today.");
            System.out.println(ret.toString());
        } catch (WsdlClient.WSDLClientException e) {
            e.printStackTrace();
        }
    }
}