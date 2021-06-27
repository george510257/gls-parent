package com.gls.job.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuxueli
 * @date 17/5/9
 */
@Getter
@AllArgsConstructor
public enum ExecutorBlockStrategy {
    /**
     *
     */
    SERIAL_EXECUTION("单机串行"),
    /*CONCURRENT_EXECUTION("并行"),*/
    DISCARD_LATER("丢弃后续调度"),
    COVER_EARLY("覆盖之前调度");
    private final String title;
}
