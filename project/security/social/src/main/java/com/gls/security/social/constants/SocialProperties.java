package com.gls.security.social.constants;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
public class SocialProperties {
    private String authorizationRequestBaseUri;
    private String authorizationResponseBaseUri;
    private String loginPage;
    private String loginProcessingUrl;
}
