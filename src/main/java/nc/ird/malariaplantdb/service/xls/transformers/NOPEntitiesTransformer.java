package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.service.xls.structures.ClassMap;

/**
 * Entities transformer which doesn't modify the entities map.
 */
public class NOPEntitiesTransformer extends EntitiesTransformer {

    @Override
    public void transformEntities(ClassMap entitiesMap) {
        // no processing
    }
}
