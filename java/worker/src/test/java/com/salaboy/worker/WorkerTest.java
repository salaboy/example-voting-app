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
        Vote voteA1 = resultsKeyValueTemplate.insert(new Vote("vote", "123-1231", "a", "123-1231" ));
        assertThat(voteA1).isNotNull();
        System.out.println("Voted A1");
        Vote voteA2 = resultsKeyValueTemplate.insert(new Vote("vote", "123-1232", "a", "123-1232" ));
        assertThat(voteA2).isNotNull();
        System.out.println("Voted A2");
        Vote voteA3 = resultsKeyValueTemplate.insert(new Vote("vote", "123-1233", "a", "123-1233" ));
        assertThat(voteA3).isNotNull();
        System.out.println("Voted A3");
        Vote voteA4 = resultsKeyValueTemplate.insert(new Vote("vote", "123-1234", "a", "123-1234" ));
        assertThat(voteA4).isNotNull();
        System.out.println("Voted A4");
        Vote voteA5 = resultsKeyValueTemplate.insert(new Vote("vote", "123-1235", "a", "123-1235" ));
        assertThat(voteA5).isNotNull();
        System.out.println("Voted A5");
        Vote voteA6 = resultsKeyValueTemplate.insert(new Vote("vote", "123-1236", "a", "123-1236" ));
        assertThat(voteA6).isNotNull();
        System.out.println("Voted A6");
        Vote voteA7 = resultsKeyValueTemplate.insert(new Vote("vote", "123-1237", "a", "123-1237" ));
        assertThat(voteA7).isNotNull();
        System.out.println("Voted A7");
        Vote voteA8 = resultsKeyValueTemplate.insert(new Vote("vote", "123-1238", "a", "123-1238" ));
        assertThat(voteA8).isNotNull();
        System.out.println("Voted A8");
        Vote voteA9 = resultsKeyValueTemplate.insert(new Vote("vote", "123-1239", "a", "123-1239" ));
        assertThat(voteA9).isNotNull();
        System.out.println("Voted A9");
        Vote voteA10 = resultsKeyValueTemplate.insert(new Vote("vote", "123-12310", "a", "123-12310" ));
        assertThat(voteA10).isNotNull();
        System.out.println("Voted A10");
        Vote voteA11 = resultsKeyValueTemplate.insert(new Vote("vote", "123-12311", "a", "123-12311" ));
        assertThat(voteA11).isNotNull();
        System.out.println("Voted A11");
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
        assertThat(results.getOptionA()).isEqualTo(12);
        assertThat(results.getOptionB()).isEqualTo(2);
    }

}
