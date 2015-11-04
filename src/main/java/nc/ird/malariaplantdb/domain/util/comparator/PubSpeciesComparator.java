package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.PubSpecies;

import java.util.Comparator;

/**
 * Comparator for the PubSpecies entities
 *
 * @author acheype
 */
public class PubSpeciesComparator implements Comparator<PubSpecies> {

    private final static Comparator<PubSpecies> BY_SPECIES = (ps1, ps2) ->
        new SpeciesComparator().compare(ps1.getSpecies(), ps2.getSpecies());

    private final static Comparator<PubSpecies> BY_SPECIES_AND_ID = BY_SPECIES.thenComparing(PubSpecies::getId);

    @Override
    public int compare(PubSpecies ps1, PubSpecies ps2) {
        return ps1.equals(ps2) ? 0 : BY_SPECIES_AND_ID.compare(ps1, ps2);
    }
}
