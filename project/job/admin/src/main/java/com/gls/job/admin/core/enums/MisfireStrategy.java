package com.gls.job.admin.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuxueli 2020-10-29 21:11:23
 */
@Getter
@AllArgsConstructor
public enum MisfireStrategy {

    /**
     * do nothing
     */
    DO_NOTHING("忽略"),

    /**
     * fire once now
     */
    FIRE_ONCE_NOW("立即执行一次");

    private final String title;

}
