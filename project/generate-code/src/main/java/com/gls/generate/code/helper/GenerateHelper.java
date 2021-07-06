package com.gls.generate.code.helper;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
@Slf4j
@Component
public class GenerateHelper {
    @Resource
    private Configuration freeMarkerConfiguration;

    private String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        }
    }

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

    public void runGenerateCode(String basePackage, String path) throws Exception {
        List<String> entityFileNames = FileUtil.listFileNames(path + "\\entity");
        log.info("entityFileNames: {}", entityFileNames);
        for (String entityFileName : entityFileNames) {
            String entityName = entityFileName.replaceAll("Entity.java", "");
            Map<String, Object> root = new HashMap<>();
            root.put("basePackageUrl", basePackage);
            root.put("entityName", entityName);
            root.put("entityNameParam", toLowerCaseFirstOne(entityName));
            //Converter
            generate(root, "Converter.ftl", path + "\\converter", entityName + "Converter.java");
            //Model
            generate(root, "Model.ftl", path + "\\model", entityName + "Model.java");
            //QueryModel
            generate(root, "QueryModel.ftl", path + "\\model\\query", "Query" + entityName + "Model.java");
            //Repository
            generate(root, "Repository.ftl", path + "\\repository", entityName + "Repository.java");
            //Service
            generate(root, "Service.ftl", path + "\\service", entityName + "Service.java");
            //ServiceImpl
            generate(root, "ServiceImpl.ftl", path + "\\service\\impl", entityName + "ServiceImpl.java");
            //Validator
            generate(root, "Validator.ftl", path + "\\validator", entityName + "Validator.java");
            //Controller
            generate(root, "Controller.ftl", path + "\\controller", entityName + "Controller.java");
        }
    }
}
