package com.gls.generate.code.helper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
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
        pathJudgeExist(filePath);
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        template.process(root, out);
        out.close();
    }

    private void pathJudgeExist(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
