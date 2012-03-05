package com.delineneo.processor;

import com.delineneo.communication.SerialCommunicator;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: deline
 * Date: 2/03/12
 * Time: 8:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class JenkinsStatusProcessorTest extends EasyMockSupport {

    private static final String JENKINS_URL = "http://localhost:8080/job/JenkinsStatus/api/json";
    private static final String FAILING_BUILD_JSON = "{\"actions\":[{},{}],\"description\":\"\",\"displayName\":\"JenkinsStatus\",\"displayNameOrNull\":null," +
            "\"name\":\"JenkinsStatus\",\"url\":\"http://localhost:8080/job/JenkinsStatus/\",\"buildable\":true,\"builds\":[{\"number\":4,\"url\":\"http://localhost:8080/job/JenkinsStatus/4/\"}," +
            "{\"number\":3,\"url\":\"http://localhost:8080/job/JenkinsStatus/3/\"},{\"number\":2,\"url\":\"http://localhost:8080/job/JenkinsStatus/2/\"}," +
            "{\"number\":1,\"url\":\"http://localhost:8080/job/JenkinsStatus/1/\"}],\"color\":\"red\",\"firstBuild\":{\"number\":1,\"url\":\"http://localhost:8080/job/JenkinsStatus/1/\"}," +
            "\"healthReport\":[{\"description\":\"Build stability: All recent builds failed.\",\"iconUrl\":\"health-00to19.png\",\"score\":0}],\"inQueue\":false," +
            "\"keepDependencies\":false,\"lastBuild\":{\"number\":4,\"url\":\"http://localhost:8080/job/JenkinsStatus/4/\"},\"lastCompletedBuild\":{\"number\":4," +
            "\"url\":\"http://localhost:8080/job/JenkinsStatus/4/\"},\"lastFailedBuild\":{\"number\":4,\"url\":\"http://localhost:8080/job/JenkinsStatus/4/\"},\"lastStableBuild\":null," +
            "\"lastSuccessfulBuild\":null,\"lastUnstableBuild\":null,\"lastUnsuccessfulBuild\":{\"number\":4,\"url\":\"http://localhost:8080/job/JenkinsStatus/4/\"}," +
            "\"nextBuildNumber\":5,\"property\":[{}],\"queueItem\":null,\"concurrentBuild\":false,\"downstreamProjects\":[],\"scm\":{},\"upstreamProjects\":[]}";
    
    private static final String PASSING_BUILD_JSON = "{\"actions\":[{},{}],\"description\":\"\",\"displayName\":\"JenkinsStatus\",\"displayNameOrNull\":null," +
            "\"name\":\"JenkinsStatus\",\"url\":\"http://localhost:8080/job/JenkinsStatus/\",\"buildable\":true,\"builds\":[{\"number\":7,\"url\":\"http://localhost:8080/job/JenkinsStatus/7/\"}," +
            "{\"number\":6,\"url\":\"http://localhost:8080/job/JenkinsStatus/6/\"},{\"number\":5,\"url\":\"http://localhost:8080/job/JenkinsStatus/5/\"}," +
            "{\"number\":4,\"url\":\"http://localhost:8080/job/JenkinsStatus/4/\"},{\"number\":3,\"url\":\"http://localhost:8080/job/JenkinsStatus/3/\"}]," +
            "\"color\":\"blue\",\"firstBuild\":{\"number\":3,\"url\":\"http://localhost:8080/job/JenkinsStatus/3/\"},\"healthReport\":[{\"description\":\"Build stability: 4 out of the last 5 builds failed.\"," +
            "\"iconUrl\":\"health-00to19.png\",\"score\":20}],\"inQueue\":false,\"keepDependencies\":false,\"lastBuild\":{\"number\":7,\"url\":\"http://localhost:8080/job/JenkinsStatus/7/\"}," +
            "\"lastCompletedBuild\":{\"number\":7,\"url\":\"http://localhost:8080/job/JenkinsStatus/7/\"},\"lastFailedBuild\":{\"number\":6,\"url\":\"http://localhost:8080/job/JenkinsStatus/6/\"}," +
            "\"lastStableBuild\":{\"number\":7,\"url\":\"http://localhost:8080/job/JenkinsStatus/7/\"},\"lastSuccessfulBuild\":{\"number\":7,\"url\":\"http://localhost:8080/job/JenkinsStatus/7/\"}," +
            "\"lastUnstableBuild\":null,\"lastUnsuccessfulBuild\":{\"number\":6,\"url\":\"http://localhost:8080/job/JenkinsStatus/6/\"},\"nextBuildNumber\":8,\"property\":[{}],\"queueItem\":null," +
            "\"concurrentBuild\":false,\"downstreamProjects\":[],\"scm\":{},\"upstreamProjects\":[]}";

    private JenkinsStatusProcessor jenkinsStatusProcessor;
    private RestTemplate restTemplate = createMock("restTemplate", RestTemplate.class);
    private SerialCommunicator serialCommunicator = createMock("serialCommunicator", SerialCommunicator.class);

    @Before
    public void setup() {
        jenkinsStatusProcessor = new JenkinsStatusProcessor();
        jenkinsStatusProcessor.setRestTemplate(restTemplate);
        jenkinsStatusProcessor.setSerialCommunicator(serialCommunicator);
    }

    @Test
    public void failingBuildShouldSendMessageToTurnLedOn() throws Exception {
        expect(restTemplate.getForObject(JENKINS_URL, String.class)).andReturn(FAILING_BUILD_JSON);
        serialCommunicator.send('0');
        
        replayAll();
        jenkinsStatusProcessor.process();
        verifyAll();
    }

    @Test
    public void passingBuildShouldSendMessageToTurnLedOff() throws Exception {
        expect(restTemplate.getForObject(JENKINS_URL, String.class)).andReturn(PASSING_BUILD_JSON);
        serialCommunicator.send('1');

        replayAll();
        jenkinsStatusProcessor.process();
        verifyAll();
    }
    
    @Test
    public void unignoreAndCheckInThisTestToCauseBuildFailure() {
        fail("Failing test should cause arduino LED to light up...");
    }
}
