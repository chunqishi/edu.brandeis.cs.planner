package edu.brandeis.cs.planner.service;

import edu.brandeis.cs.planner.db.ServiceEntity;
import edu.brandeis.cs.planner.db.ServiceManagerDB;
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
//    public static final String Param_Grid_GridID = "grids.grid.grid_id";

    XMLConfiguration config = null;

    List<String> service_managers = new ArrayList<String>();
    //    List<String> grid_ids = new ArrayList<String>();
    List<List<ServiceInfo>> grid_services = new ArrayList<List<ServiceInfo>>();

    public WsdlInfo() {
        String xmlPath = WsdlInfo.class.getResource("/config.xml").getFile();
        FileBasedConfigurationBuilder<XMLConfiguration> builder =
                new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
                        .configure(params.xml().setFileName(xmlPath));
        try {
            config = builder.getConfiguration();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        for (String service_manager : config.getStringArray(Param_Grid_ServiceManager))
            service_managers.add(service_manager);
//        for (String grid_id : config.getStringArray(Param_Grid_GridID))
//            grid_ids.add(grid_id);
        logger.debug("ServiceManagers: {}", service_managers);
//        logger.debug("GridIds: {}", grid_ids);
        ServiceManagerDB sm = new ServiceManagerDB();
        List<ServiceEntity> entities = sm.listServices();
        for (int i = 0; i < service_managers.size(); i++) {
            String service_manager = service_managers.get(i);
//            String grid_id = grid_ids.get(i);
            List<ServiceInfo> infos = new ArrayList<>();
            for (ServiceEntity entity : entities) {
                ServiceInfo info = new ServiceInfo();
                info.setService_manager(service_manager);
                info.setGrid_id(entity.getGridid());
                info.setService_id(entity.getServiceid());
                infos.add(info);
            }
            grid_services.add(infos);
        }
    }


}
