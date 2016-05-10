package edu.brandeis.cs.planner.service;

import edu.brandeis.cs.planner.db.ServiceEntity;
import edu.brandeis.cs.planner.db.ServiceManagerDB;
import edu.brandeis.cs.planner.utils.ConfigXML;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 310201833 on 2016/5/5.
 */
public class WsdlInfo {

    final static Logger logger = LoggerFactory.getLogger(WsdlInfo.class);

    List<String> wsdls = new ArrayList<String>();

    Parameters params = new Parameters();
    public static final String Param_Grid_ServiceManager = "grids/grid/service_manager";
    List<String> service_managers = new ArrayList<String>();
    List<List<ServiceInfo>> grid_services = new ArrayList<List<ServiceInfo>>();

    public List<String> getWsdls() {
        return wsdls;
    }

    public List<List<ServiceInfo>> getGrid_services() {
        return grid_services;
    }

    public List<String> getService_managers() {
        return service_managers;
    }

    public WsdlInfo(List<ServiceEntity> entities) {
        for (String service_manager : ConfigXML.config().getStringArray(Param_Grid_ServiceManager))
            service_managers.add(service_manager);
        logger.debug("ServiceManagers: {}", service_managers);
        for (int i = 0; i < service_managers.size(); i++) {
            String service_manager = service_managers.get(i);
            List<ServiceInfo> infos = new ArrayList<>();
            for (ServiceEntity entity : entities) {
                ServiceInfo info = new ServiceInfo();
                info.setService_manager(service_manager);
                info.setGrid_id(entity.getGridid());
                info.setService_id(entity.getServiceid());
                infos.add(info);
                wsdls.add(info.toWsdl());
            }
            grid_services.add(infos);
        }

    }


}
