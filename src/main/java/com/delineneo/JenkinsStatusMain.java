package com.delineneo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by IntelliJ IDEA.
 * User: deline
 * Date: 2/03/12
 * Time: 8:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class JenkinsStatusMain {

    public JenkinsStatusMain() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:*/jenkinsStatusSystem.xml"});
    }

    public static void main(String[] args) throws Exception {
        ShutDownHook hook = new ShutDownHook();
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(hook);

        JenkinsStatusMain jenkinsStatusMain = new JenkinsStatusMain();
    }

    static class ShutDownHook extends Thread {
        public void run() {
            System.out.println("Shutting down application...");
        }
    }
}
