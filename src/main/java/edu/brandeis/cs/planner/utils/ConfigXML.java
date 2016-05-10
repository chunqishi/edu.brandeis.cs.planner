package edu.brandeis.cs.planner.utils;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

/**
 * Created by 310201833 on 2016/5/6.
 */
public class ConfigXML {
    final static Logger logger = LoggerFactory.getLogger(ConfigXML.class);
    XMLConfiguration config = null;
    Parameters params = new Parameters();
    static ConfigXML self = null;

    public static XMLConfiguration config() {
        if (self == null) {
            synchronized (ConfigXML.class) {
                if (self == null)
                    self = new ConfigXML();
            }
        }
        return self.config;
    }

    protected ConfigXML() {
        try {
            String xmlPath = ConfigXML.class.getResource("/config.xml").toURI().getPath();
            init(xmlPath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public ConfigXML(String xmlPath) {
        init(xmlPath);
    }

    protected void init(String xmlPath) {
        logger.debug("XML_PATH:{}", xmlPath);
        FileBasedConfigurationBuilder<XMLConfiguration> builder =
                new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
                        .configure(params.xml().setFileName(xmlPath)
                                .setExpressionEngine(new XPathExpressionEngine()));
        try {
            config = builder.getConfiguration();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
