package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ScoreTemplateModel;
import com.gls.quality.inspection.process.web.model.query.QueryScoreTemplateModel;
import com.gls.quality.inspection.process.web.service.ScoreTemplateService;
import com.gls.quality.inspection.process.web.validator.ScoreTemplateValidator;
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
@RequestMapping("/scoreTemplate")
public class ScoreTemplateController {
    @Resource
    private ScoreTemplateService scoreTemplateService;
    @Resource
    private ScoreTemplateValidator scoreTemplateValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(scoreTemplateValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ScoreTemplateModel>> query(QueryScoreTemplateModel queryScoreTemplateModel, Pageable pageable) {
        return new Result<>(scoreTemplateService.getPage(queryScoreTemplateModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ScoreTemplateModel> getInfo(@PathVariable Long id) {
        return new Result<>(scoreTemplateService.getById(id));
    }

    @PostMapping
    public Result<ScoreTemplateModel> create(@Valid @RequestBody ScoreTemplateModel scoreTemplateModel) {
        scoreTemplateService.add(scoreTemplateModel);
        return new Result<>(scoreTemplateService.getById(scoreTemplateModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ScoreTemplateModel> update(@Valid @RequestBody ScoreTemplateModel scoreTemplateModel) {
        scoreTemplateService.update(scoreTemplateModel);
        return new Result<>(scoreTemplateService.getById(scoreTemplateModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        scoreTemplateService.remove(id);
        return Result.SUCCESS;
    }
}
