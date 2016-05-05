package edu.brandeis.cs.planner.db;


import java.util.List;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class ServiceManagerDB {
    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        ServiceManagerDB ME = new ServiceManagerDB();

      /* List down all the employees */
        ME.listServices();
    }

    public ServiceManagerDB(){
        factory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
    }

    /* Method to  READ all the employees */
    public void listServices() {
//        Session session = factory.getCurrentSession();
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String query = "SELECT * from service;";
            List<Object[]> services = (List<Object[]>)session.createSQLQuery(query).list();
            for (Object[] tuple: services) {
                ServiceEntity si = (ServiceEntity) tuple[0];
                Number roleId = (Number)tuple[1];
                System.out.println(si);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
