package com.gls.job.core.api.model.enums;

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
    SERIAL_EXECUTION("Serial execution"),
    /*CONCURRENT_EXECUTION("并行"),*/
    DISCARD_LATER("Discard Later"),
    COVER_EARLY("Cover Early");

    private String title;

    public void setTitle(String title) {
        this.title = title;
    }
}
