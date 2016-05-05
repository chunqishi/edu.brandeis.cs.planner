package edu.brandeis.cs.planner.service;

import org.apache.commons.collections.ArrayStack;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310201833 on 2016/5/5.
 */
public class WSDLInfo {
    List<String> wsdls = new ArrayList<String>();

    Parameters params = new Parameters();
    public static final String Param_Grid = "grid.service_manager";
    List<String> grids = new ArrayList<String>();

    public WSDLInfo() {
//        System.out.println(WSDLInfo.class.getResource("/lappsgrid.xml").getFile());
        FileBasedConfigurationBuilder<XMLConfiguration> builder =
                new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
                        .configure(params.xml()
                                .setFileName(WSDLInfo.class.getResource("/lappsgrid.xml").getFile()));
        try {
            XMLConfiguration config = builder.getConfiguration();
//            System.out.println(config.getString("grid.service_manager"));
            for (String grid : config.getStringArray(Param_Grid))
                grids.add(grid);
            System.out.println(grids);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }




}
