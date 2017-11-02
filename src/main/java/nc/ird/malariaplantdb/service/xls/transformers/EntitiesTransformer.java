package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.service.xls.structures.ClassMap;

/**
 * Abstract class for transformers which modifies the entities after a loading or a filling on a property.
 */
public abstract class EntitiesTransformer {

    /**
     * Process executed on the entities. It could be a transformation of some properties or an object referencing
     * in the entities map.
     *
     * @param entitiesMap the entities map to process
     */
    public abstract void transformEntities(ClassMap entitiesMap);

}
