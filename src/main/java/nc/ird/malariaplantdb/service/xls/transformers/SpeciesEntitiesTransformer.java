package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.domain.PubSpecies;
import nc.ird.malariaplantdb.domain.Species;
import nc.ird.malariaplantdb.repository.SpeciesRepository;
import nc.ird.malariaplantdb.service.xls.structures.ClassMap;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Special process to reference in the entities Map (species are parts of PubSpecies but need to be referenced by
 * other entities). If the species already exists in the database, the reference will be used.
 */
@Service
public class SpeciesEntitiesTransformer extends EntitiesTransformer implements ApplicationContextAware {

    // by implementing ApplicationContextAware, the Spring application context is supplied
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void transformEntities(ClassMap entitiesMap) {

        List<PubSpecies> pubSpeciesEntities = entitiesMap.getList(PubSpecies.class);

        TreeSet<Species> speciesSet = new TreeSet<>();
        for (PubSpecies pubSp : pubSpeciesEntities){

            Map<String, SpeciesRepository> speciesRepos = applicationContext.getBeansOfType(SpeciesRepository.class);
            SpeciesRepository speciesRepo = speciesRepos.entrySet().iterator().next().getValue();

            Optional<Species> alreadyDefinedSp = speciesRepo.findByFamilyAndSpeciesAllIgnoreCase(pubSp.getSpecies()
                .getFamily(), pubSp.getSpecies().getSpecies());
            if (alreadyDefinedSp.isPresent())
                pubSp.setSpecies(alreadyDefinedSp.get());

            speciesSet.add(pubSp.getSpecies());
        }
        entitiesMap.putList(Species.class, new ArrayList<Species>(speciesSet));
    }
}
