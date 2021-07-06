package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ConfigChannelModel;
import com.gls.quality.inspection.process.web.model.query.QueryConfigChannelModel;
import com.gls.quality.inspection.process.web.service.ConfigChannelService;
import com.gls.quality.inspection.process.web.validator.ConfigChannelValidator;
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
@RequestMapping("/configChannel")
public class ConfigChannelController {
    @Resource
    private ConfigChannelService configChannelService;
    @Resource
    private ConfigChannelValidator configChannelValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(configChannelValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ConfigChannelModel>> query(QueryConfigChannelModel queryConfigChannelModel, Pageable pageable) {
        return new Result<>(configChannelService.getPage(queryConfigChannelModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ConfigChannelModel> getInfo(@PathVariable Long id) {
        return new Result<>(configChannelService.getById(id));
    }

    @PostMapping
    public Result<ConfigChannelModel> create(@Valid @RequestBody ConfigChannelModel configChannelModel) {
        configChannelService.add(configChannelModel);
        return new Result<>(configChannelService.getById(configChannelModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ConfigChannelModel> update(@Valid @RequestBody ConfigChannelModel configChannelModel) {
        configChannelService.update(configChannelModel);
        return new Result<>(configChannelService.getById(configChannelModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        configChannelService.remove(id);
        return Result.SUCCESS;
    }
}
