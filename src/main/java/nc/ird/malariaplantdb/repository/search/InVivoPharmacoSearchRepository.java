package nc.ird.malariaplantdb.repository.search;

import nc.ird.malariaplantdb.domain.InVivoPharmaco;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InVivoPharmaco entity.
 */
public interface InVivoPharmacoSearchRepository extends ElasticsearchRepository<InVivoPharmaco, Long> {
}
