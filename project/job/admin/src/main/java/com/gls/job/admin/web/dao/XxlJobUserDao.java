package com.gls.job.admin.web.dao;

import com.gls.job.admin.web.entity.XxlJobUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author george 2019-05-04 16:44:59
 */
@Mapper
public interface XxlJobUserDao {

    List<XxlJobUser> pageList(@Param("offset") int offset,
                              @Param("pagesize") int pagesize,
                              @Param("username") String username,
                              @Param("role") int role);

    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize,
                      @Param("username") String username,
                      @Param("role") int role);

    XxlJobUser loadByUserName(@Param("username") String username);

    int save(XxlJobUser glsJobUser);

    int update(XxlJobUser glsJobUser);

    int delete(@Param("id") int id);

}
