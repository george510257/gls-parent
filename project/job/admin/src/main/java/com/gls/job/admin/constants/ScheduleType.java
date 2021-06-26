package com.gls.job.admin.constants;

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
    NONE("无"),

    /**
     * schedule by cron
     */
    CRON("CRON"),

    /**
     * schedule by fixed rate (in seconds)
     */
    FIX_RATE("固定速度"),

    /**
     * schedule by fix delay (in seconds)， after the last time
     */
    /*FIX_DELAY("固定延迟")*/;

    private final String title;

}
