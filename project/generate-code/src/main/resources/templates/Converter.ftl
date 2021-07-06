package ${basePackageUrl}.converter;
import com.gls.framework.core.base.BaseConverter;
import ${basePackageUrl}.entity.${entityName}Entity;
import ${basePackageUrl}.model.${entityName}Model;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
* @author george
*/
@Component
public class ${entityName}Converter implements BaseConverter<${entityName}Entity, ${entityName}Model> {
    @Override
    public ${entityName}Model copySourceToTarget(${entityName}Entity entity, ${entityName}Model model) {
        return model;
    }

    @Override
    public ${entityName}Entity copyTargetToSource(${entityName}Model model, ${entityName}Entity entity) {
        return entity;
    }
}
