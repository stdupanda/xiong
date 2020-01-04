package cn.xiong.myjavaagent;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

/**
 * java agent
 * 
 * @see java.lang.instrument.Instrumentation
 * Btrace、Arthas、jrebel 等等
 * -javaagent:d:/path/your.jar -Xbootclasspath/a:/path/your.jar
 * -javaagent:d:D:\your.jar -Xbootclasspath/a:D:\your.jar
 */
public class App {
    private static final Logger log = Logger.getLogger(App.class.getSimpleName());

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("this is preMain. now begin !");
        log.info("agentArgs: " + agentArgs);

        instrumentation.addTransformer(new MyTransformer());

        System.out.println("..........................");
        System.out.println("..........................");
        System.out.println("..........................");
    }
}
