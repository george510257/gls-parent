package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.SpotCheckAudioModel;
import com.gls.quality.inspection.process.web.model.query.QuerySpotCheckAudioModel;
import com.gls.quality.inspection.process.web.service.SpotCheckAudioService;
import com.gls.quality.inspection.process.web.validator.SpotCheckAudioValidator;
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
@RequestMapping("/spotCheckAudio")
public class SpotCheckAudioController {
    @Resource
    private SpotCheckAudioService spotCheckAudioService;
    @Resource
    private SpotCheckAudioValidator spotCheckAudioValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(spotCheckAudioValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<SpotCheckAudioModel>> query(QuerySpotCheckAudioModel querySpotCheckAudioModel, Pageable pageable) {
        return new Result<>(spotCheckAudioService.getPage(querySpotCheckAudioModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<SpotCheckAudioModel> getInfo(@PathVariable Long id) {
        return new Result<>(spotCheckAudioService.getById(id));
    }

    @PostMapping
    public Result<SpotCheckAudioModel> create(@Valid @RequestBody SpotCheckAudioModel spotCheckAudioModel) {
        spotCheckAudioService.add(spotCheckAudioModel);
        return new Result<>(spotCheckAudioService.getById(spotCheckAudioModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<SpotCheckAudioModel> update(@Valid @RequestBody SpotCheckAudioModel spotCheckAudioModel) {
        spotCheckAudioService.update(spotCheckAudioModel);
        return new Result<>(spotCheckAudioService.getById(spotCheckAudioModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        spotCheckAudioService.remove(id);
        return Result.SUCCESS;
    }
}
