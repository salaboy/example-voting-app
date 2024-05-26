package com.salaboy.vote;

import com.salaboy.model.Vote;
import io.dapr.client.domain.CloudEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {
    "vote.pubsub=pubsub",
	"vote.topic=newVote"
})
class VoteApplicationTests extends BaseIntegrationTest {

	@Autowired
	private VoteController voteController;

	@Autowired
	private TestRestController testRestController;

	@Test
    public void testVote() throws InterruptedException {
		ExtendedModelMap extendedModelMap = new ExtendedModelMap();
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.put("vote", Collections.singletonList("a"));
		MockHttpServletResponse response = new MockHttpServletResponse();
		String userId = UUID.randomUUID().toString().substring(0, 10);
		voteController.vote(extendedModelMap, formData, userId , response);

		assertThat(response.getCookies().length).isEqualTo(1);

		Thread.sleep(3000);

		List<CloudEvent<Vote>> events = testRestController.getEvents();

		assertThat(!events.isEmpty()).isTrue();
		assertThat(events.size()).isEqualTo(1);

		formData.put("vote", Collections.singletonList("b"));
		voteController.vote(extendedModelMap, formData, userId, response);

		Thread.sleep(3000);
		
		events = testRestController.getEvents();

		assertThat(!events.isEmpty()).isTrue();
		assertThat(events.size()).isEqualTo(2);
	}

}
