package ${basePackageUrl}.validator;
import ${basePackageUrl}.model.${entityName}Model;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ${entityName}Validator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ${entityName}Model.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ${entityName}Model model = (${entityName}Model) target;
        // todo
    }
}
