package com.gls.generate.code.helper;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author george
 */
@Component
public class GenerateHelper {
    @Resource
    private Configuration freeMarkerConfiguration;

    public void generate(Map<String, Object> root, String templateName, String saveUrl, String entityName) throws Exception {
        //获取模板
        Template template = freeMarkerConfiguration.getTemplate(templateName);
        //输出文件
        printFile(root, template, saveUrl, entityName);
    }

    private void printFile(Map<String, Object> root, Template template, String filePath, String fileName) throws Exception {
        Writer out = FileUtil.getWriter(new File(filePath, fileName), StandardCharsets.UTF_8, false);
        template.process(root, out);
        out.close();
    }
}
