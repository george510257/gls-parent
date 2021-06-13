package com.gls.job.admin.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * trigger type enum
 *
 * @author xuxueli 2018-09-16 04:56:41
 */
@Getter
@AllArgsConstructor
public enum TriggerType {

    /**
     *
     */
    MANUAL("manual"),
    CRON("cron"),
    RETRY("retry"),
    PARENT("parent"),
    API("api"),
    MISFIRE("misfire");

    private final String title;

}
