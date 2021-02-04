package pl.dmcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import pl.dmcs.enums.RequestType;
import pl.dmcs.helper.ElasticSearchHelper;
import pl.dmcs.model.Book;
import pl.dmcs.request.BookRequest;
import pl.dmcs.server.EchoMultiServer;
import pl.dmcs.service.BookSearchService;

import java.io.IOException;
import java.util.List;

public class ServerApp {

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore","C:\\Program Files\\Java\\jre1.8.0_271\\bin\\examplestore");
        System.setProperty("javax.net.ssl.keyStorePassword","password");

        try {
            new EchoMultiServer().start(1);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
//        RestHighLevelClient restHighLevelClient = ElasticSearchHelper.connectToElasticSearch();
//        List<Book> list = Book.randomBooksList(20);
//        ObjectMapper objectMapper = new ObjectMapper();
//        for (Book book : list) {
//            ElasticSearchHelper.addFileToElasticSearch(restHighLevelClient,objectMapper.writeValueAsString(book),"book");
//        }
//        restHighLevelClient.close();

    }

}
