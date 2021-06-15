package com.gls.job.admin.web.dao;

import com.gls.job.admin.web.model.JobRegistry;
import com.gls.job.core.api.model.enums.RegistryType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Mapper
public interface JobRegistryDao {

    List<Integer> findDead(@Param("timeout") int timeout,
                           @Param("nowTime") Date nowTime);

    int removeDead(@Param("ids") List<Integer> ids);

    List<JobRegistry> findAll(@Param("timeout") int timeout,
                              @Param("nowTime") Date nowTime);

    int registryUpdate(@Param("registryGroup") RegistryType registryGroup,
                       @Param("registryKey") String registryKey,
                       @Param("registryValue") String registryValue,
                       @Param("updateTime") Date updateTime);

    int registrySave(@Param("registryGroup") RegistryType registryGroup,
                     @Param("registryKey") String registryKey,
                     @Param("registryValue") String registryValue,
                     @Param("updateTime") Date updateTime);

    int registryDelete(@Param("registryGroup") RegistryType registryGroup,
                       @Param("registryKey") String registryKey,
                       @Param("registryValue") String registryValue);

}
