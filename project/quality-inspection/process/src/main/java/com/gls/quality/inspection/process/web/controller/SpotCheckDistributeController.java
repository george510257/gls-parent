package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.SpotCheckDistributeModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpotCheckDistributeModel;
import com.gls.quality.inspection.process.web.service.SpotCheckDistributeService;
import com.gls.quality.inspection.process.web.validator.SpotCheckDistributeValidator;
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
@RequestMapping("/spotCheckDistribute")
public class SpotCheckDistributeController {
    @Resource
    private SpotCheckDistributeService spotCheckDistributeService;
    @Resource
    private SpotCheckDistributeValidator spotCheckDistributeValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(spotCheckDistributeValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<SpotCheckDistributeModel>> query(QuerySpotCheckDistributeModel querySpotCheckDistributeModel, Pageable pageable) {
        return new Result<>(spotCheckDistributeService.getPage(querySpotCheckDistributeModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<SpotCheckDistributeModel> getInfo(@PathVariable Long id) {
        return new Result<>(spotCheckDistributeService.getById(id));
    }

    @PostMapping
    public Result<SpotCheckDistributeModel> create(@Valid @RequestBody SpotCheckDistributeModel spotCheckDistributeModel) {
        spotCheckDistributeService.add(spotCheckDistributeModel);
        return new Result<>(spotCheckDistributeService.getById(spotCheckDistributeModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<SpotCheckDistributeModel> update(@Valid @RequestBody SpotCheckDistributeModel spotCheckDistributeModel) {
        spotCheckDistributeService.update(spotCheckDistributeModel);
        return new Result<>(spotCheckDistributeService.getById(spotCheckDistributeModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        spotCheckDistributeService.remove(id);
        return Result.SUCCESS;
    }
}
