package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.CombinedScoreItemsModel;
import com.gls.quality.inspection.process.web.model.query.QueryCombinedScoreItemsModel;
import com.gls.quality.inspection.process.web.service.CombinedScoreItemsService;
import com.gls.quality.inspection.process.web.validator.CombinedScoreItemsValidator;
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
@RequestMapping("/combinedScoreItems")
public class CombinedScoreItemsController {
    @Resource
    private CombinedScoreItemsService combinedScoreItemsService;
    @Resource
    private CombinedScoreItemsValidator combinedScoreItemsValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(combinedScoreItemsValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<CombinedScoreItemsModel>> query(QueryCombinedScoreItemsModel queryCombinedScoreItemsModel, Pageable pageable) {
        return new Result<>(combinedScoreItemsService.getPage(queryCombinedScoreItemsModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<CombinedScoreItemsModel> getInfo(@PathVariable Long id) {
        return new Result<>(combinedScoreItemsService.getById(id));
    }

    @PostMapping
    public Result<CombinedScoreItemsModel> create(@Valid @RequestBody CombinedScoreItemsModel combinedScoreItemsModel) {
        combinedScoreItemsService.add(combinedScoreItemsModel);
        return new Result<>(combinedScoreItemsService.getById(combinedScoreItemsModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<CombinedScoreItemsModel> update(@Valid @RequestBody CombinedScoreItemsModel combinedScoreItemsModel) {
        combinedScoreItemsService.update(combinedScoreItemsModel);
        return new Result<>(combinedScoreItemsService.getById(combinedScoreItemsModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        combinedScoreItemsService.remove(id);
        return Result.SUCCESS;
    }
}
