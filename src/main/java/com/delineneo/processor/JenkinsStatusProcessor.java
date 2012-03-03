package com.delineneo.processor;

import com.delineneo.communication.SerialCommunicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.apache.commons.lang.StringUtils.contains;

/**
 * Created by IntelliJ IDEA.
 * User: deline
 * Date: 2/03/12
 * Time: 8:33 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class JenkinsStatusProcessor {
    private static final String JENKINS_URL = "http://localhost:8080/job/JenkinsStatus/api/json" ;
    private static final String SUCCESS_BUILD_COLOR = "blue";
    public static final char BUILD_FAIL = '1';
    public static final char BUILD_SUCCESS = '0';

    private RestTemplate restTemplate;
    private SerialCommunicator serialCommunicator;

    @Scheduled(fixedDelay=5000)
    public void process() {

        String jsonString = restTemplate.getForObject(JENKINS_URL, String.class);

        boolean buildSuccess = isBuildSuccessful(jsonString);
        try {
            serialCommunicator.send(buildSuccess ? BUILD_SUCCESS : BUILD_FAIL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isBuildSuccessful(String jsonString) {
        if (contains(jsonString, SUCCESS_BUILD_COLOR)) {
            return true;
        }
        return false;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setSerialCommunicator(SerialCommunicator serialCommunicator) {
        this.serialCommunicator = serialCommunicator;
    }
}
