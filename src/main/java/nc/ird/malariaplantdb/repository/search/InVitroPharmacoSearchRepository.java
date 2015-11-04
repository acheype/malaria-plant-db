package nc.ird.malariaplantdb.repository.search;

import nc.ird.malariaplantdb.domain.InVitroPharmaco;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InVitroPharmaco entity.
 */
public interface InVitroPharmacoSearchRepository extends ElasticsearchRepository<InVitroPharmaco, Long> {
}
