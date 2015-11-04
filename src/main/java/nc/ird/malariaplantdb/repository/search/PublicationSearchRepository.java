package nc.ird.malariaplantdb.repository.search;

import nc.ird.malariaplantdb.domain.Publication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Publication entity.
 */
public interface PublicationSearchRepository extends ElasticsearchRepository<Publication, Long> {
}
