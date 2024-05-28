package example;

import java.lang.instrument.Instrumentation;

public class JavaAgentMain {
    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("hello from java agent. args = " + args);
    }
}
