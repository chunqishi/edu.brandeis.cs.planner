package edu.brandeis.cs.planner.deduction;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPVariable;
import gnu.prolog.io.ParseException;
import gnu.prolog.term.AtomTerm;
import gnu.prolog.vm.PrologException;

/**
 * Created by 310201833 on 2016/5/9.
 */
public class JIPrologEngine {
    JIPEngine jip;
    String scriptPath;
    public JIPrologEngine(){
        jip = new JIPEngine();
        scriptPath = "/planner_template_2.pl";
//        scriptPath = "/helloworld.pl";
        jip.consultFile(JIPrologEngine.class.getResource(scriptPath).getFile());
        String initializationGoal = "workflow(a1, a3, L).";
//        String initializationGoal = "likes(mary,L).";
        JIPQuery jipQuery = jip.openSynchronousQuery(initializationGoal);
        JIPTerm solution;
        // Loop while there is another solution
        while (jipQuery.hasMoreChoicePoints())
        {
            solution = jipQuery.nextSolution();
            System.out.println(solution);
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
