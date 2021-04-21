package com.gls.job.admin.web.entity.enums;

import com.gls.job.admin.core.util.I18nUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author george 2020-10-29 21:11:23
 */
@Getter
@AllArgsConstructor
public enum ScheduleType {

    /**
     *
     */
    NONE(I18nUtil.getString("schedule_type_none")),

    /**
     * schedule by cron
     */
    CRON(I18nUtil.getString("schedule_type_cron")),

    /**
     * schedule by fixed rate (in seconds)
     */
    FIX_RATE(I18nUtil.getString("schedule_type_fix_rate")),

    /**
     * schedule by fix delay (in seconds)， after the last time
     */
    /*FIX_DELAY(I18nUtil.getString("schedule_type_fix_delay"))*/;

    private final String title;

}
