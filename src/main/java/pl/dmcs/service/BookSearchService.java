package pl.dmcs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import pl.dmcs.enums.ResponseStatus;
import pl.dmcs.helper.ElasticSearchHelper;
import pl.dmcs.model.Book;
import pl.dmcs.request.BookRequest;
import pl.dmcs.response.BookResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookSearchService {

    private final ObjectMapper mapper = new ObjectMapper();

    public List<Book> getAllByBookRequest(BookRequest bookRequest) throws IOException {
        RestHighLevelClient client = ElasticSearchHelper.connectToElasticSearch();

        QueryBuilder matchSpecificFieldQuery = QueryBuilders.matchPhrasePrefixQuery(
                bookRequest.getSearchBy().toString(), bookRequest.getSearchPhrase());

        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(matchSpecificFieldQuery)
                .size(10);

        SearchResponse searchResponse = ElasticSearchHelper.searchFromElasticSearch(client, builder);

        List<Book> books = new ArrayList<>();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Book book = mapper.readValue(searchHit.getSourceAsString(), Book.class);
            books.add(book);
        }

        return books;
    }
    public BookResponse searchedBooks(BookRequest bookRequest) throws IOException {
        return new BookResponse(ResponseStatus.OK,null,getAllByBookRequest(bookRequest));
    }
}
