package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ConfigScoreSystemModel;
import com.gls.quality.inspection.process.web.model.query.QueryConfigScoreSystemModel;
import com.gls.quality.inspection.process.web.service.ConfigScoreSystemService;
import com.gls.quality.inspection.process.web.validator.ConfigScoreSystemValidator;
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
@RequestMapping("/configScoreSystem")
public class ConfigScoreSystemController {
    @Resource
    private ConfigScoreSystemService configScoreSystemService;
    @Resource
    private ConfigScoreSystemValidator configScoreSystemValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(configScoreSystemValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ConfigScoreSystemModel>> query(QueryConfigScoreSystemModel queryConfigScoreSystemModel, Pageable pageable) {
        return new Result<>(configScoreSystemService.getPage(queryConfigScoreSystemModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ConfigScoreSystemModel> getInfo(@PathVariable Long id) {
        return new Result<>(configScoreSystemService.getById(id));
    }

    @PostMapping
    public Result<ConfigScoreSystemModel> create(@Valid @RequestBody ConfigScoreSystemModel configScoreSystemModel) {
        configScoreSystemService.add(configScoreSystemModel);
        return new Result<>(configScoreSystemService.getById(configScoreSystemModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ConfigScoreSystemModel> update(@Valid @RequestBody ConfigScoreSystemModel configScoreSystemModel) {
        configScoreSystemService.update(configScoreSystemModel);
        return new Result<>(configScoreSystemService.getById(configScoreSystemModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        configScoreSystemService.remove(id);
        return Result.SUCCESS;
    }
}
