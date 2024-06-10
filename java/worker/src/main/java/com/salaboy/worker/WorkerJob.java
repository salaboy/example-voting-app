package com.salaboy.worker;

import com.salaboy.model.Results;
import com.salaboy.model.Vote;

import io.diagrid.spring.core.keyvalue.DaprKeyValueTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.keyvalue.core.query.KeyValueQuery;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WorkerJob {

	@Autowired
	@Qualifier("voteKeyValueTemplate")
	private DaprKeyValueTemplate voteKeyValueTemplate;
	
	@Autowired
	@Qualifier("resultsKeyValueTemplate")
	private DaprKeyValueTemplate resultsKeyValueTemplate;


	@Scheduled(fixedDelay = 1000)
	public void work() {
		System.out.println("Fetching votes..");

		//http ":3500/v1.0-alpha1/state/votes-statestore/query?metadata.contentType=application/json&metadata.queryIndexName=voteIndex" < query.json 
		KeyValueQuery<String> keyValueQuery = new KeyValueQuery<String>("'type' == 'vote'");
		keyValueQuery.setRows(1000);
		keyValueQuery.setOffset(0);
		Iterable<Vote> votes = voteKeyValueTemplate.find(keyValueQuery, Vote.class);

		int optionA = 0;
		int optionB = 0;
		for (Vote vote : votes) {
			if(vote.getOption().equals("a")){
				optionA++;
			}
			if(vote.getOption().equals("b")){
				optionB++;
			}
		}
		
		System.out.println("Storing results: a: "+ optionA + " - b: "+ optionB);
		// Count results and update using KeyValueTemplate
		Results results = new Results("results",optionA, optionB);
		resultsKeyValueTemplate.update(results);
	}

}
