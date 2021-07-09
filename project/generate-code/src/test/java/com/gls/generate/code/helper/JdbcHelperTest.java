package com.gls.generate.code.helper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcHelperTest {
    @Resource
    private JdbcHelper jdbcHelper;

    @Test
    public void getTables() {
        List<Map<String, Object>> result = jdbcHelper.getTables("aiad-common-user");
        result.forEach(map -> {
            log.info("===========================");
            map.forEach((key, value) -> {
                log.info("key:{},value:{}", key, value);
            });
        });
    }

    @Test
    public void getTableColumns() {
        List<Map<String, Object>> result = jdbcHelper.getTableColumns("aiad-common-user", "user");
        result.forEach(map -> {
            log.info("===========================");
            map.forEach((key, value) -> {
                log.info("key:{},value:{}", key, value);
            });
        });
    }
}