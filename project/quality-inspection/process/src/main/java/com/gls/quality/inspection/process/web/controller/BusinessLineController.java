package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.BusinessLineModel;
import com.gls.quality.inspection.process.web.model.query.QueryBusinessLineModel;
import com.gls.quality.inspection.process.web.service.BusinessLineService;
import com.gls.quality.inspection.process.web.validator.BusinessLineValidator;
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
@RequestMapping("/businessLine")
public class BusinessLineController {
    @Resource
    private BusinessLineService businessLineService;
    @Resource
    private BusinessLineValidator businessLineValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(businessLineValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<BusinessLineModel>> query(QueryBusinessLineModel queryBusinessLineModel, Pageable pageable) {
        return new Result<>(businessLineService.getPage(queryBusinessLineModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<BusinessLineModel> getInfo(@PathVariable Long id) {
        return new Result<>(businessLineService.getById(id));
    }

    @PostMapping
    public Result<BusinessLineModel> create(@Valid @RequestBody BusinessLineModel businessLineModel) {
        businessLineService.add(businessLineModel);
        return new Result<>(businessLineService.getById(businessLineModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<BusinessLineModel> update(@Valid @RequestBody BusinessLineModel businessLineModel) {
        businessLineService.update(businessLineModel);
        return new Result<>(businessLineService.getById(businessLineModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        businessLineService.remove(id);
        return Result.SUCCESS;
    }
}
