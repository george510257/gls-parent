package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.SpotCheckModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpotCheckModel;
import com.gls.quality.inspection.process.web.service.SpotCheckService;
import com.gls.quality.inspection.process.web.validator.SpotCheckValidator;
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
@RequestMapping("/spotCheck")
public class SpotCheckController {
    @Resource
    private SpotCheckService spotCheckService;
    @Resource
    private SpotCheckValidator spotCheckValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(spotCheckValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<SpotCheckModel>> query(QuerySpotCheckModel querySpotCheckModel, Pageable pageable) {
        return new Result<>(spotCheckService.getPage(querySpotCheckModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<SpotCheckModel> getInfo(@PathVariable Long id) {
        return new Result<>(spotCheckService.getById(id));
    }

    @PostMapping
    public Result<SpotCheckModel> create(@Valid @RequestBody SpotCheckModel spotCheckModel) {
        spotCheckService.add(spotCheckModel);
        return new Result<>(spotCheckService.getById(spotCheckModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<SpotCheckModel> update(@Valid @RequestBody SpotCheckModel spotCheckModel) {
        spotCheckService.update(spotCheckModel);
        return new Result<>(spotCheckService.getById(spotCheckModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        spotCheckService.remove(id);
        return Result.SUCCESS;
    }
}
