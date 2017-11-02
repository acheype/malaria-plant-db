package nc.ird.malariaplantdb.service.xls.fillers;

import nc.ird.malariaplantdb.repository.CompilerRepository;
import nc.ird.malariaplantdb.service.xls.structures.PropVals;
import nc.ird.malariaplantdb.service.xls.transformers.StringNormalizer;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Find in the database the{@code Compiler} whose given and family are in a{@code PropVals} object.
 * The{@code Compiler} will be searched through a repository accessed by the Spring container.
 */
@Service
public class CompilersDbEntityRefFiller extends DbEntityRefFiller implements ApplicationContextAware {

    // by implementing ApplicationContextAware, the Spring application context is supplied
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    protected List<Object> findValuesInDB(PropVals propValsSearched){

        Transformer transformer = new StringNormalizer();
        String family = (String) transformer.transform(propValsSearched.get("compilers.family"));
        String given = (String) transformer.transform(propValsSearched.get("compilers.given"));

        // a better way is to get the repo from the Spring container is to use a factory which lets Spring instantiate
        // the filler, but the drawback is that the user must pass the factory to the import module
        Map<String, CompilerRepository> compilerRepos = applicationContext.getBeansOfType(CompilerRepository.class);

        CompilerRepository compilerRepository = compilerRepos.entrySet().iterator().next().getValue();

        return new ArrayList<>(compilerRepository.findByFamilyAndGivenAllIgnoreCase(family, given));
    }
}
