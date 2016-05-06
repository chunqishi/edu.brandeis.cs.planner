package edu.brandeis.cs.planner.utils;


import edu.brandeis.cs.planner.db.ServiceManagerDB;
import org.junit.Test;

public class TestURLFetcher {

    @Test
    public void test() {
        System.out.println("TEST...");
        String urlString = "http://www.bing.com";
        String urlcontent = URLFetcher.getAsString(urlString);
        System.out.println(urlcontent);
    }
}