package com.gls.job.admin.web.dao;

import com.gls.job.admin.web.entity.XxlJobGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by george on 16/9/30.
 */
@Mapper
public interface XxlJobGroupDao {

    List<XxlJobGroup> findAll();

    List<XxlJobGroup> findByAddressType(@Param("addressType") int addressType);

    int save(XxlJobGroup glsJobGroup);

    int update(XxlJobGroup glsJobGroup);

    int remove(@Param("id") int id);

    XxlJobGroup load(@Param("id") int id);

    List<XxlJobGroup> pageList(@Param("offset") int offset,
                               @Param("pagesize") int pagesize,
                               @Param("appname") String appname,
                               @Param("title") String title);

    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize,
                      @Param("appname") String appname,
                      @Param("title") String title);

}
