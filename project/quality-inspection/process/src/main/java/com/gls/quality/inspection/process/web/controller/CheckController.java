package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.CheckModel;
import com.gls.quality.inspection.process.web.model.query.QueryCheckModel;
import com.gls.quality.inspection.process.web.service.CheckService;
import com.gls.quality.inspection.process.web.validator.CheckValidator;
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
@RequestMapping("/check")
public class CheckController {
    @Resource
    private CheckService checkService;
    @Resource
    private CheckValidator checkValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(checkValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<CheckModel>> query(QueryCheckModel queryCheckModel, Pageable pageable) {
        return new Result<>(checkService.getPage(queryCheckModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<CheckModel> getInfo(@PathVariable Long id) {
        return new Result<>(checkService.getById(id));
    }

    @PostMapping
    public Result<CheckModel> create(@Valid @RequestBody CheckModel checkModel) {
        checkService.add(checkModel);
        return new Result<>(checkService.getById(checkModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<CheckModel> update(@Valid @RequestBody CheckModel checkModel) {
        checkService.update(checkModel);
        return new Result<>(checkService.getById(checkModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        checkService.remove(id);
        return Result.SUCCESS;
    }
}
