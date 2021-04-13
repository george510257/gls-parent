package com.gls.starter.data.jpa.generator;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author george
 */
@Slf4j
public class SnowflakeIdentifierGenerator implements Configurable, IdentifierGenerator {

    public static final String NAME = "snowflakeIdentifierGenerator";

    public static final String STRATEGY = "com.gls.starter.data.jpa.generator.SnowflakeIdentifierGenerator";

    /**
     * 工作机器ID(0~31)
     */
    public static final String WORKER_ID_NAME = "workerId";

    /**
     * 数据中心ID(0~31)
     */
    public static final String DATA_CENTER_ID_NAME = "dataCenterId";

    private static SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        long id = snowflakeIdWorker.nextId();
        log.info("id:" + id);
        return id;
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        // 打印所有参数信息
        params.keySet().forEach(key -> {
            String value = params.getProperty((String) key);
            log.info(key + ": " + value);
        });
        long workerId = Long.parseLong(params.getProperty(WORKER_ID_NAME));
        long dataCenterId = Long.parseLong(params.getProperty(DATA_CENTER_ID_NAME));
        snowflakeIdWorker = new SnowflakeIdWorker(workerId, dataCenterId);
    }
}
