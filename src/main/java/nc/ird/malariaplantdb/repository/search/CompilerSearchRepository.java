package nc.ird.malariaplantdb.repository.search;

import nc.ird.malariaplantdb.domain.Compiler;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Compiler entity.
 */
public interface CompilerSearchRepository extends ElasticsearchRepository<Compiler, Long> {
}
