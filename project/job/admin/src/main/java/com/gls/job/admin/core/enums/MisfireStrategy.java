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
    DO_NOTHING("do_nothing"),

    /**
     * fire once now
     */
    FIRE_ONCE_NOW("fire_once_now");

    private final String title;

}
