package nc.ird.malariaplantdb.service.xls.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * <p>If a criteria property has its value in the range of a set of criteria values,
 * check if a tested property empty or not (must be empty or null if the property {@code isEmpty}' is true,
 * must be not empty if false). The tested property can be a string, a collection,
 * a map or an array).</p>
 *
 * <p>If the criteria is not fulfilled, the validation will succeed.</p>
 *
 * @author acheype
 */
@Documented
@Constraint(validatedBy = {EmptyOrNotIfPropertyValueValidator.class})
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmptyOrNotIfPropertyValue {

    String message() default "As the '{criteriaProperty}' property is in the '{criteriaValues}' range, " +
            "'{testedProperty}' property must fulfill the condition isNullorEmpty = {isEmpty}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return the property name for which its value will be compared
     */
    String criteriaProperty();

    /**
     * @return the values to compare to the criteria properties
     */
    String[] criteriaValues();

    /**
     * @return the property which will be tested
     */
    String testedProperty();

    /**
     * Define if the tested property must be empty or not
     */
    boolean isEmpty();

    /**
     * Defines several <code>@NotEmptyIfPropertyValue</code> annotations on the same element
     *
     * @see EmptyOrNotIfPropertyValue
     */
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        EmptyOrNotIfPropertyValue[] value();
    }

}
