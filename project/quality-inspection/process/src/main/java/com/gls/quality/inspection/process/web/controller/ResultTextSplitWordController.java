package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ResultTextSplitWordModel;
import com.gls.quality.inspection.process.web.model.query.QueryResultTextSplitWordModel;
import com.gls.quality.inspection.process.web.service.ResultTextSplitWordService;
import com.gls.quality.inspection.process.web.validator.ResultTextSplitWordValidator;
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
@RequestMapping("/resultTextSplitWord")
public class ResultTextSplitWordController {
    @Resource
    private ResultTextSplitWordService resultTextSplitWordService;
    @Resource
    private ResultTextSplitWordValidator resultTextSplitWordValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(resultTextSplitWordValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ResultTextSplitWordModel>> query(QueryResultTextSplitWordModel queryResultTextSplitWordModel, Pageable pageable) {
        return new Result<>(resultTextSplitWordService.getPage(queryResultTextSplitWordModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ResultTextSplitWordModel> getInfo(@PathVariable Long id) {
        return new Result<>(resultTextSplitWordService.getById(id));
    }

    @PostMapping
    public Result<ResultTextSplitWordModel> create(@Valid @RequestBody ResultTextSplitWordModel resultTextSplitWordModel) {
        resultTextSplitWordService.add(resultTextSplitWordModel);
        return new Result<>(resultTextSplitWordService.getById(resultTextSplitWordModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ResultTextSplitWordModel> update(@Valid @RequestBody ResultTextSplitWordModel resultTextSplitWordModel) {
        resultTextSplitWordService.update(resultTextSplitWordModel);
        return new Result<>(resultTextSplitWordService.getById(resultTextSplitWordModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        resultTextSplitWordService.remove(id);
        return Result.SUCCESS;
    }
}
