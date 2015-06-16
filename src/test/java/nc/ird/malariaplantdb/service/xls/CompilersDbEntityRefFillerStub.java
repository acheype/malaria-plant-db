package nc.ird.malariaplantdb.service.xls;

import nc.ird.malariaplantdb.entities.Compiler;
import nc.ird.malariaplantdb.service.xls.exceptions.XlsEntityNotFoundException;
import nc.ird.malariaplantdb.service.xls.transformers.CompilersDbEntityRefFiller;

import java.util.Arrays;
import java.util.List;

/**
 * Stub for CompilersDbEntityRefFiller class
 *
 * @author acheype
 */
public class CompilersDbEntityRefFillerStub extends CompilersDbEntityRefFiller {

    @Override
    protected List<Object> findValues(List<String> refIdentifierProperties, List<?> compilerNames) throws XlsEntityNotFoundException {
        Compiler c = new Compiler();
        c.setFamily("SameFamily");
        c.setGiven("SameGiven");
        return Arrays.asList(c);
    }
}
