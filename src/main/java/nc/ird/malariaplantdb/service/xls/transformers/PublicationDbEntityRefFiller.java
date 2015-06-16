package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.repositories.PublicationRepo;
import nc.ird.malariaplantdb.service.xls.exceptions.DbEntityNotFoundException;
import nc.ird.malariaplantdb.service.xls.exceptions.XlsEntityNotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Find from the database an entity by its title, then set it to the corresponding final entity property
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
    protected List<Object> findValues(List<String> refIdentifierProperties,
                                      List<?> identifierValues) throws XlsEntityNotFoundException,
            DbEntityNotFoundException {

        // a better way is to get the repo from the Spring container is to use a factory which lets Spring instantiate
        // the filler, but the drawback is that the user must pass the factory to the import module
        Map<String, PublicationRepo> publiRepos = applicationContext.getBeansOfType(PublicationRepo.class);
        assert (publiRepos.size() == 1);
        PublicationRepo publiRepo = publiRepos.entrySet().iterator().next().getValue();

        return new ArrayList<>(
                publiRepo.findByTitleIgnoreCase(
                        TrimStringTransformer.getInstance().transform(identifierValues.get(0).toString())
                )
        );
    }

}
