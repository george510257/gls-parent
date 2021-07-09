package com.gls.generate.code.service;

import com.gls.generate.code.helper.GenerateHelper;
import com.gls.generate.code.helper.JdbcHelper;
import com.gls.generate.code.model.TableModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author george
 */
@Slf4j
@Service
public class GenerateCodeService {
    @Resource
    private GenerateHelper generateHelper;
    @Resource
    private JdbcHelper jdbcHelper;

    public void runGenerateCode(String basePackage, String path) throws Exception {
        List<TableModel> tableModels = generateHelper.getTableModels(basePackage, path);
        log.info("tableModels:{}", tableModels);
        generateHelper.generateCode(path, tableModels);
    }

    public void runGenerateCodeEntity(String basePackage, String path, String schema) throws Exception {
        List<TableModel> tableModels = jdbcHelper.getTableModels(basePackage, schema);
        log.info("tableModels:{}", tableModels);
        generateHelper.generateCodeEntity(path, tableModels);
    }
}
