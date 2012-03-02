package com.delineneo.processor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: deline
 * Date: 2/03/12
 * Time: 8:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class JenkinsStatusProcessorTest {

    private JenkinsStatusProcessor jenkinsStatusProcessor;

    @Before
    public void setup() {
        jenkinsStatusProcessor = new JenkinsStatusProcessor();
    }

    @Test
    public void failingBuildShouldSendMessageToTurnLedOn() {
        fail("Implement me");
    }
}
