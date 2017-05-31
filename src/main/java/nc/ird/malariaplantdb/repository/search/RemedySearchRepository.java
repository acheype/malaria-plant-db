package nc.ird.malariaplantdb.repository.search;

import nc.ird.malariaplantdb.domain.Remedy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Remedy entity.
 */
public interface RemedySearchRepository extends ElasticsearchRepository<Remedy, Long> {
}
