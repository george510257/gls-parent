package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.SectionAnnotatedModel;
import com.gls.quality.inspection.process.web.model.query.QuerySectionAnnotatedModel;
import com.gls.quality.inspection.process.web.service.SectionAnnotatedService;
import com.gls.quality.inspection.process.web.validator.SectionAnnotatedValidator;
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
@RequestMapping("/sectionAnnotated")
public class SectionAnnotatedController {
    @Resource
    private SectionAnnotatedService sectionAnnotatedService;
    @Resource
    private SectionAnnotatedValidator sectionAnnotatedValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(sectionAnnotatedValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<SectionAnnotatedModel>> query(QuerySectionAnnotatedModel querySectionAnnotatedModel, Pageable pageable) {
        return new Result<>(sectionAnnotatedService.getPage(querySectionAnnotatedModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<SectionAnnotatedModel> getInfo(@PathVariable Long id) {
        return new Result<>(sectionAnnotatedService.getById(id));
    }

    @PostMapping
    public Result<SectionAnnotatedModel> create(@Valid @RequestBody SectionAnnotatedModel sectionAnnotatedModel) {
        sectionAnnotatedService.add(sectionAnnotatedModel);
        return new Result<>(sectionAnnotatedService.getById(sectionAnnotatedModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<SectionAnnotatedModel> update(@Valid @RequestBody SectionAnnotatedModel sectionAnnotatedModel) {
        sectionAnnotatedService.update(sectionAnnotatedModel);
        return new Result<>(sectionAnnotatedService.getById(sectionAnnotatedModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        sectionAnnotatedService.remove(id);
        return Result.SUCCESS;
    }
}
