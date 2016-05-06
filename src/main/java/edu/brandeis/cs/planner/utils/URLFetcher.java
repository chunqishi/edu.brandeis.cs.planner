package edu.brandeis.cs.planner.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by 310201833 on 2016/5/6.
 */
public class URLFetcher {
    final static Logger logger = LoggerFactory.getLogger(URLFetcher.class);
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko";

    public static String getAsString(String urlString) {
        if (urlString == null || urlString.length() < 1)
            return null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(urlString);

        httpGet.addHeader("User-Agent", USER_AGENT);
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            System.out.println("GET Response Status:: "
                    + httpResponse.getStatusLine().getStatusCode());
            logger.debug("HTTP Status: {}", httpResponse.getStatusLine().getStatusCode());
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpResponse.getEntity().getContent()));
            StringBuffer response = new StringBuffer();
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();
            System.out.println(response.toString());
            logger.debug("Fetched Length: {}", response.length());
            httpClient.close();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
