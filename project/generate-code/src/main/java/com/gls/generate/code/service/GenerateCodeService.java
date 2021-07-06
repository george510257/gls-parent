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
                "com.gls.quality.inspection.process.web",
                "E:\\code\\OpenSource\\Github\\gls-parent\\project\\quality-inspection\\process\\src\\main\\java\\com\\gls\\quality\\inspection\\process\\web");
    }
}
