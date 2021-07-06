package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.IntentionAnalysisModel;
import com.gls.quality.inspection.process.web.model.query.QueryIntentionAnalysisModel;
import com.gls.quality.inspection.process.web.service.IntentionAnalysisService;
import com.gls.quality.inspection.process.web.validator.IntentionAnalysisValidator;
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
@RequestMapping("/intentionAnalysis")
public class IntentionAnalysisController {
    @Resource
    private IntentionAnalysisService intentionAnalysisService;
    @Resource
    private IntentionAnalysisValidator intentionAnalysisValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(intentionAnalysisValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<IntentionAnalysisModel>> query(QueryIntentionAnalysisModel queryIntentionAnalysisModel, Pageable pageable) {
        return new Result<>(intentionAnalysisService.getPage(queryIntentionAnalysisModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<IntentionAnalysisModel> getInfo(@PathVariable Long id) {
        return new Result<>(intentionAnalysisService.getById(id));
    }

    @PostMapping
    public Result<IntentionAnalysisModel> create(@Valid @RequestBody IntentionAnalysisModel intentionAnalysisModel) {
        intentionAnalysisService.add(intentionAnalysisModel);
        return new Result<>(intentionAnalysisService.getById(intentionAnalysisModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<IntentionAnalysisModel> update(@Valid @RequestBody IntentionAnalysisModel intentionAnalysisModel) {
        intentionAnalysisService.update(intentionAnalysisModel);
        return new Result<>(intentionAnalysisService.getById(intentionAnalysisModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        intentionAnalysisService.remove(id);
        return Result.SUCCESS;
    }
}
