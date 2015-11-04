package nc.ird.malariaplantdb.repository.search;

import nc.ird.malariaplantdb.domain.PlantIngredient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PlantIngredient entity.
 */
public interface PlantIngredientSearchRepository extends ElasticsearchRepository<PlantIngredient, Long> {
}
