package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.DictionaryModel;
import com.gls.quality.inspection.process.web.model.query.QueryDictionaryModel;
import com.gls.quality.inspection.process.web.service.DictionaryService;
import com.gls.quality.inspection.process.web.validator.DictionaryValidator;
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
@RequestMapping("/dictionary")
public class DictionaryController {
    @Resource
    private DictionaryService dictionaryService;
    @Resource
    private DictionaryValidator dictionaryValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(dictionaryValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<DictionaryModel>> query(QueryDictionaryModel queryDictionaryModel, Pageable pageable) {
        return new Result<>(dictionaryService.getPage(queryDictionaryModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<DictionaryModel> getInfo(@PathVariable Long id) {
        return new Result<>(dictionaryService.getById(id));
    }

    @PostMapping
    public Result<DictionaryModel> create(@Valid @RequestBody DictionaryModel dictionaryModel) {
        dictionaryService.add(dictionaryModel);
        return new Result<>(dictionaryService.getById(dictionaryModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<DictionaryModel> update(@Valid @RequestBody DictionaryModel dictionaryModel) {
        dictionaryService.update(dictionaryModel);
        return new Result<>(dictionaryService.getById(dictionaryModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        dictionaryService.remove(id);
        return Result.SUCCESS;
    }
}
