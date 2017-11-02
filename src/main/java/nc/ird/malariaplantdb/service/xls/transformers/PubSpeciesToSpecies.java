package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.domain.PubSpecies;
import org.apache.commons.collections.Transformer;

/**
 * Transformer which take a{@code PubSpecies} and return a{@code Species}
 */
public class PubSpeciesToSpecies implements Transformer {

    @Override
    public Object transform(Object input) {
        if (!(input instanceof PubSpecies)) {
            throw new IllegalArgumentException(String.format("The PubSpeciesToSpecies transformer need a " +
                "PubSpecies object as input. It received this object instead : '%s'", input));
        } else {
            PubSpecies pubSpecies = (PubSpecies) input;
            return pubSpecies.getSpecies();
        }
    }
}
