package edu.brandeis.cs.planner.utils;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 310201833 on 2016/5/6.
 */
public class URLFetcher {
    final static Logger logger = LoggerFactory.getLogger(URLFetcher.class);
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko";

    public static String getAsString(String urlString) {
        int timeout = ConfigXML.config().getInt("connection/timeout");
        boolean use_proxy = ConfigXML.config().getBoolean("connection/proxies/use_proxy");
        boolean has_credential = ConfigXML.config().getBoolean("connection/credentials/use_credential");
        return getAsString(urlString, timeout, use_proxy, has_credential);
    }

    public static String getAsString(String urlString, int timeout_in_s, boolean use_proxy, boolean has_credential) {
        if (urlString == null || urlString.length() < 1)
            return null;

        int timout_in_ms = timeout_in_s * 1000; // Timeout in millis.
        HttpHost proxy = null;
        if (use_proxy) {
            String http_proxy = ConfigXML.config().getString("connection/proxies/http_proxy");
            logger.debug("HTTP_PROXY: {}", http_proxy);
            proxy = HttpHost.create(http_proxy);
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timout_in_ms)
                .setConnectTimeout(timout_in_ms)
                .setSocketTimeout(timout_in_ms)
                .setProxy(proxy)
                .build();

        CloseableHttpClient httpClient = null;
        if (has_credential) {
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            try {
                URL url = new URL(urlString);
                String host = url.getHost();
                int port = url.getPort();
                System.out.println(
                        "connection/credentials/credential[@host='" + host + "'and @port='" + port + "']/username");
                String username = ConfigXML.config().getString(
                        "connection/credentials/credential[@host='" + host + "' and @port='" + port + "']/username");
                String password = ConfigXML.config().getString(
                        "connection/credentials/credential[@host='" + host + "' and @port='" + port + "']/password");
                logger.debug("Credential: Username = '{}'", username);
                credsProvider.setCredentials(
                        new AuthScope(host, port),
                        new UsernamePasswordCredentials(username, password));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            httpClient = HttpClients.custom()
                    .setDefaultCredentialsProvider(credsProvider)
                    .build();
        } else {
            httpClient = HttpClients.createDefault();
        }

        HttpGet httpGet = new HttpGet(urlString);
        httpGet.setConfig(requestConfig);
        httpGet.addHeader("User-Agent", USER_AGENT);
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println("GET Response Status:"
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
