package ${basePackageUrl}.service.impl;
import ${basePackageUrl}.converter.${entityName}Converter;
import ${basePackageUrl}.entity.${entityName}Entity;
import ${basePackageUrl}.model.${entityName}Model;
import ${basePackageUrl}.model.query.Query${entityName}Model;
import ${basePackageUrl}.repository.${entityName}Repository;
import ${basePackageUrl}.service.${entityName}Service;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author george
 */
@Service
public class ${entityName}ServiceImpl
        extends BaseServiceImpl<${entityName}Repository, ${entityName}Converter, ${entityName}Entity, ${entityName}Model, Query${entityName}Model>
        implements ${entityName}Service {
    public ${entityName}ServiceImpl(${entityName}Repository repository, ${entityName}Converter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<${entityName}Entity> getSpec(Query${entityName}Model query${entityName}Model) {
        // todo
        return null;
    }
}
