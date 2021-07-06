package com.gls.generate.code.service;

import cn.hutool.core.io.FileUtil;
import com.gls.generate.code.helper.GenerateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
@Slf4j
@Service
public class GenerateCodeService {
    @Resource
    private GenerateHelper generateHelper;

    public void runGenerateCode(String basePackage, String path) throws Exception {
        List<String> entityFileNames = FileUtil.listFileNames(path + "\\entity");
        log.info("entityFileNames: {}", entityFileNames);
        for (String entityFileName : entityFileNames) {
            String entityName = entityFileName.replaceAll("Entity.java", "");
            Map<String, Object> root = new HashMap<>();
            root.put("basePackageUrl", basePackage);
            root.put("entityName", entityName);
            //Converter
            generateHelper.generate(root, "Converter.ftl", path + "\\converter", entityName + "Converter.java");
            //Model
            generateHelper.generate(root, "Model.ftl", path + "\\model", entityName + "Model.java");
            //QueryModel
            generateHelper.generate(root, "QueryModel.ftl", path + "\\model\\query", "Query" + entityName + "Model.java");
            //Repository
            generateHelper.generate(root, "Repository.ftl", path + "\\repository", entityName + "Repository.java");
            //Service
            generateHelper.generate(root, "Service.ftl", path + "\\service", entityName + "Service.java");
            //ServiceImpl
            generateHelper.generate(root, "ServiceImpl.ftl", path + "\\service\\impl", entityName + "ServiceImpl.java");
            //Validator
            generateHelper.generate(root, "Validator.ftl", path + "\\validator", entityName + "Validator.java");
        }
    }

    @PostConstruct
    public void generateQualityInspection() throws Exception {
        runGenerateCode(
                "com.gls.quality.inspection.process.web",
                "E:\\code\\OpenSource\\Github\\gls-parent\\project\\quality-inspection\\process\\src\\main\\java\\com\\gls\\quality\\inspection\\process\\web");
    }
}
