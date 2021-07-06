package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.IndustryCategoryModel;
import com.gls.quality.inspection.process.web.model.query.QueryIndustryCategoryModel;
import com.gls.quality.inspection.process.web.service.IndustryCategoryService;
import com.gls.quality.inspection.process.web.validator.IndustryCategoryValidator;
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
@RequestMapping("/industryCategory")
public class IndustryCategoryController {
    @Resource
    private IndustryCategoryService industryCategoryService;
    @Resource
    private IndustryCategoryValidator industryCategoryValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(industryCategoryValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<IndustryCategoryModel>> query(QueryIndustryCategoryModel queryIndustryCategoryModel, Pageable pageable) {
        return new Result<>(industryCategoryService.getPage(queryIndustryCategoryModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<IndustryCategoryModel> getInfo(@PathVariable Long id) {
        return new Result<>(industryCategoryService.getById(id));
    }

    @PostMapping
    public Result<IndustryCategoryModel> create(@Valid @RequestBody IndustryCategoryModel industryCategoryModel) {
        industryCategoryService.add(industryCategoryModel);
        return new Result<>(industryCategoryService.getById(industryCategoryModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<IndustryCategoryModel> update(@Valid @RequestBody IndustryCategoryModel industryCategoryModel) {
        industryCategoryService.update(industryCategoryModel);
        return new Result<>(industryCategoryService.getById(industryCategoryModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        industryCategoryService.remove(id);
        return Result.SUCCESS;
    }
}
