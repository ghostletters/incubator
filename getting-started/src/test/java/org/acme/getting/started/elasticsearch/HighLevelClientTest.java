package org.acme.getting.started.elasticsearch;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchAction;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

class HighLevelClientTest {

    @Test
    void testElasticWrite() throws IOException {
        HighLevelClient highLevelClient = new HighLevelClient();
        RestHighLevelClient client = highLevelClient.getClient();

        IndexRequest indexRequest = new IndexRequest("posts")
                .id("1")
                .source("user", "kimchy",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");

        IndexResponse indexResponse = client.index(indexRequest, DEFAULT);

        String index = indexResponse.getIndex();
        String id = indexResponse.getId();
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("index created");
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("index updated");
        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("shard stuff going on. Bad?!?");
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.println("Failure: " + reason);
            }
        }
    }

    @Test
    void testElasticRead() throws IOException {
        HighLevelClient highLevelClient = new HighLevelClient();
        RestHighLevelClient client = highLevelClient.getClient();

        GetRequest getRequest = new GetRequest(
                "posts",
                "1");

        GetResponse getResponse = client.get(getRequest, DEFAULT);

        String index = getResponse.getIndex();
        String id = getResponse.getId();
        if (getResponse.isExists()) {
            long version = getResponse.getVersion();
            String sourceAsString = getResponse.getSourceAsString();
            System.out.println("Response: " + sourceAsString);
        } else {
            System.out.println("Nothing found.");
        }
    }

    @Test
    void testElasticQuery() throws IOException {
        HighLevelClient highLevelClient = new HighLevelClient();
        RestHighLevelClient client = highLevelClient.getClient();

        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
                .fuzziness(Fuzziness.AUTO)
                .prefixLength(3)
                .maxExpansions(10);

        SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource()
                .query(matchQueryBuilder)
                .from(0)
                .size(5)
                .timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("posts");
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, DEFAULT);

        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            float score = hit.getScore();
            String result = hit.getSourceAsString();
            System.out.printf("Search result (%s): %s%n", score, result);
        }
    }
}