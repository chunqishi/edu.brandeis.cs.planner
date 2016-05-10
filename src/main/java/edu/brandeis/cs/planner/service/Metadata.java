package edu.brandeis.cs.planner.service;

import edu.brandeis.cs.planner.utils.ConfigXML;
import edu.brandeis.cs.planner.utils.JsonReader;
import edu.brandeis.cs.planner.utils.WsdlClient;
import jp.go.nict.langrid.repackaged.net.arnx.jsonic.JSON;
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
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * {
 "discriminator" : "http://vocab.lappsgrid.org/ns/meta",
 "payload" : {
 "$schema" : "http://vocab.lappsgrid.org/schema/service-schema-1.0.0.json",
 "name" : "edu.brandeis.cs.lappsgrid.stanford.corenlp.Parser",
 "version" : "2.0.0",
 "description" : " DkPro OpenNLP Parser",
 "vendor" : "http://www.cs.brandeis.edu/",
 "allow" : "http://vocab.lappsgrid.org/ns/allow#any",
 "license" : "http://vocab.lappsgrid.org/ns/license#apache-2.0",
 "requires" : {
 "language" : [ "en" ],
 "format" : [ "http://vocab.lappsgrid.org/ns/media/jsonld#lif" ],
 "annotations" : [ "http://vocab.lappsgrid.org/PhraseStructure" ]
 },
 "produces" : {
 "language" : [ "en" ],
 "format" : [ "http://vocab.lappsgrid.org/ns/media/jsonld#lif" ],
 "annotations" : [ "http://vocab.lappsgrid.org/PhraseStructure" ]
 }
 }
 }
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
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

    public List<String> getMetadataJsons() {
        return metadataJsons;
    }

    public List<JsonReader> toJsonReaders() {
        List<JsonReader> readers = new ArrayList<JsonReader>();
        for (String jsonString: metadataJsons) {
            readers.add(new JsonReader(jsonString));
        }
        return readers;
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
