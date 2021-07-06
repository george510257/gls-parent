package com.gls.quality.inspection.process.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.framework.api.model.BaseModel;
import com.gls.framework.api.result.Result;
import com.gls.quality.inspection.process.web.model.QingyunModel;
import com.gls.quality.inspection.process.web.model.query.QueryQingyunModel;
import com.gls.quality.inspection.process.web.service.QingyunService;
import com.gls.quality.inspection.process.web.validator.QingyunValidator;
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
@RequestMapping("/qingyun")
public class QingyunController {
    @Resource
    private QingyunService qingyunService;
    @Resource
    private QingyunValidator qingyunValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(qingyunValidator);
    }

    @GetMapping
    @JsonView(BaseModel.SimpleView.class)
    public Result<Page<QingyunModel>> query(QueryQingyunModel queryQingyunModel, Pageable pageable) {
        return new Result<>(qingyunService.getPage(queryQingyunModel, pageable));
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(BaseModel.DetailView.class)
    public Result<QingyunModel> getInfo(@PathVariable Long id) {
        return new Result<>(qingyunService.getById(id));
    }

    @PostMapping
    public Result<QingyunModel> create(@Valid @RequestBody QingyunModel qingyunModel) {
        qingyunService.add(qingyunModel);
        return new Result<>(qingyunService.getById(qingyunModel.getId()));
    }

    @PutMapping("/{id:\\d+}")
    public Result<QingyunModel> update(@Valid @RequestBody QingyunModel qingyunModel) {
        qingyunService.update(qingyunModel);
        return new Result<>(qingyunService.getById(qingyunModel.getId()));
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<String> delete(@PathVariable Long id) {
        qingyunService.remove(id);
        return Result.SUCCESS;
    }
}
