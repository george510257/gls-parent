package com.gls.generate.code.service;

import com.gls.generate.code.helper.GenerateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author george
 */
@Slf4j
@Service
public class GenerateCodeService {
    @Resource
    private GenerateHelper generateHelper;

    @PostConstruct
    public void generateQualityInspection() throws Exception {
        generateHelper.runGenerateCode(
                "com.gls.job.dashboard.web",
                "E:\\code\\OpenSource\\Github\\gls-parent\\project\\job\\dashboard\\src\\main\\java\\com\\gls\\job\\dashboard\\web");
    }
}
