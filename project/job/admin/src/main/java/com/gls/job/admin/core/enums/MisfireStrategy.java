package com.gls.job.admin.core.enums;

import com.gls.job.admin.core.util.I18nUtil;
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
    DO_NOTHING(I18nUtil.getString("misfire_strategy_do_nothing")),

    /**
     * fire once now
     */
    FIRE_ONCE_NOW(I18nUtil.getString("misfire_strategy_fire_once_now"));

    private final String title;

}
