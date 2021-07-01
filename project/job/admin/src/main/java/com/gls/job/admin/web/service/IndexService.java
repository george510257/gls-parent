package com.gls.job.admin.web.service;

import java.util.Date;
import java.util.Map;

/**
 * @author george
 */
public interface IndexService {
    /**
     * get Dashboard Info
     *
     * @return
     */
    Map<String, Object> getDashboardInfo();

    /**
     * get Chart Info
     *
     * @param startDate
     * @param endDate
     * @return
     */
    Map<String, Object> getChartInfo(Date startDate, Date endDate);
}
