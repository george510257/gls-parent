package com.gls.job.admin.web.repository;

import com.gls.job.admin.web.entity.JobRegistryEntity;
import com.gls.job.core.enums.RegistryType;
import com.gls.starter.data.jpa.base.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author xuxueli
 * @date 16/9/30
 */
public interface JobRegistryRepository extends BaseEntityRepository<JobRegistryEntity> {

    /**
     * 根据updateDate获取
     *
     * @param updateDate
     * @return
     */
    List<JobRegistryEntity> findAllByUpdateDateBefore(Timestamp updateDate);

    /**
     * 更新
     *
     * @param updateDate
     * @param registryType
     * @param registryKey
     * @param registryValue
     * @return
     */
    @Query(value = "update JobRegistryEntity jobRegistry " +
            "set jobRegistry.updateDate = :updateDate " +
            "where jobRegistry.registryType = :registryType " +
            "and jobRegistry.registryKey = :registryKey " +
            "and jobRegistry.registryValue = :registryValue")
    void updateRegistry(@Param("updateDate") Timestamp updateDate,
                        @Param("registryType") RegistryType registryType,
                        @Param("registryKey") String registryKey,
                        @Param("registryValue") String registryValue);

    /**
     * 删除
     *
     * @param registryType
     * @param registryKey
     * @param registryValue
     */
    void deleteByRegistryTypeAndRegistryKeyAndRegistryValue(RegistryType registryType, String registryKey, String registryValue);

}
