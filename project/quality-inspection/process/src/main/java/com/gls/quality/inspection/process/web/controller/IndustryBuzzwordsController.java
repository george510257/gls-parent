package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.IndustryBuzzwordsModel;
import com.gls.quality.inspection.process.web.model.query.QueryIndustryBuzzwordsModel;
import com.gls.quality.inspection.process.web.service.IndustryBuzzwordsService;
import com.gls.quality.inspection.process.web.validator.IndustryBuzzwordsValidator;
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
@RequestMapping("/industryBuzzwords")
public class IndustryBuzzwordsController {
    @Resource
    private IndustryBuzzwordsService industryBuzzwordsService;
    @Resource
    private IndustryBuzzwordsValidator industryBuzzwordsValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(industryBuzzwordsValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<IndustryBuzzwordsModel>> query(QueryIndustryBuzzwordsModel queryIndustryBuzzwordsModel, Pageable pageable) {
        return new Result<>(industryBuzzwordsService.getPage(queryIndustryBuzzwordsModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<IndustryBuzzwordsModel> getInfo(@PathVariable Long id) {
        return new Result<>(industryBuzzwordsService.getById(id));
    }

    @PostMapping
    public Result<IndustryBuzzwordsModel> create(@Valid @RequestBody IndustryBuzzwordsModel industryBuzzwordsModel) {
        industryBuzzwordsService.add(industryBuzzwordsModel);
        return new Result<>(industryBuzzwordsService.getById(industryBuzzwordsModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<IndustryBuzzwordsModel> update(@Valid @RequestBody IndustryBuzzwordsModel industryBuzzwordsModel) {
        industryBuzzwordsService.update(industryBuzzwordsModel);
        return new Result<>(industryBuzzwordsService.getById(industryBuzzwordsModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        industryBuzzwordsService.remove(id);
        return Result.SUCCESS;
    }
}
