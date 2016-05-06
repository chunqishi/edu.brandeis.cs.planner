package edu.brandeis.cs.planner.service;

import edu.brandeis.cs.planner.utils.URLFetcher;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 310201833 on 2016/5/5.
 */
public class ServiceInfo {

    String service_manager;
    String grid_id;
    String service_id;
    String wsdl;
    String wsdlContent;

    public ServiceInfo() {
    }

    public ServiceInfo(String service_manager, String grid_id, String service_id) {
        this.service_manager = service_manager;
        this.grid_id = grid_id;
        this.service_id = service_id;
        this.init();
    }

    public void init() {
        this.wsdl = this.toURL();
        this.wsdlContent = URLFetcher.getAsString(this.wsdl);
    }

    public String toURL() {
        StringBuilder sb = new StringBuilder("invoker/");
        sb.append(grid_id);
        sb.append(":");
        sb.append(service_id);
        try {
            return new URL(new URL(service_manager), sb.toString()).toString() ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getService_manager() {
        return service_manager;
    }

    public void setService_manager(String service_manager) {
        this.service_manager = service_manager;
    }

    public String getGrid_id() {
        return grid_id;
    }

    public void setGrid_id(String grid_id) {
        this.grid_id = grid_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getWsdl() {
        return wsdl;
    }

    public String getWsdlContent() {
        return wsdlContent;
    }
}
