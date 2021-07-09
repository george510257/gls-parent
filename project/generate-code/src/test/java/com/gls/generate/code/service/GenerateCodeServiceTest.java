package com.gls.generate.code.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateCodeServiceTest {
    @Resource
    private GenerateCodeService generateCodeService;

    @Test
    public void runGenerateCode() {
        try {
            generateCodeService.runGenerateCode(
                    "com.gls.quality.inspection.process.web",
                    "E:\\code\\OpenSource\\Github\\gls-parent\\project\\quality-inspection\\process\\src\\main\\java\\com\\gls\\quality\\inspection\\process\\web"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runGenerateCodeEntity() {
        try {
            generateCodeService.runGenerateCodeEntity(
                    "com.gls.quality.inspection.process.web",
                    "E:\\code\\OpenSource\\Github\\gls-parent\\project\\quality-inspection\\process\\src\\main\\java\\com\\gls\\quality\\inspection\\process\\web",
                    "ai_quality_check"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}