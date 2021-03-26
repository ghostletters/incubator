package org.acme.getting.started.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HighLevelClient {

    RestHighLevelClient client;

    public void talkToElastic() {
        RestHighLevelClient client = getClient();
    }

    protected RestHighLevelClient getClient() {
        if (client == null) {
            client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost("localhost", 9200, "http"),
                            new HttpHost("localhost", 9201, "http")));
        }
        return client;
    }
}
