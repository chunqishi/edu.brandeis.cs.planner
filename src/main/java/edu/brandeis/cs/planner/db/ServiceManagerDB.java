package edu.brandeis.cs.planner.db;


import java.util.List;
import java.util.Date;
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
        ME.listEmployees();

      /* List down new list of the employees */
        ME.listEmployees();
    }

    /* Method to  READ all the employees */
    public void listEmployees() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String query = "SELECT * from service";
            List employees = session.createQuery(query).list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext(); ) {
                ServiceEntity si = (ServiceEntity) iterator.next();
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
