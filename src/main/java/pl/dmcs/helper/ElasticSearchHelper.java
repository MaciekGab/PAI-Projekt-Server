package pl.dmcs.helper;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import pl.dmcs.enums.BookCategory;
import pl.dmcs.model.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ElasticSearchHelper {

    public static RestHighLevelClient connectToElasticSearch() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }

    public static SearchResponse searchFromElasticSearch(RestHighLevelClient client, SearchSourceBuilder searchSourceBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.searchType(SearchType.DEFAULT);
        searchRequest.source(searchSourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    public static SearchHit[] getAllSearchHits(RestHighLevelClient client) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        return response.getHits().getHits();
    }

    public static void addFileToElasticSearch(RestHighLevelClient client, String jsonObject, String indexName) throws IOException {
        IndexRequest request = new IndexRequest(indexName);
        request.source(jsonObject, XContentType.JSON);

        client.index(request, RequestOptions.DEFAULT);
    }

    public static void deleteAllFromElasticSearch(RestHighLevelClient client) throws IOException {
        for (SearchHit searchHit : getAllSearchHits(client)) {
            DeleteRequest deleteRequest = new DeleteRequest(searchHit.getIndex());
            deleteRequest.id(searchHit.getId());

            client.delete(deleteRequest, RequestOptions.DEFAULT);
        }
    }
}
