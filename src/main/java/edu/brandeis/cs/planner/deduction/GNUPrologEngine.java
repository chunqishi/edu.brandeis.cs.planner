package edu.brandeis.cs.planner.deduction;


import gnu.prolog.database.PrologTextLoaderError;
import gnu.prolog.term.*;
import gnu.prolog.vm.Environment;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.PrologCode;
import gnu.prolog.vm.PrologException;
import gnu.prolog.io.ParseException;
import gnu.prolog.io.TermReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GnuPrologEngine {
    final static Logger logger = LoggerFactory.getLogger(GnuPrologEngine.class);
    Environment env;
    Interpreter interpreter;
    String scriptPath;

    protected void getVariableSet(Term term, Set<VariableTerm> set) {
        if (term instanceof VariableTerm) {
            set.add((VariableTerm) term);
        } else if (term instanceof CompoundTerm) {
            CompoundTerm c = (CompoundTerm) term;
            for (int i = 0, n = c.tag.arity; i < n; i++)
                getVariableSet(c.args[i], set);
        }
    }



	private void debug()
	{
		List<PrologTextLoaderError> errors = env.getLoadingErrors();
		for (PrologTextLoaderError error : errors)
		{
			error.printStackTrace();
		}
	}

    public GnuPrologEngine() throws ParseException, PrologException {
        env = new Environment();
        scriptPath = "/planner_template_2.pl";
        env.ensureLoaded(AtomTerm.get(GnuPrologEngine.class.getResource(scriptPath).getFile()));
        interpreter = env.createInterpreter();
        env.runInitialization(interpreter);
        String qs = "workflow(a1, a3, L)";
        Term query = TermReader.stringToTerm(qs, env);
        Interpreter.Goal goal = interpreter.prepareGoal(query);
        int r = interpreter.execute(goal);
        if (r == PrologCode.FAIL) {
            System.out.println("FAIL ...");
            debug();
        } else if (r == PrologCode.SUCCESS_LAST) {
            System.out.println("SUCCESS_LAST");
        } else if (r == PrologCode.SUCCESS) {
            System.out.println("SUCCESS");
            Set<VariableTerm> vars = new LinkedHashSet<VariableTerm>();
            getVariableSet(query, vars);
            for (VariableTerm v : vars)
                System.out.println("\n" + v.name + " =  " + v);
        } else {
            System.out.println("CODE:" + r);
        }
    }

    public static void main(String[] args) throws PrologException, ParseException {
        GnuPrologEngine pe = new GnuPrologEngine();
    }

//
//    protected final String scriptPath;
//
//    private static boolean issetup = false;
//    private static Environment env;
//    private static Interpreter interpreter;
//
//    protected boolean loadScript(String scriptPathString,
//                                 final Environment env) {
//        final AtomTerm scriptPath = AtomTerm.get(scriptPathString);
//        final boolean[] reading = new boolean[] { true };
//        Thread th = new Thread () {
//            @Override public void run () {
//                env.ensureLoaded(scriptPath);
//                synchronized (reading) {
//                    reading[0] = false;
//                    reading.notify();
//                }
//            }
//        };
//        th.setDaemon(true);
//        synchronized (reading) {
//            th.start();
//            long limit = System.currentTimeMillis() + TIMELIMIT_TO_LOAD;
//            try {
//                reading.wait(TIMELIMIT_TO_LOAD);
//                while (reading[0]) {
//                    long dt = limit - System.currentTimeMillis();
//                    if (dt > 0) { // spurious wakeup?
//                        reading.wait(dt);
//                    } else {
//                        th.interrupt();
//                        return true;
//                    }
//                }
//            } catch (InterruptedException ex) {
//                return true;
//            }
//        }
//        return false;
//    }
//    protected boolean resolve(Term query, Interpreter interp, Environment env)
//            throws PrologException
//    {
//        Set<VariableTerm> vars = new LinkedHashSet<VariableTerm> ();
//        getVariableSet(query, vars);
//        Interpreter.Goal goal = interp.prepareGoal(query);
//        for (;;) {
//            int r = interp.execute(goal);
//            if (r == PrologCode.FAIL)
//                con.printf("\nno\n");
//            else if (r == PrologCode.SUCCESS_LAST)
//                if (vars.isEmpty()) {
//                    con.printf("\nyes\n");
//                } else {
//                    for (VariableTerm v: vars)
//                        con.printf("\n%s = %s", v.name, v);
//                    con.printf(" ;\n\nno\n");
//                }
//            else if (r == PrologCode.SUCCESS)
//                if (vars.isEmpty()) {
//                    con.printf("\nyes\n");
//                    interp.stop(goal);
//                } else {
//                    for (VariableTerm v: vars)
//                        con.printf("\n%s = %s", v.name, v);
//                    String line = con.readLine();
//                    if (";".equals(line))
//                        continue;
//                    interp.stop(goal);
//                    return (line == null);
//                }
//            else
//                con.printf("[code = %d]\n", r);
//            return false;
//        }
//    }
//
//    public void run() {
//        Environment env = new Environment ();
//        if (loadScript(scriptPath, env)) { // interrupted ?
//            con.printf("something is wrong with: %s\n", scriptPath);
//            return;
//        }
//        List<PrologTextLoaderError> errors = env.getLoadingErrors();
//        if (! errors.isEmpty()) {
//            for (PrologTextLoaderError e: errors) {
//                int lineNumber = e.getLine();
//                if (lineNumber != 0)
//                    con.printf("%s:%d: ", e.getFile(), lineNumber);
//                con.printf("%s\n", e.getMessage());
//            }
//            return;
//        }
//
//        Interpreter interp = env.createInterpreter();
//        env.runInitialization(interp);
//
//        for (;;) {
//            String qs = con.readLine("?- "); // query string
//            if (qs == null)                  // EOF ?
//                break;
//
//            // 問い合わせの末尾のピリオドは無視する
//            qs = qs.trim();
//            int n = qs.length();
//            if (n > 0 && qs.charAt(n - 1) == '.')
//                qs = qs.substring(0, n - 1);
//
//            Term query;
//            try {
//                query = TermReader.stringToTerm(qs, env);
//            } catch (ParseException ex) {
//                con.printf("syntax error: %s\n", ex.getMessage());
//                continue;
//            }
//            try {
//                if (resolve(query, interp, env)) // EOF ?
//                    break;
//            } catch (PrologException ex) {
//                con.printf("%s\n", ex.getMessage());
//            }
//        }
//        con.printf("\nbye\n");
//    }
//
//
//    private synchronized static void setup()
//    {
//        if (issetup)
//        {
//            return;// don't setup more than once
//        }
//
//        // Construct the environment
//        env = new Environment();
//
//        // get the filename relative to the class file
//        env.ensureLoaded(AtomTerm.get(GnuPrologEngine.class.getResource("planner.pl").getFile()));
//        // Get the interpreter.
//        interpreter = env.createInterpreter();
//        // Run the initialization
//        env.runInitialization(interpreter);
//        // So that we don't repeat ourselves
//        issetup = true;
//    }
//
//    public static void main(String [] args){
//        synchronized (interpreter)// so that this class is thread safe.
//        {
//
//            // Execute the goal and return the return code.
//            int rc = interpreter.runOnce(goalTerm);
//
//            // If it succeeded.
//            if (rc == PrologCode.SUCCESS || rc == PrologCode.SUCCESS_LAST)
//            {
//                // Create the answer
//                Pair<String, Integer> answer = new Pair<String, Integer>(null, 0);
//
//                // Get hold of the actual Terms which the variable terms point to
//                Term list = listTerm.dereference();
//                Term value = answerTerm.dereference();
//                // Check it is valid
//                if (list != null)
//                {
//                    if (list instanceof CompoundTerm)
//                    {
//                        CompoundTerm cList = (CompoundTerm) list;
//                        if (cList.tag == TermConstants.listTag)// it is a list
//                        {// Turn it into a string to use.
//                            answer.left = TermWriter.toString(list);
//                        }
//                        else
//                        {
//                            throw new NoAnswerException("List is not a list but is a CompoundTerm: " + list);
//                        }
//                    }
//                    else
//                    {
//                        throw new NoAnswerException("List is not a list: " + list);
//                    }
//                }
//                else
//                {
//                    throw new NoAnswerException("List null when it should not be null");
//                }
//                if (value != null)
//                {
//                    if (value instanceof IntegerTerm)
//                    {
//                        answer.right = ((IntegerTerm) value).value;
//                    }
//                    else
//                    {
//                        throw new NoAnswerException("Answer is not an integer: (" + value + ") but List is:" + list);
//                    }
//                }
//                else
//                {
//                    throw new NoAnswerException("Answer null when it should not be null");
//                }
//
//                return answer;
//            }
//            else
//            {
//                throw new NoAnswerException("Goal failed");
//            }
//        }
//    }

}
