package com.salaboy.result;

import com.salaboy.model.Results;
import io.diagrid.spring.core.keyvalue.DaprKeyValueTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "dapr.statestore.query-index=VotesQueryIndex",
})
public class ResultTest extends BaseIntegrationTest {
    
    @Autowired
    DaprKeyValueTemplate keyValueTemplate;

    @SpyBean
    FetchResultsJob fetchResultsJob;

    @Test
    public void testResultJob() throws InterruptedException {
        Results results = new Results(3, 5);
        keyValueTemplate.insert("results", results);
     
        Thread.sleep(5000);

        await()
          .atMost(Duration.ofSeconds(5))
          .untilAsserted(() -> verify(fetchResultsJob, atLeast(2)).fetchResults());

        Thread.sleep(5000);
    }

}
