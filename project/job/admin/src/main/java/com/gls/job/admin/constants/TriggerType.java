package com.gls.job.admin.constants;

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
    MANUAL("手动触发"),
    CRON("Cron触发"),
    RETRY("失败重试触发"),
    PARENT("父任务触发"),
    API("API触发"),
    MISFIRE("调度过期补偿");

    private final String title;

}
