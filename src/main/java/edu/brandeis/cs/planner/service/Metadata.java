package edu.brandeis.cs.planner.service;

import edu.brandeis.cs.planner.utils.ConfigXML;
import edu.brandeis.cs.planner.utils.WsdlClient;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310201833 on 2016/5/6.
 */
public class Metadata {
    final static Logger logger = LoggerFactory.getLogger(Metadata.class);
    private WsdlClient client = new WsdlClient();

    List<String> wsdls = new ArrayList<String>();
    List<String> metadataJsons = new ArrayList<String>();

    public Metadata(List<String> wsdls) {
        this.wsdls.addAll(wsdls);
        init();
    }

    public Metadata() {

    }

    protected void init() {
        boolean use_proxy = ConfigXML.config().getBoolean("connection/proxies/use_proxy");
        if (use_proxy) {
            client.setProxy(ConfigXML.config().getString("connection/proxies/http_proxy"));
        }
        String service_manager = ConfigXML.config().getString("grids/grid/service_manager");
        try {
            URL url = new URL(service_manager);
            String host = url.getHost();
            int port = url.getPort();
            String username = ConfigXML.config().getString(
                    "connection/credentials/credential[@host='" + host + "' and @port='" + port + "']/username");
            String password = ConfigXML.config().getString(
                    "connection/credentials/credential[@host='" + host + "' and @port='" + port + "']/password");
            client.authorize(username, password);
        } catch (MalformedURLException e) {
            logger.warn("Wrong Service Manager", e);
            e.printStackTrace();
        }
        for (String wsdlString: wsdls) {
            String metadataString = callMetadata(wsdlString);
            metadataJsons.add(metadataString);
        }
    }

    protected String callMetadata(String wsldString) {
//        System.out.println("WSDL = " + wsldString);
        logger.debug("WSDL = {}", wsldString);
        try {
            client.init(wsldString);
            Object ret = client.callService("", "getMetadata");
            return ret.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("GetMetadata ERROR: ", e);
        }
        return null;
    }
}
