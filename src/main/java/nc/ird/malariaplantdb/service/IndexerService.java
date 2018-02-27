package nc.ird.malariaplantdb.service;

import nc.ird.malariaplantdb.domain.*;
import nc.ird.malariaplantdb.domain.Compiler;
import nc.ird.malariaplantdb.repository.CompilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexerService {

    private static final Class[] CLASSES_TO_INDEX = {Author.class, Compiler.class, Ethnology.class,
        InVivoPharmaco.class, InVitroPharmaco.class, PlantIngredient.class, Publication.class, PubSpecies.class,
        Remedy.class, Species.class, User.class};

    @Autowired
    CompilerRepository compilerRepository;

    private  static  final  String INDEX_NAME = "compiler";
    public static final double INDEX_COMMIT_SIZE = 100;

    // https://stackoverflow.com/questions/37312415/batch-indexing-spring-data-jpa-entries-to-elastic-through-spring-data-elasticsea

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public long bulkIndex() throws Exception {
        int counter = 0;
        try {
            if (elasticsearchTemplate.indexExists("compiler")) {
                elasticsearchTemplate.deleteIndex("compiler");
            }

            elasticsearchTemplate.createIndex("compiler");

            List<Compiler> compilers = compilerRepository.findAll();
            List<IndexQuery> queries = new ArrayList<IndexQuery>();

            for (Compiler compiler : compilers) {
                IndexQuery indexQuery = new IndexQuery();
//                indexQuery.setId(car.getId().toString());
//                indexQuery.setSource(gson.toJson(car));
                indexQuery.setIndexName("compiler");
                queries.add(indexQuery);
                if (counter % INDEX_COMMIT_SIZE == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                    System.out.println("bulkIndex counter : " + counter);
                }
                counter++;
            }
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
