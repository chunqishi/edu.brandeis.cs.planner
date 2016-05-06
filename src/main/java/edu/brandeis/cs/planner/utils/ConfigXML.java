package edu.brandeis.cs.planner.utils;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * Created by 310201833 on 2016/5/6.
 */
public class ConfigXML {
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
        this(ConfigXML.class.getResource("/config.xml").getFile());
    }

    public ConfigXML(String xmlPath) {
        FileBasedConfigurationBuilder<XMLConfiguration> builder =
                new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
                        .configure(params.xml().setFileName(xmlPath));
        try {
            config = builder.getConfiguration();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
