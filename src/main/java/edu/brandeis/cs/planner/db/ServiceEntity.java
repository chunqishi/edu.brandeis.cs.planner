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


@Entity
@Table(name = "service")


/**
 * CREATE TABLE service (
 dtype character varying(31) NOT NULL,
 gridid character varying(255) NOT NULL,
 serviceid character varying(255) NOT NULL,
 createddatetime timestamp without time zone,
 updateddatetime timestamp without time zone,
 active boolean NOT NULL,
 alternateserviceid character varying(255),
 appauthkey character varying(255),
 approved boolean NOT NULL,
 containertype character varying(255),
 copyrightinfo character varying(255),
 federateduseallowed boolean NOT NULL,
 howtogetmembershipinfo text,
 instance oid,
 instancesize integer NOT NULL,
 instancetype integer,
 licenseinfo text,
 membersonly boolean NOT NULL,
 owneruserid character varying(255),
 resourceid character varying(255),
 servicedescription text,
 servicename character varying(255),
 servicetypedomainid character varying(255),
 servicetypeid character varying(255),
 streaming boolean,
 timeoutmillis integer NOT NULL,
 usealternateservice boolean NOT NULL,
 visible boolean NOT NULL,
 wrappersourcecodeurlclazz character varying(255),
 wrappersourcecodeurlstringvalue character varying(255),
 wsdl oid,
 deployed boolean,
 deployedid character varying(255)
 );
 */
public class ServiceEntity {

//    @Id
//    @GeneratedValue
//    private Long id;

    @Column(name = "dtype")
    private String dtype;

    @Column(name = "gridid")
    private String gridid;

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
