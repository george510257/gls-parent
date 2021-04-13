package com.gls.security.core.constants;

import com.gls.framework.core.constants.FrameworkConstants;

/**
 * @author george
 */
public interface SecurityConstants extends FrameworkConstants {

    String SECURITY_PROPERTIES_PREFIX = BASE_PROPERTIES_PREFIX + ".security";

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_CAPTCHA_URL_PREFIX = "/captcha";
}
