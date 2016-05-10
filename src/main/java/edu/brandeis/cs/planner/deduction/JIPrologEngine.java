package edu.brandeis.cs.planner.deduction;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPVariable;
import gnu.prolog.io.ParseException;
import gnu.prolog.term.AtomTerm;
import gnu.prolog.vm.PrologException;
import org.apache.commons.collections.iterators.ObjectArrayIterator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 310201833 on 2016/5/9.
 */
public class JIPrologEngine {
    private final static Logger logger = LoggerFactory.getLogger(JIPrologEngine.class);
    private String scriptPath = "/planner_template.pl";
    private String rules = null;
    public static final String NewLine = System.getProperty("line.separator");

    public JIPrologEngine() throws IOException {
        rules = getFileAsString(scriptPath);
    }

    public JIPrologEngine(String scriptPath) throws IOException {
        rules = getFileAsString(scriptPath);
    }

    public Map<String, Object> queryFactsWithGoal(List<String> factsStrings, String goalString) {
        StringBuilder sb = new StringBuilder();
        for (String factString : factsStrings) {
            sb.append(factsStrings).append(NewLine);
        }
        sb.append(rules);
        JIPEngine jip = init(sb.toString());
        Map<String, Object> res = queryGoal(jip, goalString);
        return res;
    }


    protected static JIPEngine init(File scriptFile) throws IOException {
        JIPEngine jip = new JIPEngine();
        jip.consultFile(scriptFile.getCanonicalPath());
        return jip;
    }

    public static String getFileAsString(String path) throws IOException {
        return IOUtils.toString(new FileInputStream(new File(JIPrologEngine.class.getResource(path).getFile())));
    }

    protected static JIPEngine init(String scriptString) {
        JIPEngine jip = new JIPEngine();
        InputStream stream = new ByteArrayInputStream(scriptString.getBytes(StandardCharsets.UTF_8));
        // hashcode as stream name.
        jip.consultStream(stream, String.valueOf(scriptString.hashCode()));
        return jip;
    }

    protected static Map<String, Object> queryGoal(JIPEngine jip, String goalString) {
        JIPQuery jipQuery = jip.openSynchronousQuery(goalString);
        JIPTerm solution;
        Map res = new LinkedHashMap<String, Object>();
        // Loop while there is another solution
        while (jipQuery.hasMoreChoicePoints()) {
            solution = jipQuery.nextSolution();
            System.out.println(solution);
            if (solution == null)
                break;
            JIPVariable[] vars = solution.getVariables();
            for (JIPVariable var : vars) {
                if (!var.isAnonymous()) {
                    res.put(var.getName(), var.toString(jip));
                    logger.debug(var.getName() + " = ", var.toString(jip));
//                    System.out.print(var.getName() + " = " + var.toString(jip) + " ");
//                    System.out.println();
                }
            }
        }
        return res;
    }

//
//    public JIPrologEngine() {
//        jip = new JIPEngine();
//        scriptPath = "/planner_template.pl";
////        scriptPath = "/helloworld.pl";
//        String scriptString = null;
//        try {
//            scriptString = IOUtils.toString(new FileInputStream(new File(JIPrologEngine.class.getResource(scriptPath).getFile())));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        InputStream stream = new ByteArrayInputStream(scriptString.getBytes(StandardCharsets.UTF_8));
////        jip.consultFile(JIPrologEngine.class.getResource(scriptPath).getFile());
//        jip.consultStream(stream, scriptPath);
//        String initializationGoal = "workflow(a1, a8, L).";
////        String initializationGoal = "likes(mary,L).";
//        JIPQuery jipQuery = jip.openSynchronousQuery(initializationGoal);
//        JIPTerm solution;
//        // Loop while there is another solution
//        while (jipQuery.hasMoreChoicePoints()) {
//            solution = jipQuery.nextSolution();
//            System.out.println(solution);
//            if (solution == null)
//                break;
//            JIPVariable[] vars = solution.getVariables();
//            for (JIPVariable var : vars) {
//                if (!var.isAnonymous()) {
//                    System.out.print(var.getName() + " = " + var.toString(jip) + " ");
//                    System.out.println();
//                }
//            }
//        }
//    }

    public static void main(String[] args) throws PrologException, IOException {
        JIPrologEngine pe = new JIPrologEngine();
    }
}
