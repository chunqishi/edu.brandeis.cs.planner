package edu.brandeis.cs.planner.utils;


import edu.brandeis.cs.planner.service.ServiceInfo;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestJsonReader {

    @Test
    public void test() {
        System.out.println("TEST..." + this.getClass());
        String jsonString = "{\n" +
                "  \"discriminator\" : \"http://vocab.lappsgrid.org/ns/meta\",\n" +
                "  \"payload\" : {\n" +
                "    \"$schema\" : \"http://vocab.lappsgrid.org/schema/service-schema-1.0.0.json\",\n" +
                "    \"name\" : \"edu.brandeis.cs.lappsgrid.stanford.corenlp.Parser\",\n" +
                "    \"version\" : \"2.0.0\",\n" +
                "    \"description\" : \" DkPro OpenNLP Parser\",\n" +
                "    \"vendor\" : \"http://www.cs.brandeis.edu/\",\n" +
                "    \"allow\" : \"http://vocab.lappsgrid.org/ns/allow#any\",\n" +
                "    \"license\" : \"http://vocab.lappsgrid.org/ns/license#apache-2.0\",\n" +
                "    \"requires\" : {\n" +
                "      \"language\" : [ \"en\" ],\n" +
                "      \"format\" : [ \"http://vocab.lappsgrid.org/ns/media/jsonld#lif\" ],\n" +
                "      \"annotations\" : [ \"http://vocab.lappsgrid.org/PhraseStructure\" ]\n" +
                "    },\n" +
                "    \"produces\" : {\n" +
                "      \"language\" : [ \"en\" ],\n" +
                "      \"format\" : [ \"http://vocab.lappsgrid.org/ns/media/jsonld#lif\" ],\n" +
                "      \"annotations\" : [ \"http://vocab.lappsgrid.org/PhraseStructure\" ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
        JsonReader reader = new JsonReader(jsonString);
        System.out.println(reader.read("payload.name"));
        System.out.println(reader.read("payload.description"));
    }
}