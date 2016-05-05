package edu.brandeis.cs.planner.db;


import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
public class ServiceManagerDB {
    final static Logger logger = LoggerFactory.getLogger(ServiceManagerDB.class);

    private static SessionFactory factory;

    public ServiceManagerDB() {
        if (factory == null)
            synchronized (ServiceManagerDB.class) {
                if (factory == null)
                    factory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
            }
    }

    public List<ServiceEntity> listServices() {
        Session session = factory.getCurrentSession();
//        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String query = "select h from ServiceEntity as h";
            List<ServiceEntity> services = (List<ServiceEntity>) session.createQuery(query).list();
            for (ServiceEntity service : services) {
                System.out.println(service);
                logger.debug("{}", service);
            }
            tx.commit();
            return services;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            logger.debug("Error rolling back transaction");
        } finally {
//            session.close();
        }
        return null;
    }
}
