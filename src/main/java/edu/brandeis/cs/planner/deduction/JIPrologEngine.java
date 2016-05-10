package edu.brandeis.cs.planner.deduction;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPVariable;
import gnu.prolog.io.ParseException;
import gnu.prolog.term.AtomTerm;
import gnu.prolog.vm.PrologException;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by 310201833 on 2016/5/9.
 */
public class JIPrologEngine {
    JIPEngine jip;
    String scriptPath;

    




    public JIPrologEngine(){
        jip = new JIPEngine();
        scriptPath = "/planner_template.pl";
//        scriptPath = "/helloworld.pl";
        String scriptString = null;
        try {
            scriptString = IOUtils.toString(new FileInputStream(new File(JIPrologEngine.class.getResource(scriptPath).getFile())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream stream = new ByteArrayInputStream(scriptString.getBytes(StandardCharsets.UTF_8));
//        jip.consultFile(JIPrologEngine.class.getResource(scriptPath).getFile());
        jip.consultStream(stream, scriptPath);
        String initializationGoal = "workflow(a1, a8, L).";
//        String initializationGoal = "likes(mary,L).";
        JIPQuery jipQuery = jip.openSynchronousQuery(initializationGoal);
        JIPTerm solution;
        // Loop while there is another solution
        while (jipQuery.hasMoreChoicePoints())
        {
            solution = jipQuery.nextSolution();
            System.out.println(solution);
            if(solution == null)
                break;
            JIPVariable[] vars = solution.getVariables();
            for (JIPVariable var : vars) {
                if (!var.isAnonymous()) {
                    System.out.print(var.getName() + " = " + var.toString(jip) + " ");
                    System.out.println();
                }
            }
        }
    }

    public static void main(String[] args) throws PrologException, ParseException {
        JIPrologEngine pe = new JIPrologEngine();
    }
}
