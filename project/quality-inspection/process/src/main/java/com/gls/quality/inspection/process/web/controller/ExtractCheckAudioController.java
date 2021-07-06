package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ExtractCheckAudioModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckAudioModel;
import com.gls.quality.inspection.process.web.service.ExtractCheckAudioService;
import com.gls.quality.inspection.process.web.validator.ExtractCheckAudioValidator;
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
@RequestMapping("/extractCheckAudio")
public class ExtractCheckAudioController {
    @Resource
    private ExtractCheckAudioService extractCheckAudioService;
    @Resource
    private ExtractCheckAudioValidator extractCheckAudioValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(extractCheckAudioValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ExtractCheckAudioModel>> query(QueryExtractCheckAudioModel queryExtractCheckAudioModel, Pageable pageable) {
        return new Result<>(extractCheckAudioService.getPage(queryExtractCheckAudioModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ExtractCheckAudioModel> getInfo(@PathVariable Long id) {
        return new Result<>(extractCheckAudioService.getById(id));
    }

    @PostMapping
    public Result<ExtractCheckAudioModel> create(@Valid @RequestBody ExtractCheckAudioModel extractCheckAudioModel) {
        extractCheckAudioService.add(extractCheckAudioModel);
        return new Result<>(extractCheckAudioService.getById(extractCheckAudioModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ExtractCheckAudioModel> update(@Valid @RequestBody ExtractCheckAudioModel extractCheckAudioModel) {
        extractCheckAudioService.update(extractCheckAudioModel);
        return new Result<>(extractCheckAudioService.getById(extractCheckAudioModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        extractCheckAudioService.remove(id);
        return Result.SUCCESS;
    }
}
