package nc.ird.malariaplantdb.service.xls.validators;

import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;
import org.apache.commons.beanutils.PropertyUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * <p>If a criteria property has its value in the range of a set of criteria values,
 * check if a tested property empty or not (must be empty or null if the property {@code isEmpty}' is true,
 * must be not empty if false). The tested property can be a string, a collection,
 * a map or an array).</p>
 * <p>
 * <p>If the criteria is not fulfilled, the validators will succeed.</p>
 *
 * @author acheype
 */
public class EmptyOrNotIfPropertyValueValidator implements ConstraintValidator<EmptyOrNotIfPropertyValue, Object> {

    /**
     * The property name for which its value will be compared
     */
    private String criteriaProperty;

    /**
     * The values to compare to the criteria properties.
     */
    private String[] criteriaValues;

    /**
     * The property which will be tested
     */
    private String testedProperty;

    /**
     * Define if the tested property must be empty or not
     */
    private boolean isEmpty;

    /**
     * The error message;
     */
    String message;

    @Override
    public void initialize(EmptyOrNotIfPropertyValue constraintAnnotation) {
        criteriaProperty = constraintAnnotation.criteriaProperty();
        criteriaValues = constraintAnnotation.criteriaValues();
        testedProperty = constraintAnnotation.testedProperty();
        isEmpty = constraintAnnotation.isEmpty();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Boolean result;

        Object criteriaVal;
        Object testedVal;

        try {
            criteriaVal = PropertyUtils.getProperty(value, criteriaProperty);
            testedVal = PropertyUtils.getProperty(value, testedProperty);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ImportRuntimeException("An error occurs during the bean validation. Please check " +
                    "the parameters of the corresponding annotation.", e);
        }

        if (criteriaVal != null && !criteriaVal.toString().isEmpty()) {
            boolean criteriaOK = Arrays.asList(criteriaValues).contains(criteriaVal.toString());
            if (!criteriaOK)
                result = true;
            else
                result = isEmpty ? isEmpty(testedVal) : !isEmpty(testedVal);
        } else
            result = true;

        // binds the error message to the tested property
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addPropertyNode(testedProperty)
                .addConstraintViolation();

        return result;
    }

    private boolean isEmpty(Object value) {
        if (value == null) return true;
        if (value.getClass().isArray()) {
            return Array.getLength(value) == 0;
        } else if (value instanceof Collection) {
            return ((Collection) value).size() == 0;
        } else if (value instanceof Map) {
            return ((Map) value).size() == 0;
        } else {
            return ((String) value).length() == 0;
        }
    }

}
