package com.salaboy.worker;

import com.salaboy.model.Results;
import com.salaboy.model.Vote;
import io.diagrid.spring.core.keyvalue.DaprKeyValueTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "worker.vote.queryIndex=VotesQueryIndex"
})
public class WorkerTest extends BaseIntegrationTest {
    
    @Autowired
    DaprKeyValueTemplate voteKeyValueTemplate;

    @Autowired
    DaprKeyValueTemplate resultsKeyValueTemplate;

    @SpyBean
    WorkerJob workerJob;

    @Test
    public void testWorker() throws InterruptedException {
        Vote voteA = resultsKeyValueTemplate.insert(new Vote("vote", "123-123", "a", "123-123" ));
        assertThat(voteA).isNotNull();
        System.out.println("Voted A");
        Vote voteB = voteKeyValueTemplate.insert(new Vote("vote", "456-456", "b", "456-456"));
        assertThat(voteB).isNotNull();
        System.out.println("Voted B");

        await()
          .atMost(Duration.ofSeconds(5))
          .untilAsserted(() -> verify(workerJob, atLeast(3)).work());

        Vote voteB2 = voteKeyValueTemplate.insert(new Vote("vote", "789-789", "b", "789-789"));
        assertThat(voteB2).isNotNull();
        System.out.println("Voted B2");

        await()
          .atMost(Duration.ofSeconds(5))
          .untilAsserted(() -> verify(workerJob, atLeast(3)).work());
        
        Thread.sleep(5000);

		    Optional<Results> resultsOptional = resultsKeyValueTemplate.findById("results", Results.class);
        assertThat(resultsOptional.isPresent()).isTrue();

        Results results = resultsOptional.get();
        assertThat(results.getOptionA()).isEqualTo(1);
        assertThat(results.getOptionB()).isEqualTo(2);
    }

}
