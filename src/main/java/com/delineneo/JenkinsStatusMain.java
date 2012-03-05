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
        JenkinsStatusMain jenkinsStatusMain = new JenkinsStatusMain();
    }
}
