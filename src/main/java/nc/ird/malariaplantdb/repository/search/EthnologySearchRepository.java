package nc.ird.malariaplantdb.repository.search;

import nc.ird.malariaplantdb.domain.Ethnology;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Ethnology entity.
 */
public interface EthnologySearchRepository extends ElasticsearchRepository<Ethnology, Long> {
}
