package edu.brandeis.cs.planner.db;


import org.junit.Test;

public class TestServiceManagerDB {

    @Test
    public void test(){
        System.out.println("TEST..." + this.getClass());
        ServiceManagerDB sm = new ServiceManagerDB();
        sm.listServices();
    }
}