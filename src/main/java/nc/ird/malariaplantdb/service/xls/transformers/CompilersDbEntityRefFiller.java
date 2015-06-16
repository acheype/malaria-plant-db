package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.repositories.CompilerRepo;
import nc.ird.malariaplantdb.service.xls.exceptions.DbEntityNotFoundException;
import nc.ird.malariaplantdb.service.xls.exceptions.XlsEntityNotFoundException;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * If identifierValues is null, then set an empty compiler list.
 * <p>
 * The string format is the followed : each compiler name with the last name first, a coma
 * (,) then the given name. For several authors, each compiler complete name must be
 * separated by a slash (/). The format is space insensitive.
 * For instance, this string is correct : "Bourdy, Geneviève / Deharo, Eric"
 */
@Service
public class CompilersDbEntityRefFiller extends DbEntityRefFiller implements ApplicationContextAware {

    static final private String COMPILERS_REGEXP = "([a-zA-ZÀ-ÿ \\-]+),([a-zA-ZÀ-ÿ \\-]+)/?";

    // by implementing ApplicationContextAware, the Spring application context is supplied
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    protected List<Object> findValues(List<String> refIdentifierProperties,
                                      List<?> compilerNames) throws XlsEntityNotFoundException,
            DbEntityNotFoundException {

        // a better way is to get the repo from the Spring container is to use a factory which lets Spring instantiate
        // the filler, but the drawback is that the user must pass the factory to the import module
        Map<String, CompilerRepo> compilerRepos = applicationContext.getBeansOfType(CompilerRepo.class);
        assert (compilerRepos.size() == 1);
        CompilerRepo compilerRepo = compilerRepos.entrySet().iterator().next().getValue();

        return new ArrayList<>(
                compilerRepo.findByFamilyAndGivenAllIgnoreCase(compilerNames.get(0).toString(),
                        compilerNames.get(1).toString())
        );
    }

    @Override
    public void fillPropertyWithRef(List<String> refEntityIdentifierProperties, List<?> identifierValues,
                                    List<?> refEntities, Object entity, String outputProperty) throws
            IllegalAccessException, NoSuchMethodException, InvocationTargetException, XlsEntityNotFoundException,
            DbEntityNotFoundException {

        List<Object> compilersResult = new ArrayList<>();

        // as all the family and given names are in a single string, extract them
        String allNames = (String) identifierValues.get(0);

        if (allNames != null) {
            Pattern pattern = Pattern.compile(COMPILERS_REGEXP);
            Matcher matcher = pattern.matcher(allNames);

            while (matcher.find()) {
                String family = matcher.group(1);
                String given = matcher.group(2);

                List<Object> curCompilerResult = findValues(refEntityIdentifierProperties, Arrays.asList(family, given));

                assert (curCompilerResult.size() <= 1);

                if (curCompilerResult.isEmpty())
                    throw new DbEntityNotFoundException(
                            String.format("No entity 'Compiler' found with the properties {family, given} = {'%s', " +
                                            "'%s'}",
                                    family, given));
                else
                    compilersResult.add(curCompilerResult.get(0));
            }
            assert (compilersResult.size() >= 1);
        }

        PropertyUtils.setProperty(entity, outputProperty, compilersResult);
    }
}
