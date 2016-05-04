package edu.brandeis.cs.planner.db;

/**
 * Created by 310201833 on 2016/5/4.
 */

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



/***
 * CREATE TABLE service (
 * dtype character varying(31) NOT NULL,
 * gridid character varying(255) NOT NULL,
 * serviceid character varying(255) NOT NULL,
 * createddatetime timestamp without time zone,
 * updateddatetime timestamp without time zone,
 * active boolean NOT NULL,
 * alternateserviceid character varying(255),
 * appauthkey character varying(255),
 * approved boolean NOT NULL,
 * containertype character varying(255),
 * copyrightinfo character varying(255),
 * federateduseallowed boolean NOT NULL,
 * howtogetmembershipinfo text,
 * instance oid,
 * instancesize integer NOT NULL,
 * instancetype integer,
 * licenseinfo text,
 * membersonly boolean NOT NULL,
 * owneruserid character varying(255),
 * resourceid character varying(255),
 * servicedescription text,
 * servicename character varying(255),
 * servicetypedomainid character varying(255),
 * servicetypeid character varying(255),
 * streaming boolean,
 * timeoutmillis integer NOT NULL,
 * usealternateservice boolean NOT NULL,
 * visible boolean NOT NULL,
 * wrappersourcecodeurlclazz character varying(255),
 * wrappersourcecodeurlstringvalue character varying(255),
 * wsdl oid,
 * deployed boolean,
 * deployedid character varying(255)
 * );
 ***/

@Entity
@Table(name = "service")
public class ServiceEntity {

    public ServiceEntity(){

    }

    public ServiceEntity(String dtype, String gridid, String serviceid, Date createddatetime, Date updateddatetime, boolean active, String alternateserviceid, String appauthkey, boolean approved, String containertype, String copyrightinfo, boolean federateduseallowed, String howtogetmembershipinfo, int instancesize, int instancetype, String licenseinfo, boolean membersonly, String owneruserid, String resourceid, boolean streaming, int timeoutmillis, boolean usealternateservice, boolean visible, String wrappersourcecodeurlclazz, String wrappersourcecodeurlstringvalue, boolean deployed, String deployedid) {
        this.dtype = dtype;
        this.gridid = gridid;
        this.serviceid = serviceid;
        this.createddatetime = createddatetime;
        this.updateddatetime = updateddatetime;
        this.active = active;
        this.alternateserviceid = alternateserviceid;
        this.appauthkey = appauthkey;
        this.approved = approved;
        this.containertype = containertype;
        this.copyrightinfo = copyrightinfo;
        this.federateduseallowed = federateduseallowed;
        this.howtogetmembershipinfo = howtogetmembershipinfo;
        this.instancesize = instancesize;
        this.instancetype = instancetype;
        this.licenseinfo = licenseinfo;
        this.membersonly = membersonly;
        this.owneruserid = owneruserid;
        this.resourceid = resourceid;
        this.streaming = streaming;
        this.timeoutmillis = timeoutmillis;
        this.usealternateservice = usealternateservice;
        this.visible = visible;
        this.wrappersourcecodeurlclazz = wrappersourcecodeurlclazz;
        this.wrappersourcecodeurlstringvalue = wrappersourcecodeurlstringvalue;
        this.deployed = deployed;
        this.deployedid = deployedid;
    }

//    @Id
//    @GeneratedValue
//    private Long id;


    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getGridid() {
        return gridid;
    }

    public void setGridid(String gridid) {
        this.gridid = gridid;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public Date getCreateddatetime() {
        return createddatetime;
    }

    public void setCreateddatetime(Date createddatetime) {
        this.createddatetime = createddatetime;
    }

    public Date getUpdateddatetime() {
        return updateddatetime;
    }

    public void setUpdateddatetime(Date updateddatetime) {
        this.updateddatetime = updateddatetime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAlternateserviceid() {
        return alternateserviceid;
    }

    public void setAlternateserviceid(String alternateserviceid) {
        this.alternateserviceid = alternateserviceid;
    }

    public String getAppauthkey() {
        return appauthkey;
    }

    public void setAppauthkey(String appauthkey) {
        this.appauthkey = appauthkey;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getContainertype() {
        return containertype;
    }

    public void setContainertype(String containertype) {
        this.containertype = containertype;
    }

    public String getCopyrightinfo() {
        return copyrightinfo;
    }

    public void setCopyrightinfo(String copyrightinfo) {
        this.copyrightinfo = copyrightinfo;
    }

    public boolean isFederateduseallowed() {
        return federateduseallowed;
    }

    public void setFederateduseallowed(boolean federateduseallowed) {
        this.federateduseallowed = federateduseallowed;
    }

    public String getHowtogetmembershipinfo() {
        return howtogetmembershipinfo;
    }

    public void setHowtogetmembershipinfo(String howtogetmembershipinfo) {
        this.howtogetmembershipinfo = howtogetmembershipinfo;
    }

    public int getInstancesize() {
        return instancesize;
    }

    public void setInstancesize(int instancesize) {
        this.instancesize = instancesize;
    }

    public int getInstancetype() {
        return instancetype;
    }

    public void setInstancetype(int instancetype) {
        this.instancetype = instancetype;
    }

    public String getLicenseinfo() {
        return licenseinfo;
    }

    public void setLicenseinfo(String licenseinfo) {
        this.licenseinfo = licenseinfo;
    }

    public boolean isMembersonly() {
        return membersonly;
    }

    public void setMembersonly(boolean membersonly) {
        this.membersonly = membersonly;
    }

    public String getOwneruserid() {
        return owneruserid;
    }

    public void setOwneruserid(String owneruserid) {
        this.owneruserid = owneruserid;
    }

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public boolean isStreaming() {
        return streaming;
    }

    public void setStreaming(boolean streaming) {
        this.streaming = streaming;
    }

    public int getTimeoutmillis() {
        return timeoutmillis;
    }

    public void setTimeoutmillis(int timeoutmillis) {
        this.timeoutmillis = timeoutmillis;
    }

    public boolean isUsealternateservice() {
        return usealternateservice;
    }

    public void setUsealternateservice(boolean usealternateservice) {
        this.usealternateservice = usealternateservice;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getWrappersourcecodeurlclazz() {
        return wrappersourcecodeurlclazz;
    }

    public void setWrappersourcecodeurlclazz(String wrappersourcecodeurlclazz) {
        this.wrappersourcecodeurlclazz = wrappersourcecodeurlclazz;
    }

    public String getWrappersourcecodeurlstringvalue() {
        return wrappersourcecodeurlstringvalue;
    }

    public void setWrappersourcecodeurlstringvalue(String wrappersourcecodeurlstringvalue) {
        this.wrappersourcecodeurlstringvalue = wrappersourcecodeurlstringvalue;
    }

    public boolean isDeployed() {
        return deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    public String getDeployedid() {
        return deployedid;
    }

    public void setDeployedid(String deployedid) {
        this.deployedid = deployedid;
    }

    public String getDtype() {

        return dtype;
    }


    @Column(name = "dtype")
    private String dtype;

    @Column(name = "gridid")
    private String gridid;

    @Id
    @Column(name = "serviceid")
    private String serviceid;

    @Column(name = "createddatetime")
    private Date createddatetime;


    @Column(name = "updateddatetime")
    private Date updateddatetime;

    @Column(name = "active")
    private boolean active;

    @Column(name = "alternateserviceid")
    private String alternateserviceid;


    @Column(name = "appauthkey")
    private String appauthkey;


    @Column(name = "approved")
    private boolean approved;

    @Column(name = "containertype")
    private String containertype;


    @Column(name = " copyrightinfo")
    private String copyrightinfo;

    @Column(name = "federateduseallowed")
    private boolean federateduseallowed;


    @Column(name = "howtogetmembershipinfo")
    private String howtogetmembershipinfo;


//    @Column(name = "instance")
//    private int instance;

    @Column(name = "instancesize")
    private int instancesize;


    @Column(name = " instancetype")
    private int instancetype;


    @Column(name = "licenseinfo")
    private String licenseinfo;


    @Column(name = "membersonly")
    private boolean membersonly;


    @Column(name = "owneruserid")
    private String owneruserid;

    @Column(name = "resourceid")
    private String resourceid;


    @Column(name = "streaming")
    private boolean streaming;

    @Column(name = "timeoutmillis")
    private int timeoutmillis;


    @Column(name = "usealternateservice")
    private boolean usealternateservice;


    @Column(name = "visible")
    private boolean visible;


    @Column(name = "wrappersourcecodeurlclazz")
    private String wrappersourcecodeurlclazz;


    @Column(name = "wrappersourcecodeurlstringvalue")
    private String wrappersourcecodeurlstringvalue;


//    @Column(name = "wsdl")
//    private String wsdl;


    @Column(name = "deployed")
    private boolean deployed;


    @Column(name = "deployedid")
    private String deployedid;

}
