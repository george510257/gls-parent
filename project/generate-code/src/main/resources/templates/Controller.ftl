package ${basePackageUrl}.controller;
import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import ${basePackageUrl}.model.${entityName}Model;
import ${basePackageUrl}.model.query.Query${entityName}Model;
import ${basePackageUrl}.service.${entityName}Service;
import ${basePackageUrl}.validator.${entityName}Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/${entityNameParam}")
public class ${entityName}Controller {
    @Resource
    private ${entityName}Service ${entityNameParam}Service;
    @Resource
    private ${entityName}Validator ${entityNameParam}Validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(${entityNameParam}Validator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<${entityName}Model>> query(Query${entityName}Model query${entityName}Model, Pageable pageable) {
        return new Result<>(${entityNameParam}Service.getPage(query${entityName}Model, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<${entityName}Model> getInfo(@PathVariable Long id) {
        return new Result<>(${entityNameParam}Service.getById(id));
    }

    @PostMapping
    public Result<${entityName}Model> create(@Valid @RequestBody ${entityName}Model ${entityNameParam}Model) {
        ${entityNameParam}Service.add(${entityNameParam}Model);
        return new Result<>(${entityNameParam}Service.getById(${entityNameParam}Model.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<${entityName}Model> update(@Valid @RequestBody ${entityName}Model ${entityNameParam}Model) {
        ${entityNameParam}Service.update(${entityNameParam}Model);
        return new Result<>(${entityNameParam}Service.getById(${entityNameParam}Model.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        ${entityNameParam}Service.remove(id);
        return Result.SUCCESS;
    }
}
