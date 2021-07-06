package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ScoreItemsModel;
import com.gls.quality.inspection.process.web.model.query.QueryScoreItemsModel;
import com.gls.quality.inspection.process.web.service.ScoreItemsService;
import com.gls.quality.inspection.process.web.validator.ScoreItemsValidator;
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
@RequestMapping("/scoreItems")
public class ScoreItemsController {
    @Resource
    private ScoreItemsService scoreItemsService;
    @Resource
    private ScoreItemsValidator scoreItemsValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(scoreItemsValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ScoreItemsModel>> query(QueryScoreItemsModel queryScoreItemsModel, Pageable pageable) {
        return new Result<>(scoreItemsService.getPage(queryScoreItemsModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ScoreItemsModel> getInfo(@PathVariable Long id) {
        return new Result<>(scoreItemsService.getById(id));
    }

    @PostMapping
    public Result<ScoreItemsModel> create(@Valid @RequestBody ScoreItemsModel scoreItemsModel) {
        scoreItemsService.add(scoreItemsModel);
        return new Result<>(scoreItemsService.getById(scoreItemsModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ScoreItemsModel> update(@Valid @RequestBody ScoreItemsModel scoreItemsModel) {
        scoreItemsService.update(scoreItemsModel);
        return new Result<>(scoreItemsService.getById(scoreItemsModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        scoreItemsService.remove(id);
        return Result.SUCCESS;
    }
}
