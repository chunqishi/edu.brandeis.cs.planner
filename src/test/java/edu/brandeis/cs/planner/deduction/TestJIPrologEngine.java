package edu.brandeis.cs.planner.deduction;


import edu.brandeis.cs.planner.db.ServiceEntity;
import edu.brandeis.cs.planner.service.WsdlInfo;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestJIPrologEngine {

    @Test
    public void test() throws IOException {
        System.out.println("TEST..." + this.getClass());
        Rules rules = new Rules();
        List<String> facts = new ArrayList<String>();
        facts.add("splitter(a1).\n" +
                "splitter(b1).\n" +
                "\n" +
                "tokenizer(a2).\n" +
                "tokenizer(b2).\n" +
                "\n" +
                "tagger(a3).\n" +
                "tagger(b3).\n" +
                "\n" +
                "chunking(a4).\n" +
                "chunking(b4).\n" +
                "\n" +
                "ner(a5).\n" +
                "ner(b5).\n" +
                "\n" +
                "parser(a6).\n" +
                "parser(b6).\n" +
                "\n" +
                "dependencyparser(a7).\n" +
                "dependencyparser(b7).\n" +
                "\n" +
                "\n" +
                "coreference(a8).\n" +
                "coreference(b8).");
        JIPrologEngine.queryFactsWithGoal(facts, rules.getRules(),"workflow(a1, a3, L).");
    }
}