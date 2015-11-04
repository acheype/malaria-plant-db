package nc.ird.malariaplantdb.repository.search;

import nc.ird.malariaplantdb.domain.Species;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Species entity.
 */
public interface SpeciesSearchRepository extends ElasticsearchRepository<Species, Long> {
}
