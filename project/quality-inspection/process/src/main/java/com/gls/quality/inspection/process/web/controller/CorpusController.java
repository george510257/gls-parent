package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.CorpusModel;
import com.gls.quality.inspection.process.web.model.query.QueryCorpusModel;
import com.gls.quality.inspection.process.web.service.CorpusService;
import com.gls.quality.inspection.process.web.validator.CorpusValidator;
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
@RequestMapping("/corpus")
public class CorpusController {
    @Resource
    private CorpusService corpusService;
    @Resource
    private CorpusValidator corpusValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(corpusValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<CorpusModel>> query(QueryCorpusModel queryCorpusModel, Pageable pageable) {
        return new Result<>(corpusService.getPage(queryCorpusModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<CorpusModel> getInfo(@PathVariable Long id) {
        return new Result<>(corpusService.getById(id));
    }

    @PostMapping
    public Result<CorpusModel> create(@Valid @RequestBody CorpusModel corpusModel) {
        corpusService.add(corpusModel);
        return new Result<>(corpusService.getById(corpusModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<CorpusModel> update(@Valid @RequestBody CorpusModel corpusModel) {
        corpusService.update(corpusModel);
        return new Result<>(corpusService.getById(corpusModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        corpusService.remove(id);
        return Result.SUCCESS;
    }
}
