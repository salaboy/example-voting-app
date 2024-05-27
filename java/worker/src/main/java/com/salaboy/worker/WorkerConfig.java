package com.salaboy.worker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.dapr.client.DaprClientBuilder;
import io.diagrid.spring.boot.autoconfigure.statestore.DaprStateStoreProperties;
import io.diagrid.spring.core.keyvalue.DaprKeyValueAdapter;
import io.diagrid.spring.core.keyvalue.DaprKeyValueTemplate;

@Configuration
public class WorkerConfig {

    @Value("${worker.vote.statestore:kvstore}")
	private String voteStateStore;

	@Value("${worker.vote.queryIndex:VotesQueryIndex}")
	private String voteQueryIndex;

	@Value("${worker.results.statestore:kvstore}")
	private String resultsStateStore;

    @Bean
    public DaprKeyValueAdapter voteKeyValueAdapter(DaprClientBuilder daprClientBuilder, DaprStateStoreProperties daprStateStoreProperties) {
        return new DaprKeyValueAdapter(daprClientBuilder.build(), daprClientBuilder.buildPreviewClient(), voteStateStore, voteQueryIndex);
    }

    @Bean
    public DaprKeyValueTemplate voteKeyValueTemplate(DaprKeyValueAdapter voteKeyValueAdapter) {
        return new DaprKeyValueTemplate(voteKeyValueAdapter);
    }

	@Bean
    public DaprKeyValueAdapter resultsKeyValueAdapter(DaprClientBuilder daprClientBuilder, DaprStateStoreProperties daprStateStoreProperties) {
        return new DaprKeyValueAdapter(daprClientBuilder.build(), daprClientBuilder.buildPreviewClient(), resultsStateStore, voteQueryIndex);
    }

    @Bean
    public DaprKeyValueTemplate resultsKeyValueTemplate(DaprKeyValueAdapter resultsKeyValueAdapter) {
        return new DaprKeyValueTemplate(resultsKeyValueAdapter);
    }
}
