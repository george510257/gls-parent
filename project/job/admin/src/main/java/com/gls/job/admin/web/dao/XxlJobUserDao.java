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

    public List<XxlJobUser> pageList(@Param("offset") int offset,
                                     @Param("pagesize") int pagesize,
                                     @Param("username") String username,
                                     @Param("role") int role);

    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("username") String username,
                             @Param("role") int role);

    public XxlJobUser loadByUserName(@Param("username") String username);

    public int save(XxlJobUser glsJobUser);

    public int update(XxlJobUser glsJobUser);

    public int delete(@Param("id") int id);

}
