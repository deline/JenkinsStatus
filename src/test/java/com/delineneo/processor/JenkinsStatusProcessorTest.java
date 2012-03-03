package com.delineneo.processor;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

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

    private JenkinsStatusProcessor jenkinsStatusProcessor;
    private RestTemplate restTemplate = createMock("restTemplate", RestTemplate.class);

    @Before
    public void setup() {
        jenkinsStatusProcessor = new JenkinsStatusProcessor();
        jenkinsStatusProcessor.setRestTemplate(restTemplate);
    }

    @Test
    public void failingBuildShouldSendMessageToTurnLedOn() {

        expect(restTemplate.getForObject(JENKINS_URL, String.class)).andReturn(FAILING_BUILD_JSON);

        replayAll();
        jenkinsStatusProcessor.process();
        verifyAll();
    }

    //    @Test
    public void passingBuildShouldSendMessageToTurnLedOff() {
        fail("Test not yet implemented");
    }
}
