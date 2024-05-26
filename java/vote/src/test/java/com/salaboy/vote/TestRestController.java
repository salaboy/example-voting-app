package com.salaboy.vote;

import com.salaboy.model.Vote;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestRestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestRestController.class);

    public static final String pubSubName = "pubsub";
    public static final String topicName = "newVote";

    private List<CloudEvent<Vote>> events = new ArrayList<>();

    @Topic(name = topicName, pubsubName = pubSubName)
    @PostMapping("/subscribe")
    public void handleMessages(@RequestBody CloudEvent<Vote> event) {
        LOG.info("++++++CONSUME {}------", event);
        events.add(event);
    }

    public List<CloudEvent<Vote>> getEvents() {
        return events;
    }

}
