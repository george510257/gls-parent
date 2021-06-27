package com.gls.security.social.constants;

import lombok.Data;

/**
 * @author george
 */
@Data
public class SocialProperties {
    private String authorizationRequestBaseUri;
    private String authorizationResponseBaseUri;
    private String loginPage;
    private String loginProcessingUrl;
}
