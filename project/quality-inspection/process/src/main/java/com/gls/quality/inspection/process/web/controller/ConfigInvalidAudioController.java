package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ConfigInvalidAudioModel;
import com.gls.quality.inspection.process.web.model.query.QueryConfigInvalidAudioModel;
import com.gls.quality.inspection.process.web.service.ConfigInvalidAudioService;
import com.gls.quality.inspection.process.web.validator.ConfigInvalidAudioValidator;
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
@RequestMapping("/configInvalidAudio")
public class ConfigInvalidAudioController {
    @Resource
    private ConfigInvalidAudioService configInvalidAudioService;
    @Resource
    private ConfigInvalidAudioValidator configInvalidAudioValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(configInvalidAudioValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ConfigInvalidAudioModel>> query(QueryConfigInvalidAudioModel queryConfigInvalidAudioModel, Pageable pageable) {
        return new Result<>(configInvalidAudioService.getPage(queryConfigInvalidAudioModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ConfigInvalidAudioModel> getInfo(@PathVariable Long id) {
        return new Result<>(configInvalidAudioService.getById(id));
    }

    @PostMapping
    public Result<ConfigInvalidAudioModel> create(@Valid @RequestBody ConfigInvalidAudioModel configInvalidAudioModel) {
        configInvalidAudioService.add(configInvalidAudioModel);
        return new Result<>(configInvalidAudioService.getById(configInvalidAudioModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ConfigInvalidAudioModel> update(@Valid @RequestBody ConfigInvalidAudioModel configInvalidAudioModel) {
        configInvalidAudioService.update(configInvalidAudioModel);
        return new Result<>(configInvalidAudioService.getById(configInvalidAudioModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        configInvalidAudioService.remove(id);
        return Result.SUCCESS;
    }
}
