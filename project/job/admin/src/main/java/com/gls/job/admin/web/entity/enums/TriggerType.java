package com.gls.job.admin.web.entity.enums;

import com.gls.job.admin.core.util.I18nUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * trigger type enum
 *
 * @author george 2018-09-16 04:56:41
 */
@Getter
@AllArgsConstructor
public enum TriggerType {

    /**
     *
     */
    MANUAL(I18nUtil.getString("job_conf_trigger_type_manual")),
    CRON(I18nUtil.getString("job_conf_trigger_type_cron")),
    RETRY(I18nUtil.getString("job_conf_trigger_type_retry")),
    PARENT(I18nUtil.getString("job_conf_trigger_type_parent")),
    API(I18nUtil.getString("job_conf_trigger_type_api")),
    MISFIRE(I18nUtil.getString("job_conf_trigger_type_misfire"));

    private final String title;

}
