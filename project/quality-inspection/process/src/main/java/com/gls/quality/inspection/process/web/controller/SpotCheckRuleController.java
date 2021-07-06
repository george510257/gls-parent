package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.SpotCheckRuleModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpotCheckRuleModel;
import com.gls.quality.inspection.process.web.service.SpotCheckRuleService;
import com.gls.quality.inspection.process.web.validator.SpotCheckRuleValidator;
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
@RequestMapping("/spotCheckRule")
public class SpotCheckRuleController {
    @Resource
    private SpotCheckRuleService spotCheckRuleService;
    @Resource
    private SpotCheckRuleValidator spotCheckRuleValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(spotCheckRuleValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<SpotCheckRuleModel>> query(QuerySpotCheckRuleModel querySpotCheckRuleModel, Pageable pageable) {
        return new Result<>(spotCheckRuleService.getPage(querySpotCheckRuleModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<SpotCheckRuleModel> getInfo(@PathVariable Long id) {
        return new Result<>(spotCheckRuleService.getById(id));
    }

    @PostMapping
    public Result<SpotCheckRuleModel> create(@Valid @RequestBody SpotCheckRuleModel spotCheckRuleModel) {
        spotCheckRuleService.add(spotCheckRuleModel);
        return new Result<>(spotCheckRuleService.getById(spotCheckRuleModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<SpotCheckRuleModel> update(@Valid @RequestBody SpotCheckRuleModel spotCheckRuleModel) {
        spotCheckRuleService.update(spotCheckRuleModel);
        return new Result<>(spotCheckRuleService.getById(spotCheckRuleModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        spotCheckRuleService.remove(id);
        return Result.SUCCESS;
    }
}
