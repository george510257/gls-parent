package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.CorpusAudioTranforModel;
import com.gls.quality.inspection.process.web.model.query.QueryCorpusAudioTranforModel;
import com.gls.quality.inspection.process.web.service.CorpusAudioTranforService;
import com.gls.quality.inspection.process.web.validator.CorpusAudioTranforValidator;
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
@RequestMapping("/corpusAudioTranfor")
public class CorpusAudioTranforController {
    @Resource
    private CorpusAudioTranforService corpusAudioTranforService;
    @Resource
    private CorpusAudioTranforValidator corpusAudioTranforValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(corpusAudioTranforValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<CorpusAudioTranforModel>> query(QueryCorpusAudioTranforModel queryCorpusAudioTranforModel, Pageable pageable) {
        return new Result<>(corpusAudioTranforService.getPage(queryCorpusAudioTranforModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<CorpusAudioTranforModel> getInfo(@PathVariable Long id) {
        return new Result<>(corpusAudioTranforService.getById(id));
    }

    @PostMapping
    public Result<CorpusAudioTranforModel> create(@Valid @RequestBody CorpusAudioTranforModel corpusAudioTranforModel) {
        corpusAudioTranforService.add(corpusAudioTranforModel);
        return new Result<>(corpusAudioTranforService.getById(corpusAudioTranforModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<CorpusAudioTranforModel> update(@Valid @RequestBody CorpusAudioTranforModel corpusAudioTranforModel) {
        corpusAudioTranforService.update(corpusAudioTranforModel);
        return new Result<>(corpusAudioTranforService.getById(corpusAudioTranforModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        corpusAudioTranforService.remove(id);
        return Result.SUCCESS;
    }
}
