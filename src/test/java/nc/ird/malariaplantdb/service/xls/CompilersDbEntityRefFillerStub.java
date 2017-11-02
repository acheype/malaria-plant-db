package nc.ird.malariaplantdb.service.xls;

import nc.ird.malariaplantdb.domain.Compiler;
import nc.ird.malariaplantdb.service.xls.fillers.DbEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.structures.PropVals;

import java.util.Arrays;
import java.util.List;

/**
 * Stub for CompilersDbEntityRefFiller class
 *
 * @author acheype
 */
public class CompilersDbEntityRefFillerStub extends DbEntityRefFiller {

    private long count = 1;

    @Override
    protected List<Object> findValuesInDB(PropVals propValsSearched) {
        Compiler c = new Compiler();
        c.setId(count);
        c.setFamily("SameFamily" + count);
        c.setGiven("SameGiven" + count);
        count++;
        return Arrays.asList(c);
    }
}
