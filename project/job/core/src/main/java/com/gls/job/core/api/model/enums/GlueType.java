package com.gls.job.core.api.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuxueli
 * @date 17/4/26
 */
@Getter
@AllArgsConstructor
public enum GlueType {

    /**
     *
     */
    BEAN("BEAN", false, null, null),
    GLUE_GROOVY("GLUE(Java)", false, null, null),
    GLUE_SHELL("GLUE(Shell)", true, "bash", ".sh"),
    GLUE_PYTHON("GLUE(Python)", true, "python", ".py"),
    GLUE_PHP("GLUE(PHP)", true, "php", ".php"),
    GLUE_NODEJS("GLUE(Nodejs)", true, "node", ".js"),
    GLUE_POWERSHELL("GLUE(PowerShell)", true, "powershell", ".ps1");

    private final String desc;
    private final boolean isScript;
    private final String cmd;
    private final String suffix;

}
