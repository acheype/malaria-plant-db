package nc.ird.malariaplantdb.service.xls.fillers;

import nc.ird.malariaplantdb.repository.PublicationRepository;
import nc.ird.malariaplantdb.service.xls.structures.PropVals;
import nc.ird.malariaplantdb.service.xls.transformers.StringNormalizer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Find from the database an publication entity by its title, then set it to the entity outputProperty.
 *
 * @author acheype
 */
@Service
public class PublicationDbEntityRefFiller extends DbEntityRefFiller implements ApplicationContextAware {

    // by implementing ApplicationContextAware, the Spring application context is supplied
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    protected List<Object> findValuesInDB(PropVals propValsSearched){

        // a better way is to get the repo from the Spring container is to use a factory which lets Spring instantiate
        // the filler, but the drawback is that the user must pass the factory to the import module
        Map<String, PublicationRepository> publiRepos = applicationContext.getBeansOfType(PublicationRepository.class);

        PublicationRepository publiRepo = publiRepos.entrySet().iterator().next().getValue();
        return new ArrayList<>(
                publiRepo.findByTitleIgnoreCase(
                        StringNormalizer.getInstance().transform(propValsSearched.get(propValsSearched.keySet()
                                .iterator().next()).toString())
                )
        );
    }

}
