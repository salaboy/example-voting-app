package com.salaboy.worker;

import com.redis.testcontainers.RedisContainer;
import io.diagrid.dapr.DaprContainer;
import io.diagrid.dapr.QuotedBoolean;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Testcontainers
public abstract class BaseIntegrationTest {

    public static Network daprNetwork = Network.newNetwork();

    @Container
    public static RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis/redis-stack"))
            .withNetworkAliases("redis")
            .withNetwork(daprNetwork);

    @Container
    public static DaprContainer daprContainer = new DaprContainer("daprio/daprd:1.13.2")
            .withAppName("local-dapr-app")
            .withNetwork(daprNetwork)
            .withComponent(new DaprContainer.Component("kvstore", "state.redis", getStateStoreProperties()))
            .withComponent(new DaprContainer.Component("pubsub", "pubsub.in-memory", Collections.emptyMap() ))
            .withAppPort(8080)
            .withDaprLogLevel(DaprContainer.DaprLogLevel.debug)
            .withAppChannelAddress("host.testcontainers.internal");

    private static Map<String, Object> getStateStoreProperties() {
        Map<String, Object> stateStoreProperties = new HashMap<String, Object>();
        stateStoreProperties.put("keyPrefix", "name");
        stateStoreProperties.put("actorStateStore", new QuotedBoolean("true"));
        stateStoreProperties.put("redisHost", "redis:6379");
        stateStoreProperties.put("redisPassword", "");
        stateStoreProperties.put("queryIndexes", "[{\"name\": \"VotesQueryIndex\",\"indexes\": [{\"key\": \"type\",\"type\": \"TEXT\"}]}]");
        return stateStoreProperties;
    }

    @BeforeAll
    static void beforeAll() {
        org.testcontainers.Testcontainers.exposeHostPorts(8080);
        System.setProperty("dapr.grpc.port", Integer.toString(daprContainer.getGRPCPort()));
        System.setProperty("dapr.http.port", Integer.toString(daprContainer.getHTTPPort()));
    }

}
