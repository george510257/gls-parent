package com.gls.job.admin.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuxueli 2020-10-29 21:11:23
 */
@Getter
@AllArgsConstructor
public enum ScheduleType {

    /**
     *
     */
    NONE("none"),

    /**
     * schedule by cron
     */
    CRON("cron"),

    /**
     * schedule by fixed rate (in seconds)
     */
    FIX_RATE("fix_rate"),

    /**
     * schedule by fix delay (in seconds)， after the last time
     */
    /*FIX_DELAY("fix_delay")*/;

    private final String title;

}
