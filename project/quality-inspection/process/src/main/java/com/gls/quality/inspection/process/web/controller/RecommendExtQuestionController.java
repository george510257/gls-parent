package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.RecommendExtQuestionModel;
import com.gls.quality.inspection.process.web.model.query.QueryRecommendExtQuestionModel;
import com.gls.quality.inspection.process.web.service.RecommendExtQuestionService;
import com.gls.quality.inspection.process.web.validator.RecommendExtQuestionValidator;
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
@RequestMapping("/recommendExtQuestion")
public class RecommendExtQuestionController {
    @Resource
    private RecommendExtQuestionService recommendExtQuestionService;
    @Resource
    private RecommendExtQuestionValidator recommendExtQuestionValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(recommendExtQuestionValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<RecommendExtQuestionModel>> query(QueryRecommendExtQuestionModel queryRecommendExtQuestionModel, Pageable pageable) {
        return new Result<>(recommendExtQuestionService.getPage(queryRecommendExtQuestionModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<RecommendExtQuestionModel> getInfo(@PathVariable Long id) {
        return new Result<>(recommendExtQuestionService.getById(id));
    }

    @PostMapping
    public Result<RecommendExtQuestionModel> create(@Valid @RequestBody RecommendExtQuestionModel recommendExtQuestionModel) {
        recommendExtQuestionService.add(recommendExtQuestionModel);
        return new Result<>(recommendExtQuestionService.getById(recommendExtQuestionModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<RecommendExtQuestionModel> update(@Valid @RequestBody RecommendExtQuestionModel recommendExtQuestionModel) {
        recommendExtQuestionService.update(recommendExtQuestionModel);
        return new Result<>(recommendExtQuestionService.getById(recommendExtQuestionModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        recommendExtQuestionService.remove(id);
        return Result.SUCCESS;
    }
}
