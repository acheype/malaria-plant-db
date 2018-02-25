package nc.ird.malariaplantdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexerService {
    private  static  final  String INDEX_NAME = "compiler";
    public static final double INDEX_COMMIT_SIZE = 100;

    // https://stackoverflow.com/questions/37312415/batch-indexing-spring-data-jpa-entries-to-elastic-through-spring-data-elasticsea

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    public long bulkIndex() throws Exception {
        int counter = 0;
        try {
            if (!elasticsearchTemplate.indexExists(INDEX_NAME)) {

            } else {
                elasticsearchTemplate.deleteIndex(INDEX_NAME);
            }
            elasticsearchTemplate.createIndex(INDEX_NAME);

            List<IndexQuery> queries = new ArrayList<IndexQuery>();

//            for (Car car : cars) {
                IndexQuery indexQuery = new IndexQuery();
//                indexQuery.setId(car.getId().toString());
//                indexQuery.setSource(gson.toJson(car));
                indexQuery.setIndexName(INDEX_NAME);
                queries.add(indexQuery);
                if (counter % INDEX_COMMIT_SIZE == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                    System.out.println("bulkIndex counter : " + counter);
                }
                counter++;
//            }
            if (queries.size() > 0) {
                elasticsearchTemplate.bulkIndex(queries);
            }
            elasticsearchTemplate.refresh(INDEX_NAME, true);
            System.out.println("bulkIndex completed.");
        } catch (Exception e) {
            System.out.println("IndexerService.bulkIndex e;" + e.getMessage());
            throw e;
        }
        return -1;
    }

}
