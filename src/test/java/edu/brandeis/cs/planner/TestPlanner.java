package edu.brandeis.cs.planner;


import edu.brandeis.cs.planner.utils.ConfigXML;
import junit.framework.Assert;
import org.junit.Test;

public class TestPlanner {

    @Test
    public void test() {
        System.out.println("TEST..." + this.getClass());
        Planner pl = new Planner();
        String ret = pl.execute("{\n" +
                "\"input\":\"text\",\n" +
                "\"output\": \"tokenizer\"\n" +
                "}");
        System.out.println(ret);
    }
}