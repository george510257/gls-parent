package ${basePackageUrl}.controller;
import ${basePackageUrl}.model.${entityName}Model;
import ${basePackageUrl}.model.query.Query${entityName}Model;
import ${basePackageUrl}.service.${entityName}Service;
import ${basePackageUrl}.validator.${entityName}Validator;
import com.gls.starter.data.jpa.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/${entityNameParam}")
public class ${entityName}Controller extends BaseController<${entityName}Service, ${entityName}Model, Query${entityName}Model> {
    public ${entityName}Controller(${entityName}Service service, ${entityName}Validator validator) {
        super(service, validator);
    }
}
