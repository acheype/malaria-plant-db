package nc.ird.malariaplantdb.repository.search;

import nc.ird.malariaplantdb.domain.PubSpecies;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PubSpecies entity.
 */
public interface PubSpeciesSearchRepository extends ElasticsearchRepository<PubSpecies, Long> {
}
