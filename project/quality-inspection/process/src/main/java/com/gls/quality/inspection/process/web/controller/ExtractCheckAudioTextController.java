package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.ExtractCheckAudioTextModel;
import com.gls.quality.inspection.process.web.model.query.QueryExtractCheckAudioTextModel;
import com.gls.quality.inspection.process.web.service.ExtractCheckAudioTextService;
import com.gls.quality.inspection.process.web.validator.ExtractCheckAudioTextValidator;
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
@RequestMapping("/extractCheckAudioText")
public class ExtractCheckAudioTextController {
    @Resource
    private ExtractCheckAudioTextService extractCheckAudioTextService;
    @Resource
    private ExtractCheckAudioTextValidator extractCheckAudioTextValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(extractCheckAudioTextValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<ExtractCheckAudioTextModel>> query(QueryExtractCheckAudioTextModel queryExtractCheckAudioTextModel, Pageable pageable) {
        return new Result<>(extractCheckAudioTextService.getPage(queryExtractCheckAudioTextModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<ExtractCheckAudioTextModel> getInfo(@PathVariable Long id) {
        return new Result<>(extractCheckAudioTextService.getById(id));
    }

    @PostMapping
    public Result<ExtractCheckAudioTextModel> create(@Valid @RequestBody ExtractCheckAudioTextModel extractCheckAudioTextModel) {
        extractCheckAudioTextService.add(extractCheckAudioTextModel);
        return new Result<>(extractCheckAudioTextService.getById(extractCheckAudioTextModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<ExtractCheckAudioTextModel> update(@Valid @RequestBody ExtractCheckAudioTextModel extractCheckAudioTextModel) {
        extractCheckAudioTextService.update(extractCheckAudioTextModel);
        return new Result<>(extractCheckAudioTextService.getById(extractCheckAudioTextModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        extractCheckAudioTextService.remove(id);
        return Result.SUCCESS;
    }
}
