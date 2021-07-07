package com.gls.generate.code.helper;

import cn.hutool.core.io.FileUtil;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

    private String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
    }

    private void generate(Map<String, Object> root, String templateName, String saveUrl, String entityName) throws Exception {
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
            root.put("entityNameLower", toLowerCaseFirstOne(entityName));
            root.put("columns", getEntityColumns(path + "\\entity\\" + entityFileName));
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

    private List<Map<String, String>> getEntityColumns(String entityFilePath) throws IOException {
        List<Map<String, String>> columns = new ArrayList<>();
        CompilationUnit compilationUnit = StaticJavaParser.parse(FileUtil.file(entityFilePath));
        compilationUnit.findAll(FieldDeclaration.class).forEach(fieldDeclaration -> {
            fieldDeclaration.getVariables().forEach(variableDeclarator -> {
                Map<String, String> column = new HashMap<>();
                String name = variableDeclarator.getNameAsString();
                String type = variableDeclarator.getTypeAsString();
                log.info("variableDeclarator: {} {}", name, type);
                column.put("name", name);
                column.put("nameUpper", toUpperCaseFirstOne(name));
                column.put("type", type.replaceAll("Entity", "Model"));
                columns.add(column);
            });
        });
        return columns;
    }
}
