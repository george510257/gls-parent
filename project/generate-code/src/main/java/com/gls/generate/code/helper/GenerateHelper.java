package com.gls.generate.code.helper;

import cn.hutool.core.io.FileUtil;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.gls.generate.code.model.ColumnModel;
import com.gls.generate.code.model.TableModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author george
 */
@Slf4j
@Component
public class GenerateHelper {
    @Resource
    private Configuration freeMarkerConfiguration;

    public void generate(TableModel root, String templateName, String saveUrl, String entityName) throws Exception {
        //获取模板
        Template template = freeMarkerConfiguration.getTemplate(templateName);
        //输出文件
        printFile(root, template, saveUrl, entityName);
    }

    private void printFile(TableModel root, Template template, String filePath, String fileName) throws Exception {
        Writer out = FileUtil.getWriter(new File(filePath, fileName), StandardCharsets.UTF_8, false);
        template.process(root, out);
        out.close();
    }

    public List<TableModel> getTableModels(String basePackage, String path) throws FileNotFoundException {
        List<TableModel> tableModels = new ArrayList<>();
        List<String> entityFileNames = FileUtil.listFileNames(path + "\\entity");
        for (String entityFileName : entityFileNames) {
            String entityName = entityFileName.replaceAll("Entity.java", "");
            TableModel root = new TableModel();
            root.setBasePackageUrl(basePackage)
                    .setEntityName(entityName)
                    .setEntityNameLower(StringHelper.toLowerCaseFirstOne(entityName))
                    .setColumns(getColumnModels(path + "\\entity\\" + entityFileName));
            tableModels.add(root);
        }
        return tableModels;
    }

    private List<ColumnModel> getColumnModels(String entityFilePath) throws FileNotFoundException {
        List<ColumnModel> columns = new ArrayList<>();
        CompilationUnit compilationUnit = StaticJavaParser.parse(FileUtil.file(entityFilePath));
        compilationUnit.findAll(FieldDeclaration.class).forEach(fieldDeclaration -> {
            fieldDeclaration.getVariables().forEach(variableDeclarator -> {
                ColumnModel column = new ColumnModel();
                String name = variableDeclarator.getNameAsString();
                String type = variableDeclarator.getTypeAsString().replaceAll("Entity", "Model");
                String typeEntityName = type.replaceAll("Model", "")
                        .replaceAll("List", "")
                        .replaceAll("<", "").replaceAll(">", "");
                column.setName(name)
                        .setNameUpper(StringHelper.toUpperCaseFirstOne(name))
                        .setType(type)
                        .setTypeEntityName(typeEntityName)
                        .setTypeEntityNameLower(StringHelper.toLowerCaseFirstOne(typeEntityName));
                columns.add(column);
            });
        });
        return columns;
    }

    public void generateCode(String path, List<TableModel> result) throws Exception {
        for (TableModel root : result) {
            String entityName = root.getEntityName();
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

    public void generateCodeEntity(String path, List<TableModel> result) throws Exception {
        for (TableModel root : result) {
            String entityName = root.getEntityName();
            generate(root, "Entity.ftl", path + "\\Entity", entityName + "Entity.java");
        }
    }
}
